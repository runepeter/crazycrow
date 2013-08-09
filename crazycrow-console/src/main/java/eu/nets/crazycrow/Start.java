package eu.nets.crazycrow;

import java.io.File;
import java.util.EnumSet;
import java.util.List;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.jersey.spi.spring.container.servlet.SpringServlet;


import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.jolokia.http.AgentServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import eu.nets.crazycrow.config.ApplicationConfiguration;
import eu.nets.utils.jetty.embedded.ContextPathConfig;
import eu.nets.utils.jetty.embedded.EmbeddedJettyBuilder;
import eu.nets.utils.jetty.embedded.PropertiesFileConfig;
import eu.nets.utils.jetty.embedded.StaticConfig;
import eu.nets.utils.jetty.embedded.StdoutRedirect;
import static eu.nets.utils.jetty.embedded.EmbeddedJettyBuilder.isStartedWithAppassembler;
import static eu.nets.utils.jetty.embedded.EmbeddedSpringBuilder.createApplicationContext;
import static eu.nets.utils.jetty.embedded.EmbeddedSpringBuilder.createSpringContextLoader;
import static javax.servlet.DispatcherType.ERROR;
import static javax.servlet.DispatcherType.REQUEST;

public class Start {

    private static final Logger logger = LoggerFactory.getLogger(Start.class);

    public static void main(String[] args) {
        boolean onServer = isStartedWithAppassembler();

        if (!onServer) {
            EnvironmentProperties.load();
        }

        // Jersey uses java.util.log, magic to redirect to slf4j. Also Jetty seems to get confused by the presence of jul for some reason
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // load contextPath and port from environment.properties, fall back to /csm:9090
        ContextPathConfig contextPath = onServer ? new PropertiesFileConfig() : new StaticConfig(EnvironmentProperties.ContextPath.getValue(), 9090);
        EmbeddedJettyBuilder jettyBuilder = new EmbeddedJettyBuilder(contextPath, !onServer);

        // start the spring container
        WebApplicationContext applicationContext = createApplicationContext("CrazyCrow Console", ApplicationConfiguration.class);
        ContextLoaderListener contextLoaderListener = createSpringContextLoader(applicationContext);

        EmbeddedJettyBuilder.ServletContextHandlerBuilder contextHandler = jettyBuilder.createRootServletContextHandler("");
        contextHandler.addEventListener(contextLoaderListener);

        ServletHolder jerseyServlet = new ServletHolder(SpringServlet.class);
        jerseyServlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
        filterHolder.setInitParameter("allowedOrigins", "*");
        filterHolder.setInitParameter("allowedMethods", "GET,POST,DELETE,PUT,HEAD");
        filterHolder.setInitParameter("allowedHeaders", "*");
        filterHolder.setInitParameter("allowedHeaders", "*");
        contextHandler.getHandler().addFilter(filterHolder, "/*", EnumSet.of(REQUEST, ERROR));

        ServletHolder resourceServlet;
        String resourceBase;
        if (onServer) {
            resourceBase = Start.class.getClassLoader().getResource("webapp/").toExternalForm();
        } else {
            resourceBase = new File("src/main/resources/webapp").getAbsolutePath();
        }

        resourceServlet = new ServletHolder(DefaultServlet.class);
        resourceServlet.setInitParameter("dirAllowed", "true");
        resourceServlet.setInitParameter("resourceBase", resourceBase);
        resourceServlet.setInitParameter("pathInfoOnly", "true");

        ServletHolder jmxServlet = new ServletHolder(AgentServlet.class);

        contextHandler.getHandler().addServlet(resourceServlet, "/*");
        contextHandler.getHandler().addServlet(jerseyServlet, "/api/*");
        contextHandler.getHandler().addServlet(jmxServlet, "/jmx/*");

        // let's fire this thing up now...
        if (onServer) {
            StdoutRedirect.tieSystemOutAndErrToLog();
            jettyBuilder.addHttpAccessLogAtRoot();
        }

        jettyBuilder.startJetty();
        logger.info("Crazy Crow Server started!");
    }
}
