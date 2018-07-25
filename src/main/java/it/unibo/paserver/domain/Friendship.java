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
 * A Friendship is a relationship between two Users.
 * One is the sender, and the other is the receiver.
 * 
 *
 * @see User
 *
 */
@Entity
public class Friendship implements Serializable {

	/**
	 * The status of a friendship, which could be
	 * Accepter, pending or rejected.
	 * 
	 *
	 *
	 */
	public enum FriendshipStatus {
		ACCEPTED, PENDING, REJECTED
	};

	private static final long serialVersionUID = -876346233474298270L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private User sender;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private User receiver;

	@NotNull
	@Enumerated(EnumType.STRING)
	private FriendshipStatus status;

	/**
	 * Returns the sender of the Friendship.
	 * 
	 * @return the sender of the Friendship
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * Sets the sender of the Friendship.
	 * 
	 * @param sender the sender of the Friendship
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * Returns the receiver of the Friendship.
	 * 
	 * @return the receiver of the Friendship
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * Sets the receiver of the Friendship.
	 * 
	 * @param receiver the receiver of the Friendship
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	/**
	 * Returns the current status of the Friendship.
	 * 
	 * @return the current status of the Friendship
	 * @see FriendshipStatus
	 */
	public FriendshipStatus getStatus() {
		return status;
	}

	/**
	 * Sets the current status of the Friendship.
	 * 
	 * @param status the current status of the Friendship
	 * @see FriendshipStatus
	 */
	public void setStatus(FriendshipStatus status) {
		this.status = status;
	}

	/**
	 * Returns the id of the Friendship.
	 * 
	 * @return the id of the Friendship
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id of the Friendship.
	 * 
	 * @param id the id of the Friendship
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((receiver == null) ? 0 : receiver.hashCode());
		result = prime * result + ((sender == null) ? 0 : sender.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Friendship other = (Friendship) obj;
		if (receiver == null) {
			if (other.receiver != null)
				return false;
		} else if (!receiver.equals(other.receiver))
			return false;
		if (sender == null) {
			if (other.sender != null)
				return false;
		} else if (!sender.equals(other.sender))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

}
