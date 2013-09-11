package eu.nets.crazycrow.nsa;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import eu.nets.crazycrow.nsa.config.ApplicationConfiguration;


public class NsaApp {


    public static void main(String[] args) throws Exception {

        //System.setProperty("http.proxyHost", "wpad");
        //System.setProperty("http.proxyPort", "8080");

        new AnnotationConfigApplicationContext(ApplicationConfiguration.class);

        Thread.sleep(Long.MAX_VALUE);
    }

}
