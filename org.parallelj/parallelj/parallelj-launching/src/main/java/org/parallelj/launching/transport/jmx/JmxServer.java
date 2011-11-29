/*
 *     ParallelJ, framework for parallel computing
 *
 *     Copyright (C) 2010 Atos Worldline or third-party contributors as
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

package org.parallelj.launching.transport.jmx;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.rmi.NoSuchObjectException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.parallelj.internal.reflect.ProgramAdapter;
import org.parallelj.internal.reflect.ProgramAdapter.Adapter;
import org.parallelj.launching.In;
import org.parallelj.launching.LaunchingMessageKind;
import org.parallelj.launching.parser.Parser;

/**
 * Class representing a Parallelj JMX Server for remote launching 
 */
public class JmxServer {
	private final static String DEFAULT_SERVER_URL_FORMAT = "service:jmx:rmi://%s/jndi/rmi://%s:%s/server";
	private final static String DEFAULT_BEAN_NAME_FORMAT = "%s:type=%s";

	private String host;
	private int port;
	private String serverUrlFormat;
	private String beanNameFormat;
	private Registry register = null;
	private MBeanServer mbs = null;
	private JMXConnectorServer jmxConnectorServer = null;
	private List<ObjectName> beanNames = new ArrayList<ObjectName>();

	/**
	 * Contructor for a JMX Server
	 * 
	 * @param host the host ip
	 * @param port the port the JMX Server to listen to
	 */
	public JmxServer(String host, int port) {
		this.host = host;
		this.port = port;
		this.serverUrlFormat = DEFAULT_SERVER_URL_FORMAT;
		this.beanNameFormat = DEFAULT_BEAN_NAME_FORMAT;
	}

	/**
	 * Start the JMX Server
	 * 
	 * @throws IOException
	 */
	public synchronized void start() throws IOException {
		LaunchingMessageKind.I0003.format(this.host, this.port);
		this.mbs = ManagementFactory.getPlatformMBeanServer();

		String oldRmiServerName = System
				.getProperty("java.rmi.server.hostname");
		System.setProperty("java.rmi.server.hostname", this.host);

		register = LocateRegistry.createRegistry(this.port);
		if (oldRmiServerName == null) {
			Properties props = System.getProperties();
			for (Object key : props.keySet()) {
				if (key.equals("java.rmi.server.hostname")) {
					props.remove(key);
					break;
				}
			}
		} else {
			System.setProperty("java.rmi.server.hostname", oldRmiServerName);
		}

		String serviceURL = String.format(serverUrlFormat, this.host,
				this.host, this.port);
		LaunchingMessageKind.I0004.format(serviceURL);

		JMXServiceURL url = new JMXServiceURL(serviceURL);
		this.jmxConnectorServer = JMXConnectorServerFactory
				.newJMXConnectorServer(url, null, mbs);
		this.jmxConnectorServer.start();

	}

	/**
	 * Stop the JMX Server
	 */
	public synchronized void stop() {
		LaunchingMessageKind.I0005.format();
		unRegisterMBeans();

		try {
			if (this.jmxConnectorServer != null) {
				this.jmxConnectorServer.stop();
			}
		} catch (IOException e1) {
			// Do nothing
		}
		try {
			UnicastRemoteObject.unexportObject(register, true);
		} catch (NoSuchObjectException e) {
			// Do nothing
		}
	}

	/**
	 * Try to register standard MBean class in the JMX server
	 * 
	 * @param beanClass the class name of the MBean 
	 */
	public void registerMBean(String className) {
		if (this.mbs != null && className != null) {
			try {
				Class<?> clazz = Class.forName(className);

				String fqnName = clazz.getCanonicalName();
				String domain = fqnName.substring(0, fqnName.lastIndexOf('.'));
				String type = fqnName.substring(fqnName.lastIndexOf('.') + 1);

				ObjectName objectName = new ObjectName(String.format(
						beanNameFormat, domain, type));
				if (!mbs.isRegistered(objectName)) {
					LaunchingMessageKind.I0006.format(objectName);
					mbs.registerMBean(clazz.newInstance(), objectName);
					LaunchingMessageKind.I0007.format(objectName);
				}
			} catch (Exception e) {
				LaunchingMessageKind.E0005.format(className, e);
			}
		} else {
			LaunchingMessageKind.E0003.format();
		}
	}

	/**
	 * This method try to register a class annotated with @Program as
	 * a MBean in the JMX server
	 * 
	 * @param beanClass the class name of the Program 
	 */
	public void registerProgramAsMBean(String beanClass) {
		if (this.mbs != null && beanClass != null) {
			try {
				@SuppressWarnings("unchecked")
				Class<? extends Adapter> clazz = (Class<? extends Adapter>) Class
						.forName(beanClass);
				String fqnName = clazz.getCanonicalName();
				String domain = fqnName.substring(0, fqnName.lastIndexOf('.'));
				String type = fqnName.substring(fqnName.lastIndexOf('.') + 1);

				/*
				 * List of types annotated with @In and its Parser class:
				 * adapterArgs[0] : the attribute name adapterArgs[1] : the
				 * canonical name of the corresponding parser class
				 */
				List<ArgEntry> adapterArgs = new ArrayList<ArgEntry>();

				// Search for annotation @ProgramParameter on attributes of
				// class clazz
				for (Field field : clazz.getDeclaredFields()) {
					// Search for an annotation @In
					for (Annotation annotation : field.getAnnotations()) {
						if (annotation.annotationType().equals(In.class)) {
							// Add the attribute where is the @In annotation and
							// the Parser class
							Class<? extends Parser> parserClass = ((In)annotation).parser();
							adapterArgs.add(new ArgEntry(field.getName(), field.getType(), parserClass));
						}
					}
				}

				// Register the bean as a DynamicMBean in the JMX server...
				Class<?>[] cls = clazz.getInterfaces();
				for (Class<?> class1 : cls) {
					System.out.println("=>" + clazz + " implements ["
							+ class1.getCanonicalName() + "]");
				}
				if (Arrays.asList(clazz.getInterfaces()).contains(
						ProgramAdapter.Adapter.class)) {
					DynamicLegacyProgram dprogram = new DynamicLegacyProgram(
							clazz, adapterArgs);
					ObjectName objectName = new ObjectName(String.format(
							beanNameFormat, domain, type));
					this.beanNames.add(objectName);

					if (!mbs.isRegistered(objectName)) {
						LaunchingMessageKind.I0006.format(objectName);
						mbs.registerMBean(dprogram, objectName);
						LaunchingMessageKind.I0007.format(objectName);
					}
				} else {
					LaunchingMessageKind.E0004.format(clazz);
				}
			} catch (Exception e) {
				LaunchingMessageKind.E0005.format(beanClass, e);
			}
		} else {
			LaunchingMessageKind.E0003.format();
		}
	}

	/**
	 * Unregister all registered MBeans in the JMX server.
	 */
	private void unRegisterMBeans() {
		if (beanNames != null) {
			for (ObjectName _name : beanNames) {
				// unRegister the bean in the JMX server...
				try {
					mbs.unregisterMBean(_name);
					LaunchingMessageKind.I0008.format(_name);
				} catch (MBeanRegistrationException e) {
					// Do nothing
				} catch (InstanceNotFoundException e) {
					// Do nothing
				}
			}
			beanNames.clear();
		}
	}
}