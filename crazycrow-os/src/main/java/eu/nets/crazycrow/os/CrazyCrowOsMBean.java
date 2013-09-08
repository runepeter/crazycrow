package eu.nets.crazycrow.os;

import eu.nets.crazycrow.Crow;

public interface CrazyCrowOsMBean extends Crow {

    void fillHeap(long heapSizeAfterFill);

    void clearFill();

    void maxOpenFiles(int pid, int softLimit, int hardLimit) throws Exception;
}
