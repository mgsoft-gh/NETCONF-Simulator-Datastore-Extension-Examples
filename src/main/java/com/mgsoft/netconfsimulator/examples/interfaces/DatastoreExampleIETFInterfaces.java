
package com.mgsoft.netconfsimulator.examples.interfaces;

import com.mgsoft.netconfsimulator.examples.common.ObjectStorage;
import com.mgsoft.ncsim.datastoreapi.DatastoreListener;
import com.mgsoft.ncsim.datastoreapi.Datastore;
import com.mgsoft.ncsim.datastoreapi.DatastoreNode;
import com.mgsoft.ncsim.datastoreapi.Session;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * 
 * NETCONF Simulator Datastore Extension example: DatastoreExampleIETFInterfaces
 * 
 * This class demonstrates the usage of the MG-SOFT NETCONF Simulator Datastore Extension API. 
 * It implements the live behavior of simulated server interfaces based on the ietf-interfaces@2018-02-20 YANG module as:
 *  - State data 'admin-status' and 'oper-status' are set based on the 'enabled' node value.
 *  - State data 'if-index' is assigned automatically. Relation between an interface 'name' and its 'if-index' is stored in a file
 *    in order to assure 'if-index' persistence. 
 *  - The disabled state of the loopback interface is treated as an error.
 *  - Traffic counters are periodically increased.
 * 
 * @author MG-SOFT <support@mg-soft.com>
 * Copyright (C) 2018-2025 MG-SOFT Corporation. All rights reserved.
 * 
 */
public class DatastoreExampleIETFInterfaces  extends DatastoreListener {
    
    // Constants
    private static final Object TIMER_IF_TRAFFIC_REF = new Object();
    
    private static final String ENABLED = "enabled";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String TYPE_LOOPBACK = "softwareLoopback";
    private static final String ADMIN_STATUS = "admin-status";
    private static final String OPER_STATUS = "oper-status";
    private static final String IF_INDEX = "if-index";
    
    private static final String INTERFACE_LIST_PATH = "/ietf-interfaces:interfaces/interface";
    private static final String INTERFACE_ENABLED_PATH = INTERFACE_LIST_PATH + "/" + ENABLED;
    
    
    private static final String STATISTICS = "statistics";
    private static final String IN_OCTETS = STATISTICS + "/in-octets";
    private static final String OUT_OCTETS = STATISTICS + "/out-octets";
    private static final String IN_UNICAST_PKTS = STATISTICS + "/in-unicast-pkts";
    private static final String IN_BROADCAST_PKTS = STATISTICS + "/in-broadcast-pkts";
    private static final String IN_MULTICAST_PKTS = STATISTICS + "/in-multicast-pkts";
    private static final String OUT_UNICAST_PKTS = STATISTICS + "/out-unicast-pkts";
    private static final String OUT_BROADCAST_PKTS = STATISTICS + "/out-broadcast-pkts";
    private static final String OUT_MULTICAST_PKTS = STATISTICS + "/out-multicast-pkts";
    private static final String IN_DISCARDS = STATISTICS + "/in-discards";
    private static final String IN_ERRORS = STATISTICS + "/in-errors";
    private static final String IN_UNKNOWN_PROTOS = STATISTICS + "/in-unknown-protos";
    private static final String OUT_DISCARDS = STATISTICS + "/out-discards";
    private static final String OUT_ERRORS = STATISTICS + "/out-errors";
    
    private static final Random rnd = new Random();
    
    // Key is: serverName/interfaceName
    private Map<String, Integer> ifIndexMap = new HashMap<>();
    private int maxIfIndex = 0;

    private final Path IF_INDEX_FILE_PATH = Paths.get(System.getProperty("java.io.tmpdir"), "ifIndex.data");
    
    @Override
    public void init(String yangProfileName) {
        Object dataObj = ObjectStorage.loadData(IF_INDEX_FILE_PATH);
        if (dataObj instanceof HashMap) {
            HashMap<String, Integer> ifIndexMap_ = (HashMap<String, Integer>)dataObj;
            synchronized (ifIndexMap) {
                ifIndexMap.putAll(ifIndexMap_);
                maxIfIndex =  Collections.max(ifIndexMap.values());
            }
        } 
    }

    @Override
    public void cleanup(String yangProfileName) {
        HashMap<String, Integer> ifIndexMap_;
        synchronized (ifIndexMap) {
            ifIndexMap_ = new HashMap<>(ifIndexMap);
            ifIndexMap.clear();
            maxIfIndex = 0;
        }
        ObjectStorage.saveData(ifIndexMap_, IF_INDEX_FILE_PATH);
    }

    @Override
    public void onServerStart(Session session, Datastore datastore) {
        try {
            // Simulated server started
            // Check interfaces and create 'admin-state' and 'oper-state' nodes. Assign if-index.
            // Ensure that loopback is enabled.
            checkAllInterfaces(session.getServerName(), datastore, true);
            // Schedule time for driving traffic counters
            datastore.scheduleTimer(1000, TIMER_IF_TRAFFIC_REF);
        } catch (ValidationException ex) {
        }
    }

    @Override
    public void onTimer(Session session, Datastore datastore, Object param) {
        // Timer event
        if (Objects.equals(param, TIMER_IF_TRAFFIC_REF)) {
            // Update traffic counters
            onTimerTraffic(datastore);
            // Reschedule timer
            datastore.scheduleTimer(1000, TIMER_IF_TRAFFIC_REF);
        }
    }

    @Override
    public void onRunningConfigChanged(String operation, Session session, Datastore datastore, DatastoreNode[] createdNodes, DatastoreNode[] modifiedNodes, DatastoreNode[] deletedNodes) {
        try {
             // Running configuration has been changed
            // Check if interfaces' 'enabled' has been changed, updated 'admin-state' and 'oper-state' accordingly.
            // Assign 'if-index' if needed.
            // If loopback was disabled, ValidatonException will be thrown.
            onInterfaceMayChange(session.getServerName(), datastore, createdNodes, modifiedNodes, deletedNodes);
        } catch (ValidationException ex) {
            setSessionError(session, ex);
        }
    }
    
    @Override
    public void onActiveScenariosChanged(String[] activeScenarios) {
        System.out.println("Active scenarios: " + String.join(", ", Arrays.asList(activeScenarios)));
    }
    
    private void setSessionError(Session session, ValidationException ex) {
        // Report error back to client
        session.setError("application", "invalid-value", 
                    "additional-validation", ex.getPath(), 
                    ex.getMessage(), null);
    }
    
    private void checkAllInterfaces(String serverName, Datastore datastore, boolean onStart) throws ValidationException {
        DatastoreNode interfaceNode = datastore.getNode(INTERFACE_LIST_PATH);
        while (interfaceNode != null) {
            try {
                handleInterfaceEnabledChanged(datastore, interfaceNode, onStart);
                setIfIndex(serverName, datastore, interfaceNode);
            } catch (ValidationException ex) {
                if (onStart) {
                    enableInterface(interfaceNode);
                } else {
                    throw ex;
                }
            }
            //
            interfaceNode = interfaceNode.getNextSibling();
        }
    }
    
    private void handleInterfaceEnabledChanged(Datastore datastore, DatastoreNode interfaceNode, boolean correctIfLoopbackIsDisabled) throws ValidationException {
        boolean interfaceEnabled = isInterfaceEnabled(interfaceNode);
        boolean isLoopback = isInterfaceLoopback(interfaceNode);
        if (!interfaceEnabled && isLoopback) {
            if (correctIfLoopbackIsDisabled) {
                enableInterface(interfaceNode);
                interfaceEnabled = true;
            } else {
                // Do not allow loopback interface to be disabled
                throw new ValidationException(interfaceNode.getPath(), "Loopback interface must not be disabled (policiy enforced by InterfacesDatastoreTraffic datastore extension)");
            }
        }
        String statusValue = interfaceEnabled ? "up" : "down";
        
        DatastoreNode adminStatusNode = datastore.createNode(interfaceNode, ADMIN_STATUS);
        adminStatusNode.setValue(statusValue);
        DatastoreNode operStatusNode = datastore.createNode(interfaceNode, OPER_STATUS);
        operStatusNode.setValue(statusValue);
    }
    
    private void setIfIndex(String serverName, Datastore datastore, DatastoreNode interfaceNode) {
        DatastoreNode nameNode = interfaceNode.getChild(NAME);
        if (nameNode != null) {
            String ifName = nameNode.getValue();
            DatastoreNode ifIndexNode = datastore.createNode(interfaceNode, IF_INDEX);
            int ifIndex = getIfIndex(serverName, ifName);
            ifIndexNode.setValue(ifIndex);
        }
    }
    
    private void onInterfaceMayChange(String serverName, Datastore datastore, DatastoreNode[] createdNodes, DatastoreNode[] modifiedNodes, DatastoreNode[] deletedNodes) throws ValidationException {
        if (createdNodes != null) {
            for (DatastoreNode createdNode : createdNodes) {
                checkNodeChanged(serverName, datastore, createdNode);
            }
        }
        if (modifiedNodes != null) {
            for (DatastoreNode modifiedNode : modifiedNodes) {
                checkNodeChanged(serverName, datastore, modifiedNode);
            }
        }
        if (deletedNodes != null) {
            for (DatastoreNode deletedNode : deletedNodes) {
                checkNodeChanged(serverName, datastore, deletedNode);
            }
        }
        
    }
    
    private void checkNodeChanged(String serverName, Datastore datastore, DatastoreNode node) throws ValidationException {
        String pathNoKeys = node.getPathNoKeys();
        switch (pathNoKeys) {
            case INTERFACE_ENABLED_PATH:
                // Go to list node
                node = node.getParent();
                // Do not break!
            case INTERFACE_LIST_PATH:
                handleInterfaceEnabledChanged(datastore, node, false);
                setIfIndex(serverName, datastore, node);
                break;
        }
    }
    
    private void onTimerTraffic(Datastore datastore) {
        DatastoreNode interfaceNode = datastore.getNode(INTERFACE_LIST_PATH);
        while (interfaceNode != null) {
            handleInterfaceTraffic(datastore, interfaceNode);
            //
            interfaceNode = interfaceNode.getNextSibling();
        }
    }
    
    private void handleInterfaceTraffic(Datastore datastore, DatastoreNode interfaceNode) {
        if (isInterfaceEnabled(interfaceNode)) {
            increaseCounter(datastore.createNode(interfaceNode, IN_OCTETS), 10000);
            increaseCounter(datastore.createNode(interfaceNode, OUT_OCTETS), 10000);
            increaseCounter(datastore.createNode(interfaceNode, IN_MULTICAST_PKTS), 100);
            increaseCounter(datastore.createNode(interfaceNode, IN_UNICAST_PKTS), 10);
            increaseCounter(datastore.createNode(interfaceNode, IN_BROADCAST_PKTS), 10);
            increaseCounter(datastore.createNode(interfaceNode, OUT_MULTICAST_PKTS), 100);
            increaseCounter(datastore.createNode(interfaceNode, OUT_UNICAST_PKTS), 10);
            increaseCounter(datastore.createNode(interfaceNode, OUT_BROADCAST_PKTS), 10);
            increaseCounter(datastore.createNode(interfaceNode, IN_DISCARDS), 1);
            increaseCounter(datastore.createNode(interfaceNode, IN_ERRORS), 1);
            increaseCounter(datastore.createNode(interfaceNode, IN_UNKNOWN_PROTOS), 1);
            increaseCounter(datastore.createNode(interfaceNode, OUT_DISCARDS), 1);
            increaseCounter(datastore.createNode(interfaceNode, OUT_ERRORS), 1);
        }
    }
    
    private void increaseCounter(DatastoreNode counterNode, int maxStep) {
        if (counterNode != null) {
            long value = 0;
            try {
                value = Long.parseLong(counterNode.getValue());
                } catch (NumberFormatException ex) {
            }
            value += rnd.nextInt(maxStep);
            counterNode.setValue(Long.toString(value));
            
        }
    }
    
    private boolean isInterfaceEnabled(DatastoreNode interfaceNode) {
        DatastoreNode enabledNode = interfaceNode.getChild(ENABLED);
        return (enabledNode == null) || Boolean.TRUE.toString().equals(enabledNode.getValue());
    }
    
    private void enableInterface(DatastoreNode interfaceNode) {
        DatastoreNode enabledNode = interfaceNode.getChild(ENABLED);
        // If 'enabled' child exists set its value to 'true', othrewise do nothigs as default value is 'true'
        if (enabledNode != null) {
            enabledNode.setValue(Boolean.TRUE.toString());
        }
    }
    
    private boolean isInterfaceLoopback(DatastoreNode interfaceNode) {
        DatastoreNode typeNode = interfaceNode.getChild(TYPE);
        return (typeNode != null)  && Objects.equals(TYPE_LOOPBACK, typeNode.getValue());
    }
    
    private int getIfIndex(String serverName, String ifName) {
        String key = serverName + "/" + ifName;
        synchronized (ifIndexMap) {
            if (ifIndexMap.containsKey(key)) {
                return ifIndexMap.get(key);
            } else {
                ifIndexMap.put(key, ++maxIfIndex);
                return maxIfIndex;
            }
        }
    }
    
    private static class ValidationException extends Exception {
        private final String path;
        public ValidationException(String path, String message) {
            super(message);
            this.path = path;
        }
        
        public String getPath() {
            return path;
        }
    }
}
