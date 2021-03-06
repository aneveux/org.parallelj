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
package org.parallelj.launching.quartz;

import java.lang.reflect.Field;

import org.parallelj.Program;
import org.parallelj.launching.Out;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 *
 */
public final class ProgramFieldsBinder {
	
	private ProgramFieldsBinder() {
	}

	/**
	 * Initialize program's attributes with values of the result JobDataMap.
	 * The program's attributes must be annotated with @Out.
	 * 
	 * @param prog
	 * @param context
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	public static void getProgramOutputFields(final Object prog, final JobExecutionContext context)
			throws NoSuchFieldException, IllegalAccessException {
		final Class<? extends Object> clazz = prog.getClass();
		if (clazz.isAnnotationPresent(Program.class)) {
			final Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].isAnnotationPresent(Out.class)) {
					final boolean wasAccessible = fields[i].isAccessible();
					if (!wasAccessible) {
						fields[i].setAccessible(true);
					}
					final Object oResult = context.getResult();
					if (oResult instanceof JobDataMap){
						final JobDataMap result = (JobDataMap) oResult;
						result.put(fields[i].getName(),fields[i].get(prog));
					}
					if (!wasAccessible) {
						fields[i].setAccessible(false);
					}
				}
			}
		}
	}

}
