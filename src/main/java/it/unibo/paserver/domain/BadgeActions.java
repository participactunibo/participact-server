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
package it.unibo.paserver.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A BadgeActions has an ActionType and a Integer quantity associated. The badge
 * is unlocked when an user completes with success a number of tasks with
 * ActionType in it equals to quantity.
 * 
 *
 * @see ActionType
 *
 */
@Entity
public class BadgeActions extends AbstractBadge {

	private static final long serialVersionUID = 3495058929223295781L;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	@JsonIgnore
	private Integer quantity;

	/**
	 * Returns the ActionType associated with the badge.
	 * 
	 * @return the ActionType associated with the badge
	 * @see ActionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Sets the ActionType associated with the badge.
	 * 
	 * @param actionType
	 *            the ActionType associated with the badge
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns an Integer representing the quantity needed to unlock the badge.
	 * 
	 * @return quantity needed to unlock the badge.
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * Sets an Integer representing the quantity needed to unlock the badge.
	 * 
	 * @param quantity
	 *            needed to unlock the badge.
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
