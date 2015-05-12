/*
 * =====================================================================================
 *
 *       Filename:  scdtimer.hh
 *
 *    Description:  head file of scdtimer
 *
 *        Version:  1.0
 *        Created:  05/11/2014 02:53:39 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  oceancalmcool (occ), yizhang.018@gmail.com
 *        Company:  oak
 *
 * =====================================================================================
 */

#ifndef __SCDTIMER_HH__
#define __SCDTIMER_HH__

#include <iostream>
#include <string>
#include <sys/time.h>
#include <signal.h>
#include <stdlib.h>
#define UTC_DIFF 8

typedef enum {
    MOR_BEG_STOCK_HOUR = 9,
    MOR_BEG_STOCK_MINUTE = 30,
    MOR_END_STOCK_HOUR = 11,
    MOR_END_STOCK_MINUTE = 30
}MORNINGMARK;

typedef enum {
    AFT_BEG_STOCK_HOUR = 13,
    AFT_BEG_STOCK_MINUTE = 0,
    AFT_END_STOCK_HOUR = 15,
    AFT_END_STOCK_MINUTE = 30
}AFTERMARK;


class ScdTimer {
public:
    ScdTimer();
    ScdTimer(void(*timerproc)(int, siginfo_t *, void *), const int interval);
    ~ScdTimer();
    int ScdSetTimerProc(void (*timerproc)(int, siginfo_t *, void *));
    int ScdSetTimer(const int interval);
    int ScdIsDuration(time_t& scd_time);
    int ScdCalSleepTime(time_t& scd_time);
private:
    struct sigaction _scd_action; 
    itimerval _scd_it_value;
    void Initialize();
    int ScdIsMorDur(time_t& scd_time);
    int ScdIsAftDur(time_t& scd_time);
    int ScdIsBwtnMorAft(time_t& scd_time);
    int ScdConvetHour(struct tm* ptm) { return (ptm->tm_hour + UTC_DIFF) % 24;}
};
#endif


