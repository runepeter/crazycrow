package eu.nets.crazycrow.nsa;

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
    public void process(Exchange exchange) throws Exception {

        PaymentInstruction paymentInstruction = exchange.getIn().getBody(PaymentInstruction.class);

        Session session = sessionFactory.getCurrentSession();
        session.save(paymentInstruction);
        session.flush();
        logger.info(session.getTransaction() + " :: Received [{}].", paymentInstruction);
    }

}
