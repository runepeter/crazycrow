package eu.nets.crazycrow.jmx;

import java.util.List;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import org.jolokia.backend.executor.MBeanServerExecutor;
import org.jolokia.detector.AbstractServerDetector;
import org.jolokia.detector.ServerHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalProcessesDetector extends AbstractServerDetector {

    private final Logger logger = LoggerFactory.getLogger(LocalProcessesDetector.class);

    @Override
    public ServerHandle detect(final MBeanServerExecutor executor) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addMBeanServers(final Set<MBeanServerConnection> servers) {
        List<VirtualMachineDescriptor> list = VirtualMachine.list();
        for (VirtualMachineDescriptor vm : list) {
            try {

                VirtualMachine process = VirtualMachine.attach(vm.id());

                String property = process.getAgentProperties().getProperty("com.sun.management.jmxremote.localConnectorAddress");
                if (property != null) {
                    JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(property));
                    servers.add(connector.getMBeanServerConnection());
                    logger.debug("Added JMX connection to [" + property + "]");
                }

            } catch (Exception e) {
                //
            }
        }
    }
}
