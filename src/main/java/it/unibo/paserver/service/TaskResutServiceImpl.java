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
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.repository.TaskResultRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskResutServiceImpl implements TaskResultService {

	private static final Logger logger = LoggerFactory
			.getLogger(TaskResutServiceImpl.class);

	@Autowired
	TaskResultRepository taskResultRepository;

	@Autowired
	TaskReportService taskReportRepository;

	@Override
	public TaskResult findById(long id) {
		return updateTaskResult(taskResultRepository.findById(id));
	}

	@Override
	@Transactional(readOnly = false)
	public TaskResult save(TaskResult taskResult) {
		return taskResultRepository.save(taskResult);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTaskResult(long id) {
		return taskResultRepository.deleteTaskResult(id);
	}

	@Override
	public List<TaskResult> getTaskResults() {
		return updateTaskResults(taskResultRepository.getTaskResults());
	}

	@Override
	public Long getTaskResultsCount() {
		return taskResultRepository.getTaskResultsCount();
	}

	@Override
	public TaskResult findByUserAndTask(long userId, long taskId) {
		return updateTaskResult(taskResultRepository.findByUserAndTask(userId,
				taskId));
	}

	@Override
	public Long getTaskResultsCountByUser(long userId) {
		return taskResultRepository.getTaskResultsCountByUser(userId);
	}

	@Override
	public Long getTaskResultsCountByTask(long taskId) {
		return taskResultRepository.getTaskResultsCountByTask(taskId);
	}

	@Override
	public List<TaskResult> getTaskResultsByTask(long taskId) {
		return updateTaskResults(taskResultRepository
				.getTaskResultsByTask(taskId));
	}

	@Override
	public List<TaskResult> getTaskResultsByUser(long userId) {
		return updateTaskResults(taskResultRepository
				.getTaskResultsByUser(userId));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private TaskResult updateTaskResult(TaskResult taskResult) {
		return taskResultRepository.updateTaskResult(taskResult, false);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	private List<TaskResult> updateTaskResults(Iterable<TaskResult> taskResults) {
		List<TaskResult> result = new ArrayList<TaskResult>();
		for (TaskResult taskResult : taskResults) {
			result.add(taskResultRepository.updateTaskResult(taskResult, false));
		}
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public TaskResult getTaskResultByTaskReport(long taskReportId) {
		TaskReport taskReport = taskReportRepository.findById(taskReportId);
		TaskResult taskResult = taskResultRepository.findByUserAndTask(
				taskReport.getUser().getId(), taskReport.getTask().getId());
		return updateTaskResult(taskResult);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskResult addData(long taskId, long userId, Data data) {
		return taskResultRepository.addData(taskId, userId, data);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskResult addData(long taskId, long userId,
			Collection<? extends Data> data) {
		return taskResultRepository.addData(taskId, userId, data);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskResult getTaskResultByTaskReport(long taskReportId,
			boolean initData) {
		if (initData) {
			TaskResult result = getTaskResultByTaskReport(taskReportId);
			logger.debug(
					"Loaded TaskResult for taskReport {}, size {} elements",
					taskReportId, result.getData().size());
			Hibernate.initialize(result.getData());
			return result;
		} else {
			return getTaskResultByTaskReport(taskReportId);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void updateTaskResult(long id) {
		TaskResult taskResult = findById(id);
		taskResultRepository.updateTaskResult(taskResult, true);
	}

}
