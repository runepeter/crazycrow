package eu.nets.crazycrow.nsa;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import twitter4j.Status;

final class TwitterPaymentProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		Status status = (Status)exchange.getIn().getBody();
		
		System.out.println(status.getUser().getName() + ": " + status.getText());
	}
}