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
	
	private static final Pattern pattern = Pattern.compile ("^(@\\S+).*?(\\d+(kroner| kroner|spenn| spenn|kr| kr|,-)).*");
	
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

		Matcher matcher = pattern.matcher(text);
		if (matcher.matches()) {
			paymentInstruction.setCreditSocialId(matcher.group(1));
			paymentInstruction.setAmount(new BigDecimal(matcher.group(2).replaceAll("[^\\d]", "")));
		} else {
			paymentInstruction = null;
		}
		
		return paymentInstruction;
	}
}