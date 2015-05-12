# -*- coding: utf-8-*-
'''
Create on 4th Aug 2014
@author zhangyi22<zhangyi22@baidu.com>
@Special for calculating latest fetching time
'''
import os
import sys
import time

class MoniCalTime(object):
    def cal_time_postfix(self):
        timestr = time.localtime(time.time())
        month   = str(timestr.tm_mon)
        mday    = str(timestr.tm_mday)
        hour    = str(timestr.tm_hour)
        if(timestr.tm_mon < 10):
            month = '0' + str(timestr.tm_mon)
        if(timestr.tm_mday < 10):
            mday = '0' + str(timestr.tm_mday)
        if(timestr.tm_hour < 10):
            hour = '0' + str(timestr.tm_hour)
        if(timestr.tm_min < 30):
            minute = '00'
        else:
            minute = '30'

        self.log_post_fix = str(timestr.tm_year) + \
                            month + \
                            mday + \
                            hour + \
                            minute

        return self.log_post_fix
