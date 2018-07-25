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
package it.unibo.paserver.service;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.support.Pipeline;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

public interface TaskReportService {

	TaskReport findById(long id);

	TaskReport findByUserAndTask(long userId, long taskId);

	TaskReport save(TaskReport taskReport);

	Long getTaskReportsCount();

	Long getTaskReportsCountByUser(long userId);

	Long getTaskReportsCountByTask(long taskId);

	List<TaskReport> getTaskReports();

	List<TaskReport> getTaskReportsByUser(long userId);

	List<TaskReport> getTaskReportsByTask(long taskId);

	Set<String> getAssignedOfficialEmailByTask(long taskId);

	boolean deleteTaskReport(long id);

	List<TaskReport> getTaskReportsForData(long userId, Pipeline.Type dataType,
			DateTime sampletimestamp);

	List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now);

	void addDataToTaskReports(Long userId, Pipeline.Type dataType,
			List<Data> data);

	TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId, Long ownerId);

	List<TaskReport> getTaskReportsByTask(Long id, TaskState state);

}
