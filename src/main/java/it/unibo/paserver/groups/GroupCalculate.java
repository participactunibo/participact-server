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

import it.unibo.paserver.service.GroupsContactsService;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class GroupCalculate {

	private List<GroupUser> users;

	public GroupCalculate(List<GroupUser> allGroupUsers) {
		users = allGroupUsers;
	}

	public List<GroupUser> getUsers() {
		return users;
	}

	public void calculateGroups(List<GroupUser> usersOfCurrentHour,
			IGroupUserElementDataManager groupUserElementDataManager, GroupsContactsService groupsContactsService, DateTime date) {

		for (GroupUser listGroupUser : users) {
			listGroupUser.incrementHour();
		}

		for (GroupUser currentGroupUser : usersOfCurrentHour) {
			for (GroupUser listGroupUser : users) {

				if (listGroupUser.getId() == currentGroupUser.getId()) {
					listGroupUser.setPoint(currentGroupUser.getCurrentPoint());

					List<GroupUserElement> groupUserElementsToAdd = groupUserElementDataManager
							.getGroupUserElementByGroupUser(listGroupUser,
									usersOfCurrentHour, groupsContactsService, date);

					for (GroupUserElement currentGroupUserElement : groupUserElementsToAdd)
						listGroupUser
								.addGroupUserElement(currentGroupUserElement);

					break;
				}
			}
		}

		for (GroupUser currentUser : users) {
			currentUser.invalidateLists();
			currentUser.updateLists();
		}
		int i=0;
	}

	public ArrayList<Long> getCommunity(long id) {
		if (users != null)
			for (GroupUser groupUser : users) {
				if (groupUser.getId() == id)
					return groupUser.getCommunity();
			}
		return null;
	}

	public ArrayList<Long> getFriends(long id) {
		if (users != null)
			for (GroupUser groupUser : users) {
				if (groupUser.getId() == id)
					return groupUser.getFriends();
			}
		return null;
	}

	public ArrayList<Long> getFamiliarStrangers(long id) {
		if (users != null)
			for (GroupUser groupUser : users) {
				if (groupUser.getId() == id)
					return groupUser.getFamiliarStrangers();
			}
		return null;
	}

	public ArrayList<Long> getStrangers(long id) {
		if (users != null)
			for (GroupUser groupUser : users) {
				if (groupUser.getId() == id)
					return groupUser.getStrangers();
			}
		return null;
	}
}
