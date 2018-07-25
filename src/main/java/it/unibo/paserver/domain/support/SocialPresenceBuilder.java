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

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;

/**
 * Build SocialPresence object using builder pattern.
 * 
 *
 * @see SocialPresence
 *
 */
public class SocialPresenceBuilder extends EntityBuilder<SocialPresence> {

	@Override
	void initEntity() {
		entity = new SocialPresence();
	}

	@Override
	SocialPresence assembleEntity() {
		return entity;
	}

	/**
	 * Sets SocialPresence User.
	 * 
	 * @param user SocialPresence User
	 * @return this builder
	 * @see User
	 */
	public SocialPresenceBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	/**
	 * Sets SocialPresence social network.
	 * 
	 * @param socialNetwork SocialPresence social network
	 * @return this builder
	 * @see SocialPresence
	 */
	public SocialPresenceBuilder setSocialNetwork(
			SocialPresenceType socialNetwork) {
		entity.setSocialNetwork(socialNetwork);
		return this;
	}

	/**
	 * Sets SocialPresence social id.
	 * 
	 * @param socialId SocialPresence social id
	 * @return this builder
	 */
	public SocialPresenceBuilder setSocialId(String socialId) {
		entity.setSocialId(socialId);
		return this;
	}

	/**
	 * Sets SocialPresence id
	 * 
	 * @param id SocialPresence id
	 * @return this builder
	 */
	public SocialPresenceBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}
}
