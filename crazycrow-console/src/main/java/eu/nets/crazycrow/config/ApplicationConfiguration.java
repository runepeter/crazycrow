package eu.nets.crazycrow.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "eu.nets.crazycrow.rest"
})
public class ApplicationConfiguration {
}
