/*
 * =====================================================================================
 *
 *       Filename:  scdextractor.hh
 *
 *    Description:  head file of scd extractor
 *
 *        Version:  1.0
 *        Created:  05/06/2014 09:56:27 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#ifndef __SCDEXTRACTOR_HH
#define __SCDEXTRACTOR_HH

#include <iostream>
#include <string>
#include <map>
#include <stdlib.h>

#define MAXLEN 256
#define SHORTLEN 16
#define STARTYEAR 1900

typedef std::pair<std::string, double> ScdExKeyValue;
class ScdExtractor {
public:
    ScdExtractor(){}
    ScdExtractor(std::string scdex_buf){_scdex_buf = scdex_buf;}
    ScdExtractor(std::string scdex_buf, std::string scdex_stockkey)
    {
        _scdex_stockkey = scdex_stockkey;
        _scdex_buf      = scdex_buf;
    }
    ~ScdExtractor() {}
    void ScdExSetPools(std::string scdex_buf);
    void ScdExReqTmpParser();
    void ScdExReqTmpParserQq();
    void ScdExPrintNeedData();
    std::string ScdExFetchNeedData();
    std::string ScdCalTime();
private:
    std::string _scdex_buf;
    std::string _scdex_stockkey;
    std::map<std::string, double> _scdex_needdata;

    void ScdExWeiBi();
    void ScdExWeiCha();

    void ScdExSingleRatio(std::string scdex_oneratio);
    void ScdExBigDeals();
    void ScdExSingleDeal(std::string scdex_onedeal);
    void ScdExCurPrice();
    void ScdExCurDealScale();
    void ScdExCurDealScaleQq();
   
    int ScdExSingleScale(std::string scdex_onescale);
    int ScdExSingleScaleQq(std::string scdex_onescale);
};

#endif


