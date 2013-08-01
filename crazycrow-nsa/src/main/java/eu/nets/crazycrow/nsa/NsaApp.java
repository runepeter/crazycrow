package eu.nets.crazycrow.nsa;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;


public class NsaApp {
	
	private static final String CONSUMER_KEY = "yyrw6UYUIZM1VaQW1h5u0w";
	private static final String CONSUMER_SECRET = "a8Ep2ioqyxB3OFoubXUegvi8AWOwwqpkEqi0M8Xxi8";
	private static final String ACCESS_TOKEN = "21330633-M9RUuXXLnttLx8wvVKVNFN2X0glrOUqNwcvPt3spk";
	private static final String ACCESS_TOKEN_SECRET = "EukyC6s1qYWaA5QVn9lqkdzUOMHD3W5zkrggSFTiI";
	
	public static void main(String[] args) throws Exception {
		
		System.setProperty("http.proxyHost", "wpad");
		System.setProperty("http.proxyPort", "8080");
		
		DefaultCamelContext camelContext = new DefaultCamelContext();
		camelContext.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
//				from("twitter://search?type=polling" +
//						"&keywords=javazone" +
//						"&delay=60" +
//						"&consumerKey=" + CONSUMER_KEY + 
//						"&consumerSecret=" + CONSUMER_SECRET + 
//						"&accessToken=" + ACCESS_TOKEN + 
//						"&accessTokenSecret=" + ACCESS_TOKEN_SECRET)
//					.process(new TwitterPaymentProcessor())
//					.to("direct:paymentInstructions");
				
				from("file://incoming?move=processed&readLock=rename")
					.process(new FilePaymentProcessor())
					.split(body(List.class))
					.to("direct:paymentInstructions");
				
				from("direct:paymentInstructions")
					.process(new PaymentInstructionProcessor());
			}
		});
		
		camelContext.start();
		
		Thread.sleep(Long.MAX_VALUE);
		
	}
	
}
