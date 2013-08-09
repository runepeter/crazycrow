package eu.nets.crazycrow.spring;

import java.util.Hashtable;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import eu.nets.crazycrow.Crow;

public class CrowRegistrationPostProcessorAdapter extends InstantiationAwareBeanPostProcessorAdapter {

    private final Logger logger = LoggerFactory.getLogger(CrowRegistrationPostProcessorAdapter.class);

    @Autowired
    private MBeanServer mBeanServer;

    @Override
    public boolean postProcessAfterInstantiation(final Object bean, final String beanName) throws BeansException {

        if (bean instanceof Crow) {
            register((Crow) bean);
            logger.info("Successfully registered Crow [{}] as an JMX MBean.", beanName);
        }

        return true;
    }

    private void register(Crow crow) {
        try {
            mBeanServer.registerMBean(crow, objectName(crow));
        } catch (Exception e) {
            throw new RuntimeException("Unable to register Crow [" + crow + "].", e);
        }
    }

    private ObjectName objectName(final Crow crow) {

        Hashtable<String, String> map = new Hashtable<String, String>();
        map.put("name", crow.getName());
        map.put("type", crow.getType());

        try {
            return new ObjectName(crow.getDomain(), map);
        } catch (MalformedObjectNameException e) {
            throw new RuntimeException("Unable to create ObjectName for Crow [" + crow + "].", e);
        }
    }
}
