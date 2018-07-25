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
 * Utility Class.
 * Used to aggregate different Points of User.
 * 
 *
 * @see Points
 * @see User
 *
 */
public class Score implements Comparable<Score> {

	private User user;
	private int value;

	/**
	 * Returns the user to whom Points are associated.
	 * 
	 * @return the user to whom Points are associated
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user to whom Points are associated.
	 * 
	 * @param user the user to whom Points are associated
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the Integer value.
	 * 
	 * @return the Integer value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the Integer value.
	 * 
	 * @param value the Integer value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + value;
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
		Score other = (Score) obj;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

	@Override
	public int compareTo(Score o) {

		if (o == null)
			return 1;
		Score score = (Score) o;
		if (equals(o))
			return 0;
		if (value > o.value)
			return 1;
		else if (value < o.value)
			return -1;
		else {
			return user.compareTo(score.user);
		}
	}
}
