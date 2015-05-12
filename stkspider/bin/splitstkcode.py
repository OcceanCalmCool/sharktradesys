# -*- coding: utf-8-*-
"""
Create on 6th Nov 2014
@author: zhangyi22<zhangyi22@baidu.com>
@description: spliting the whole stk code file to some snippets, the number of these snippets according
              processes which can be configured
"""

import os
import sys
from os.path import getsize
from caltime import MoniCalTime

STKCODE="../data/stkc.conf"
class GenSnippet:
    def __init__(self):
        self.stkc = STKCODE
    def split2snippet(self):
        filenum = int(os.popen('grep "processor" /proc/cpuinfo | wc -l').read()[0])  
        fpstkc = open(self.stkc, 'r')
        for line in fpstkc.readlines():
            line = line.strip('\r\n')
            stklist = line.split(',')
        index = 0
        print stklist
        stklists = [[] for i in range(filenum)]
        while index < len(stklist):
            stklists[index % filenum].append(stklist[index])
            index = index + 1
        index = 0
        while index < len(stklists):
            tf_snippet = "../data/snippet_" + str(index)
            fpsnippetop = open(tf_snippet, 'a+')
            for item in stklists[index]:
                item = str(item) + ","
                fpsnippetop.write(item)
            fpsnippetop.close()
            index = index + 1
        fpstkc.close()
        
def main():
    gensnippet = GenSnippet()
    gensnippet.split2snippet()

if __name__ == "__main__":
    main()

