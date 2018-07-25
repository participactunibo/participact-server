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
package it.unibo.paserver.domain.rest;

import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.User;

/**
 * Utility Class.
 * Used to obtain a REST-friendly representation of a
 * Score
 * 
 *
 * @see Score
 *
 */
public class ScoreRestResult implements Comparable<ScoreRestResult> {

	private String userName;
	private long userId;
	private int scoreValue;

	/**
	 * Use this constructor to obtain ScoreRestResult instance
	 * from a Score object.
	 * 
	 * @param score the score used to instantiate a new ScoreRestResult object
	 * @see Score
	 */
	public ScoreRestResult(Score score) {
		User user = score.getUser();
		this.userName = user.getName() + " " + user.getSurname();
		this.userId = user.getId();
		this.scoreValue = score.getValue();
	}

	/**
	 * Returns the User name and surname.
	 * 
	 * @return the User name and surname
	 * @see User
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the User name and surname.
	 * 
	 * @param userName the User name and surname
	 * @see User
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Returns the User id.
	 * 
	 * @return the User id.
	 * @see User
	 */
	public long getUserId() {
		return userId;
	}

	/**
	 * Sets the User id.
	 * 
	 * @param userId the User id.
	 * @see User
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}

	/**
	 * Returns the value of the Score.
	 * 
	 * @return the value of the Score
	 * @see Score
	 */
	public int getScoreValue() {
		return scoreValue;
	}

	/**
	 * Sets the value of the Score.
	 * 
	 * @param scoreValue the value of the Score
	 * @see Score
	 */
	public void setScoreValue(int scoreValue) {
		this.scoreValue = scoreValue;
	}

	@Override
	public int compareTo(ScoreRestResult o) {
		if (o == null)
			return 1;
		ScoreRestResult score = (ScoreRestResult) o;
		if (equals(o))
			return 0;
		if (scoreValue > o.scoreValue)
			return 1;
		else if (scoreValue < o.scoreValue)
			return -1;
		else {
			return userName.compareTo(score.userName);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + scoreValue;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		ScoreRestResult other = (ScoreRestResult) obj;
		if (scoreValue != other.scoreValue)
			return false;
		if (userId != other.userId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

}
