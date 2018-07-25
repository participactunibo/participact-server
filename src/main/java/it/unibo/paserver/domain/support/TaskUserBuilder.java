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
package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.User;

import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TaskUserBuilder extends EntityBuilder<TaskUser> {

	@Override
	void initEntity() {
		entity = new TaskUser();

	}

	public TaskUserBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskUserBuilder setTask(Task task)
	{
		entity.setTask(task);
		return this;
	}

	public TaskUserBuilder setApproved(TaskValutation valutation) {
		entity.setValutation(valutation);
		return this;
	}


	public TaskUserBuilder setOwner(User owner) {
		entity.setOwner(owner);
		return this;
	}
	
	public TaskUserBuilder setUsersToAssign(Set<User> users){
		entity.setUsersToAssign(users);
		return this;
	}

	@Override
	TaskUser assembleEntity() {
		return entity;
	}

}
