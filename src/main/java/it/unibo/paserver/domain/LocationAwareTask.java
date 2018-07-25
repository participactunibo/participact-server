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

import it.unibo.paserver.domain.flat.TaskFlat;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class LocationAwareTask extends Task {

	private static final long serialVersionUID = -6855620555493420665L;

	@NotNull
	private Double latitude;

	@NotNull
	private Double longitude;

	@NotNull
	private Double radius;

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@Override
	public String toString() {
		String actions = "";
		for (Action act : getActions()) {
			actions += act.getName() + " ";
		}
		return String
				.format("%s Id:%s Name:%s DeadLine:%s Latitude:%s Longitude:%s Actions:%s",
						Task.class.getSimpleName(), getId(), getName(),
						getDeadline(), getLatitude(), getLongitude(), actions);
	}

	public TaskFlat convertToTaskFlat() {
		return new TaskFlat(this);
	}
}
