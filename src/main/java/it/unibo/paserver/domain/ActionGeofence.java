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

import javax.persistence.Column;
import javax.persistence.Entity;
@Entity
public class ActionGeofence extends Action{


	/**
	 * 
	 */
	private static final long serialVersionUID = 5152731135179951109L;
	
	@Column(columnDefinition = "TEXT")
	private String interestPoints;

	private String description;
	
	
	public ActionGeofence(){
	}
	
	
	//method


	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInterestPoints() {
		return interestPoints;
	}

	public void setInterestPoints(String interestPoints) {
		this.interestPoints = interestPoints;
	}

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}


	@Override
	public String toString() {
		return  description ;
	}
	
	


}
