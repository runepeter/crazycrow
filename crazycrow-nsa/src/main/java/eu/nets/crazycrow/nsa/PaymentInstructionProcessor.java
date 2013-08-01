package eu.nets.crazycrow.nsa;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaymentInstructionProcessor implements Processor {
	
	private final Logger logger = LoggerFactory.getLogger(PaymentInstructionProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		PaymentInstruction paymentInstruction = exchange.getIn().getBody(PaymentInstruction.class);
		logger.info("Received [{}].", paymentInstruction);
	}

}
