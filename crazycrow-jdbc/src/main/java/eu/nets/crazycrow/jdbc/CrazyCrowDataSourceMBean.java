package eu.nets.crazycrow.jdbc;

import java.util.concurrent.TimeUnit;

import eu.nets.crazycrow.Crow;

public interface CrazyCrowDataSourceMBean extends Crow {

    void setQueryDelayMs(long delay);

    long getQueryDelayMs();

    void setUpdateDelayMs(long delay);

    long getUpdateDelayMs();

}
