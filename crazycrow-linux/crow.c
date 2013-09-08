
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>
#include <sys/time.h>
#include <sys/types.h>
#include <sys/resource.h>

int main(int argc, char** argv) 
{
  pid_t pid;
  int soft, max, retval;
  struct rlimit current_limits, new_limits;
   
  pid = atoi(argv[1]);
  soft = atoi(argv[2]);
  max = atoi(argv[3]);

  getrlimit(RLIMIT_NOFILE, &current_limits);

  new_limits.rlim_cur = soft;
  new_limits.rlim_max = max;

  retval = prlimit(pid, RLIMIT_NOFILE, &new_limits, &current_limits);
  if (retval != 0) {
    puts(strerror(errno));
    return retval;
  }

  puts("success!");
  return 0;
}
