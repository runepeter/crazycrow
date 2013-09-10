package eu.nets.crazycrow.nsa.camel;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import eu.nets.crazycrow.nsa.PaymentInstruction;

@Component
public class NsaRouteBuilder extends SpringRouteBuilder {

    private final Logger logger = LoggerFactory.getLogger(NsaRouteBuilder.class);

    private static final String CONSUMER_KEY = "WK2V6RLPZDWifZLs9PTPIQ"; //"yyrw6UYUIZM1VaQW1h5u0w";
    private static final String CONSUMER_SECRET = "UCNed922hA7FPL7Ob77f2p5BsTHloWRr7hXcpaQ0ts"; //"a8Ep2ioqyxB3OFoubXUegvi8AWOwwqpkEqi0M8Xxi8";
    private static final String ACCESS_TOKEN = "15070781-tL7kGGSpGkchNw3eJX3AEkgf7Ng4UrUSgNTklltzc"; //"21330633-M9RUuXXLnttLx8wvVKVNFN2X0glrOUqNwcvPt3spk";
    private static final String ACCESS_TOKEN_SECRET = "gUhGDfQoOCKdysRQ5oJ6nLZZRBdLiCj9FG40YMc70"; //"EukyC6s1qYWaA5QVn9lqkdzUOMHD3W5zkrggSFTiI";

    @Override
    public void configure() throws Exception {
        from("twitter://search?type=polling" +
                "&keywords=nsa2" +
                "&delay=60" +
                "&count=200" +
                "&consumerKey=" + CONSUMER_KEY +
                "&consumerSecret=" + CONSUMER_SECRET +
                "&accessToken=" + ACCESS_TOKEN +
                "&accessTokenSecret=" + ACCESS_TOKEN_SECRET)
                .autoStartup(true)
                .beanRef("twitterPaymentProcessor")
                .filter(body(PaymentInstruction.class).isNotNull())
                .to("seda:paymentInstructions");

        from("file://incoming?move=processed&readLock=rename")
                .beanRef("filePaymentProcessor")
                .split(body(List.class))
                .to("seda:paymentInstructions");

        from("seda:paymentInstructions?concurrentConsumers=10")
                .transacted()
                .beanRef("paymentInstructionProcessor")
                .transform(simple("${body.debitSocialId},${body.creditSocialId},${body.amount}"))
                .to("file://ngpp");

        from("file://ngpp")
                .convertBodyTo(String.class, "utf-8")
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        String body = exchange.getIn().getBody(String.class).trim();
                        String[] parts = body.split(",");

                        String message = "Hei! Du har nettopp mottatt " + parts[2] + " spenn fra " + parts[0] + " på din konto. " + System.currentTimeMillis();
                        System.err.println(parts[1] + ">> " + message);

                        exchange.getIn().setHeader("tuser", parts[1].replace("@", ""));
                        exchange.getIn().setBody(message);
                        exchange.setProperty("tuser", parts[1].replace("@", ""));

                        exchange.getOut().setHeader("tuser", parts[1].replace("@", ""));
                        exchange.getOut().setBody(message);
                    }
                })
                .recipientList(simple("twitter://directMessage?user=${in.headers.tuser}" +
                        "&consumerKey=" + CONSUMER_KEY +
                        "&consumerSecret=" + CONSUMER_SECRET +
                        "&accessToken=" + ACCESS_TOKEN +
                        "&accessTokenSecret=" + ACCESS_TOKEN_SECRET))
                .onException(Throwable.class)
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        logger.error("Unable to send DM to payee [{}].", exchange.getIn().getBody());
                    }
                })
                .handled(true)
                .end();

        from("timer://foo?fixedRate=true&period=5s")
                .transacted()
                .beanRef("paymentLoggerService", "doIt");

        from("jetty:http://localhost:8081/otp/twitter/callback")
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        System.err.println(exchange.getIn().getBody());
                    }
                });

    }
}
