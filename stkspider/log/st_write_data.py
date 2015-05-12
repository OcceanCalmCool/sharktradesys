# -*- coding: utf-8-*-

'''
Create on Apr 21, 2015
@author: YiZhang<zhangyi22@baidu.com>
@description sharktrade write data into database
'''
import sys
import os
import moni_db_util

class StWriteData(object):
    def __init__(self):
        self.db = moni_db_util.MoniDbUtil()

    def write_data_todb(self):
        cat_cmd = "cat ./log* > mergelog"
        os.system(cat_cmd)
        fp = open("./mergelog", 'r')
        line = fp.readline()
        while line:
            ret_list = line.strip().split('\t')
            line = fp.readline()
            sql_st_data = "insert into traderec values " + \
                          "(" + "\"" + ret_list[0] + "\"" + "," + \
                          "\"" + ret_list[1] + "\"" + "," + \
                          "\"" + ret_list[2] + "\"" + "," + \
                          "\"" + ret_list[3] + "\"" + "," + \
                          "\"" + ret_list[4] + "\"" + \
                          ")"
            self.db.execute_sql(sql_st_data)
        
def main():
    stw = StWriteData()
    stw.write_data_todb()

if __name__ == "__main__":
    main()



