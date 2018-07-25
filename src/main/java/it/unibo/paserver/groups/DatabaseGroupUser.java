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
package it.unibo.paserver.groups;

import java.util.ArrayList;

public class DatabaseGroupUser {

	private long id;

	// community are users who are in the upper right corner in the Cartesian
	// graph
	private ArrayList<Long> community;

	// friends are users who are in the lower right corner in the Cartesian
	// graph
	private ArrayList<Long> friends;

	// familiar strangers are users who are in the upper left corner in the
	// Cartesian graph
	private ArrayList<Long> familiarStrangers;

	// familiar strangers are users who are in the lower left corner in the
	// Cartesian graph
	private ArrayList<Long> strangers;

	public DatabaseGroupUser() {
		this.community = new ArrayList<Long>();
		this.familiarStrangers = new ArrayList<Long>();
		this.friends = new ArrayList<Long>();
		this.strangers = new ArrayList<Long>();
	}

	public ArrayList<Long> getCommunity() {
		return community;
	}

	public void setCommunity(ArrayList<Long> community) {
		this.community = community;
	}

	public ArrayList<Long> getFriends() {
		return friends;
	}

	public void setFriends(ArrayList<Long> friends) {
		this.friends = friends;
	}

	public ArrayList<Long> getFamiliarStrangers() {
		return familiarStrangers;
	}

	public void setFamiliarStrangers(ArrayList<Long> familiarStrangers) {
		this.familiarStrangers = familiarStrangers;
	}

	public ArrayList<Long> getStrangers() {
		return strangers;
	}

	public void setStrangers(ArrayList<Long> strangers) {
		this.strangers = strangers;
	}

	public void addFriends(long id) {
		if (!this.friends.contains(id))
			this.friends.add(id);
	}

	public void addCommunity(long id) {
		if (!this.community.contains(id))
			this.community.add(id);
	}

	public void addFamiliarStrangers(long id) {
		if (!this.familiarStrangers.contains(id))
			this.familiarStrangers.add(id);
	}

	public void addStrangers(long id) {
		if (!this.strangers.contains(id))
			this.strangers.add(id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
