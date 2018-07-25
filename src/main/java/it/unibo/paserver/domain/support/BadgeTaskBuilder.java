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

import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.Task;

/**
 * Build a BadgeTask object using the builder pattern.
 * 
 *
 * @see BadgeTask
 *
 */
public class BadgeTaskBuilder extends EntityBuilder<BadgeTask> {

	@Override
	void initEntity() {
		entity = new BadgeTask();

	}

	@Override
	BadgeTask assembleEntity() {
		return entity;
	}

	/**
	 * Sets the BadgeTask title.
	 * 
	 * @param title the BadgeTask title
	 * @return this builder
	 */
	public BadgeTaskBuilder setTitle(String title) {
		entity.setTitle(title);
		return this;
	}

	/**
	 * Sets the BadgeTask description.
	 * 
	 * @param description the BadgeTask description
	 * @return this builder
	 */
	public BadgeTaskBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	/**
	 * Sets the BadgeTask Task.
	 * 
	 * @param task the BadgeTask Task
	 * @return this builder
	 * @see Task
	 */
	public BadgeTaskBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	/**
	 * Sets the BadgeTask id.
	 * 
	 * @param id the BadgeTask id
	 * @return this builder
	 */
	public BadgeTaskBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

}
