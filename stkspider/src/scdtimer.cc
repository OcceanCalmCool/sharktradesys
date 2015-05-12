/*
 * =====================================================================================
 *
 *       Filename:  scdtimer.cc
 *
 *    Description:  scd timer implemention code for control task
 *
 *        Version:  1.0
 *        Created:  05/11/2014 02:53:04 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */
#include "scdtimer.hh"

ScdTimer::ScdTimer()
{
    Initialize();
}

ScdTimer::ScdTimer(void(*timerproc)(int, siginfo_t *, void *), const int interval)
{
    Initialize();
    ScdSetTimerProc(timerproc);
    ScdSetTimer(interval);
}

void ScdTimer::Initialize()
{
    _scd_action.sa_flags = SA_SIGINFO;
    sigemptyset(&_scd_action.sa_mask);
    _scd_action.sa_sigaction = NULL;

    _scd_it_value.it_interval.tv_sec = 0;
    _scd_it_value.it_interval.tv_usec = 0;
    _scd_it_value.it_value = _scd_it_value.it_interval;
}

int ScdTimer::ScdSetTimerProc(void (*timerproc)(int, siginfo_t *, void *))
{
    _scd_action.sa_sigaction = timerproc;
    return sigaction(SIGALRM,&_scd_action,NULL);
}

int ScdTimer::ScdSetTimer(const int interval)
{
     _scd_it_value.it_interval.tv_sec=interval; 
     _scd_it_value.it_interval.tv_usec=0;
     _scd_it_value.it_value=_scd_it_value.it_interval;
     return setitimer(ITIMER_REAL,&_scd_it_value,NULL);
}

ScdTimer::~ScdTimer()
{
    ScdSetTimer(0);
}

int ScdTimer::ScdIsMorDur(time_t& scd_time)
{
    struct tm* ptm;
    time(&scd_time);
    ptm = gmtime(&scd_time);
    int lh = ScdConvetHour(ptm);

    if(lh > MOR_BEG_STOCK_HOUR && 
       lh < MOR_END_STOCK_HOUR) {
        return 1;
    } else if(lh == MOR_BEG_STOCK_HOUR) {
        if(ptm->tm_min >= MOR_BEG_STOCK_MINUTE) {
            return 1;
        }
    } else if(lh == MOR_END_STOCK_HOUR) {
        if(ptm->tm_min <= MOR_END_STOCK_MINUTE) {
            return 1;
        }
    } else {
        return 0;
    }
}

int ScdTimer::ScdIsAftDur(time_t& scd_time)
{
    struct tm* ptm;
    time(&scd_time);
    ptm = gmtime(&scd_time);
    int lh = ScdConvetHour(ptm);

    if(lh > AFT_BEG_STOCK_HOUR && 
       lh < AFT_END_STOCK_HOUR) {
        return 1;
    } else if(lh == AFT_BEG_STOCK_HOUR) {
        if(ptm->tm_min >= AFT_BEG_STOCK_MINUTE) {
            return 1;
        }
    } else if(lh == AFT_END_STOCK_HOUR) {
        if(ptm->tm_min <= AFT_END_STOCK_MINUTE) {
            return 1;
        }
    } else {
        return 0;
    }
}

int ScdTimer::ScdIsBwtnMorAft(time_t& scd_time)
{
    struct tm* ptm;
    time(&scd_time);
    ptm = gmtime(&scd_time);
    int lh = ScdConvetHour(ptm);
    
    if(lh > MOR_END_STOCK_HOUR && 
       lh < AFT_BEG_STOCK_HOUR) {
        return 1;
    } else if(lh == MOR_END_STOCK_HOUR) {
        if(ptm->tm_min > MOR_END_STOCK_MINUTE) {
            return 1;
        }
    } else {
        return 0;
    }
}

int ScdTimer::ScdIsDuration(time_t& scd_time)
{
    if(ScdIsMorDur(scd_time) || ScdIsAftDur(scd_time)) {
        return 1;
    } else {
        return 0;
    }
}

int ScdTimer::ScdCalSleepTime(time_t& scd_time)
{
    if(ScdIsBwtnMorAft(scd_time)) {
        struct tm* ptm;
        time(&scd_time);
        ptm = gmtime(&scd_time);
        int lh = ScdConvetHour(ptm); 
        return AFT_BEG_STOCK_HOUR * 3600 - (lh * 3600 + ptm->tm_min * 60);
    } else {
        struct tm* ptm;
        time(&scd_time);
        ptm = gmtime(&scd_time);
        int lh = ScdConvetHour(ptm);
        if(lh <  MOR_BEG_STOCK_HOUR) {
            return (MOR_BEG_STOCK_HOUR * 3600 + MOR_BEG_STOCK_MINUTE * 60) -
                   (lh * 3600 + ptm->tm_min * 60);
        } else if(lh == MOR_BEG_STOCK_HOUR) {
            return (MOR_BEG_STOCK_MINUTE * 60 - ptm->tm_min * 60);
        } else if(lh > AFT_END_STOCK_HOUR) {
            return 24 * 3600 - (lh * 3600 + ptm->tm_min * 60) + 
                   (MOR_BEG_STOCK_HOUR * 3600 + MOR_BEG_STOCK_MINUTE * 60);
        }
    }
}




