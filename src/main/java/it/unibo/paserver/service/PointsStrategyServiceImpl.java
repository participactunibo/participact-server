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
package it.unibo.paserver.service;

import it.unibo.paserver.gamificationlogic.PointsStrategy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PointsStrategyServiceImpl implements PointsStrategyService {

	@Autowired
	@Qualifier("PointsStrategyByAverageReputation")
	PointsStrategy averageReputationStrategy;

	@Autowired
	@Qualifier("PointsStrategyByAverageLevel")
	PointsStrategy averageLevelStrategy;

	@Autowired
	@Qualifier("PointsStrategyBySumReputation")
	PointsStrategy sumReputationStrategy;

	@Autowired
	@Qualifier("PointsStrategyBySumLevel")
	PointsStrategy sumLevelStrategy;

	@Autowired
	@Qualifier("PointsStrategyGroup")
	PointsStrategy pointsStrategyGroup;

	List<PointsStrategy> allStrategies;

	@Override
	public List<PointsStrategy> getAllStrategies() {

		if (allStrategies == null) {
			allStrategies = new ArrayList<PointsStrategy>(2);
			allStrategies.add(averageLevelStrategy);
			allStrategies.add(averageReputationStrategy);
			allStrategies.add(sumLevelStrategy);
			allStrategies.add(sumReputationStrategy);
			allStrategies.add(pointsStrategyGroup);
		}

		return allStrategies;
	}

	@Override
	public PointsStrategy getStrategyById(int id) {
		if (id == averageReputationStrategy.getId())
			return averageReputationStrategy;
		else if (id == averageLevelStrategy.getId())
			return averageLevelStrategy;
		else if (id == sumLevelStrategy.getId())
			return sumLevelStrategy;
		else if (id == sumReputationStrategy.getId())
			return sumReputationStrategy;
		else if (id == pointsStrategyGroup.getId())
			return pointsStrategyGroup;
		return null;
	}

	@Override
	public PointsStrategy getStrategyByName(String name) {
		if (name != null) {
			if (name.equalsIgnoreCase(averageReputationStrategy.getName()))
				return averageReputationStrategy;
			else if (name.equalsIgnoreCase(averageLevelStrategy.getName()))
				return averageLevelStrategy;
			else if (name.equalsIgnoreCase(sumLevelStrategy.getName()))
				return sumLevelStrategy;
			else if (name.equalsIgnoreCase(sumReputationStrategy.getName()))
				return sumReputationStrategy;
			else if (name.equalsIgnoreCase(pointsStrategyGroup.getName()))
				return pointsStrategyGroup;
		}
		return null;
	}

}
