EXE=scd
EXE2=ctrlproc
EXE3=stkratio
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
OBJ3=stkratio.o scdhttpclient.o
PTHREAD=pthread
all:$(EXE) $(EXE2) $(EXE3)
$(EXE): $(OBJ)
	$(CC) -l$(PTHREAD) $(LINKFLAGS) $(LIBS) -lcurl -o $(EXE) $(OBJ)
	cp $(EXE) ../bin/
	cp $(SCRIPTS) ../bin/
$(EXE2): $(OBJ2)
	$(CC) -l$(PTHREAD) $(LINKFLAGS) $(LIBS) -o $(EXE2) $(OBJ2)
	cp $(EXE2) ../bin/
$(EXE3): $(OBJ3)
	$(CC) -l$(PTHREAD) $(LINKFLAGS) $(LIBS) -lcurl -o $(EXE3) $(OBJ3)
main.o: main.cc
ctrlproc.o:ctrlproc.cc
scd.o: scd.cc scd.hh
scdextractor.o: scdextractor.cc scdextractor.hh
scdtimer.o: scdtimer.cc scdtimer.hh
scdhttpclient.o:scdhttpclient.cc scdhttpclient.hh
rwCus.o:rwCus.cc rwCus.hh
mydb.o:mydb.hh mydb.cc
stkratio.o:stkratio.cc
.PHONY: clean
clean:
	rm -f *.o $(EXE) $(EXE2)
	

