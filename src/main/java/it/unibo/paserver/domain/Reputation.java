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

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * A Reputation represents how much a User is reliable for a
 * specific ActionType
 * 
 *
 * @see User
 * @see ActionType
 *
 */
@Entity
public class Reputation implements Serializable {

	private static final long serialVersionUID = -2282847684576241479L;
	public static final int REPUTATION_MAX = 100;
	public static final int REPUTATION_MIN = 0;

	public static final int INIT_VALUE = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private User user;

	@NotNull
	private Integer value;

	@NotNull
	@Enumerated(EnumType.STRING)
	private ActionType actionType;

	/**
	 * Returns the User.
	 * 
	 * @return the user
	 * @see User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the User
	 * 
	 * @param user the user
	 * @see User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the Reputation value as Integer.
	 * 
	 * @return the Reputation value as Integer
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Sets the Reputation value.
	 * @param value the Reputation value
	 */
	public void setValue(Integer value) {
		if (value < REPUTATION_MIN)
			throw new IllegalArgumentException(
					"Reputation value must be bigger than" + REPUTATION_MIN);
		else if (value > REPUTATION_MAX)
			throw new IllegalArgumentException(
					"Reputation value must be lower than" + REPUTATION_MAX);
		else
			this.value = value;
	}

	/**
	 * Returns the ActionType.
	 * 
	 * @return the ActionType
	 * @see ActionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Sets the ActionType
	 * 
	 * @param actionType tge ActionType
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionType == null) ? 0 : actionType.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reputation other = (Reputation) obj;
		if (actionType != other.actionType)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
