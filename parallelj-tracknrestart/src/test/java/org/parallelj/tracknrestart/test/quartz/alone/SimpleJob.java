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
package org.parallelj.tracknrestart.test.quartz.alone;


import org.parallelj.tracknrestart.ReturnCodes;
import org.parallelj.tracknrestart.listeners.ForEachListener;
import org.parallelj.tracknrestart.plugins.TrackNRestartPluginAll;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@PersistJobDataAfterExecution
public class SimpleJob implements Job {

	private static Logger log = LoggerFactory.getLogger(SimpleJob.class);

	int iterationNumber = 10;
	
	int nbrSuccess,nbrFailure = 0;
	
	JobDataMap result = new JobDataMap();

	public SimpleJob() {
	}

	public void execute(JobExecutionContext context)
			throws JobExecutionException {

		JobDetail jobDetail = context.getJobDetail();
		JobDataMap jobDataMap = jobDetail.getJobDataMap();

		try {

			// Le listener est toujours pr�sent (� condition d'avoir activ� le 'initial/track' ou le 'restart/track' i.e. configur� le 'TrackJobPlugin')
			ForEachListener forEachListener = (ForEachListener) jobDataMap.get(TrackNRestartPluginAll.FOR_EACH_LISTENER);

			// En mode 'initial/track' simple 'restartedFireInstanceId' est null, 
			// en mode 'restart/track' 'restartedFireInstanceId' contient l'id du job � re-starter 
			String restartedFireInstanceId = jobDataMap.getString(TrackNRestartPluginAll.RESTARTED_FIRE_INSTANCE_ID);

			boolean reconductSucceeded = true; // toujours 'true' � priori
			for (int element = 0; element < iterationNumber; element++) {
				if (restartedFireInstanceId == null) { // mode 'initial/track'
					boolean pair = simulateBusiness(element); // le traitement de l'�l�ment est tent� pour la premi�re fois 
									   				  // et le r�sultat de la tentative (succ�s ou �chec) est consign� (pair=succes,impair=echec)
					if (pair) { // succes
						if (reconductSucceeded) {
							forEachListener.forEachInstanceComplete(String.valueOf(element), true);
						}
					} else { // echec
						forEachListener.forEachInstanceComplete(String.valueOf(element), false);
					}
					
				} else { // mode 'restart/track'
					// V�rifier si l'�l�ment a d�j� �t� trait� pr�c�demment et d�terminer s'il s'agit d'un �chec ou d'un succ�s
					// Remarque : si l'�l�ment n'a pas �t� trait� pr�c�demment, il est consid�r� par d�faut comme un �chec 
					boolean wasSuccess = forEachListener.isForEachInstanceIgnorable(restartedFireInstanceId, String.valueOf(element));
					boolean wasFailure = !wasSuccess;
					if (wasFailure) { // L'�l�ment n'a pas encore �t� trait� ou bien trait� pr�c�demment avec �chec 
						boolean pair = simulateBusiness(element); // son traitement est re-tent� 
														  // et le r�sultat de la tentative (succ�s ou �chec) est consign� (pair=succes,impair=echec)
						forEachListener.forEachInstanceComplete(String.valueOf(element), pair);
					} else {
						// L'�l�ment a �t� trait� pr�c�demment avec succ�s,
						if (reconductSucceeded) {
							// L'�l�ment est ignor� mais son succ�s pr�c�dent est tout de m�me consign� � nouveau pour cette ex�cution du job
							forEachListener.forEachInstanceComplete(String.valueOf(element), true);
						} else {
							// L'�l�ment est ignor� et son succ�s pr�c�dent n'est pas consign� � nouveau pour cette ex�cution du job
						}
					}
				}
			}
			result.put("nbrExpected", iterationNumber);
			result.put("nbrSuccess", nbrSuccess);
			result.put("nbrFailure", nbrFailure);
			result.put("currentFireInstanceId", context.getFireInstanceId());
			result.put(TrackNRestartPluginAll.RESTARTED_FIRE_INSTANCE_ID, restartedFireInstanceId);
			if (nbrFailure==0){
				result.put("RETURN_CODE",ReturnCodes.SUCCESS);
			} else {
				result.put("RETURN_CODE",ReturnCodes.FAILURE);
			}
//			context.getScheduler().addJob(jobDetail,true);
		} catch (Exception e) {
			result.put("RETURN_CODE",ReturnCodes.ABORTED);
			JobExecutionException jexex = new JobExecutionException(e, false);
			throw jexex;
		} finally {
			context.setResult(result);
		}
	}

	private boolean simulateBusiness(int element) {
		boolean success = (Math.floor(Math.random()*1000))%2==0;
		if (success) 
			nbrSuccess++;
		else
			nbrFailure++;
		return success;
	}

}
