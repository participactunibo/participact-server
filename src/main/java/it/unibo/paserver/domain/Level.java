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

/**
 * A Level is a user-friendly representation of Reputation.
 * The Reputation range is divided is different sub-ranges
 * each of which corresponds to a Level
 * 
 *
 * @see Reputation
 *
 */
public class Level {

	/**
	 * The Rank of a level, could be
	 * Low, Medium_low, Medium_High or High
	 * 
	 *
	 *
	 */
	public enum LevelRank {
		LOW, MEDIUM_LOW, MEDIUM_HIGH, HIGH
	};

	// Maybe could be better using Reputation.REPUTATION_MAX and
	// Reputation.REPUTATION_MIN
	private static final int MEDIUM_LOW_THRESHOLD = 26;
	private static final int MEDIUM_HIGH_THRESHOLD = 51;
	private static final int HIGH_THRESHOLD = 76;

	private Level() {
	} // Must use static method to get an instance

	private ActionType actionType;
	private LevelRank levelRank;

	/**
	 * Return the ActionType which is related
	 * to the Level
	 * 
	 * @return the ActionType which is related to the Level
	 * @see ActionType
	 */
	public ActionType getActionType() {
		return actionType;
	}

	/**
	 * Sets the ActionType which is related
	 * to the Level
	 * 
	 * @param actionType the ActionType which is related to the Level
	 * @see ActionType
	 */
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	/**
	 * Returns the rank of the level
	 * 
	 * @return the rank of the level
	 * @see LevelRank
	 */
	public LevelRank getLevelRank() {
		return levelRank;
	}

	/**
	 * Sets the rank of the level
	 * 
	 * @param levelRank
	 * @see LevelRank
	 */
	public void setLevelRank(LevelRank levelRank) {
		this.levelRank = levelRank;
	}

	/**
	 * You should use this static method to instantiate a Level
	 * object passing a Reputation
	 * 
	 * @param reputation the Reputation used to instantiate the resulting Level
	 * @return a Level built using a Reputation
	 * @see Reputation
	 */
	public static Level getLevelFromReputation(Reputation reputation) {
		Level result = new Level();
		int reputationValue = reputation.getValue();
		LevelRank levelRank = LevelRank.LOW;
		if (reputationValue >= HIGH_THRESHOLD)
			levelRank = LevelRank.HIGH;
		else if (reputationValue >= MEDIUM_HIGH_THRESHOLD)
			levelRank = LevelRank.MEDIUM_HIGH;
		else if (reputationValue >= MEDIUM_LOW_THRESHOLD)
			levelRank = LevelRank.MEDIUM_LOW;
		result.actionType = reputation.getActionType();
		result.levelRank = levelRank;
		return result;

	}

}
