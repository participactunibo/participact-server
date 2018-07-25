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

public class MainGroups {

	private GroupUserInizialized users = new GroupUserInizialized();
	private GroupUserDataManager userDataManager = new GroupUserDataManager();
	private GroupUserElementDataManager userElementDataManager = new GroupUserElementDataManager();

	public void inizialize() {
		GroupUserInizialized users = new GroupUserInizialized();
		GroupUserDataManager userDataManager = new GroupUserDataManager();
		GroupUserElementDataManager userElementDataManager = new GroupUserElementDataManager();

		//GroupCalculate calculate = new GroupCalculate();

//		calculate.calculateGroups(3, users, userDataManager,
//				userElementDataManager);

//		for (GroupUser user : users.getAlleGroupUser()) {
//			System.out.println("User: " + user.getId());
//
//			System.out.print("Friends: ");
//			for (Long id : calculate.getFriends(user.getId()))
//				System.out.print(id + " ");
//			System.out.println();
//
//			System.out.print("Community: ");
//			for (Long id : calculate.getCommunity(user.getId()))
//				System.out.print(id + " ");
//			System.out.println();
//
//			System.out.print("Strangers: ");
//			for (Long id : calculate.getStrangers(user.getId()))
//				System.out.print(id + " ");
//			System.out.println();
//
//			System.out.print("FamiliarStrangers: ");
//			for (Long id : calculate.getFamiliarStrangers(user.getId()))
//				System.out.print(id + " ");
//			System.out.println();
//			System.out.println();
//		}
	}

	public GroupUserInizialized getUsers() {
		return users;
	}

	public GroupUserDataManager getUserDataManager() {
		return userDataManager;
	}

	public GroupUserElementDataManager getUserElementDataManager() {
		return userElementDataManager;
	}

}
