package eu.nets.crazycrow.nsa.camel;

import java.util.List;

import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

import eu.nets.crazycrow.nsa.FilePaymentProcessor;
import eu.nets.crazycrow.nsa.PaymentInstructionProcessor;
import eu.nets.crazycrow.nsa.TwitterPaymentProcessor;

@Component
public class NsaRouteBuilder extends SpringRouteBuilder {

    private static final String CONSUMER_KEY = "yyrw6UYUIZM1VaQW1h5u0w";
    private static final String CONSUMER_SECRET = "a8Ep2ioqyxB3OFoubXUegvi8AWOwwqpkEqi0M8Xxi8";
    private static final String ACCESS_TOKEN = "21330633-M9RUuXXLnttLx8wvVKVNFN2X0glrOUqNwcvPt3spk";
    private static final String ACCESS_TOKEN_SECRET = "EukyC6s1qYWaA5QVn9lqkdzUOMHD3W5zkrggSFTiI";

    @Override
    public void configure() throws Exception {
        from("twitter://search?type=polling" +
                "&keywords=#nsa2" +
                "&delay=10" +
                "&consumerKey=" + CONSUMER_KEY +
                "&consumerSecret=" + CONSUMER_SECRET +
                "&accessToken=" + ACCESS_TOKEN +
                "&accessTokenSecret=" + ACCESS_TOKEN_SECRET)
                .beanRef("twitterPaymentProcessor")
                .to("direct:paymentInstructions");

        from("file://incoming?move=processed&readLock=rename")
                .beanRef("filePaymentProcessor")
                .split(body(List.class))
                .to("direct:paymentInstructions");

        from("direct:paymentInstructions")
                .beanRef("paymentInstructionProcessor");
    }
}
