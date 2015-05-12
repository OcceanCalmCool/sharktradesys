#!/bin/bash

start() {
    timestamp=`date '+%Y%m%d%H%M%S'`
    echo "$timestamp" 
    scp work@yf-dan-offlone00.yf01.baidu.com:/home/work/logs/* ./input
    for file in ./input/*
    do
        if test -f $file
        then 
            mv $file $file.$timestamp
        fi
    done
    hadoop fs -rm /zhangyi22/input/*
    hadoop fs -put ./input/* /zhangyi22/input/
    mv ./input/* ./input_bk/
}

case "$1" in
    start)
        shift
        start $*
    ;;
    stop)
        shift
    ;;
    *)
        echo $"Usage: $0 {start|stop}"
esac 
