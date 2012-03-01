//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.01 at 02:25:20 PM CET 
//


package org.parallelj.internal.conf.pojos;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the parallelj package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Procedure_QNAME = new QName("http://parallelj.org", "procedure");
    private final static QName _Procedures_QNAME = new QName("http://parallelj.org", "procedures");
    private final static QName _Telnet_QNAME = new QName("http://parallelj.org", "telnet");
    private final static QName _Ssh_QNAME = new QName("http://parallelj.org", "ssh");
    private final static QName _Configuration_QNAME = new QName("http://parallelj.org", "configuration");
    private final static QName _Servers_QNAME = new QName("http://parallelj.org", "servers");
    private final static QName _Beans_QNAME = new QName("http://parallelj.org", "beans");
    private final static QName _Bean_QNAME = new QName("http://parallelj.org", "bean");
    private final static QName _Jmx_QNAME = new QName("http://parallelj.org", "jmx");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: parallelj
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CProcedure }
     * 
     */
    public CProcedure createCProcedure() {
        return new CProcedure();
    }

    /**
     * Create an instance of {@link CTelnet }
     * 
     */
    public CTelnet createCTelnet() {
        return new CTelnet();
    }

    /**
     * Create an instance of {@link CServers }
     * 
     */
    public CServers createCServers() {
        return new CServers();
    }

    /**
     * Create an instance of {@link CBeans }
     * 
     */
    public CBeans createCBeans() {
        return new CBeans();
    }

    /**
     * Create an instance of {@link CJmx }
     * 
     */
    public CJmx createCJmx() {
        return new CJmx();
    }

    /**
     * Create an instance of {@link CProcedures }
     * 
     */
    public CProcedures createCProcedures() {
        return new CProcedures();
    }

    /**
     * Create an instance of {@link ParalleljConfiguration }
     * 
     */
    public ParalleljConfiguration createParalleljConfiguration() {
        return new ParalleljConfiguration();
    }

    /**
     * Create an instance of {@link CBean }
     * 
     */
    public CBean createCBean() {
        return new CBean();
    }

    /**
     * Create an instance of {@link CSsh }
     * 
     */
    public CSsh createCSsh() {
        return new CSsh();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CProcedure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "procedure")
    public JAXBElement<CProcedure> createProcedure(CProcedure value) {
        return new JAXBElement<CProcedure>(_Procedure_QNAME, CProcedure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CProcedures }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "procedures")
    public JAXBElement<CProcedures> createProcedures(CProcedures value) {
        return new JAXBElement<CProcedures>(_Procedures_QNAME, CProcedures.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CTelnet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "telnet")
    public JAXBElement<CTelnet> createTelnet(CTelnet value) {
        return new JAXBElement<CTelnet>(_Telnet_QNAME, CTelnet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CSsh }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "ssh")
    public JAXBElement<CSsh> createSsh(CSsh value) {
        return new JAXBElement<CSsh>(_Ssh_QNAME, CSsh.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParalleljConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "configuration")
    public JAXBElement<ParalleljConfiguration> createConfiguration(ParalleljConfiguration value) {
        return new JAXBElement<ParalleljConfiguration>(_Configuration_QNAME, ParalleljConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CServers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "servers")
    public JAXBElement<CServers> createServers(CServers value) {
        return new JAXBElement<CServers>(_Servers_QNAME, CServers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CBeans }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "beans")
    public JAXBElement<CBeans> createBeans(CBeans value) {
        return new JAXBElement<CBeans>(_Beans_QNAME, CBeans.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CBean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "bean")
    public JAXBElement<CBean> createBean(CBean value) {
        return new JAXBElement<CBean>(_Bean_QNAME, CBean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CJmx }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://parallelj.org", name = "jmx")
    public JAXBElement<CJmx> createJmx(CJmx value) {
        return new JAXBElement<CJmx>(_Jmx_QNAME, CJmx.class, null, value);
    }

}
