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
package org.parallelj.internal;

import org.parallelj.internal.util.Formatter;
import org.parallelj.internal.util.Formatter.Format;

/**
 * This enumeration contains the types of messages logged by //J.
 * 
 * @author Atos Worldline
 * @since 0.4.0
 */
public enum MessageKind {
	
	/**Error: invalid argument when creating a new Processor.
	 * 
	 */
	@Format("Invalid argument [%2$s] for kind [%1$s]")
	E0001,
	
	/**Error: Can't find configuration file.
	 * 
	 */
	@Format("Can't find configuration file. Make sure [%s] is in the classpath!!")
	E0002,
	
	/**
	 * Info: building a program
	 */
	@Format("building program [%s]")
	I0001,
	
	/**
	 * Warning: member not found in a type.
	 */
	@Format("member [%s] not found in type [%s]")	
	W0001,
	
	/**
	 * Warning: a type is not assignable to another one.
	 * 
	 */
	W0002,
	
	/**
	 * Warning: runtime exception caught by //J. 
	 * 
	 */
	W0003,
	
	/**
	 * Warning: operation interrupted
	 */
	@Format("Operation interrupted [%s]")
	W0004;
	
	
	/**
	 * Method used to format a message
	 * 
	 * @param args
	 *            the arguments used to format the message
	 * @return the formatted message
	 */
	public String format(Object... args) {
		// delegates to formatter
		return formatter.print(this, args);
	}

	
	static Formatter<MessageKind> formatter = new Formatter<MessageKind>(MessageKind.class);

}
