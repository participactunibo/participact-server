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

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.repository.ActionRepository;
import it.unibo.paserver.repository.JpaActionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ActionServiceImpl implements ActionService {

	@Autowired
	ActionRepository actionRepository;

	@Override
	@Transactional(readOnly = false)
	public Action save(Action action) {
		if (actionRepository == null)
			actionRepository = new JpaActionRepository();
		return actionRepository.save(action);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteAction(long id) {
		return actionRepository.deleteAction(id);
	}

	@Override
	public Action findById(long id) {
		return actionRepository.findById(id);
	}

	@Override
	public Action findByName(String name) {
		return actionRepository.findByName(name);
	}

	@Override
	public List<Action> getActions() {
		return actionRepository.getActions();
	}

	@Override
	public Long getActionsCount() {
		return actionRepository.getActionsCount();
	}

	@Override
	public Long getActionsCount(long taskId) {
		return actionRepository.getActionsCount(taskId);
	}

	@Override
	public List<Action> getActions(long taskId) {
		return actionRepository.getActions();
	}

}
