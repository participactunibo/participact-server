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

import java.util.List;

/**
 * Badge interface with getter and setters for title, description and list of
 * users who currently have unlocked the badge
 * 
 *
 * @see User
 *
 */
public interface Badge {

	/**
	 * Returns a String object representing the title of the badge.
	 * 
	 * @return the title of the badge
	 */
	public String getTitle();

	/**
	 * Sets the title of the badge.
	 * 
	 * @param title the text of the title
	 */
	public void setTitle(String title);

	/**
	 * Returns a String object representing the description of the badge.
	 * 
	 * @return the description of the badge
	 */
	public String getDescription();

	/**
	 * Sets the description of the badge.
	 * 
	 * @param description the text of the description
	 */
	public void setDescription(String description);

	/**
	 * Return a Long object representing the id of the badge.
	 * 
	 * @return the id of the badge
	 */
	public Long getId();

	/**
	 * Sets the id of the badge.
	 * 
	 * @param id the id of the badge
	 */
	public void setId(Long id);

	/**
	 * Return a List of users who currently have unlocked the badge.
	 * 
	 * @return users who currently have unlocked the badge
	 * @see User
	 */
	public List<User> getUsers();

	/**
	 * Set a List of users who currently have unlocked the badge.
	 * 
	 * @param users users who currently have unlocked the badge
	 * @see User
	 */
	public void setUsers(List<User> users);

}
