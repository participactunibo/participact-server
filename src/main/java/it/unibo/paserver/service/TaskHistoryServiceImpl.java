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

import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.repository.TaskHistoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskHistoryServiceImpl implements TaskHistoryService {

	@Autowired
	TaskHistoryRepository taskHistoryRepository;

	@Override
	public TaskHistory findById(long id) {
		return taskHistoryRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskHistory save(TaskHistory taskHistory) {
		return taskHistoryRepository.save(taskHistory);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTaskHistory(long id) {
		return taskHistoryRepository.deleteTaskHistory(id);
	}

	@Override
	public List<TaskHistory> getTaskHistories() {
		return taskHistoryRepository.getTaskHistories();
	}

	@Override
	public Long getTaskHistoriesCount() {
		return taskHistoryRepository.getTaskHistoriesCount();
	}

	@Override
	public Long getTaskHistoriesCount(long taskReportId) {
		return taskHistoryRepository.getTaskHistoriesCount(taskReportId);
	}

	@Override
	public Long getTaskHistoriesCount(long userId, long taskId) {
		return taskHistoryRepository.getTaskHistoriesCount(userId, taskId);
	}

	@Override
	public List<TaskHistory> getTaskHistories(long taskReportId) {
		return taskHistoryRepository.getTaskHistories(taskReportId);
	}

	@Override
	public List<TaskHistory> getTaskHistories(long userId, long taskId) {
		return taskHistoryRepository.getTaskHistories(userId, taskId);
	}

}
