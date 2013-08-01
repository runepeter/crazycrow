package eu.nets.crazycrow.nsa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import eu.nets.crazycrow.nsa.FilePaymentProcessor;
import eu.nets.crazycrow.nsa.PaymentInstructionProcessor;
import eu.nets.crazycrow.nsa.TwitterPaymentProcessor;

@Configuration
@ImportResource("classpath:/spring-camel.xml")
@ComponentScan("eu.nets.crazycrow.nsa.camel")
public class CamelConfiguration {

    @Bean
    public TwitterPaymentProcessor twitterPaymentProcessor() {
        return new TwitterPaymentProcessor();
    }

    @Bean
    public FilePaymentProcessor filePaymentProcessor() {
        return new FilePaymentProcessor();
    }

    @Bean
    public PaymentInstructionProcessor paymentInstructionProcessor() {
        return new PaymentInstructionProcessor();
    }
}
