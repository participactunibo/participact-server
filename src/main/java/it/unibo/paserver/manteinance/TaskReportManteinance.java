/*******************************************************************************
 * Participact
 * Copyright 2013-2018 Alma Mater Studiorum - Università di Bologna
 * 
 * This file is part of ParticipAct.
 * 
 * ParticipAct is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License.
 * 
 * ParticipAct is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with ParticipAct. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package it.unibo.paserver.manteinance;

import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.service.TaskReportService;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskReportManteinance {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskReportManteinance.class);

	@Autowired
	TaskReportService taskReportService;

	@Scheduled(fixedDelay = 3600000)
	public void setAvailableExpiredTasksToRejected() {
		logger.info("Setting task still AVAILABLE past their availbility date to IGNORED.");
		List<TaskReport> taskReports = taskReportService
				.getExpiredTaskReportStillAvailable(new DateTime());
		for (TaskReport taskReport : taskReports) {
			TaskHistory taskHistory = new TaskHistory();
			taskHistory.setState(TaskState.IGNORED);
			taskHistory.setTaskReport(taskReport);
			taskHistory.setTimestamp(taskReport.getTask().getDeadline());
			taskReport.addHistory(taskHistory);
			taskReportService.save(taskReport);
		}
	}
}
