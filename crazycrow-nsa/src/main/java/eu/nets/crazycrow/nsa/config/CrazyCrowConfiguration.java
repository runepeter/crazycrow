package eu.nets.crazycrow.nsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.support.MBeanServerFactoryBean;

import eu.nets.crazycrow.os.CrazyCrowOs;
import eu.nets.crazycrow.spring.CrowRegistrationPostProcessorAdapter;

@Configuration
public class CrazyCrowConfiguration {

    @Bean
    public MBeanServerFactoryBean mBeanServerFactoryBean() {
        MBeanServerFactoryBean factoryBean = new MBeanServerFactoryBean();
        factoryBean.setLocateExistingServerIfPossible(true);
        return factoryBean;
    }

    @Bean
    public CrowRegistrationPostProcessorAdapter crowRegistrationPostProcessorAdapter() {
        return new CrowRegistrationPostProcessorAdapter();
    }

    @Bean
    public CrazyCrowOs crazyCrowOs() {
        return new CrazyCrowOs();
    }
}
