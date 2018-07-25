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
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.repository.TaskRepository;
import it.unibo.paserver.repository.TaskUserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional(readOnly = true)
public class TaskUserServiceImpl implements TaskUserService {



@Autowired
TaskUserRepository taskUserRepository;
	
	
	@Override
	@Transactional(readOnly = false)
	public TaskUser valutateTaskUser(TaskUser taskUser, boolean decision) {
		if (decision)
			taskUser.setValutation(TaskValutation.APPROVED);
		else
			taskUser.setValutation(TaskValutation.REFUSED);
		TaskUser t = taskUserRepository.save(taskUser);
		return t;
	}

	@Override
	public TaskUser findById(long id) {
		return taskUserRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public TaskUser save(TaskUser task) {
		return taskUserRepository.save(task);
	}

	@Override
	public Long getTaskUsersCount() {
		return taskUserRepository.getTaskUsersCount();
	}

	@Override
	public List<TaskUser> getTaskUsersByOwner(long ownerId,
			TaskValutation currentTaskValutation) {
		return taskUserRepository.getTaskUsersByOwner(ownerId, currentTaskValutation);
	}

	@Override
	public List<TaskUser> getTaskUser() {
		// TODO Auto-generated method stub
		return taskUserRepository.getTaskUser();
	}
	
	

	@Override
	public List<TaskUser> getTaskUserByOwner(long ownerId) {
		return taskUserRepository.getTaskUserByOwner(ownerId);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteTask(long id) {
		return taskUserRepository.deleteTask(id);
	}

	@Override
	public TaskUser getTaskUserByTaskId(long id) {
		return taskUserRepository.getTaskUserByTaskId(id);
	}

}
