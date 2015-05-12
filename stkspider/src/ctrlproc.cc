#include <iostream>
#include "rllib/rlsharedmemory.h"
#include "rllib/rlmailbox.h"
#include "rwCus.hh"

#define CPUNUM 8
#define MAXLEN 256
#define LOGPATH "../log/"
#define DATAPATH "../data/"
#define WORKHOME "/home/work/logs/"



void CleanLogData()
{
   std::string cleanLog = "log.*";
   std::string cmd = "rm -f ";
   std::string cleanUpCmd = cmd + LOGPATH + cleanLog;
   system(cleanUpCmd.c_str());
}

int main(int argc, char* argv[])
{
    CleanLogData();
    ReaderWriter reader_test;
    char read_buf[256];
    int cnt_pro = 0;
    int ret;
    reader_test.pmbx->clear();
    while (cnt_pro < CPUNUM)
    {
        if((ret = reader_test.pmbx->read(read_buf,sizeof(read_buf))) > 0) 
        {
            printf("recv, ret:%d\n", cnt_pro);
            cnt_pro ++;
        }
        else
        {
            cnt_pro ++;
            printf("Read mbx error, ret:%d\n", ret);
        }
        reader_test.pmbx->clear();
    }
    printf("recv all proc message %d\n", CPUNUM);
    //system("ctrlproclog.sh");
    return 0;
}
