# -*- coding: utf-8-*-

'''
Create on 24th July 2014
@author zhangyi22<zhangyi22@baidu.com>
@for generate url for testing
'''
import os
import sys
from os.path import getsize
from caltime import MoniCalTime 

WISEQUERY="../data/se.wise_query_set.txt"
PCQUERY="../data/se.pc_query_set.txt"
CPUNUM="/proc/cpuinfo"
MERGELOG="../data/se.mergelog.txt"
TARGETDIR="work@cp01-rd-bu-09-rd027.cp01.baidu.com:/home/work/yizhang22/autotest/aladdin/trunk/moniala/output/"

class RecordLog:
    def __init__(self,value_list):
        self.query    = value_list[0]
        self.date     = value_list[1]
        self.all      = value_list[2]
        self.isala    = value_list[3]
        self.pvcnt    = value_list[4]
        self.planid   = value_list[5]
        self.srcid    = value_list[6]
        self.userid   = value_list[7]

class GenUrl:
    def __init__(self):
        self.wisefile = WISEQUERY
        self.pcfile = PCQUERY
        self.mergefile = MERGELOG
        self.gdict = {}
        self.timestamp = MoniCalTime().cal_time_postfix()
        scppc = "scp " + \
                TARGETDIR + \
                "se." + \
                self.timestamp + \
                ".pc_query_set.txt " + \
               PCQUERY
        scpwise = "scp " + \
                  TARGETDIR + \
                  "se." + \
                  self.timestamp + \
                  ".wise_query_set.txt " + \
                  WISEQUERY
        scpdict = "scp " + \
                  TARGETDIR + \
                  "se." + \
                  self.timestamp + \
                  ".mergelog.txt " + \
                  MERGELOG
        print scppc
        print scpwise
        print scpdict
        os.system(scppc)
        os.system(scpwise)
        os.system(scpdict)

    def query2url(self):
        filenum = int(os.popen('grep "processor" /proc/cpuinfo | wc -l').read()[0])  
        wq_size = getsize(WISEQUERY)
        pq_size = getsize(PCQUERY)
        wq_chksize = wq_size / filenum + 1
        pq_chksize = pq_size / filenum + 1
        fpwise = open(self.wisefile, 'r')
        fppc = open(self.pcfile, 'r')
        fpmerge = open(self.mergefile, 'r')
        for line in fpmerge.readlines():
            kd_list = line.split('\t') 
            var = RecordLog(kd_list)
            self.gdict[var.query] = var
        i = 0
        while i < filenum:
            chunk_w = fpwise.readlines(wq_chksize)
            chunk_p = fppc.readlines(pq_chksize)
            tf_w = "../data/wiseurl_" + \
                 str(i) + \
                 ".txt"
            tf_p = "../data/pcurl_" + \
                   str(i) + \
                   ".txt"
            tf_m = "../data/dict_merge_" + \
                   str(i) + \
                   ".txt"
            fpwiseoutput = open(tf_w, 'w')
            fppcoutput = open(tf_p, 'w')
            fpdictoutput = open(tf_m, 'a+')

            for line in chunk_w:
                urlstr = "http://www.baidu.com/s?word=" + \
                         line
                fpwiseoutput.write(urlstr)
                line = line.strip('\n')
                if line in self.gdict:
                    fpdictoutput.write(self.convert2str(line))
            fpwiseoutput.close()

            for line in chunk_p:
                urlstr = "http://www.baidu.com/s?wd=" + \
                         line
                fppcoutput.write(urlstr)
                line = line.strip('\n')
                if line in self.gdict:
                    fpdictoutput.write(self.convert2str(line))
            fppcoutput.close()
            fpdictoutput.close()
            i = i + 1

    def convert2str(self,line):
        if line in self.gdict:
            wstr = line + '\t' \
                   + str(self.gdict[line].date) + '\t' \
                   + str(self.gdict[line].all) + '\t' \
                   + str(self.gdict[line].isala) + '\t' \
                   + str(self.gdict[line].pvcnt) + '\t' \
                   + str(self.gdict[line].planid) + '\t' \
                   + str(self.gdict[line].srcid) + '\t' \
                   + str(self.gdict[line].userid)
        return wstr
            
def main():
    genurl = GenUrl()
    genurl.query2url()

if __name__ == "__main__":
    main()
