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
import it.unibo.paserver.domain.support.Pipeline.Type;
import it.unibo.paserver.repository.DataRepository;
import it.unibo.paserver.repository.TaskReportRepository;
import it.unibo.paserver.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskReportServiceImpl implements TaskReportService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskReportServiceImpl.class);

	public static final int SAMPLES_TOLERANCE = 30;

	@Autowired
	TaskReportRepository taskReportRepository;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	DataRepository dataRepository;

	@Override
	public TaskReport findById(long id) {
		return taskReportRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskReport save(TaskReport taskReport) {
		return taskReportRepository.save(taskReport);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTaskReport(long id) {
		return taskReportRepository.deleteTaskReport(id);
	}

	@Override
	public List<TaskReport> getTaskReports() {
		return taskReportRepository.getTaskReports();
	}

	@Override
	public Long getTaskReportsCount() {
		return taskReportRepository.getTaskReportsCount();
	}

	@Override
	public TaskReport findByUserAndTask(long userId, long taskId) {
		return taskReportRepository.findByUserAndTask(userId, taskId);
	}

	@Override
	public Long getTaskReportsCountByUser(long userId) {
		return taskReportRepository.getTaskReportsCountByUser(userId);
	}

	@Override
	public Long getTaskReportsCountByTask(long taskId) {
		return taskReportRepository.getTaskReportsCountByTask(taskId);
	}

	@Override
	public List<TaskReport> getTaskReportsByUser(long userId) {
		return taskReportRepository.getTaskReportsByUser(userId);
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(long taskId) {
		return taskReportRepository.getTaskReportsByTask(taskId);
	}

	@Override
	public List<TaskReport> getTaskReportsForData(long userId,
			Pipeline.Type dataType, DateTime sampleTimestamp) {
		List<TaskReport> taskReportsResult = taskReportRepository
				.getTaskReportsForData(userId, dataType, sampleTimestamp);
		return taskReportsResult;
	}

	@Override
	@Transactional(readOnly = false)
	public void addDataToTaskReports(Long userId, Type dataType, List<Data> data) {
		if (data.size() == 0) {
			logger.debug("Trying to save an empty list");
			return;
		}
		Map<TaskReport, List<Data>> dataForTaskReports = new HashMap<TaskReport, List<Data>>();
		// sort data by sampling timestamp
		Collections.sort(data, new CompareData());
		DateTime targetTime = null;
		List<TaskReport> lastTaskReports = null;
		for (Data d : data) {
			/*
			 * Assume that samples whose sampling time is less than 30 minutes
			 * from the first one belong to the same task reports
			 */
			if (targetTime == null
					|| Minutes.minutesBetween(targetTime,
							d.getSampleTimestamp()).getMinutes() > SAMPLES_TOLERANCE) {
				targetTime = d.getSampleTimestamp();
				lastTaskReports = getTaskReportsForData(userId, dataType,
						targetTime);
			}
			// save data and add it to targetTaskReports
			Data newData = dataRepository.save(d);
			for (TaskReport tr : lastTaskReports) {
				if (!dataForTaskReports.containsKey(tr)) {
					dataForTaskReports.put(tr, new ArrayList<Data>());
				}
				dataForTaskReports.get(tr).add(newData);
			}
		}
		for (TaskReport tr : dataForTaskReports.keySet()) {
			List<Data> dataToAdd = dataForTaskReports.get(tr);
			tr.getTaskResult().getData().addAll(dataToAdd);
			taskReportRepository.save(tr);
		}
	}

	@Override
	public List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now) {
		return taskReportRepository.getExpiredTaskReportStillAvailable(now);
	}

	private static class CompareData implements Comparator<Data> {

		@Override
		public int compare(Data o1, Data o2) {
			if (o1 == null) {
				return 1;
			}
			if (o2 == null) {
				return -1;
			}
			if (o1.getSampleTimestamp() == null) {
				return 1;
			}
			if (o2.getSampleTimestamp() == null) {
				return -1;
			}
			return o1.getSampleTimestamp().compareTo(o2.getSampleTimestamp());
		}
	}

	@Override
	public Set<String> getAssignedOfficialEmailByTask(long taskId) {
		return taskReportRepository.getAssignedOfficialEmailByTask(taskId);
	}

	@Override
	public TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId,
			Long ownerId) {
		return taskReportRepository.findByUserAndTaskAndOwner(userId, taskId,
				ownerId);
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(Long id, TaskState state) {
		return taskReportRepository.getTaskReportsByTask(id,state);
	}

}
