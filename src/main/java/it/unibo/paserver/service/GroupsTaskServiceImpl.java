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

import it.unibo.paserver.domain.GroupsTask;
import it.unibo.paserver.repository.GroupsTaskRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupsTaskServiceImpl implements GroupsTaskService {

	@Autowired
	private GroupsTaskRepository groupsTaskRepository;

	@Override
	public GroupsTask findById(long id) {
		return groupsTaskRepository.findById(id);
	}

	@Override
	public GroupsTask save(GroupsTask account) {
		return groupsTaskRepository.save(account);
	}

	@Override
	public List<GroupsTask> getAllGroupsTasks() {
		return groupsTaskRepository.getAllGroupsTasks();
	}

	@Override
	public List<GroupsTask> getGroupsTaskByUserId(long id) {
		return groupsTaskRepository.getGroupsTaskByUserId(id);
	}

	@Override
	public boolean deleteGroups(long id) {
		return deleteGroups(id);
	}

	@Override
	public boolean deleteAllGroups() {
		return groupsTaskRepository.deleteAllGroups();
	}

	@Override
	public List<GroupsTask> getGroupsTaskByGroupId(long id) {
		return groupsTaskRepository.getGroupsTaskByGroupId(id);
	}

	@Override
	public boolean isUserPresentInGroup(long id) {
		return groupsTaskRepository.isUserPresentInGroup(id);
	}

}
