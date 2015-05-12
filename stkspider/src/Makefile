EXE=scd
EXE2=ctrlproc
CC=g++
LINKFLAGS=      -L/usr/lib64/mysql
LIBS=           -lmysqlclient
SCRIPTS=splitstkcode.py sharktrade.sh caltime.py pushlog.sh
OBJ=main.o scdextractor.o scdtimer.o scd.o scdhttpclient.o mydb.o\
	rllib/rlstring.o \
	rllib/rlcutil.o \
	rllib/rlsharedmemory.o \
	rllib/rlmailbox.o \
	rllib/rlwthread.o
OBJ2=ctrlproc.o rwCus.o mydb.o\
	rllib/rlstring.o \
	rllib/rlwthread.o \
	rllib/rlcutil.o \
	rllib/rlsharedmemory.o \
	rllib/rlmailbox.o
PTHREAD=pthread
all:$(EXE) $(EXE2)
$(EXE): $(OBJ)
	$(CC) -l$(PTHREAD) $(LINKFLAGS) $(LIBS) -lcurl -o $(EXE) $(OBJ)
	cp $(EXE) ../bin/
	cp $(SCRIPTS) ../bin/
$(EXE2): $(OBJ2)
	$(CC) -l$(PTHREAD) $(LINKFLAGS) $(LIBS) -o $(EXE2) $(OBJ2)
	cp $(EXE2) ../bin/
main.o: main.cc
ctrlproc.o:ctrlproc.cc
scd.o: scd.cc scd.hh
scdextractor.o: scdextractor.cc scdextractor.hh
scdtimer.o: scdtimer.cc scdtimer.hh
scdhttpclient.o:scdhttpclient.cc scdhttpclient.hh
rwCus.o:rwCus.cc rwCus.hh
mydb.o:mydb.hh mydb.cc
.PHONY: clean
clean:
	rm -f *.o $(EXE) $(EXE2)
	

