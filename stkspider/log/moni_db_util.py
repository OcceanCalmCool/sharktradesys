# -*- coding: utf-8-*-

'''
Create on May 28, 2014
@author: YiZhang<zhangyi22@baidu.com>
@description Used for Db operation
'''
import sys
import os
import MySQLdb
import ConfigParser
import codecs

class MoniDbUtil(object):
    def __init__(self):
        self.cf = ConfigParser.ConfigParser()
        self.cf.readfp(codecs.open("../conf/stdb.conf", "r", "utf-8-sig"))
        self.name = "DB"
        self.db_host     = self.cf.get(self.name, "host")
        self.db_user     = self.cf.get(self.name, "user")
        self.db_passwd   = self.cf.get(self.name, "passwd")
        self.db_name     = self.cf.get(self.name, "name")
        self.db_port     = self.cf.get(self.name, "port")
        self.db_sock     = self.cf.get(self.name, "sock")

    def print_value(self):
        print self.name
        print self.db_host
        print self.db_user
        print self.db_passwd
        print self.db_name
        print self.db_port

    def execute_sql(self,sql):
        db = MySQLdb.connect(user=self.db_user,\
                             host=self.db_host,\
                             passwd=self.db_passwd,\
                             db=self.db_name,\
                             unix_socket=self.db_sock,\
                             port=int(self.db_port))

        cursor = db.cursor()
        cursor.execute(sql)
        db.commit()
        cursor.close()
        db.close()

'''
def main():
    db = MoniDbUtil()
    db.print_value()
    db.execute_sql("insert into responstime values (0,1);")

if __name__ == "__main__":
    main()
'''
