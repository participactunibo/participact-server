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

import java.util.List;

import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;

public interface TaskUserService {
	
	TaskUser valutateTaskUser(TaskUser task, boolean decision);

	TaskUser findById(long id);

	TaskUser save(TaskUser task);

	Long getTaskUsersCount();
	
	List<TaskUser> getTaskUsersByOwner(long ownerId, TaskValutation currentTaskValutation);


	List<TaskUser> getTaskUser();
	
	List<TaskUser> getTaskUserByOwner(long ownerId);
	
	boolean deleteTask(long id);
	
	TaskUser getTaskUserByTaskId(long id);


}
