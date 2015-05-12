#include <iostream>
#include "rlmailbox.h"
#include "rlsharedmemory.h"

int main()
{
    rlMailbox mbx("testproc.mbx");    
    rlSharedMemory shm("testproc.shm",8);
    char buf[8+1]="euxyacg";
    int i = shm.readInt(0,2);
    i++;
    shm.writeInt(0,2,i);
    mbx.write(buf,sizeof(buf));
}
