EXE=testproc
CC=g++
OBJ=main.o rlstring.o rlwthread.o rlcutil.o rlsharedmemory.o rlmailbox.o ReaderCus.o
PTHREAD=pthread
$(EXE): $(OBJ)
	$(CC) -l$(PTHREAD) -o $(EXE) $(OBJ)
main.o: main.cpp
rlstring.o: rlstring.cpp rlstring.h
rlwthread.o: rlwthread.cpp rlwthread.h
rlcutil.o: rlcutil.cpp rlcutil.h
rlsharedmemory.o: rlsharedmemory.cpp rlsharedmemory.h
rlmailbox.o: rlmailbox.cpp rlmailbox.h
ReaderCus.o: ReaderCus.cpp ReaderCus.h
.PHONY: clean
clean:
	rm -f *.o $(EXE)
