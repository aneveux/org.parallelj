/*
 *     ParallelJ, framework for parallel computing
 *
 *     Copyright (C) 2010, 2011, 2012 Atos Worldline or third-party contributors as
 *     indicated by the @author tags or express copyright attribution
 *     statements applied by the authors.
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.24 at 04:10:38 PM CET 
//


package org.parallelj.internal.conf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParalleljConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParalleljConfiguration">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{parallelj}procedures"/>
 *         &lt;element ref="{parallelj}servers"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name="parallelj")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ParalleljConfiguration", propOrder = {
    "procedures",
    "servers"
})
public class ParalleljConfiguration {

    @XmlElement(namespace = "parallelj", required = true)
    protected CProcedures procedures;
    @XmlElement(namespace = "parallelj", required = true)
    protected CServers servers;

    /**
     * Gets the value of the procedures property.
     * 
     * @return
     *     possible object is
     *     {@link CProcedures }
     *     
     */
    public CProcedures getProcedures() {
        return procedures;
    }

    /**
     * Sets the value of the procedures property.
     * 
     * @param value
     *     allowed object is
     *     {@link CProcedures }
     *     
     */
    public void setProcedures(CProcedures value) {
        this.procedures = value;
    }

    /**
     * Gets the value of the servers property.
     * 
     * @return
     *     possible object is
     *     {@link CServers }
     *     
     */
    public CServers getServers() {
        return servers;
    }

    /**
     * Sets the value of the servers property.
     * 
     * @param value
     *     allowed object is
     *     {@link CServers }
     *     
     */
    public void setServers(CServers value) {
        this.servers = value;
    }

}
