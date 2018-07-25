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
package it.unibo.paserver.repository;

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;

import java.util.List;

/**
 * Repository for SocialPresence entities.
 * 
 *
 *
 */
public interface SocialPresenceRepository {

	/**
	 * Saves given SocialPresence.
	 * 
	 * @param socialPresence SocialPresence to save
	 * @return saved SocialPresence
	 * @see SocialPresence
	 */
	SocialPresence save(SocialPresence socialPresence);

	/**
	 * Creates new SocialPresence with given arguments.
	 * 
	 * @param user SocialPresence User.
	 * @param socialNetwork SocialPresence social network
	 * @param socialId SocialPresence social id
	 * @return the new SocialPresences
	 * @see User
	 * @see SocialPresenceType
	 * @see SocialPresence
	 */
	SocialPresence create(User user, SocialPresenceType socialNetwork,
			String socialId);

	/**
	 * Returns all SocialPresences for given User id.
	 * 
	 * @param userId User id
	 * @return List of SocialPresence for given User id
	 * @see SocialPresence
	 */
	List<SocialPresence> getSocialPresencesForUser(long userId);

	/**
	 * Returns SocialPresence for given User id and Social Network.
	 * 
	 * @param userId User id
	 * @param socialNetwork Social Network
	 * @return SocialPresence for given User id and Social Network
	 * @see SocialPresence
	 * @see SocialPresenceType
	 */
	SocialPresence getSocialPresenceForUserAndSocialNetwork(long userId,
			SocialPresenceType socialNetwork);

	/**
	 * Returns all SocialPresences for give Social Network.
	 * 
	 * @param socialNetwork social network you are looking for
	 * @return List of all SocialPresence for given social network
	 * @see SocialPresence
	 * @see SocialPresenceType
	 */
	List<SocialPresence> getSocialPresencesForSocialNetwork(
			SocialPresenceType socialNetwork);

	/**
	 * Returns all SocialPresences saved in database.
	 * 
	 * @return all SocialPresences saved in database
	 * @see SocialPresence
	 */
	List<SocialPresence> getSocialPresences();

	/**
	 * Returns the amount of SocialPresencs saved in database.
	 * 
	 * @return the amount of SocialPresences saved in database
	 */
	Long getSocialPresencesCount();

}
