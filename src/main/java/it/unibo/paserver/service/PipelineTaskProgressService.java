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

import java.util.List;
import java.util.Set;

public interface PipelineTaskProgressService {

	PipelineTaskProgress findById(long id);

	PipelineTaskProgress save(PipelineTaskProgress pipelineTaskProgress);

	List<PipelineTaskProgress> getAll();

	boolean delete(long id);

	boolean deleteAll();

	List<Long> getAllId();

	List<PipelineTaskProgress> findByTaskStepId(long id);

	int getMaxStepByPipelineId(long id, long groupId);

	List<String> getAllResponseByActionPipelineId(long id);

	Set<Long> getAllPipelineTaskId();
	
	Set<Long> getAllStepTaskId();
}
