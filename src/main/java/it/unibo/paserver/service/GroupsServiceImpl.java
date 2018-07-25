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

import it.unibo.paserver.domain.DataGroups;
import it.unibo.paserver.repository.GroupsRepository;
import it.unibo.paserver.repository.JpaGroupsRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupsServiceImpl implements GroupsService {

	@Autowired
	private GroupsRepository groupsRepository;

	@Override
	@Transactional(readOnly = true)
	public DataGroups findById(long id) {
		return groupsRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public DataGroups save(DataGroups group) {
		return groupsRepository.save(group);
	}

	@Override
	@Transactional(readOnly = true)
	public List<DataGroups> getAllGroups() {
		return groupsRepository.getAllGroups();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteGroups(long id) {
		return groupsRepository.deleteGroups(id);
	}

	@Override
	public List<DataGroups> getGroupsByUserId(long id) {
		// TODO Auto-generated method stub
		return groupsRepository.getGroupsByUserId(id);
	}

	@Override
	public boolean deleteAllGroups() {
		return groupsRepository.deleteAllGroups();
	}

	@Override
	public List<Long> getAllId() {
		return groupsRepository.getAllId();
	}

	@Override
	public long getNextId() {
		return groupsRepository.getNextId();
	}

	@Override
	public List<Long> getAllGroupsId() {
		return groupsRepository.getAllGroupsId();
	}

}
