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

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;

import java.util.List;

/**
 * Implementations of this interface decide how to assign points to users when
 * they successfully complete a task.
 * 
 *
 *
 */
public interface PointsStrategy {

	/**
	 * Returns Points assigned to User who completed with success Task.
	 * 
	 * @param user
	 *            who completed the Task
	 * @param task
	 *            the task completed with success
	 * @param type why user has gained points
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return Points assigned to User who completed with success Task
	 * @see User
	 * @see Task
	 * @see PointsType
	 */
	public Points computePoints(User user, Task task, PointsType type, boolean persist);

	/**
	 * Returns Points assigned to User who completed with success Task using
	 * given Reputations.
	 * 
	 * @param user
	 *            who completed the Task
	 * @param task
	 *            the task completed with success
	 * @param reputations
	 * @param type why user has gained points
	 * @param persist
	 *            true if the result should be saved in DB
	 * @return Points assigned to User who completed with success Task using
	 *         given Reputations
	 * @see User
	 * @see Task
	 * @see Reputation
	 * @see PointsType
	 */
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist);

	/**
	 * Returns the strategy id.
	 * 
	 * @return the strategy id
	 */
	public int getId();

	/**
	 * Returns the strategy name.
	 * 
	 * @return the strategy name
	 */
	public String getName();
}
