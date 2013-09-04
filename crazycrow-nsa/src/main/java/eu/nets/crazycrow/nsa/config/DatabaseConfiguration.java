package eu.nets.crazycrow.nsa.config;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import eu.nets.crazycrow.jdbc.CrazyCrowDataSource;

@Configuration
public class DatabaseConfiguration {

    private final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Bean
    public DataSource dataSource() {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:target/db");
        ds.setUser("sa");
        ds.setPassword("sa");

        final JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:target/db", "sa", "sa");
        pool.setMaxConnections(3);
        pool.setLoginTimeout(60);

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                int activeConnections = pool.getActiveConnections();
                logger.info("active connections #: " + activeConnections);
            }
        }, 5, 5, TimeUnit.SECONDS);

        return new CrazyCrowDataSource(pool);
    }

    @Bean
    public NamingStrategy namingStrategy() {
        return new ImprovedNamingStrategy();
    }

    @Bean
    public SessionFactory sessionFactory() {

        final String[] packages = {
                "eu.nets.crazycrow.nsa",
        };

        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.setNamingStrategy(namingStrategy());
        builder.scanPackages(packages);
        builder.addProperties(createHibernateProperties());

        return builder.buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory());
        transactionManager.setDataSource(dataSource());
        transactionManager.setDefaultTimeout(5);
        return transactionManager;
    }

    private Properties createHibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.show_sql", "false");
        properties.put("hibernate.format_sql", "false");
        properties.put("hibernate.jdbc.batch_size", "1000");
        properties.put("hibernate.id.new_generator_mappings", "true");
        properties.put("hibernate.order_insert", "true");
        properties.put("hibernate.order_updates", "true");
        return properties;
    }

}
