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

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;

/**
 * Build a Friendship object using the builder pattern.
 * 
 *
 * @see Friendship
 *
 */
public class FriendshipBuilder extends EntityBuilder<Friendship> {

	@Override
	void initEntity() {
		entity = new Friendship();

	}

	@Override
	Friendship assembleEntity() {
		return entity;
	}

	/**
	 * Sets the Friendship sender.
	 * 
	 * @param sender the Friendship sender
	 * @return this builder
	 * @see User
	 */
	public FriendshipBuilder setSender(User sender) {
		entity.setSender(sender);
		return this;
	}

	/**
	 * Sets the Friendship receiver.
	 * 
	 * @param receiver the Friendship receiver
	 * @return this builder
	 * @see User
	 */
	public FriendshipBuilder setReceiver(User receiver) {
		entity.setReceiver(receiver);
		return this;
	}

	/**
	 * Sets the Friendship status.
	 * 
	 * @param status the Friendship status
	 * @return this builder
	 * @see FriendshipStatus
	 */
	public FriendshipBuilder setStatus(FriendshipStatus status) {
		entity.setStatus(status);
		return this;
	}

	/**
	 * Sets the FriendshipStatus id.
	 * 
	 * @param id the FriendshipStatus id
	 * @return this builder
	 */
	public FriendshipBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
