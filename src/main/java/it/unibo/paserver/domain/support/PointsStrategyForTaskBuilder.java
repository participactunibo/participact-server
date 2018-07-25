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

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;

/**
 * Build a PointsStrategyForTask object using builder pattern.
 * 
 *
 * @see PointsStrategyForTask
 * 
 */
public class PointsStrategyForTaskBuilder extends
		EntityBuilder<PointsStrategyForTask> {

	@Override
	void initEntity() {
		entity = new PointsStrategyForTask();

	}

	@Override
	PointsStrategyForTask assembleEntity() {
		return entity;
	}

	/**
	 * Set the PointsStrategyForTask id.
	 * 
	 * @param id
	 *            the PointsStrategyForTask id
	 * @return this builder
	 */
	public PointsStrategyForTaskBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	/**
	 * Sets the PointsStrategyForTask Task.
	 * 
	 * @param task
	 *            the PointsStrategyForTask Task
	 * @return this builder
	 * @see Task
	 */
	public PointsStrategyForTaskBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	/**
	 * Sets the PointsStrategyForTask strategy id.
	 * 
	 * @param strategyId
	 *            the PointsStrategyForTask strategy id
	 * @return this builder
	 */
	public PointsStrategyForTaskBuilder setStrategyId(Integer strategyId) {
		entity.setStrategyId(strategyId);
		return this;
	}

}
