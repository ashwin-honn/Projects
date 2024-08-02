#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "cache.h"

static cache_entry_t *cache = NULL;
static int cache_size = 0;
static int clock = 0;
static int num_queries = 0;
static int num_hits = 0;

int cache_create(int num_entries) {
  if (cache != NULL) {
    return -1;
  }

  if (num_entries >= 2 && num_entries <= 4096)
  {

    cache = (cache_entry_t*)calloc(num_entries, sizeof(cache_entry_t));      // if valid num entries then create cache
    cache_size = num_entries;
    return 1;
  }

  else {
    return -1;
  }
}

int cache_destroy(void) {
  if (cache == NULL){
    return -1;
  }

  free(cache);
  cache = NULL;           // destroy cache
  cache_size = 0;
  return 1;
}

int cache_lookup(int disk_num, int block_num, uint8_t *buf) {
  if (buf == NULL){
    return -1;
  }

  int x;
  num_queries += 1;

  for (x = 0; x < cache_size; x++ ){

    

    if (cache[x].disk_num == disk_num && cache[x].block_num == block_num && cache[x].valid){    // go through cache and look for match

      
      memcpy(buf, cache[x].block, JBOD_BLOCK_SIZE);         // match found, copy to buf
      num_hits += 1;
      clock += 1;
      cache[x].access_time = clock;
      return 1;
    }
    
  }

  return -1;

}

void cache_update(int disk_num, int block_num, const uint8_t *buf) {
  int x;

  for (x = 0; x < cache_size; x++ ){
    if (cache[x].disk_num == disk_num && cache[x].block_num == block_num && cache[x].valid){     // go through cache and if match found, update contents
      memcpy(cache[x].block, buf, JBOD_BLOCK_SIZE);
      clock += 1;
      cache[x].access_time = clock;
    }
  }
}

int cache_insert(int disk_num, int block_num, const uint8_t *buf) {
  if (buf == NULL) {
    return -1;
  }

  if (!cache_enabled()){
    return -1;
  }

  if (disk_num < 0 || disk_num > 15){
    return -1;
  }

  if (block_num < 0 || block_num > 255) {
    return -1;
  }

  int y;

  for (y = 0; y < cache_size; y++ ){

    if (cache[y].valid)
    {
      if (cache[y].disk_num == disk_num && cache[y].block_num == block_num)
      {                 // go through cache and if entry exists, return -1

        return -1;
      }
    }

    else {
      break;
    }
  }

  if (y >= cache_size){       // cache is full
    int x;
    int min_index  = 0;
    for (x = 0; x < cache_size; x++){
      if (cache[x].access_time < cache[min_index].access_time){         // find least recently used index
        min_index = x;
      }
    }
    
   
    cache[min_index].disk_num = disk_num;           // replace properties of least recently used
    cache[min_index].block_num = block_num;
    memcpy(cache[min_index].block, buf, JBOD_BLOCK_SIZE);
    clock += 1;
    cache[min_index].access_time = clock;
    return 1;

  }

  else {                  // cache not full
    cache[y].valid = true;
    cache[y].disk_num = disk_num;                     // set properties
    cache[y].block_num = block_num;
    memcpy(cache[y].block, buf, JBOD_BLOCK_SIZE);
    clock += 1;
    cache[y].access_time = clock;
    y += 1;
    return 1;

  }

}

bool cache_enabled(void) {
  if (cache_size >= 2){              // see if cache is active
    return true;
  }
  return false;
}

void cache_print_hit_rate(void) {
  fprintf(stderr, "Hit rate: %5.1f%%\n", 100 * (float) num_hits / num_queries);
}
