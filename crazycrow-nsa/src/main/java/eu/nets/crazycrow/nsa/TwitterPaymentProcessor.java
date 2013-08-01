package eu.nets.crazycrow.nsa;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;

final public class TwitterPaymentProcessor implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(TwitterPaymentProcessor.class);

	private static final Pattern pattern1 = Pattern.compile("^(@\\S+) Her får du (\\d+) kroner.*$");
	private static final Pattern pattern2 = Pattern.compile("^(@\\S+) Jeg sender deg (\\d+) spenn.*$");
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Status status = (Status)exchange.getIn().getBody();
		exchange.getOut().setBody(toPaymentInstruction(status));
	}

	public static PaymentInstruction toPaymentInstruction(Status status) {
		PaymentInstruction paymentInstruction = new PaymentInstruction();
		paymentInstruction.setSource(Source.Twitter);
		paymentInstruction.setDebitSocialId("@" + status.getUser().getScreenName());
		
		String text = status.getText();

		Matcher matcher1 = pattern1.matcher(text);
		Matcher matcher2 = pattern2.matcher(text);
		
		if (matcher1.matches()) {
			paymentInstruction.setCreditSocialId(matcher1.group(1));
			paymentInstruction.setAmount(new BigDecimal(matcher1.group(2)));
		} else if (matcher2.matches()) {
			paymentInstruction.setCreditSocialId(matcher2.group(1));
			paymentInstruction.setAmount(new BigDecimal(matcher2.group(2)));
		} else {
			paymentInstruction = null;
		}
		
		return paymentInstruction;
	}
}