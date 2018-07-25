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
package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.DataPhoto;

import java.util.Comparator;

public class DataPhotoComparator implements Comparator<DataPhoto> {

	@Override
	public int compare(DataPhoto o1, DataPhoto o2) {
		if (o1 == null) {
			return 1;
		}
		if (o2 == null) {
			return -1;
		}
		if (!o1.getUser().equals(o2.getUser())) {
			return o1.getUser().compareTo(o2.getUser());
		}
		return o1.getSampleTimestamp().compareTo(o2.getSampleTimestamp());
	}

}
