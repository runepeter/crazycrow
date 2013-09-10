package eu.nets.crazycrow.nsa.camel;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import eu.nets.crazycrow.nsa.PaymentInstruction;
import eu.nets.crazycrow.nsa.PaymentLoggerService;

@Component
public class NsaRouteBuilder extends SpringRouteBuilder {
	
	
    private final Logger logger = LoggerFactory.getLogger(NsaRouteBuilder.class);

    private static final String CONSUMER_KEY = "WK2V6RLPZDWifZLs9PTPIQ"; //"yyrw6UYUIZM1VaQW1h5u0w";
    private static final String CONSUMER_SECRET = "UCNed922hA7FPL7Ob77f2p5BsTHloWRr7hXcpaQ0ts"; //"a8Ep2ioqyxB3OFoubXUegvi8AWOwwqpkEqi0M8Xxi8";
    private static final String ACCESS_TOKEN = "15070781-tL7kGGSpGkchNw3eJX3AEkgf7Ng4UrUSgNTklltzc"; //"21330633-M9RUuXXLnttLx8wvVKVNFN2X0glrOUqNwcvPt3spk";
    private static final String ACCESS_TOKEN_SECRET = "gUhGDfQoOCKdysRQ5oJ6nLZZRBdLiCj9FG40YMc70"; //"EukyC6s1qYWaA5QVn9lqkdzUOMHD3W5zkrggSFTiI";

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void configure() throws Exception {
    	final AtomicInteger ngppCounter = new AtomicInteger(0);
    	final PaymentLoggerService paymentLoggerService = new PaymentLoggerService(sessionFactory, ngppCounter);
    	
        from("twitter://search?type=polling" +
                "&keywords=nsa2" +
                "&delay=60" +
                "&count=200" +
                "&consumerKey=" + CONSUMER_KEY +
                "&consumerSecret=" + CONSUMER_SECRET +
                "&accessToken=" + ACCESS_TOKEN +
                "&accessTokenSecret=" + ACCESS_TOKEN_SECRET)
                .autoStartup(false)
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
//                .onException(Throwable.class)
//                .process(new Processor() {
//					@Override
//					public void process(Exchange exchange) throws Exception {
//						logger.error("Unable to send file to NGPP [{}].", exchange.getIn().getBody());
//					}
//                })
//                .handled(true)
//                .end();

        from("file://ngpp")
                .convertBodyTo(String.class, "utf-8")
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        String body = exchange.getIn().getBody(String.class).trim();
                        LoggerFactory.getLogger(getClass()).info("NGPP received payment: " + body);
                        String[] parts = body.split(",");
                        
                        ngppCounter.incrementAndGet();
                        
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

        from("timer://counter?fixedRate=true&period=5s")
                .transacted()
                .bean(paymentLoggerService, "doIt");

        from("jetty:http://localhost:8081/otp/twitter/callback")
                .process(new Processor() {
                    @Override
                    public void process(final Exchange exchange) throws Exception {
                        System.err.println(exchange.getIn().getBody());
                    }
                });

    }
}
