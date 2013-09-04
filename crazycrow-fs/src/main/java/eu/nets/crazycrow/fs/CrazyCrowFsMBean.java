package eu.nets.crazycrow.fs;

import eu.nets.crazycrow.Crow;

public interface CrazyCrowFsMBean extends Crow {

    String getPath();

    void op();

    void fill(long spaceAfterFill);

}
