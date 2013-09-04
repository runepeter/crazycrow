package eu.nets.crazycrow.fs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CrazyCrowFs implements CrazyCrowFsMBean {

    private final Logger logger = LoggerFactory.getLogger(CrazyCrowFs.class);

    private final File dir;

    private Status status = Status.ENABLED;

    public CrazyCrowFs(File dir) {
        this.dir = dir;
    }

    @Override
    public String getDomain() {
        return "eu.nets.crazycrow";
    }

    @Override
    public String getName() {
        return "FileSystem";
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
        return status.equals(Status.ENABLED);
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
    public String getPath() {
        return dir.getAbsolutePath();
    }

    @Override
    public void fill(long spaceAfterFill) {

        try {

            final FileStore store = Files.getFileStore(dir.toPath());
            long free = store.getUnallocatedSpace();

            long toFill = free - spaceAfterFill;
            if (toFill > 0) {

                long blocks = toFill / 1024;
                long remaining = toFill - (1024 * blocks);

                String filler0Path = new File(dir, "filler0.dat").getAbsolutePath();
                String filler1Path = new File(dir, "filler1.dat").getAbsolutePath();

                Runtime.getRuntime().exec("dd if=/dev/zero of=" + filler0Path + " bs=1024 count=" + blocks);
                Runtime.getRuntime().exec("dd if=/dev/zero of=" + filler1Path + " bs=" + remaining + " count=1");
                logger.info("Filled filesystem ({}), leaving {} bytes free.", store.toString(), spaceAfterFill);

                Executors.newSingleThreadScheduledExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            logger.info("Free space after fill {}.", store.getUnallocatedSpace());
                        } catch (IOException e) {
                        }
                    }
                }, 2, TimeUnit.SECONDS);

            }

        } catch (Throwable e) {
            throw new RuntimeException("Unable to fill filesystem under " + dir + ".", e);
        }
    }

    @Override
    public void op() {

    }
}
