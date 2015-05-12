/*
 * =====================================================================================
 *
 *       Filename:  main.cc
 *
 *    Description:  main function call ScdColloctor instance
 *
 *        Version:  1.0
 *        Created:  04/20/2014 06:29:42 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#include "scdextractor.hh"
#include "scdhttpclient.hh"
#include "scd.hh"
#include "scdtimer.hh"
#include "rllib/rlmailbox.h"
#include "rllib/rlsharedmemory.h"
#include "rllib/rlwthread.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <vector>
#include <map>

#define NUM_PAR 3

static void Usage()
{
    std::cout << "scd for collect data: scd urlprefix snippet_*\n";
}

int main(int argc, char * argv[])
{   
    //rlMailbox mbx("testproc.mbx");    
    if(argc < NUM_PAR)
    {
        (void) fprintf(stderr, "Can not start process because of lacking parameters\n");
        Usage();
    }
    int pos = 1;
    std::string urlprefix = argv[pos++];
    std::string snippetfn = argv[pos++];
    
    ScdCollector testscd(urlprefix, snippetfn);
    testscd.ScdReadConf();
    testscd.ScdWork();
    /*
    char buf[8+1]="finish!";
    int ret;
    rlsleep(200);
    ret = mbx.write(buf, sizeof(buf));
    */
    return 0;
}
