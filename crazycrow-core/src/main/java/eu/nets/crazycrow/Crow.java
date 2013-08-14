package eu.nets.crazycrow;

public interface Crow {

    public static enum Status {
        ENABLED, DISABLED
    }

    String getDomain();

    String getName();

    String getType();

    Status getStatus();

    boolean isEnabled();

    boolean enable();

    boolean disable();

}
