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
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;

/**
 * Build a Reputation object using builder pattern
 * 
 *
 * @see Reputation
 *
 */
public class ReputationBuilder extends EntityBuilder<Reputation> {

	@Override
	void initEntity() {
		entity = new Reputation();

	}

	@Override
	Reputation assembleEntity() {
		return entity;
	}

	/**
	 * Sets Reputation User.
	 * 
	 * @param user Reputation User
	 * @return this builder
	 * @see User
	 */
	public ReputationBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * Sets Reputation value.
	 * 
	 * @param value Reputation value
	 * @return this builder
	 */
	public ReputationBuilder setValue(Integer value) {
		entity.setValue(value);
		return this;
	}

	/**
	 * Sets Reputation ActionType.
	 * 
	 * @param actionType Reputation ActionType
	 * @return this builder
	 * @see ActionType
	 */
	public ReputationBuilder setActionType(ActionType actionType) {
		entity.setActionType(actionType);
		return this;
	}

	/**
	 * Sets Reputation id.
	 * 
	 * @param id Reputation id
	 * @return this builder
	 */
	public ReputationBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
