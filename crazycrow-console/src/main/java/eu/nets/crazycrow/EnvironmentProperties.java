package eu.nets.crazycrow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum EnvironmentProperties {
    ContextPath("contextPath", "/"),
    ;

    private final String key;
    private final String value;

    private EnvironmentProperties(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static void load() {

        Logger logger = LoggerFactory.getLogger(EnvironmentProperties.class);

        logger.info("Environment properties:");
        for (EnvironmentProperties prop : values()) {
            System.setProperty(prop.key, prop.value);

            if (prop.key.contains("password")) {
                logger.info("  " + prop.key + ": [*****]");
            } else {
                logger.info("  " + prop.key + ": [" + prop.value + "]");
            }
        }
    }
}
