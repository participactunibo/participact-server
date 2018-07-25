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

import java.util.List;

/**
 * Service for PointsStrategyForTask entities.
 * 
 *
 * @see PointsStrategyForTask
 *
 */
public interface PointsStrategyForTaskService {

	/**
	 * Save given PointsStrategyForTask.
	 * 
	 * @param pointsStrategyForTask
	 *            PointsStrategyForTask to save
	 * @return saved PointsStrategyForTask
	 * @see PointsStrategyForTask
	 */
	PointsStrategyForTask save(PointsStrategyForTask pointsStrategyForTask);

	/**
	 * Create PointsStrategyForTask with given arguments.
	 * 
	 * @param task
	 *            PointsStrategyForTask Task
	 * @param strategyId
	 *            PointsStrategyForTask strategy id
	 * @return the new PointsStrategyForTask
	 * @see PointsStrategyForTask
	 * @see Task
	 */
	PointsStrategyForTask create(Task task, Integer strategyId);

	/**
	 * Returns PointsStrategyForTask for given Task.
	 * 
	 * @param taskId
	 *            task id
	 * @return PointsStrategyForTask for given Task
	 * @see PointsStrategyForTask
	 * @see Task
	 */
	PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId);

	/**
	 * Returns all PointsStrategyForTasks
	 * 
	 * @return all PointsStrategyForTask
	 * @see PointsStrategyForTask
	 */
	List<PointsStrategyForTask> getPointsStrategyForTasks();

	/**
	 * Return the amount of PointsStrategyForTask saved in database.
	 * 
	 * @return the amount of PointsStrategyForTask saved in database
	 */
	Long getPointsStrategyForTaskCount();

}
