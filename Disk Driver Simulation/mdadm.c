/* Author:   Ashwin Honnawarkar
   Date:   4/17/24
    */
    
    
    
/***
 *      ______ .___  ___. .______     _______.  ______              ____    __   __  
 *     /      ||   \/   | |   _  \   /       | /      |            |___ \  /_ | /_ | 
 *    |  ,----'|  \  /  | |  |_)  | |   (----`|  ,----'              __) |  | |  | | 
 *    |  |     |  |\/|  | |   ___/   \   \    |  |                  |__ <   | |  | | 
 *    |  `----.|  |  |  | |  |   .----)   |   |  `----.             ___) |  | |  | | 
 *     \______||__|  |__| | _|   |_______/     \______|            |____/   |_|  |_| 
 *                                                                                   
 */


#include <stdio.h>
#include <string.h>
#include <assert.h>

#include "cache.h"
#include "mdadm.h"
#include "util.h"
#include "jbod.h"
#include "net.h"

 int mdadm_mount(void) {
  int result = jbod_client_operation(0, NULL);       // mount has value 0 in the enum so can pass 0
  // int create = cache_create(4096);

  // if (create == -1){
  //   return -1;
  // }

  if (result == 0) {
    return 1;
  }
  
  else {
    return -1;
  }
}

int mdadm_unmount(void) {
  int command = JBOD_UNMOUNT << 14;                 // shift 14 bits to command section of jbod operation
  int result = jbod_client_operation(command, NULL);
  // int destroy = cache_destroy();

  // if (destroy == -1){
  //   return -1;
  // }

  if (result == 0)
  {
    return 1;
  }

  else
  {
    return -1;
  }
}

int mdadm_read(uint32_t addr, uint32_t len, uint8_t *buf) {
  if (!buf){

    if (len == 0){                            // null pointer check
      return 0;
    }
    else {
      return -1;
    }
  }

  if (len > 1024){                      // too large of len
    return -1;
  }

  if (addr > JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*JBOD_NUM_DISKS) {        // check if starting address is out of bounds
    return -1;
  }

  if ((addr + len) > JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*JBOD_NUM_DISKS) {        // check if ending address is out of bounds
    return -1;
  }



  int disk_id = (addr / (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK));        

  int end_disk_id = ((len + addr) / (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK)); 


  int temp_a = disk_id << 28;

  int temp_b = JBOD_SEEK_TO_DISK << 14;

  int command = temp_a | temp_b;

  int result = jbod_client_operation(command, NULL);

  if (result != 0) {
    return -1;
  }

  int disk_starting_address = (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*disk_id);

  int block_id = (addr - disk_starting_address) / JBOD_BLOCK_SIZE;

  int temp_c = block_id << 20;

  int temp_d = JBOD_SEEK_TO_BLOCK << 14;

  int command2 = temp_c | temp_d;

  int result2 = jbod_client_operation(command2, NULL);

  if (result2 != 0) {
    return -1;
  }

  uint8_t block[JBOD_BLOCK_SIZE] = {};

  int command3 = JBOD_READ_BLOCK << 14;
  int x;

  int end_block_id = ((addr + len) - disk_starting_address) / JBOD_BLOCK_SIZE;
  int mid_block_start = addr % JBOD_BLOCK_SIZE;
  uint8_t *buf_start = buf;
  

  if (block_id == end_block_id) {                               // reading within single block

    if (!(cache_enabled() && cache_lookup(disk_id, block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result6 = jbod_client_operation(command3, block);
        if (result6 != 0)
        {
          return -1;
        }
        if (cache_enabled()) {                        // make sure cache enabled before inserting
          cache_insert(disk_id, block_id, block);
        }
        
      }

    memmove(buf_start, block + mid_block_start, len);
    return len;

  }

  if (disk_id == end_disk_id)                                         // only reading one disk
  {

    int starting_block_id = block_id;

    if (mid_block_start != 0)
    {                                                                         // starting read at mid block


      if (!(cache_enabled() && cache_lookup(disk_id, starting_block_id, block) == 1)) // if cache not populated or not in cache, use jbod
      {
        int result7 = jbod_client_operation(command3, block);
        if (result7 != 0)
        {
          return -1;
        }

        if (cache_enabled())
        {                   // check if cache enabled before inserting
          cache_insert(disk_id, starting_block_id, block);
        }
      }

      starting_block_id += 1;

      memmove(buf_start, block + mid_block_start, JBOD_BLOCK_SIZE - mid_block_start);
      buf_start += (JBOD_BLOCK_SIZE - mid_block_start);
    }

    for (x = starting_block_id; x < end_block_id; x++)
    {
      temp_c = x << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      if (!(cache_enabled() && cache_lookup(disk_id, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result4 = jbod_client_operation(command3, block);
        if (result4 != 0)
        {                                         // reading full blocks
          return -1;
        }

        if (cache_enabled()){                       // check if cache enabled before inserting
          cache_insert(disk_id, x, block);
        }
        
      }
      

      memmove(buf_start, block, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
    }

    int partial_byte_count = (addr + len) % JBOD_BLOCK_SIZE;

    if (partial_byte_count != 0)
    {

      temp_c = end_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }
      
      if (!(cache_enabled() && cache_lookup(disk_id, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result5 = jbod_client_operation(command3, block); // checking if portion of block needs to be read and reading that
        if (result5 != 0)
        {
          return -1;
        }

        if (cache_enabled()){                 // check if cache enabled before inserting
          cache_insert(disk_id, x, block);
        }
        
      }

      memmove(buf_start, block, partial_byte_count);
    }
  }

  else {                                                                  // reading multiple disks
    int starting_disk_id = disk_id;

    int starting_block_id = block_id;

    if (mid_block_start != 0)
    {                                                                         // starting read at mid block

    
      if (!(cache_enabled() && cache_lookup(disk_id, starting_block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result7 = jbod_client_operation(command3, block);
        if (result7 != 0)
        {
          return -1;
        }

        if (cache_enabled()){                               // check if cache enabled before inserting
          cache_insert(disk_id, starting_block_id, block);
        }
        
      }

      starting_block_id += 1;

      memmove(buf_start, block + mid_block_start, JBOD_BLOCK_SIZE - mid_block_start);
      buf_start += (JBOD_BLOCK_SIZE - mid_block_start);
    }

    for (x = starting_block_id; x < JBOD_NUM_BLOCKS_PER_DISK; x++)          // reading first disk
    {

      temp_c = x << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }


      if (!(cache_enabled() && cache_lookup(disk_id, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result4 = jbod_client_operation(command3, block);
        if (result4 != 0)
        {                   // reading full blocks
          return -1;
        }

        if (cache_enabled()){                 // check if cache enabled before inserting
          cache_insert(disk_id, x, block);
        }
        
      }

      memmove(buf_start, block, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
    }

    int z;

    for (z = starting_disk_id + 1; z < end_disk_id; z++){                      // reading from starting disk + 1 to second to last disk
      int temp_a = z << 28;

      int temp_b = JBOD_SEEK_TO_DISK << 14;

      int command = temp_a | temp_b;                                        
     
      for (x = 0; x < JBOD_NUM_BLOCKS_PER_DISK; x++)            // reading a full disk
      {
        int temp_c = x << 20;

        int temp_d = JBOD_SEEK_TO_BLOCK << 14;

        int command2 = temp_c | temp_d;

        int result = jbod_client_operation(command, NULL); // seek to disk

        if (result != 0)
        {
          return -1;
        }

        int result2 = jbod_client_operation(command2, NULL); // seek to block

        if (result2 != 0)
        {
          return -1;
        }

        if (!(cache_enabled() && cache_lookup(z, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result4 = jbod_client_operation(command3, block);
        if (result4 != 0)
        {                         // reading full blocks
          return -1;
        }

        if (cache_enabled()){               // check if cache enabled before inserting
          cache_insert(z, x, block);
        }
        
      }

        memmove(buf_start, block, JBOD_BLOCK_SIZE);
        buf_start += JBOD_BLOCK_SIZE;
      }
    }

    int temp_a = end_disk_id << 28;                           // reading last disk

    int temp_b = JBOD_SEEK_TO_DISK << 14;

    int command = temp_a | temp_b;


    int disk_starting_address = (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*end_disk_id);
    int end_block_id = ((addr + len) - disk_starting_address) / JBOD_BLOCK_SIZE;

    for (x = 0; x < end_block_id; x++)                                          
    {
      int temp_c = x << 20;

      int temp_d = JBOD_SEEK_TO_BLOCK << 14;

      int command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL); // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      if (!(cache_enabled() && cache_lookup(end_disk_id, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result4 = jbod_client_operation(command3, block);
        if (result4 != 0)
        {                         // reading full blocks
          return -1;
        }

        if (cache_enabled()){                     // check if cache enabled before inserting
          cache_insert(end_disk_id, x, block);
        }
      }

      memmove(buf_start, block, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
    }

    int partial_byte_count = (addr + len) % JBOD_BLOCK_SIZE;

    if (partial_byte_count != 0)
    {
      int temp_c = end_block_id << 20;

      int temp_d = JBOD_SEEK_TO_BLOCK << 14;

      int command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL); // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      if (!(cache_enabled() && cache_lookup(end_disk_id, x, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result5 = jbod_client_operation(command3, block); // checking if portion of block needs to be read and reading that
        if (result5 != 0)
        {
          return -1;
        }

        if (cache_enabled()){                     // check if cache enabled before inserting
          cache_insert(end_disk_id, x, block);
        }
        
      }

      memmove(buf_start, block, partial_byte_count);
    }

  }

  return len;
}

int mdadm_write(uint32_t addr, uint32_t len, const uint8_t *buf) {
  if (!buf){

    if (len == 0){                            // null pointer check
      return 0;
    }
    else {
      return -1;
    }
  }

  if (len > 1024){                      // too large of len
    return -1;
  }

  if (addr > JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*JBOD_NUM_DISKS) {        // check if starting address is out of bounds
    return -1;
  }

  if ((addr + len) > JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*JBOD_NUM_DISKS) {        // check if ending address is out of bounds
    return -1;
  }

 
  int disk_id = (addr / (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK));        

  int end_disk_id = ((len + addr) / (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK)); 

  int temp_a = disk_id << 28;

  int temp_b = JBOD_SEEK_TO_DISK << 14;

  int command = temp_a | temp_b;

  int result = jbod_client_operation(command, NULL);

  if (result != 0) {
    return -1;
  }

  int disk_starting_address = (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*disk_id);

  int block_id = (addr - disk_starting_address) / JBOD_BLOCK_SIZE;

  int temp_c = block_id << 20;

  int temp_d = JBOD_SEEK_TO_BLOCK << 14;

  int command2 = temp_c | temp_d;

  int result2 = jbod_client_operation(command2, NULL);

  if (result2 != 0) {
    return -1;
  }

  uint8_t block[JBOD_BLOCK_SIZE] = {};


  int write_command = JBOD_WRITE_BLOCK << 14;

  int read_command = JBOD_READ_BLOCK << 14;

  int end_block_id = ((addr + len) - disk_starting_address) / JBOD_BLOCK_SIZE;
  int mid_block_start = addr % JBOD_BLOCK_SIZE;
  const uint8_t *buf_start = buf;

  if (block_id == end_block_id) {                               // writing within single block

    if (!(cache_enabled() && cache_lookup(disk_id, block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result20 = jbod_client_operation(read_command, block); // read
        if (result20 != 0)
        {
          return -1;
        }

        if (cache_enabled()){                       // check if cache enabled before inserting
          cache_insert(disk_id, block_id, block);
        }
        
      }


    memmove(block + mid_block_start, buf_start, len);

    int result = jbod_client_operation(command, NULL);               // seek to disk

    if (result != 0)
    {
      return -1;
    }

    int result2 = jbod_client_operation(command2, NULL);         // seek to block

    if (result2 != 0)
    {
      return -1;
    }
   

    int result6 = jbod_client_operation(write_command, block);  //write
    if (result6 != 0) {                                       
    return -1;
    }

    if (cache_enabled()){
      cache_update(disk_id, block_id, block);       // update cache after writing to block
    }
    
    return len;

  }

  if (disk_id == end_disk_id)                                         // only writing one disk
  {

    int starting_block_id = block_id;

    if (mid_block_start != 0)                     // starting write at mid block
    {
      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      if (!(cache_enabled() && cache_lookup(disk_id, starting_block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result20 = jbod_client_operation(read_command, block); // read
        if (result20 != 0)
        {
          return -1;
        }

        if (cache_enabled()){                                 // check if cache enabled before inserting to cache
          cache_insert(disk_id, starting_block_id, block);
        }
        
      }


      memmove(block + mid_block_start, buf_start, JBOD_BLOCK_SIZE - mid_block_start);
      buf_start += (JBOD_BLOCK_SIZE - mid_block_start);

      result = jbod_client_operation(command, NULL);    // seek to disk

      if (result != 0)
      {
        return -1;
      }

      result2 = jbod_client_operation(command2, NULL);     // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      
      int result7 = jbod_client_operation(write_command, block);  // write
      if (result7 != 0)
      {
        return -1;
      }

      if (cache_enabled())              // make sure cache enabled before updating cache after writing to block
        cache_update(disk_id, starting_block_id, block);

      starting_block_id += 1;
      temp_c = starting_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

    }

    int x;

    for (x = starting_block_id; x < end_block_id; x++)        // writing full blocks
    {
      memmove(block, buf_start, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
      int result4 = jbod_client_operation(write_command, block);
      if (result4 != 0)
      {                                                                       
        return -1;
      }

      if (cache_enabled()){
        cache_update(disk_id, x, block);      // update cache after writing to block

      }
    
    }
    memset(block, 0, JBOD_BLOCK_SIZE);                      // clearing block for next write
    int partial_byte_count = (addr + len) % JBOD_BLOCK_SIZE;

    if (partial_byte_count != 0) // checking if leftover portion of block needs to be written and writing that
    {
      temp_c = end_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }


      if (!(cache_enabled() && cache_lookup(end_disk_id, end_block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result20 = jbod_client_operation(read_command, block); // read
        if (result20 != 0)
        {
          return -1;
        }

        if (cache_enabled()){     // make sure cache is enabled before inserting
          cache_insert(end_disk_id, end_block_id, block);
        }
        
      }


      memmove(block, buf_start, partial_byte_count);

      result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }


      temp_c = end_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      result2 = jbod_client_operation(command2, NULL);   // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      
      int result5 = jbod_client_operation(write_command, block);    //write                 
      if (result5 != 0)
      {
        return -1;
      }

      if (cache_enabled())
        cache_update(end_disk_id, end_block_id, block);       // update cache after writing to block
    }
  }

  else {                                                                  // writing multiple disks
    
    int starting_disk_id = disk_id;

    int starting_block_id = block_id;


    if (mid_block_start != 0)
    {                                                                         // starting write at mid block
      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      
      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }


      if (!(cache_enabled() && cache_lookup(disk_id, starting_block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result20 = jbod_client_operation(read_command, block); // read
        if (result20 != 0)
        {
          return -1;
        }

        if (cache_enabled()){         // make sure cache enabled before inserting to cache
          cache_insert(disk_id, starting_block_id, block);
        }
        
      }


      memmove(block + mid_block_start, buf_start, JBOD_BLOCK_SIZE - mid_block_start);
      buf_start += (JBOD_BLOCK_SIZE - mid_block_start);

      result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      result2 = jbod_client_operation(command2, NULL);   // seek to block

      if (result2 != 0)
      {
        return -1;
      }

      
      int result7 = jbod_client_operation(write_command, block); //write
      if (result7 != 0)
      {
        return -1;
      }

      if (cache_enabled())    // update cache after writing to block
        cache_update(disk_id, starting_block_id, block);      // update entry after write
      

      starting_block_id += 1;
      temp_c = starting_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;
    }

    int x;
    for (x = starting_block_id; x < JBOD_NUM_BLOCKS_PER_DISK; x++)          // writing first disk
    {
      memmove(block, buf_start, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
      int result4 = jbod_client_operation(write_command, block);
      if (result4 != 0)
      {                                                                       // writing full blocks
        return -1;
      }

      if (cache_enabled()) {  // update cache while writing blocks
        cache_update(disk_id, x, block);        //  update cache after write
      }

    }

    int z;

    for (z = starting_disk_id + 1; z < end_disk_id; z++){                      // writing from starting disk + 1 to second to last disk
      int temp_a = z << 28;

      int temp_b = JBOD_SEEK_TO_DISK << 14;

      int command = temp_a | temp_b;                                        

      int result = jbod_client_operation(command, NULL);

      if (result != 0)
      {
        return -1;
      }

      int temp_c = 0 << 20;

      int temp_d = JBOD_SEEK_TO_BLOCK << 14;

      int command2 = temp_c | temp_d;

      int result2 = jbod_client_operation(command2, NULL);

      if (result2 != 0)
      {
        return -1;
      }

      for (x = 0; x < JBOD_NUM_BLOCKS_PER_DISK; x++)            // writing a full disk
      {
        memmove(block, buf_start, JBOD_BLOCK_SIZE);
        buf_start += JBOD_BLOCK_SIZE;
        int result4 = jbod_client_operation(write_command, block);
        if (result4 != 0)
        {                                                       // writing full blocks
          return -1;
        }

        if (cache_enabled())
        {

          cache_update(z, x, block); //  update cache after writing to block

        }
      }
    }

    int temp_a = end_disk_id << 28;                           // writing last disk

    int temp_b = JBOD_SEEK_TO_DISK << 14;

    int command = temp_a | temp_b;

    int result = jbod_client_operation(command, NULL);

    if (result != 0)
    {
      return -1;
    }

    int temp_c = 0 << 20;

    int temp_d = JBOD_SEEK_TO_BLOCK << 14;

    int command2 = temp_c | temp_d;

    int result2 = jbod_client_operation(command2, NULL);

    if (result2 != 0)
    {
      return -1;
    }

    int disk_starting_address = (JBOD_BLOCK_SIZE*JBOD_NUM_BLOCKS_PER_DISK*end_disk_id);
    int end_block_id = ((addr + len) - disk_starting_address) / JBOD_BLOCK_SIZE;

    for (x = 0; x < end_block_id; x++)                                          
    {
      memmove(block, buf_start, JBOD_BLOCK_SIZE);
      buf_start += JBOD_BLOCK_SIZE;
      int result4 = jbod_client_operation(write_command, block);
      if (result4 != 0)
      {                                                                       // writing full blocks
        return -1;
      }

      if (cache_enabled()){
          cache_update(end_disk_id, x, block);   // found entry in cache so update entry after write
        
      }
      
    }

    int partial_byte_count = (addr + len) % JBOD_BLOCK_SIZE;

    if (partial_byte_count != 0)        // checking if portion of block needs to be written and writing that
    {
      memset(block, 0, JBOD_BLOCK_SIZE);

      int result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      temp_c = end_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      int result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }


      if (!(cache_enabled() && cache_lookup(end_disk_id, end_block_id, block) == 1))   // if cache not populated or not in cache, use jbod
      {
        int result20 = jbod_client_operation(read_command, block); // read
        if (result20 != 0)
        {
          return -1;
        }

        if (cache_enabled()){
          cache_insert(end_disk_id, end_block_id, block);     // insert to cache since read from jbod
        } 
        
      }

      memmove(block, buf_start, partial_byte_count);

      result = jbod_client_operation(command, NULL);   // seek to disk

      if (result != 0)
      {
        return -1;
      }

      temp_c = end_block_id << 20;

      temp_d = JBOD_SEEK_TO_BLOCK << 14;

      command2 = temp_c | temp_d;

      result2 = jbod_client_operation(command2, NULL); // seek to block

      if (result2 != 0)
      {
        return -1;
      }


      
      int result5 = jbod_client_operation(write_command, block);         //write          
      if (result5 != 0)
      {
        return -1;
      }

      if (cache_enabled())
        cache_update(end_disk_id, end_block_id, block);   // update cache after writing to block

      
    }

  }

  return len;
}
