package eu.nets.crazycrow.nsa;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;

final class TwitterPaymentProcessor implements Processor {
	
	private final Logger logger = LoggerFactory.getLogger(TwitterPaymentProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Status status = (Status)exchange.getIn().getBody();
		exchange.getOut().setBody(toPaymentInstruction(status));
	}

	static PaymentInstruction toPaymentInstruction(Status status) {
		PaymentInstruction paymentInstruction = new PaymentInstruction();
		paymentInstruction.setSource(Source.Twitter);
		paymentInstruction.setDebitSocialId(status.getUser().getName());
		
		String text = status.getText();
		
		return paymentInstruction;
	}
}