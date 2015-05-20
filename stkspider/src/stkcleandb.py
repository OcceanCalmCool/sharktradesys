# -*- coding: utf-8-*-
import os
import sys
import time
import datetime
from stkdbutil import StkDbUtil

def main():
    db = StkDbUtil()
    dnow = datetime.datetime.now()
    deltatime = datetime.timedelta(days=15)
    dend = dnow - deltatime
    dstart = dend - deltatime
    dendprev = dend.strftime('%Y-%m-%d')
    dendprev = dendprev + ' 15:30:00'
    dstartprev = dstart.strftime('%Y-%m-%d')
    dstartprev = dstartprev + ' 09:00:00'
    sql = "delete from traderec where time >= " + \
          "'" + dstartprev + "'" + \
          " and time <= " + \
          "'" + dendprev + "'"
    db.execute_sql(sql)

if __name__ == '__main__':
   main()
