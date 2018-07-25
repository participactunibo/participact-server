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

public class GroupUser {

	private final int HOURINDAY = 24;

	private long id;
	private GroupPoint currentPoint;
	private ArrayList<GroupUserElement> otherGroupUser;
	private long currentHour;
	private boolean stateValidLists = false;
	private int CONTACTSINDAY = GroupUtils.CONTACTSINDAY;
	private double INFAMILY = GroupUtils.INFAMILY;

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

	public GroupUser(long id) {
		this.id = id;
		this.otherGroupUser = new ArrayList<GroupUserElement>();
		this.currentHour = 0;
		inizializeList();
	}

	public long getId() {
		return id;
	}

	public GroupPoint getCurrentPoint() {
		return currentPoint;
	}

	public void setPoint(GroupPoint point) {
		this.currentPoint = point;
	}

	public ArrayList<GroupUserElement> getOtherGroupUser() {
		return otherGroupUser;
	}

	public long getCurrentHour() {
		return currentHour;
	}

	public ArrayList<Long> getCommunity() {
		updateLists();
		return community;
	}

	public ArrayList<Long> getFriends() {
		updateLists();
		return friends;
	}

	public ArrayList<Long> getFamiliarStrangers() {
		updateLists();
		return familiarStrangers;
	}

	public ArrayList<Long> getStrangers() {
		updateLists();
		return strangers;
	}

	public void addGroupUserElement(GroupUserElement element) {
		GroupUserElement el = null;
		boolean contain = false;

		for (GroupUserElement tmpGroupUserElement : otherGroupUser) {
			if (tmpGroupUserElement.equals(element)) {
				el = tmpGroupUserElement;
				contain = true;
				break;
			}
		}

		if (contain) {
			if (currentHour == (el.getLastHourSet() + 1)) {
				el.addDurationOfContacts(1);
			} else {
				el.addDurationOfContacts(1);
				el.addNumberOfContacts(1);
			}
			el.setLastHourSet(currentHour);
		} else {
			otherGroupUser.add(element);
			element.setLastHourSet(currentHour);
			element.addDurationOfContacts(1);
			element.addNumberOfContacts(1);
		}
	}

	public void incrementHour() {
		this.currentHour++;
	}

	public void invalidateLists() {
		stateValidLists = false;
		inizializeList();
	}

	private void inizializeList() {
		this.community = new ArrayList<Long>();
		this.friends = new ArrayList<Long>();
		this.familiarStrangers = new ArrayList<Long>();
		this.strangers = new ArrayList<Long>();
	}

	public void updateLists() {
		if (!stateValidLists) {
			double durationLimit = this.currentHour * INFAMILY;
			long contactLimit = ((this.currentHour / HOURINDAY) - (this.currentHour / HOURINDAY))
					* CONTACTSINDAY;

			if (contactLimit == 0)
				contactLimit = CONTACTSINDAY;

			inizializeList();
			for (GroupUserElement currentElement : otherGroupUser) {
				refreshLists(currentElement, durationLimit, contactLimit);
			}
			stateValidLists = true;
		}
	}

	private void refreshLists(GroupUserElement element, double durationLimit,
			long contactLimit) {
		if (element.getDurationOfContacts() < durationLimit
				&& element.getNumberOfContacts() < contactLimit) {
			this.strangers.add(element.getId());
		} else if (element.getDurationOfContacts() >= durationLimit
				&& element.getNumberOfContacts() >= contactLimit) {
			this.community.add(element.getId());
		} else if (element.getDurationOfContacts() >= durationLimit
				&& element.getNumberOfContacts() < contactLimit) {
			this.friends.add(element.getId());
		} else if (element.getDurationOfContacts() < durationLimit
				&& element.getNumberOfContacts() >= contactLimit) {
			this.familiarStrangers.add(element.getId());
		}
	}

	public String toString() {
		return "" + id;
	}
}
