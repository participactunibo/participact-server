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
package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.TaskState;

/**
 * Implementations of this interface decide how to update reputation of users
 * when they interact with a Task
 * 
 *
 *
 */
public interface ReputationStrategy {

	/**
	 * Return the new Reputation when the user interact with a Task
	 * 
	 * @param reputation
	 *            current Reputation
	 * @param newTaskState
	 *            new Task State
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return the new Reputation
	 * @see Reputation
	 * @see TaskState
	 */
	public Reputation updateReputation(Reputation reputation,
			TaskState newTaskState, boolean persist);

}
