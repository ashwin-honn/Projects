#include <stdbool.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <err.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include "net.h"
#include "jbod.h"

/* the client socket descriptor for the connection to the server */
int cli_sd = -1;

/* attempts to read n (len) bytes from fd; returns true on success and false on failure. 
It may need to call the system call "read" multiple times to reach the given size len. 
*/
static bool nread(int fd, int len, uint8_t *buf) {

  if (len == 0){
    return true;
  }
  
  ssize_t total_read = 0;

  while (total_read < len){               // loop to ensure all bytes are read
    ssize_t read_call = read(fd, buf, len - total_read);

    if (read_call == -1)
    {
      return false;
    }

    total_read += read_call;          // increment as bytes are read
    buf += read_call;
  }

  return true;
  
}

/* attempts to write n bytes to fd; returns true on success and false on failure 
It may need to call the system call "write" multiple times to reach the size len.
*/
static bool nwrite(int fd, int len, uint8_t *buf) {
  
  if (len == 0){
    return true;
  }

  ssize_t total_write = 0;

  while (total_write < len){      // loop to ensure all bytes are written
    ssize_t write_call = write(fd, buf, len - total_write);

    if (write_call == -1)
    {
      return false;
    }

    total_write += write_call;      // increment as bytes are written
    buf += write_call;
  }

  return true;


}

/* Through this function call the client attempts to receive a packet from sd 
(i.e., receiving a response from the server.). It happens after the client previously 
forwarded a jbod operation call via a request message to the server.  
It returns true on success and false on failure. 
The values of the parameters (including op, ret, block) will be returned to the caller of this function: 

op - the address to store the jbod "opcode"  
ret - the address to store the return value of the server side calling the corresponding jbod_operation function.
block - holds the received block content if existing (e.g., when the op command is JBOD_READ_BLOCK)

In your implementation, you can read the packet header first (i.e., read HEADER_LEN bytes first), 
and then use the length field in the header to determine whether it is needed to read 
a block of data from the server. You may use the above nread function here.  
*/
static bool recv_packet(int sd, uint32_t *op, uint16_t *ret, uint8_t *block) {

  uint8_t header[HEADER_LEN] = {};

  bool read = nread(sd, HEADER_LEN, header);      // reading packet header

  if (read == false) {
    return false;
  }

  uint16_t len;

  memcpy(&len, header, sizeof(uint16_t));       // extract len from packet

  len = ntohs(len);

  memcpy(op, header + sizeof(uint16_t), sizeof(uint32_t));        // extract op 

  *op = ntohl(*op);

  memcpy(ret, header + sizeof(uint16_t) + sizeof(uint32_t), sizeof(uint16_t));  // extract return code

  *ret = ntohs(*ret);

  if (len > HEADER_LEN){          // see if block needs to be read or not
    read = nread(sd, len - HEADER_LEN, block);
    
    if (read == false){
      return false;
    }
  }

  return true;


}



/* The client attempts to send a jbod request packet to sd (i.e., the server socket here); 
returns true on success and false on failure. 

op - the opcode. 
block- when the command is JBOD_WRITE_BLOCK, the block will contain data to write to the server jbod system;
otherwise it is NULL.

The above information (when applicable) has to be wrapped into a jbod request packet (format specified in readme).
You may call the above nwrite function to do the actual sending.  
*/
static bool send_packet(int sd, uint32_t op, uint8_t *block) {

  uint8_t request[JBOD_BLOCK_SIZE + HEADER_LEN] = {};

  uint16_t len = JBOD_BLOCK_SIZE + HEADER_LEN;

  if (block == NULL){
    len = HEADER_LEN;
  }

  uint16_t len1 = htons(len);     // convert to network order

  memcpy(request, &len1, sizeof(uint16_t));     // put into packet

  op = htonl(op);

  memcpy(request + sizeof(uint16_t), &op, sizeof(uint32_t));  // put op in packet

  // Don't need to populate return code bytes 

  if (len>HEADER_LEN){
    memcpy(request + HEADER_LEN, block, JBOD_BLOCK_SIZE);      // put block in if given
  }

  bool write = nwrite(sd, len, request);      // write the packet

  return write;


}



/* attempts to connect to server and set the global cli_sd variable to the
 * socket; returns true if successful and false if not. 
 * this function will be invoked by tester to connect to the server at given ip and port.
 * you will not call it in mdadm.c
*/
bool jbod_connect(const char *ip, uint16_t port) {
  
  cli_sd = socket(AF_INET, SOCK_STREAM, 0);     // create socket

  if (cli_sd == -1){
    return false;
  }

  struct sockaddr_in cad;

  cad.sin_family = AF_INET;         // assign protocol, port
  cad.sin_port = htons(port);

  if (inet_aton(ip, &cad.sin_addr) == 0)    // assign ip
  {
    return false;
  }

  if (connect(cli_sd, (const struct sockaddr *)&cad, sizeof(cad)) == -1)    // connect socket
  {
    return false;
  }

  return true;
}




/* disconnects from the server and resets cli_sd */
void jbod_disconnect(void) {

  close(cli_sd);                  // close connection
  cli_sd = -1;
}



/* sends the JBOD operation to the server (use the send_packet function) and receives 
(use the recv_packet function) and processes the response. 

The meaning of each parameter is the same as in the original jbod_operation function. 
return: 0 means success, -1 means failure.
*/
int jbod_client_operation(uint32_t op, uint8_t *block) {

  bool send = send_packet(cli_sd, op, block);   // send packet

  if (!send){
    return -1;
  }

  uint16_t ret;

  bool receive = recv_packet(cli_sd, &op, &ret, block);     // receive packet

  if (!receive) {
    return -1;
  }

  if (ret == -1){                   // process return values
    return -1;
  }

  return 0;




}
