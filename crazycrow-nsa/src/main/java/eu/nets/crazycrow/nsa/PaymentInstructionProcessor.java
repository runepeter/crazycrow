package eu.nets.crazycrow.nsa;

import java.math.BigDecimal;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class PaymentInstructionProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(PaymentInstructionProcessor.class);

    private final SessionFactory sessionFactory;

    @Autowired
    public PaymentInstructionProcessor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void process(final Exchange exchange) throws Exception {

        PaymentInstruction paymentInstruction = exchange.getIn().getBody(PaymentInstruction.class);

        Session session = sessionFactory.getCurrentSession();
        session.save(paymentInstruction);
        session.flush();

        String debitId = paymentInstruction.getDebitSocialId();
        String debitAccount = paymentInstruction.getDebitAccount();

        String creditId = paymentInstruction.getCreditSocialId();
        String creditAccount = paymentInstruction.getCreditAccount();

        String remittanceInformation = paymentInstruction.getRemittanceInformation();
        
        BigDecimal amount = paymentInstruction.getAmount();

        Source paymentSource = paymentInstruction.getSource();
        Long paymentId = paymentInstruction.getId();

        StringBuffer buffer = new StringBuffer("Received instruction to transfer ");
        buffer.append(amount).append(" NOK from ");
        buffer.append(debitId);

        if (debitAccount != null) {
            buffer.append(" (").append(debitAccount).append(")");
        }

        buffer.append(" to ").append(creditId);

        if (creditAccount != null) {
            buffer.append(" (").append(creditAccount).append(").");
        }

        buffer.append(" [").append(paymentSource).append("/").append(paymentId).append("]");
        buffer.append(" [").append(remittanceInformation).append("]");
        logger.info(buffer.toString());
    }

}
