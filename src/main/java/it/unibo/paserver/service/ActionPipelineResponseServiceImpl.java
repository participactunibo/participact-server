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

import it.unibo.paserver.domain.ActionPipelineResponse;
import it.unibo.paserver.repository.ActionPipelineResponseRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionPipelineResponseServiceImpl implements
		ActionPipelineResponseService {

	@Autowired
	private ActionPipelineResponseRepository actionPipelineResponseRepository;

	@Override
	public ActionPipelineResponse findById(long id) {
		return actionPipelineResponseRepository.findById(id);
	}

	@Override
	public List<ActionPipelineResponse> getAll() {
		return actionPipelineResponseRepository.getAll();
	}

	@Override
	public ActionPipelineResponse save(
			ActionPipelineResponse actionPipelineResponse) {
		return actionPipelineResponseRepository.save(actionPipelineResponse);
	}

	@Override
	public boolean delete(long id) {
		return actionPipelineResponseRepository.delete(id);
	}

	@Override
	public boolean deleteAll() {
		return actionPipelineResponseRepository.deleteAll();
	}

	@Override
	public List<Long> getAllId() {
		return actionPipelineResponseRepository.getAllId();
	}

	@Override
	public List<ActionPipelineResponse> getResponseByTaskId(long id) {
		return actionPipelineResponseRepository.getResponseByTaskId(id);
	}

}
