#include "rwCus.hh"

ReaderWriter::ReaderWriter():pshm(NULL),pmbx(NULL)
{
    pshm = new rlSharedMemory("testproc.shm",8);
    pmbx = new rlMailbox("testproc.mbx");
}

ReaderWriter::~ReaderWriter()
{
    if(pshm != NULL) {
        delete pshm;
        pshm = NULL;
    }

    if(pmbx != NULL) {
        delete pmbx;
        pmbx = NULL;
    }
}

