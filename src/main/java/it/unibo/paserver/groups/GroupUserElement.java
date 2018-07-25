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

public class GroupUserElement {

	private long id;
	private int numberOfContacts;
	private int durationOfContacts;
	private long lastHourSet = 0;

	public GroupUserElement(long id) {
		this.id = id;
		this.numberOfContacts = 0;
		this.durationOfContacts = 0;
	}

	public long getId() {
		return id;
	}

	public int getNumberOfContacts() {
		return numberOfContacts;
	}

	public int getDurationOfContacts() {
		return durationOfContacts;
	}

	public void addDurationOfContacts(int duration) {
		this.durationOfContacts += duration;
	}

	public long getLastHourSet() {
		return lastHourSet;
	}

	public void setLastHourSet(long lastHourSet) {
		this.lastHourSet = lastHourSet;
	}

	public void addNumberOfContacts(int number) {
		this.numberOfContacts += number;
	}

	public boolean equals(Object obj) {
		return this.id == ((GroupUserElement) obj).getId();
	}
}
