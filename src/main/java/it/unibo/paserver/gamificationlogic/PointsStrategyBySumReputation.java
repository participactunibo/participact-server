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

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.ReputationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * This implementation uses User's Reoutations for each ActionType in the Task
 * completed. If multiples reputations are given, this implementation sums the
 * values.
 * 
 *
 *
 */
@Component
@Qualifier("PointsStrategyBySumReputation")
public class PointsStrategyBySumReputation implements PointsStrategy {

	@Autowired
	private ReputationService reputationService;
	@Autowired
	private PointsService pointsService;

	private int id = 4;
	private String name = "By Sum Reputation";

	@Override
	public Points computePoints(User user, Task task, PointsType type, boolean persist) {
		Long userId = user.getId();
		Set<Action> actions = task.getActions();
		List<Reputation> reputations = new ArrayList<Reputation>();
		for (Action currentAction : actions) {
			reputations.add(reputationService.getReputationByUserAndActionType(
					userId, currentAction.getType()));
		}

		return computePoints(user, task, reputations, type, persist);
	}

	@Override
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist) {
		if (reputations.size() == 0) {

			Points result = new Points();
			result.setUser(user);
			result.setDate(new DateTime());
			result.setTask(task);
			result.setValue(0);

			return result;

		}

		DateTime now = new DateTime();

		int tempSum = 0;

		for (Reputation currentReputation : reputations) {
			double tempPointValueDouble = 60 + 0.6 * currentReputation
					.getValue();
			tempSum += (int) tempPointValueDouble;
		}

		if (persist)
			return pointsService.create(user, task, now, tempSum, type);

		else {
			Points result = new Points();
			result.setDate(now);
			result.setTask(task);
			result.setUser(user);
			result.setValue(tempSum);
			result.setType(type);
			return result;
		}
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PointsStrategyBySumReputation other = (PointsStrategyBySumReputation) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
