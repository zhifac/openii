/**
 * SchemaStoreServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.mitre.schemastore.servlet;

public class SchemaStoreServiceLocator extends org.apache.axis.client.Service implements org.mitre.schemastore.servlet.SchemaStoreService {

    public SchemaStoreServiceLocator() {
    }


    public SchemaStoreServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SchemaStoreServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SchemaStore
    private java.lang.String SchemaStore_address = "http://localhost:8080/SchemaStore/services/SchemaStore";

    public java.lang.String getSchemaStoreAddress() {
        return SchemaStore_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SchemaStoreWSDDServiceName = "SchemaStore";

    public java.lang.String getSchemaStoreWSDDServiceName() {
        return SchemaStoreWSDDServiceName;
    }

    public void setSchemaStoreWSDDServiceName(java.lang.String name) {
        SchemaStoreWSDDServiceName = name;
    }

    public org.mitre.schemastore.servlet.SchemaStoreObject getSchemaStore() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SchemaStore_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSchemaStore(endpoint);
    }

    public org.mitre.schemastore.servlet.SchemaStoreObject getSchemaStore(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.mitre.schemastore.servlet.SchemaStoreSoapBindingStub _stub = new org.mitre.schemastore.servlet.SchemaStoreSoapBindingStub(portAddress, this);
            _stub.setPortName(getSchemaStoreWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSchemaStoreEndpointAddress(java.lang.String address) {
        SchemaStore_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.mitre.schemastore.servlet.SchemaStoreObject.class.isAssignableFrom(serviceEndpointInterface)) {
                org.mitre.schemastore.servlet.SchemaStoreSoapBindingStub _stub = new org.mitre.schemastore.servlet.SchemaStoreSoapBindingStub(new java.net.URL(SchemaStore_address), this);
                _stub.setPortName(getSchemaStoreWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SchemaStore".equals(inputPortName)) {
            return getSchemaStore();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://servlet.schemastore.mitre.org", "SchemaStoreService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://servlet.schemastore.mitre.org", "SchemaStore"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SchemaStore".equals(portName)) {
            setSchemaStoreEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
