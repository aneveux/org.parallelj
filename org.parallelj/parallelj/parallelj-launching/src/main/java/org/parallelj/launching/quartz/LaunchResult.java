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

import org.parallelj.launching.LaunchingMessageKind;
import org.quartz.JobDataMap;

/**
 * The Result Object of a Program launching.
 *
 */
public class LaunchResult {
	/**
	 * The JobId generated by Quartz. 
	 */
	private String jobId;
	
	/**
	 * The result from the Quartz JobExecutionContext.
	 */
	private JobDataMap result;
	
	/**
	 * The canonicalName 
	 */
	private String canonicalName;
	
	/**
	 * Default Constructor.
	 * 
	 * @param jobId The JobId generated by Quartz.
	 * @param result The result Object from the Quartz JobExecutionContext.
	 * @param canonicalName The canonicalName of class.
	 */
	public LaunchResult(final String jobId, final JobDataMap result, final String canonicalName) {
		this.jobId = jobId;
		this.result = result;
		this.canonicalName = canonicalName;
	}

	/**
	 * Get the JobId
	 * @return the JobId
	 */
	public String getJobId() {
		return jobId;
	}

	/**
	 * Get the result object from the JobExecutionContext.
	 * 
	 * @return the result object from the JobExecutionContext.
	 */
	public JobDataMap getResult() {
		return result;
	}
	
	/**
	 * Get the CanonicalName
	 * @return the CanonicalName
	 */
	public String getCanonicalName() {
		return canonicalName;
	}

	@Override
	public String toString() {
		return LaunchingMessageKind.IQUARTZ0003.getFormatedMessage(
				this.getCanonicalName(), this.getJobId(),
				this.getResult().get(QuartzUtils.RETURN_CODE));
	}
}
