/*
 * =====================================================================================
 *
 *       Filename:  scdextractor.cc
 *
 *    Description:  implemention scd extractor this will provide some api
 *
 *        Version:  1.0
 *        Created:  05/06/2014 10:03:33 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#include <iostream>
#include <stdio.h>
#include "scdextractor.hh"

void ScdExtractor::ScdExSetPools(std::string scdex_buf)
{
    _scdex_buf = scdex_buf;
}

void ScdExtractor::ScdExWeiBi()
{
    std::string strWeibi = "委比";
    std::string preFilt(">");
    std::string posFilt("%<");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strWeibi);
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;
    double weibiKey = atof(_scdex_buf.substr(w1Pos, w2Pos - w1Pos).c_str());
    _scdex_needdata.insert(ScdExKeyValue(strWeibi, weibiKey));
}

void ScdExtractor::ScdExWeiCha()
{
    std::string strWeicha = "委差";
    std::string preFilt(">");
    std::string posFilt("<");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strWeicha);
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;

    std::string strWeichaKey = _scdex_buf.substr(w1Pos, w2Pos - w1Pos); 
    std::string elemFilter = ",";
    std::string::size_type pos = 0;
    pos = strWeichaKey.find_first_of(elemFilter, pos);

    if(pos != std::string::npos)
        strWeichaKey.erase(pos,1);

    double weichaKey = atof(strWeichaKey.c_str());
    _scdex_needdata.insert(ScdExKeyValue(strWeicha, weichaKey));
}

void ScdExtractor::ScdExBigDeals()
{
    ScdExSingleDeal(std::string("卖五"));
    ScdExSingleDeal(std::string("卖四"));
    ScdExSingleDeal(std::string("卖三"));
    ScdExSingleDeal(std::string("卖二"));
    ScdExSingleDeal(std::string("卖一"));

    ScdExSingleDeal(std::string("买一"));
    ScdExSingleDeal(std::string("买二"));
    ScdExSingleDeal(std::string("买三"));
    ScdExSingleDeal(std::string("买四"));
    ScdExSingleDeal(std::string("买五"));
}

void ScdExtractor::ScdExSingleDeal(std::string scdex_onedeal)
{
    std::string strSaleBuy = scdex_onedeal;
    std::string strScndLoc = "class=";
    std::string preFilt(">");
    std::string posFilt("<");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strSaleBuy);
    beginPos = _scdex_buf.find(strScndLoc, beginPos);
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;
    double salebuyKey = atof(_scdex_buf.substr(w1Pos, w2Pos - w1Pos).c_str());
    _scdex_needdata.insert(ScdExKeyValue(strSaleBuy, salebuyKey));
}

void ScdExtractor::ScdExCurPrice()
{
    std::string strCurPrice= "当前价";
    std::string strScndLoc = "class=";
    std::string preFilt(">");
    std::string posFilt("<");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strCurPrice);
    beginPos = _scdex_buf.find(strScndLoc, beginPos);
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;
    
    double curPriceKey = atof(_scdex_buf.substr(w1Pos, w2Pos - w1Pos).c_str());
    _scdex_needdata.insert(ScdExKeyValue(strCurPrice, curPriceKey));
}

int ScdExtractor::ScdExSingleScale(std::string scdex_onescale)
{
    std::string strDealScale = scdex_onescale;
    std::string preFilt(">");
    std::string posFilt("手<");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strDealScale);
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;
    double dealScaleKey = atof(_scdex_buf.substr(w1Pos, w2Pos - w1Pos).c_str());
    _scdex_needdata.insert(ScdExKeyValue(strDealScale, dealScaleKey));
    return dealScaleKey;
}

int ScdExtractor::ScdExSingleScaleQq(std::string scdex_onescale)
{
    std::string strDealScale = scdex_onescale;
    std::string preFilt("<span");
    std::string posFilt("</span");
    std::string::size_type beginPos = 0;
    std::string::size_type w1Pos = 0, w2Pos = 0;
    beginPos = _scdex_buf.find(strDealScale);
    beginPos = _scdex_buf.find(preFilt, beginPos);
    preFilt = ">";
    w1Pos = _scdex_buf.find(preFilt, beginPos);
    w2Pos = _scdex_buf.find(posFilt, w1Pos);
    w1Pos++;
    double dealScaleKey = atof(_scdex_buf.substr(w1Pos, w2Pos - w1Pos).c_str());
    _scdex_needdata.insert(ScdExKeyValue(strDealScale, dealScaleKey));
    return dealScaleKey;
}

void ScdExtractor::ScdExCurDealScale()
{
    double mountOut = ScdExSingleScale(std::string("外盘"));
    double mountIn = ScdExSingleScale(std::string("内盘"));
    double totalMount = mountOut + mountIn;
    _scdex_needdata.insert(ScdExKeyValue(std::string("成交量"), totalMount));
}

void ScdExtractor::ScdExCurDealScaleQq()
{
    double mountOut = ScdExSingleScaleQq(std::string("外盘"));
    double mountIn = ScdExSingleScaleQq(std::string("内盘"));
    double totalMount = mountOut + mountIn;
    _scdex_needdata.insert(ScdExKeyValue(std::string("成交量"), totalMount));
}

void ScdExtractor::ScdExReqTmpParser()
{
    ScdExWeiBi();
    ScdExWeiCha();
    ScdExCurPrice();
    ScdExBigDeals();
    ScdExCurDealScale();
}

void ScdExtractor::ScdExReqTmpParserQq()
{
    ScdExCurDealScaleQq();
}

void ScdExtractor::ScdExPrintNeedData()
{
    std::map<std::string, double>::iterator it = _scdex_needdata.begin();
    while(it != _scdex_needdata.end())
    {
        std::cout << "Key: " << it->first.c_str() << " Value: " << it->second << '\n';
        it++;
    }
}

std::string ScdExtractor::ScdExFetchNeedData()
{
    double inner = _scdex_needdata["内盘"];
    double outer = _scdex_needdata["外盘"];
    double totalmnt = _scdex_needdata["成交量"];
    char pv1[MAXLEN], pv2[MAXLEN], pv3[MAXLEN];
    
    snprintf(pv1, MAXLEN, "%u", (unsigned int)inner);
    snprintf(pv2, MAXLEN, "%u", (unsigned int)outer);
    snprintf(pv3, MAXLEN, "%u", (unsigned int)totalmnt);

    std::string curtime = ScdCalTime();
    std::string needdata = curtime + '\t' + 
                           _scdex_stockkey + '\t' +
                           pv1 + '\t' +
                           pv2;
    return needdata;
}

std::string ScdExtractor::ScdCalTime()
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

