package eu.nets.crazycrow.nsa;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentLoggerService {

    private final SessionFactory sessionFactory;

    @Autowired
    public PaymentLoggerService(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void doIt() {
        List list = sessionFactory.getCurrentSession().createCriteria(PaymentInstruction.class).list();
        System.err.println("received payments#: " + list.size());
    }
}
