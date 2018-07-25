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

public enum ActionType {
	SENSING_MOST, PHOTO, QUESTIONNAIRE, ACTIVITY_DETECTION, GEOFENCE;

	@Override
	public String toString() {
		switch (this) {
		case SENSING_MOST:
			return "Passive sensing";
		case PHOTO:
			return "Photo";
		case QUESTIONNAIRE:
			return "Questionnaire";
		case ACTIVITY_DETECTION:
			return "Activity detection";
		case GEOFENCE:
			return "Geofence";
		default:
			return "Unknown";
		}
	}

}
