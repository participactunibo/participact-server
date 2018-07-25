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
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

public interface TaskService {

	Task findById(long id);

	Task save(Task task);

	Long getTasksCount();

	Long getTasksCount(long userId);

	List<Task> getTasks();

	List<Task> getTasksByUser(long userId);

	List<Task> getTasksByUser(long userId, TaskState taskCurrentState);

	List<Task> getAvailableTasksByUser(long userId, TaskState taskCurrentState,
			DateTime dateTime);

	List<Task> getTasksByAction(long actionId);

	Task assignTaskToUsers(Task task, Collection<String> users);

	Task addUsersToTask(long taskId, Collection<String> users);

	boolean deleteTask(long id);
	
	//List<? extends Task> getTaskUser();

	List<Task> getTaskbyOwner(long userId);

	List<Task> getTaskbyOwner(long userId,
			TaskValutation currentTaskValutation);


	Task findByIdAndOwner(Long taskId, Long id);
	
	List<Task> getTasksByDate(DateTime start, DateTime end, String type);


	List<Task> getAvailableAdminTasksByUser(Long userId, TaskState available,
			DateTime dateTime);

	List<Task> getAvailableUserTasksByUser(Long userId, TaskState available,
			DateTime dateTime);

	List<Task> getAdminTasksByUser(Long userId, TaskState taskState);

	List<Task> getUserTasksByUser(Long userId, TaskState taskState);
	
	List<Task> getAdminTasks();
	
	


}
