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

import it.unibo.paserver.domain.GroupsContacts;
import it.unibo.paserver.service.GroupsContactsService;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

public class GroupUserElementDataManager implements
		IGroupUserElementDataManager {

	private int DISTANZA = GroupUtils.DISTANCE;

	@Override
	public List<GroupUserElement> getGroupUserElementByGroupUser(
			GroupUser groupUser, List<GroupUser> groups,
			GroupsContactsService groupsContactsService, DateTime date) {
		ArrayList<GroupUserElement> result = new ArrayList<GroupUserElement>();

		for (GroupUser currentGroupUser : groups) {
			if (currentGroupUser.getId() != groupUser.getId()
					&& GroupUtils.getDistance(groupUser.getCurrentPoint(),
							currentGroupUser.getCurrentPoint()) < DISTANZA) {

				if (GroupUtils.SAVECONTACTS) {
					GroupsContacts groupsContacts = new GroupsContacts();
					groupsContacts.setIdUser(groupUser.getId());
					groupsContacts.setIdMatchUser(currentGroupUser.getId());
					groupsContacts.setHourOfMatch(date);
					groupsContactsService.save(groupsContacts);
				}

				result.add(new GroupUserElement(currentGroupUser.getId()));
			}
		}

		return result;
	}
}
