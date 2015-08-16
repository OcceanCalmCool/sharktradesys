/*
 * =====================================================================================
 *
 *       Filename:  scd.cc
 *
 *    Description:  Implement scd source file
 *
 *        Version:  1.0
 *        Created:  04/20/2014 05:45:33 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#include "scdextractor.hh"
#include "scd.hh"

std::vector<std::string> g_scd_ret;

ScdCollector::ScdCollector(std::string urlprefix, std::string snippetfile):
                           scd_url_num(0),
                           scd_all_threadfd(NULL),
                           scd_cnt(0),
                           scd_prefix(urlprefix),
                           scd_snippetfile(snippetfile)
{
    /*
     * temperary using system log, later will replace it with log server epool
     */
    openlog("scd_log", LOG_PID|LOG_CONS, LOG_USER);
    scd_all_threadfd = new scdChildId[MAXURLNUMBER];
    if(scd_all_threadfd == NULL)
    {
        syslog(LOG_INFO, "euxyacg, memory locate failed for thread fd\n");
    }
}

ScdCollector::~ScdCollector() 
{
    if(scd_all_threadfd != NULL)
    {
        delete [] scd_all_threadfd;
    }
    closelog();
}

scdStatus ScdCollector::ScdWork() 
{
    for(int i = 0; i < scd_url_num; i++) 
    {
        if(SCD_ERROR == ScdCreateChild(&scd_all_threadfd[i], 
                                       (SCDURLFUNC)&ScdCollector::ScdUrlCallBack, 
                                       (void*)&scd_url_location[i]))
        {
            syslog(LOG_INFO, "euxyacg, generate thread for one url fail\n");
            return SCD_ERROR;
        }
        if(ScdJoinChild(scd_all_threadfd[i]) == SCD_ERROR)
        {
            return SCD_ERROR;
        }
    }
    ScdDataTmpQq(); 
    return SCD_OK;
}


int ScdCollector::ScdIsSh(std::string& stk_ele)
{
    if((stk_ele[0] == '6') || (stk_ele[0] == '5')) return 1;
    if((stk_ele[0] == '0') || (stk_ele[0] == '1')) return 0;
    return 0;
}

int ScdCollector::ScdReadConf()
{
    std::string tmpStkLine;
    std::string filtElems("[],");
    scd_stk_file.open(scd_snippetfile.c_str());
    if(scd_stk_file.fail())
    {
        syslog(LOG_ERR, "Can not open scd config file\n");
        return -1;
    }
    while (!scd_stk_file.eof())
    {
        std::string::size_type pos = 0, prev_pos = 0;
        getline(scd_stk_file, tmpStkLine);
        while((pos = tmpStkLine.find_first_of(filtElems, pos))
                   != std::string::npos)
        {
           std::string tmpSubStr = tmpStkLine.substr(prev_pos, pos - prev_pos); 
           if(ScdIsSh(tmpSubStr))
           {
               scd_url_location[scd_url_num] = scd_prefix + std::string("sh") + tmpSubStr;
           }
           else
           {
               scd_url_location[scd_url_num] = scd_prefix + std::string("sz") + tmpSubStr;
           }
           scd_url_pofix[scd_url_num] =  tmpSubStr;
           prev_pos = ++pos;
           scd_url_num ++;
        }
    }

    return 0;
}

void *ScdCollector::ScdUrlCallBack(std::string* arg)
{
    CHttpClient http_client;
    /*
    std::cout << "call now\n";
    syslog(LOG_INFO, "euxyacg, url:%s\n",
                      arg->c_str());
    */
    std::string resp;
    http_client.PcGets(*arg, resp);
    g_scd_ret.push_back(resp);
    //std::cout << "euxyacg, get resp: " << resp.c_str() << "\n"; 
    return 0;
}

scdStatus ScdCollector::ScdCreateChild(scdChildId* urlfd, SCDURLFUNC func, void* arg)
{
    if( 0 != pthread_create( urlfd, NULL, func, arg ) )
    {
        syslog(LOG_INFO, "euxyacg, create fail\n");
        return SCD_ERROR;
    }
    return SCD_OK;
}

scdStatus ScdCollector::ScdJoinChild(scdChildId urlfd)
{
   if(0 ==  pthread_join(urlfd, NULL))
   {
       return SCD_OK;
   }
   else  
   {
       syslog(LOG_ERR, "euxyacg, pthread_join fail\n"); 
       return SCD_ERROR;
   }
   
   /*
   struct timespec ts;
   if(clock_gettime(CLOCK_REALTIME, &ts) == -1)
   {
       syslog(LOG_ERR, "euxyacg, can not get realtime\n");
       return SCD_ERROR;
   }
   ts.tv_sec += 40;
   if(0 ==  pthread_timedjoin_np(urlfd, NULL, &ts))
   {
       return SCD_OK;
   }
   else  
   {
       syslog(LOG_ERR, "euxyacg, pthread_timedjoin_np fail\n"); 
       return SCD_ERROR;
   }
   */
}

/*
 * Description:
 * Fetch needed wangyi src html code and pass them to 
 * ScdExtractor class as parameter for finishing parsing
 */
void ScdCollector::ScdDataTmpWangyi(std::string filename)
{
    std::fstream fs(filename.c_str());
    std::stringstream ss;
    ss << fs.rdbuf();
    std::string str = ss.str();
    std::string strReduScpBegin = "委比";
    std::string strReduScpEnd = "内盘";
    std::string strOther = "</em>";
    std::string::size_type scpBegin = 0, scpEnd = 0;
    scpBegin = str.find(strReduScpBegin);
    scpEnd = str.find(strReduScpEnd, scpBegin);
    scpEnd = str.find(strOther, scpEnd);
    if(scpEnd <= scpBegin)
    {
        syslog(LOG_ERR, "euxyacg, fail to fetch whole html data, filename:%s\n", filename.c_str());
        return;
    }
    str = str.substr(scpBegin, scpEnd - scpBegin);
    filename = filename.substr(sizeof("../data/") - 1, filename.size() - sizeof("../data/") + 1); 
    ScdExtractor scd_extractor(str, filename);
    scd_extractor.ScdExReqTmpParser();
    scd_extractor.ScdExPrintNeedData();
    std::cout << scd_extractor.ScdExFetchNeedData().c_str() << '\n';
}

/*
 * Description: 
 * Fetch needed qq src html code and pass them to 
 * ScdExtrator class as parameter for finishing parsing
 */

void ScdCollector::ScdDataTmpQq()
{
    std::vector<std::string>::iterator it = g_scd_ret.begin();
    std::string filtelems("~");
    std::string::size_type pos = 0, prev_pos = 0;
    std::vector<std::string> stkneed; 
    std::vector<std::string> stkret;
    std::string curtime = ScdCalTime();
    std::string needdata;
    for(; it != g_scd_ret.end(); it++)
    {
        std::string tmpRet = *it;
        while((pos = tmpRet.find_first_of(filtelems, pos)) != std::string::npos)
        {
            std::string tmpSubStr = tmpRet.substr(prev_pos, pos - prev_pos);
            //std::cout << "euxyacg, " << tmpSubStr.c_str() << "\n";
            prev_pos = ++pos;
            stkret.push_back(tmpSubStr);
        }
        if(stkret.size() > SECURITYLEN)
        {
            char pv1[MAXLEN], pv2[MAXLEN], pv3[MAXLEN];
            snprintf(pv1, MAXLEN, "%u", (unsigned int)atof(stkret[INNERSCALE].c_str()));
            snprintf(pv2, MAXLEN, "%u", (unsigned int)atof(stkret[OUTTERSCALE].c_str()));
            snprintf(pv3, MAXLEN, "%.2f", atof(stkret[CURPRICE].c_str()));
            needdata = curtime + '\t' + 
                       stkret[2] + '\t' +
                       pv1 + '\t' +
                       pv2 + '\t' +
                       pv3;
            //std::cout << "euxyacg, needdata: " << needdata << "\n";
            //std::cout << "euxyacg, price: " << stkret[CURPRICE].c_str() << "\n";
            stkneed.push_back(needdata);
        }
        else
        {
            std::cout << "euxyacg, " << tmpRet.c_str() << "\n";
        }
        stkret.clear();
        pos = 0;
        prev_pos = 0;
    }
    unsigned int pid = getpid();
    char pv3[MAXLEN];
    snprintf(pv3, MAXLEN, "%u", (unsigned int)pid);
    std::string logpid = std::string(LOGPATH) + "log." + pv3; 
    std::ofstream foutput(logpid.c_str(), std::ios::out | std::ios::app);
    for(it = stkneed.begin(); it != stkneed.end(); it++)
    {
        foutput << (*it).c_str() << '\n';
    }
    foutput.close();
}


std::string ScdCollector::ScdCalTime()
{
    time_t timenew = time(NULL);
    char temp[MAXLEN];
    struct tm result;
    struct tm* plt = localtime_r(&timenew, &result);
    char year[SHORTLEN];
    char mon [SHORTLEN];
    char day[SHORTLEN];
    char hour[SHORTLEN];
    char min[SHORTLEN];
    
    plt->tm_mon  + 1 < 10 ? snprintf(mon, SHORTLEN, "0%u", plt->tm_mon + 1) : snprintf(mon, SHORTLEN, "%u", plt->tm_mon + 1);
    plt->tm_mday < 10 ? snprintf(day, SHORTLEN, "0%u", plt->tm_mday) : snprintf(day, SHORTLEN, "%u", plt->tm_mday);
    plt->tm_hour < 10 ? snprintf(hour, SHORTLEN, "0%u", plt->tm_hour) :snprintf(hour, SHORTLEN, "%u", plt->tm_hour);
    plt->tm_min < 10 ? snprintf(min, SHORTLEN, "0%u", plt->tm_min) : snprintf(min, SHORTLEN, "%u", plt->tm_min);
    
    snprintf(temp, MAXLEN, "%d-%s-%s %s:%s:00",
             plt->tm_year + STARTYEAR,
             mon,
             day,
             hour,
             min);
    std::string ret = temp;
    return ret;
}

void ScdCollector::ScdCleanEnv()
{
    std::string cleanUpJs = "*.js ";
    std::string cleanUpShtml = "*.shtml";
    std::string cmd = "rm -f ";
    std::string cleanUpCmd = cmd + DATAPATH + cleanUpJs + DATAPATH + cleanUpShtml;
    system(cleanUpCmd.c_str());
}

