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

import it.unibo.paserver.domain.Level.LevelRank;
import it.unibo.paserver.domain.User;

/**
 * Utility Class.
 * Used to obtain a REST-friendly representation of
 * a Participact User
 * 
 *
 * @see User
 *
 */
public class UserRestResult implements Comparable<UserRestResult> {

	private long id;
	private String name;
	private String surname;
	private LevelRank sensingMostLevel;
	private LevelRank photoLevel;
	private LevelRank questionnaireLevel;
	private LevelRank activityDetectionLevel;

	/**
	 * Use this constructor to obtain UserRestResult instance
	 * from a User object.
	 * 
	 * @param user the user used to instantiate a new UserRestResult object
	 */
	public UserRestResult(User user) {
		this.name = user.getName();
		this.surname = user.getSurname();
		this.id = user.getId();
	}

	/**
	 * Returns User's id.
	 * 
	 * @return User's id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets User's id
	 * 
	 * @param id User's id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns User's name.
	 * 
	 * @return User's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets User's name.
	 * 
	 * @param name User's name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns User's surname.
	 * 
	 * @return User's surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets User's surname.
	 * 
	 * @param surname User's surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Returns the User's LevelRank for ActionType SENSING_MOST.
	 * 
	 * @return the User's LevelRank for ActionType SENSING_MOST
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getSensingMostLevel() {
		return sensingMostLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType SENSING_MOST
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType SENSING_MOST
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setSensingMostLevel(LevelRank sensingMostLevel) {
		this.sensingMostLevel = sensingMostLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType PHOTO.
	 * 
	 * @return the User's LevelRank for ActionType PHOTO
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getPhotoLevel() {
		return photoLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType PHOTO
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType PHOTO
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setPhotoLevel(LevelRank photoLevel) {
		this.photoLevel = photoLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType QUESTIONNAIRE.
	 * 
	 * @return the User's LevelRank for ActionType QUESTIONNAIRE
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getQuestionnaireLevel() {
		return questionnaireLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType QUESTIONNAIRE
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType QUESTIONNAIRE
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setQuestionnaireLevel(LevelRank questionnaireLevel) {
		this.questionnaireLevel = questionnaireLevel;
	}

	/**
	 * Returns the User's LevelRank for ActionType ACTIVITY_DETECTION.
	 * 
	 * @return the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public LevelRank getActivityDetectionLevel() {
		return activityDetectionLevel;
	}

	/**
	 * Sets the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * 
	 * @param sensingMostLevel the User's LevelRank for ActionType ACTIVITY_DETECTION
	 * @see LevelRank
	 * @see Level
	 * @see ActionType
	 */
	public void setActivityDetectionLevel(LevelRank activityDetectionLevel) {
		this.activityDetectionLevel = activityDetectionLevel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
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
		UserRestResult other = (UserRestResult) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public int compareTo(UserRestResult o) {
		if (o == null) {
			return 1;
		}
		UserRestResult u = (UserRestResult) o;
		if (equals(u)) {
			return 0;
		}

		int nameOrder = getName().toUpperCase().compareTo(
				u.getName().toUpperCase());
		if (nameOrder != 0) {
			return nameOrder;
		}

		int surnameOrder = getSurname().toUpperCase().compareTo(
				u.getSurname().toUpperCase());
		return surnameOrder;

	}

}
