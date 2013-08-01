package eu.nets.crazycrow.nsa.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource dataSource() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:target/db");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }

    @Bean
    public NamingStrategy namingStrategy() {
        return new ImprovedNamingStrategy();
    }

    @Bean
    public SessionFactory sessionFactory() {

        final String[] packages = {
                "eu.nets.crazycrow.nsa.domain",
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
