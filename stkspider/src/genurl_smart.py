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

CPUNUM="/proc/cpuinfo"
SMARTQUERY="../data/query"

class GenUrl:
    def __init__(self):
        self.smartq = SMARTQUERY
        self.gdict = {}

    def query2url(self):
        filenum = int(os.popen('grep "processor" /proc/cpuinfo | wc -l').read()[0])  
        sq_size = getsize(SMARTQUERY)
        sq_chksize = sq_size / filenum + 1
        fpsq = open(self.smartq, 'r')
        i = 0
        while i < filenum:
            chunk_s = fpsq.readlines(sq_chksize)

            tf_s = "../data/smarturl_" + \
                 str(i) + \
                 ".txt"
            fpsmartoutput = open(tf_s, 'w')

            for line in chunk_s:
                urlstr = "http://cp01-testing-dan07.cp01.baidu.com:8012/s?ie=utf-8&f=3&tn=baidu&wd=" + \
                         line
                fpsmartoutput.write(urlstr)
                line = line.strip('\n')
            fpsmartoutput.close()
            i = i + 1
        fpsq.close()
            
def main():
    genurl = GenUrl()
    genurl.query2url()

if __name__ == "__main__":
    main()
