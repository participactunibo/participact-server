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

import it.unibo.paserver.domain.PipelineTaskProgress;
import it.unibo.paserver.repository.PipelineTaskProgressRepository;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PipelineTaskProgressServiceImpl implements
		PipelineTaskProgressService {

	@Autowired
	private PipelineTaskProgressRepository pipelineTaskProgressRepository;

	@Override
	public PipelineTaskProgress findById(long id) {
		return pipelineTaskProgressRepository.findById(id);
	}

	@Override
	public PipelineTaskProgress save(PipelineTaskProgress pipelineTaskProgress) {
		return pipelineTaskProgressRepository.save(pipelineTaskProgress);
	}

	@Override
	public List<PipelineTaskProgress> getAll() {
		return pipelineTaskProgressRepository.getAll();
	}

	@Override
	public boolean delete(long id) {
		return pipelineTaskProgressRepository.delete(id);
	}

	@Override
	public boolean deleteAll() {
		return pipelineTaskProgressRepository.deleteAll();
	}

	@Override
	public List<Long> getAllId() {
		return pipelineTaskProgressRepository.getAllId();
	}

	@Override
	public List<PipelineTaskProgress> findByTaskStepId(long id) {
		return pipelineTaskProgressRepository.findByTaskStepId(id);
	}

	@Override
	public int getMaxStepByPipelineId(long id, long groupId) {
		return pipelineTaskProgressRepository.getMaxStepByPipelineId(id,
				groupId);
	}

	@Override
	public List<String> getAllResponseByActionPipelineId(long id) {
		return pipelineTaskProgressRepository
				.getAllResponseByActionPipelineId(id);
	}

	@Override
	public Set<Long> getAllPipelineTaskId() {
		return pipelineTaskProgressRepository.getAllPipelineTaskId();
	}

	@Override
	public Set<Long> getAllStepTaskId() {
		return pipelineTaskProgressRepository.getAllStepTaskId();
	}
}
