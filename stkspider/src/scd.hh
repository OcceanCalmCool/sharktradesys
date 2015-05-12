/*
 * =====================================================================================
 *
 *       Filename:  scd.hh
 *
 *    Description:  head file of spider collect data
 *
 *        Version:  1.0
 *        Created:  04/19/2014 04:04:56 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#ifndef __SCD_HH
#define __SCD_HH

#include <iostream>
#include <string>
#include <fstream>
#include <syslog.h>
#include <stdlib.h>
#include <stdio.h>
#include <iomanip>
#include <fstream>
#include <vector>
#include <sys/types.h>
#include <unistd.h>
#include <pthread.h>
#include <sstream>
#include "scdhttpclient.hh"

#define MAXURLNUMBER 3000
#define DATAPATH "../data/"
#define LOGPATH "../log/"
#define MAXJSNUMBERLINE 8 
#define SPECIALURL 2
#define SPECIALFILENAME 5
#define SEGHTMLSRCBEG "五档盘口"
#define SEGHTMLSRCEND "今日主力增减仓:"
#define SECURITYLEN 10
#define INNERSCALE 8
#define OUTTERSCALE 7
#define CURPRICE 3

typedef pthread_t scdChildId;
typedef void *(*SCDURLFUNC)(void *arg);

typedef enum {
    SCD_ERROR = 0,
    SCD_OK
} scdStatus;

class ScdCollector {
public:
    ScdCollector(std::string urlprefix, std::string snippetfile);
    ~ScdCollector();
    scdStatus ScdWork();
    int ScdReadConf();
    std::string::size_type ScdGetUlrNum() { return scd_url_num;}
    static void *ScdUrlCallBack(std::string* arg);
    void ScdDataTmpWangyi(std::string filename);
    void ScdDataTmpQq();
    void ScdCleanEnv();
    std::string ScdCalTime();
    scdStatus ScdCreateChild(scdChildId* urlfd, SCDURLFUNC func, void* ard); 
    scdStatus ScdJoinChild(scdChildId urlfd);

private:
    std::string scd_url_location[MAXURLNUMBER];
    std::string scd_url_pofix[MAXURLNUMBER];
    std::string scd_prefix;
    std::string scd_snippetfile;
    std::ifstream scd_stk_file;
    scdChildId* scd_all_threadfd;
    std::vector<std::string> scd_proc_data;
    int ScdIsSh(std::string& stk_ele);
    int scd_url_num;
    int scd_cnt;

};
#endif


