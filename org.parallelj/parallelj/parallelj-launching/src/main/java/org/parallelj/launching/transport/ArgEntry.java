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

package org.parallelj.launching.transport;

import org.parallelj.launching.parser.Parser;

/**
 * Represents a Program argument for a remote launching. 
 *
 */
public class ArgEntry {
	private String name;
	private Class<?> type;
	private Class<? extends Parser> parser;

	/**
	 * Default constructor
	 * 
	 * @param name
	 * @param type
	 * @param parserClass
	 */
	public ArgEntry(String name, Class<?> type, Class<? extends Parser> parserClass) {
		this.name = name;
		this.type = type;
		this.parser = parserClass;
	}

	@Override
	public String toString() {
		return "ArgEntry=>name["+this.name+"]_type["+this.type+"]_parser:["+this.parser+"]";
	}

	/**
	 * Get the name of the Program field corresponding to this ArgEntry 
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the Type of the program field corresponding to this ArgEntry
	 * 
	 * @return
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * Get the Type of the Parser to use to convert a String to a complex Type.
	 * The String value comes from a remote launching. 
	 * 
	 * @return
	 */
	public Class<? extends Parser> getParser() {
		return parser;
	}
}
