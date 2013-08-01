package eu.nets.crazycrow.nsa.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class NsaRouteBuilder extends SpringRouteBuilder {
    @Override
    public void configure() throws Exception {
        from("file://incoming?move=processed&readLock=rename")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                });
    }
}
