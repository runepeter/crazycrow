package eu.nets.crazycrow.nsa;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class PaymentLoggerService {

    private final Logger logger = LoggerFactory.getLogger(PaymentLoggerService.class);

    private final SessionFactory sessionFactory;

	private final AtomicInteger ngppCounter;

    public PaymentLoggerService(final SessionFactory sessionFactory, final AtomicInteger ngppCounter) {
        this.sessionFactory = sessionFactory;
		this.ngppCounter = ngppCounter;
    }

    public void doIt() {
        long count = (Long) sessionFactory.getCurrentSession().createCriteria(PaymentInstruction.class).setProjection(Projections.rowCount()).uniqueResult();
        logger.info("Received payments #: " + count);
        logger.info("Received NGPP payments #: " + ngppCounter.get());
    }
}
