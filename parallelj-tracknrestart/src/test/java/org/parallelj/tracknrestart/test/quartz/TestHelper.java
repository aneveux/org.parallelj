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
package org.parallelj.tracknrestart.test.quartz;

//import org.parallelj.tracknrestart.jdbc.JDBCSupport;
import org.parallelj.tracknrestart.jdbc.JDBCSupport;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.SchedulerPlugin;

public class TestHelper extends JDBCSupport implements SchedulerPlugin {

	private String name;
	private Scheduler scheduler;
	private static TestHelper instance;
	
	public static String req = 
	"		select aa.job_group, aa.job_name, aa.uid, aa.restarted_uid, aa.result, " +
	"	    coalesce(s.success,0) as success, " +
	"	    coalesce(aa.failure,0) as failure, " +
	"	    coalesce(s.success,0)+coalesce(aa.failure,0) as total from " +
	"	(" +
	"	    SELECT a.job_group, a.job_name, a.uid, a.restarted_uid, a.result, f.failure FROM qrtz_track_job_details as a " +
	"	    left outer join " +
	"	    (SELECT uid,count(*) as failure FROM qrtz_track_iterations where success=0 group by uid) as f " +
	"	    on a.uid = f.uid " +
	"	) aa " +
	"	left outer join " +
	"	(SELECT uid,count(*) as success FROM qrtz_track_iterations where success=1 group by uid) as s " +
	"	on aa.uid = s.uid " +
	"	order by aa.job_group asc, aa.job_name asc, aa.uid asc ";
			
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	@Override
	public void initialize(String pname, Scheduler scheduler)
			throws SchedulerException {
		this.name = pname;
		this.scheduler = scheduler;
		instance = this;
	}

	public static TestHelper getInstance() {
		return instance;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
