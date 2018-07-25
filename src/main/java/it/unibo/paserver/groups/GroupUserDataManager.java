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
import java.util.List;

public class GroupUserDataManager implements IGroupUserDataManager {

	@Override
	public List<GroupUser> getGroupUserForHour(long hour) {
		ArrayList<GroupUser> result = new ArrayList<GroupUser>();

		if (hour == 1) {
			GroupUser tmp;
			tmp = new GroupUser(1);
			tmp.setPoint(new GroupPoint(1, 1));
			result.add(tmp);

			// match con 1 e 5
			tmp = new GroupUser(2);
			tmp.setPoint(new GroupPoint(0.99999, 0.99999));
			result.add(tmp);

			tmp = new GroupUser(3);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			// match con 1
			tmp = new GroupUser(4);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			// match con 1 e 5
			tmp = new GroupUser(5);
			tmp.setPoint(new GroupPoint(1, 1));
			result.add(tmp);
		} else if (hour == 2) {
			GroupUser tmp;
			tmp = new GroupUser(1);
			tmp.setPoint(new GroupPoint(1, 1));
			result.add(tmp);

			// match con 1
			tmp = new GroupUser(2);
			tmp.setPoint(new GroupPoint(0.99999, 0.99999));
			result.add(tmp);

			tmp = new GroupUser(3);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			tmp = new GroupUser(4);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			tmp = new GroupUser(5);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);
		} else if (hour == 3) {
			GroupUser tmp;
			tmp = new GroupUser(1);
			tmp.setPoint(new GroupPoint(1, 1));
			result.add(tmp);

			// match con 1 e 5
			tmp = new GroupUser(2);
			tmp.setPoint(new GroupPoint(0.99999, 0.99999));
			result.add(tmp);

			tmp = new GroupUser(3);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			// match con 1
			tmp = new GroupUser(4);
			tmp.setPoint(new GroupPoint(0.99999, 0.9999));
			result.add(tmp);

			// match con 1 e 5
			tmp = new GroupUser(5);
			tmp.setPoint(new GroupPoint(1, 1));
			result.add(tmp);
		}

		return result;
	}
}
