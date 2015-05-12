#include "rllib/rlsharedmemory.h"
#include "rllib/rlmailbox.h"

class ReaderWriter
{
public:
    ReaderWriter();
    ~ReaderWriter(); 
    rlSharedMemory *pshm;
    rlMailbox *pmbx;
};
