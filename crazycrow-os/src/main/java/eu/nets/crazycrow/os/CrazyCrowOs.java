package eu.nets.crazycrow.os;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrazyCrowOs implements CrazyCrowOsMBean {

    private final Logger logger = LoggerFactory.getLogger(CrazyCrowOs.class);

    private Status status = Status.ENABLED;

    private List<byte[]> fill = new ArrayList<byte[]>();

	private String linuxCrowPath = "crow"

    @Override
    public String getLinuxCrowPath() {
    	return linuxCrowPath;
    }
    
    @Override
    public void setLinuxCrowPath(String path) {
		this.linuxCrowPath = path;
    }
    
    @Override
    public String getDomain() {
        return "eu.nets.crazycrow";
    }

    @Override
    public String getName() {
        return "OperatingSystem";
    }

    @Override
    public String getType() {
        return getClass().getSimpleName();
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public boolean isEnabled() {
        return status == Status.ENABLED;
    }

    @Override
    public boolean enable() {
        this.status = Status.ENABLED;
        return true;
    }

    @Override
    public boolean disable() {
        this.status = Status.DISABLED;
        return true;
    }
    
    @Override
    public void maxOpenFiles(int pid, int softLimit, int hardLimit) throws Exception {
    	String command = String.format("%s %d %d %d", linuxCrowPath, pid, softLimit, hardLimit);
    	
    	Process process = Runtime.getRuntime().exec(command);
    }
    
    @Override
    public void fillHeap(final long freeMemoryAfterFill) {

        long max = Runtime.getRuntime().maxMemory();
        logger.info("MAX: " + max);
        long free = Runtime.getRuntime().freeMemory();
        logger.info("Free: " + free);
        long used = Runtime.getRuntime().totalMemory() - free;
        logger.info("Used: " + used);

        if (freeMemoryAfterFill > free) {
            throw new IllegalStateException("Memory cannot be freed to " + freeMemoryAfterFill + ". Function can only fill memory.");
        }

//        long bytesToFill = free - freeMemoryAfterFill;
        long bytesToFill = Runtime.getRuntime().totalMemory() - freeMemoryAfterFill;
        logger.info("Array size: " + bytesToFill);

        long jj = Math.min(Integer.MAX_VALUE - 8, bytesToFill);
        while (jj > 0) {

            bytesToFill = bytesToFill - jj;
            logger.info("2FILL: " + jj + " -> [" + Runtime.getRuntime().freeMemory() + " / " + Runtime.getRuntime().totalMemory() + "]");

            byte[] array = new byte[(int) jj];
            Arrays.fill(array, (byte) 'X');
            fill.add(array);

//            jj = Math.min(Integer.MAX_VALUE - 8, bytesToFill);
            logger.info("Free: " + Runtime.getRuntime().freeMemory());
        }


        logger.info("Free memory before and after memory fill is {} / {}.", free, Runtime.getRuntime().freeMemory());
    }

    @Override
    public void clearFill() {
        fill.clear();
    }

}
