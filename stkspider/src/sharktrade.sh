#!/bin/bash
CPUINFOFILE=/proc/cpuinfo
CONFILE=../conf/scd.conf
SNIPPETFILEPREFIX=../data/snippet_
SCD=./scd
CTRLSCD=./ctrlproc
start() {
    cpunum=`grep "processor" $CPUINFOFILE | wc -l`
    while read myline
    do
        if [ "$myline" = "[prefix]" ]
        then
            continue
        else
            URLPREFIX=$myline 
        fi
    done < $CONFILE
    #$CTRLSCD &
    for (( i=0; i<$cpunum; i++ )); do
        taskset -c $i $SCD $URLPREFIX $SNIPPETFILEPREFIX$i&
    done
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
