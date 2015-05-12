#include <iostream>
#include "rlsharedmemory.h"
#include "rlmailbox.h"
#include "ReaderCus.h"

int main()
{
    ReaderWriter reader_test;
    char buf[8+1]="euxyacg";
    char read_buf[256];
    int cnt_pro = 0;
    int ret = 0;
    /*
    int ret = reader_test.pmbx->write(buf,sizeof(buf));
    printf("euxyacg, erro_num:%d,size:%d\n", ret,sizeof(buf));
    reader_test.pmbx->read(read_buf,sizeof(read_buf));
    printf("%s\n", read_buf);
    */
    reader_test.pmbx->clear();
    reader_test.pshm->writeInt(0,2,0);
    while (cnt_pro < 5)
    {
        if((ret = reader_test.pmbx->read(read_buf,sizeof(read_buf))) > 0) 
        {
            printf("euxyacg,%s\n", read_buf);
            cnt_pro ++;
        }
        reader_test.pmbx->clear();
    }
    ret = reader_test.pshm->readInt(0,2);
    printf("recv 5 proc message,ret:%d\n", ret);
    /*
    rlSharedMemory shm("modbus.shm",8);
    int i = shm.readInt(0,2);
    printf("euxyacg, sharedmemory:%d\n",i);
    i++;
    shm.writeInt(0,2,i);
    */
}
