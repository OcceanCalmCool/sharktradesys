/*
 * =====================================================================================
 *
 *       Filename:  stkratio.cc
 *
 *    Description:  Calculate stk price and shsz index ratio
 *
 *        Version:  1.0
 *        Created:  05/31/2015 10:12:34 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  occeancalmcool (occ), zhangyi22@baidu.com
 *        Company:  BaiDu
 *
 * =====================================================================================
 */

#include "scdhttpclient.hh"
#include "scdtimer.hh"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <vector>
#include <map>

#define NUM_PAR 2

static void Usage()
{
    std::cout << "scd for collect data: scd urlprefix snippet_*\n";
}

int main(int argc, char * argv[])
{   
    if(argc < NUM_PAR)
    {
        (void) fprintf(stderr, "Can not start process because of lacking parameters\n");
        Usage();
    }
    int pos = 1;
    std::string url = argv[pos++];
    CHttpClient http_client;
    std::string resp;
    http_client.PcGets(url, resp);
    std::cout << "euxyacg, get resp4stkratio: " << resp.c_str() << "\n";
    return 0;
}
