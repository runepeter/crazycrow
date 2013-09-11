package eu.nets.crazycrow.nsa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;

final public class FilePaymentProcessor implements Processor {
	
	private static final Logger logger = LoggerFactory.getLogger(FilePaymentProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		File file = exchange.getIn().getBody(File.class);
		logger.info("Received file [{}]", file);
		
		List<PaymentInstruction> instructions = Lists.newArrayList();
		
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.ISO_8859_1))) {;
			String line = null;
			while ((line = reader.readLine()) != null) {
				instructions.add(toPaymentInstruction(line));
			}
		}
		
		exchange.getOut().setBody(instructions);
	}

	private static PaymentInstruction toPaymentInstruction(String line) {
		String[] split = line.split(";");
		PaymentInstruction paymentInstruction = new PaymentInstruction();
		paymentInstruction.setSource(Source.valueOf(split[0]));
		paymentInstruction.setDebitSocialId(split[1]);
		paymentInstruction.setCreditSocialId(split[2]);
		paymentInstruction.setAmount(new BigDecimal(split[3]));
		paymentInstruction.setRemittanceInformation(split[4]);
		return paymentInstruction;
	}
	
}