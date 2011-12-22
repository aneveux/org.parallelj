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

package org.parallelj.launching.quartz;

import java.lang.reflect.InvocationTargetException;

import org.parallelj.Programs;
import org.parallelj.internal.reflect.ProgramAdapter.Adapter;
import org.parallelj.launching.LaunchingMessageKind;
import org.parallelj.launching.ReturnCodes;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Implements the Job.execute(..) method
 * 
 * 
 */
privileged public aspect ProgramJobsAdapter percflow (execution(public void Job+.execute(..) throws JobExecutionException)) {

	/**
	 * The JobExecutionContext where get Program's arguments ant where to put
	 * the Result.
	 */
	public JobExecutionContext context;

	/*
	 * The Aspect JobsAdapter must be passed before this.
	 */
	declare precedence :
		org.parallelj.launching.quartz.JobsAdapter;

	private Adapter adpater;

	/**
	 * Launch a Program and initialize the Result as a JobDataMap.
	 * 
	 * @param self
	 * @param context
	 * @throws JobExecutionException
	 */
	void around(Job self, JobExecutionContext context)
			throws JobExecutionException : 
		execution( public void  Job+.execute(..) throws JobExecutionException) 
			&& (within(@org.parallelj.Program *) || within(JobsAdapter)) 
				&& args(context) && this(self) {
		this.adpater = (Adapter) self;
		this.context = context;
		JobDataMap jobDataMap = new JobDataMap();
		context.setResult(jobDataMap);
		jobDataMap.put(QuartzUtils.RETURN_CODE, ReturnCodes.SUCCESS);
		try {
			proceed(self, context);

			try {
				Programs.as((Adapter) self).execute().join();
				ProgramFieldsBinder.getProgramOutputFields(this, context);
			} catch (IllegalAccessException e) {
				jobDataMap.put(QuartzUtils.RETURN_CODE, ReturnCodes.FAILURE);
			} catch (NoSuchFieldException e) {
				jobDataMap.put(QuartzUtils.RETURN_CODE, ReturnCodes.FAILURE);
			}
		} catch (InvocationTargetException e) {
			jobDataMap.put(QuartzUtils.RETURN_CODE, ReturnCodes.FAILURE);
		}
	}

	/**
	 * Intercept Exception thrown in RunnableProcedure for tracing. If an
	 * Exception is thrown, the return code of a Launch becomes FAILURE.
	 * 
	 * @param self
	 */
	void around(Object self) : call(* run(..))
		    && within(org.parallelj.internal.kernel.procedure.RunnableProcedure.RunnableCall)
		    && within(Runnable+)  && this(self)  {
		try {
			proceed(self);
		} catch (Exception e) {
			LaunchingMessageKind.ELAUNCH0002.format(this.adpater, e);
			((JobDataMap) this.context.getResult()).put(
					QuartzUtils.RETURN_CODE, ReturnCodes.FAILURE);
		}
	}
}
