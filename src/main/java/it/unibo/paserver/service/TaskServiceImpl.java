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

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.TaskReportRepository;
import it.unibo.paserver.repository.TaskRepository;
import it.unibo.paserver.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TaskReportRepository taskReportRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskServiceImpl.class);

	@Override
	public Task findById(long id) {
		return taskRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public Task save(Task task) {
		return taskRepository.save(task);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTask(long id) {
		return taskRepository.deleteTask(id);
	}

	@Override
	public List<Task> getTasks() {
		return taskRepository.getTasks();
	}

	@Override
	public Long getTasksCount() {
		return taskRepository.getTasksCount();
	}

	@Override
	public Long getTasksCount(long userId) {
		return taskRepository.getTasksCount(userId);
	}

	@Override
	public List<Task> getTasksByUser(long userId) {
		return taskRepository.getTasksByUser(userId);
	}

	@Override
	public List<Task> getTasksByUser(long userId, TaskState taskCurrentState) {
		return taskRepository.getTasksByUser(userId, taskCurrentState);
	}

	@Override
	public List<Task> getTasksByAction(long actionId) {
		return taskRepository.getTasksByAction(actionId);
	}

	@Override
	@Transactional(readOnly = false)
	public Task assignTaskToUsers(Task task, Collection<String> users) {
		task.setTaskReport(new LinkedHashSet<TaskReport>());
		for (String user : users) {

			TaskReport taskReport = new TaskReport();
			taskReport.setUser(userRepository.findByEmail(user));
			taskReport.setTask(task);
			taskReport.setHistory(new ArrayList<TaskHistory>());
			taskReport.setCurrentState(TaskState.AVAILABLE);
			task.getTaskReport().add(taskReport);

			TaskHistory history = new TaskHistory();
			history.setState(TaskState.AVAILABLE);
			history.setTaskReport(taskReport);
			history.setTimestamp(new DateTime());
			taskReport.getHistory().add(history);
		}
		Task t = taskRepository.save(task);
		logger.debug("Saved task id {} description \"{}\"", t.getId(),
				t.getDescription());
		return t;
	}

	@Override
	public List<Task> getAvailableTasksByUser(long userId,
			TaskState taskCurrentState, DateTime dateTime) {
		return taskRepository.getAvailableTasksByUser(userId, taskCurrentState,
				dateTime);
	}

	@Override
	@Transactional(readOnly = false)
	public Task addUsersToTask(long taskId, Collection<String> users) {
		users = Validate.notNull(users);
		users = Validate.noNullElements(users);

		Task task = taskRepository.findById(taskId);
		
		
		if (task == null) {
			logger.error("Task {} not found, unable to add users to it", taskId);
			throw new IllegalArgumentException("Task " + taskId
					+ " not found, unable to add users to it");
		}

		if (task.getDeadline().isBefore(new DateTime())) {
			logger.error(
					"Trying adding users to task {} which is already expired",
					taskId);
			throw new IllegalArgumentException("Task " + taskId
					+ "already expired");
		}
		for (String user : users) {
			User u = userRepository.findByEmail(user);
			if (u == null) {
				logger.error("Cannot add unknown user {} to task {}", user,
						task.getId());
				throw new IllegalArgumentException("Unknown user " + user);
			}
			
			try {
				TaskReport taskReport = taskReportRepository.findByUserAndTask(
						u.getId(), taskId);
				if (taskReport != null) {
					logger.error(
							"Trying to assing task {} to user {} that is already assigned to it",
							taskId, user);
					throw new IllegalArgumentException();
				}
			} catch (NoResultException e) {
			}
			TaskReport taskReport = new TaskReport();
			taskReport.setUser(u);
			taskReport.setTask(task);
			taskReport.setHistory(new ArrayList<TaskHistory>());
			task.getTaskReport().add(taskReport);

			TaskHistory history = new TaskHistory();
			history.setState(TaskState.AVAILABLE);
			history.setTaskReport(taskReport);
			history.setTimestamp(new DateTime());
			taskReport.addHistory(history);
			logger.debug("Added user {} to task {}", u.getOfficialEmail(),
					task.getId());
		}
		task = taskRepository.save(task);
		return null;
	}

	

	@Override
	public List<Task> getTaskbyOwner(long userId) {
		return taskRepository.getTasksByOwner(userId);
	}

	

	@Override
	public Task findByIdAndOwner(Long taskId, Long ownerId) {
		return taskRepository.findByIdAndOwner(taskId, ownerId);
	}

	@Override
	public List<Task> getTaskbyOwner(long userId,
			TaskValutation currentTaskValutation) {
		return taskRepository.getTasksByOwner(userId, currentTaskValutation);

	}

	@Override
	public List<Task> getAvailableAdminTasksByUser(Long id,TaskState taskState, DateTime dateTime) {
		return taskRepository.getAvailableAdminTasksByUser(id,taskState,dateTime);
	}

	@Override
	public List<Task> getAvailableUserTasksByUser(Long id, TaskState taskState,	DateTime dateTime) {
		return taskRepository.getAvailableUserTasksByUser(id,taskState,dateTime);
	}

	@Override
	public List<Task> getAdminTasksByUser(Long userId, TaskState taskState) {
		return taskRepository.getAdminTasksByUser(userId,taskState);
	}

	@Override
	public List<Task> getUserTasksByUser(Long userId, TaskState taskState) {
		return taskRepository.getUserTasksByUser(userId,taskState);
	}

	@Override
	public List<Task> getAdminTasks() {
		return taskRepository.getTaskByAdmin();
	}

	@Override
	public List<Task> getTasksByDate(DateTime start, DateTime end, String type) {
		if(type.equals("all"))
			return taskRepository.getTasksByDate(start,end);
		else if(type.equals("user"))
			return taskRepository.getTasksUserByDate(start,end);
		else if(type.equals("admin"))
			return taskRepository.getTaskByAdmin(start,end);
		return null;
	}

	

}
