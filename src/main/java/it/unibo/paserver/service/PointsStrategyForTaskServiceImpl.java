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

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.repository.PointsStrategyForTaskRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of PointsStrategyForTaskService using
 * PointsStrategyForTaskRepository
 * 
 *
 * @see PointsStrategyForTaskService
 * @see PointsStrategyForTaskRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class PointsStrategyForTaskServiceImpl implements
		PointsStrategyForTaskService {

	@Autowired
	PointsStrategyForTaskRepository pointsStrategyForTaskRepository;

	@Override
	@Transactional(readOnly = false)
	public PointsStrategyForTask save(
			PointsStrategyForTask pointsStrategyForTask) {
		return pointsStrategyForTaskRepository.save(pointsStrategyForTask);
	}

	@Override
	@Transactional(readOnly = false)
	public PointsStrategyForTask create(Task task, Integer strategyId) {
		return pointsStrategyForTaskRepository.create(task, strategyId);
	}

	@Override
	public PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId) {
		return pointsStrategyForTaskRepository
				.getPointsStrategyForTaskByTask(taskId);
	}

	@Override
	public List<PointsStrategyForTask> getPointsStrategyForTasks() {
		return pointsStrategyForTaskRepository.getPointsStrategyForTasks();
	}

	@Override
	public Long getPointsStrategyForTaskCount() {
		return pointsStrategyForTaskRepository.getPointsStrategyForTaskCount();
	}

}
