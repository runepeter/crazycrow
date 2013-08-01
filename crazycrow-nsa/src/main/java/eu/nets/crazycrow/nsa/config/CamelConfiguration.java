package eu.nets.crazycrow.nsa.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/spring-camel.xml")
@ComponentScan("eu.nets.crazycrow.nsa.camel")
public class CamelConfiguration {


}
