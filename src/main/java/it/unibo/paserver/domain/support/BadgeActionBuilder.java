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

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.BadgeActions;

/**
 * Build a BadgeActions object using the builder pattern.
 * 
 *
 * @see BadgeActions
 *
 */
public class BadgeActionBuilder extends EntityBuilder<BadgeActions> {

	@Override
	void initEntity() {
		entity = new BadgeActions();

	}

	@Override
	BadgeActions assembleEntity() {
		return entity;
	}

	/**
	 * Sets the BadgeActions title.
	 * 
	 * @param title the BadgeActions title
	 * @return this builder
	 */
	public BadgeActionBuilder setTitle(String title) {
		entity.setTitle(title);
		return this;
	}

	/**
	 * Sets the BadgeActions description.
	 * 
	 * @param description the BadgeActions description
	 * @return this builder
	 */
	public BadgeActionBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	/**
	 * Sets the BadgeActions ActionType.
	 * 
	 * @param actionType the BadgeActions ActionType
	 * @return this builder
	 * @see ActionType
	 */
	public BadgeActionBuilder setActionType(ActionType actionType) {
		entity.setActionType(actionType);
		return this;
	}

	/**
	 * Sets the BadgeActions quantity.
	 * 
	 * @param quantity the BadgeActions quantity
	 * @return this builder
	 */
	public BadgeActionBuilder setQuantity(Integer quantity) {
		entity.setQuantity(quantity);
		return this;
	}

	/**
	 * Sets the BadgeActions id.
	 * 
	 * @param id the BadgeActions id
	 * @return this builder
	 */
	public BadgeActionBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

}
