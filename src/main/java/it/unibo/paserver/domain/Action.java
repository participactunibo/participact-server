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

import it.unibo.paserver.domain.flat.ActionFlat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Action implements Serializable {

	private static final long serialVersionUID = 8141364234733032490L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "action_id")
	private Long id;

	private String name;

	private Integer numeric_threshold;

	private Integer duration_threshold;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumeric_threshold() {
		return numeric_threshold;
	}

	public void setNumeric_threshold(Integer numeric_threshold) {
		this.numeric_threshold = numeric_threshold;
	}

	public Integer getDuration_threshold() {
		return duration_threshold;
	}

	public void setDuration_threshold(Integer duration_threshold) {
		this.duration_threshold = duration_threshold;
	}

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}

	public ActionType getType() {
		if (this instanceof ActionActivityDetection)
			return ActionType.ACTIVITY_DETECTION;

		else if (this instanceof ActionPhoto)
			return ActionType.PHOTO;

		else if (this instanceof ActionQuestionaire)
			return ActionType.QUESTIONNAIRE;

		else if (this instanceof ActionSensing)
			return ActionType.SENSING_MOST;
		
		else if (this instanceof ActionGeofence)
			return ActionType.GEOFENCE;

		else
			throw new IllegalArgumentException("No valid action type found");
	}

}
