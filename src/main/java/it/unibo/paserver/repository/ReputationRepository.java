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

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;

import java.util.List;

/**
 * Repository for Reputation entities.
 * 
 *
 *
 */
public interface ReputationRepository {

	/**
	 * Saves gives Reputation.
	 * 
	 * @param reputation Reputation to save
	 * @return saved Reputation
	 * @see Reputation
	 */
	Reputation save(Reputation reputation);

	/**
	 * Creates new Reputation with given arguments.
	 * 
	 * @param user Reputation User
	 * @param actionType Reputation ActionType
	 * @param value Reputation value
	 * @return the new Reputation
	 * @see Reputation
	 * @see User
	 * @see ActionType
	 */
	Reputation create(User user, ActionType actionType, int value);

	/**
	 * Returns all Reputations for given User id.
	 * 
	 * @param userId User id
	 * @return List of Reputations of given User id
	 * @see Reputation
	 */
	List<Reputation> getReputationsByUser(long userId);

	/**
	 * Returns Reputation for given User id and ActionType.
	 * 
	 * @param userId User id
	 * @param actionType ActionType you are looking for
	 * @return found Reputation
	 * @see Reputation
	 * @see ActionType
	 */
	Reputation getReputationByUserAndActionType(long userId,
			ActionType actionType);

	/**
	 * Returns all Reputations saved in database.
	 * 
	 * @return List of Reputations saved in database
	 */
	List<Reputation> getReputations();

	/**
	 * Returns the amount of Reputations saved in database.
	 * 
	 * @return the amount of Reputations saved in database
	 */
	Long getReputationsCount();

}
