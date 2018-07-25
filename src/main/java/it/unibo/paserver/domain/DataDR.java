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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataDR", indexes = { @Index(name = "dr_user_sampletimestamp", columnNames = {
		"user_id", "sampletimestamp" }) })
public class DataDR extends Data {

	private static final long serialVersionUID = -2301535416325629892L;
	
	@NotNull
	@Column(name = "DRState")
	private String state;
	
	@NotNull
	@Column(name = "DRPole")
	private String pole;
	
	@NotNull
	@Column(name = "DRLatitude")
	private float latitude;
	
	@NotNull
	@Column(name = "DRLongitude")
	private float longitude;
	
	@NotNull
	@Column(name = "DRAccuracy")
	private float accuracy;
	
	@NotNull
	@Column(name = "DRStatus")
	private String status;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPole() {
		return pole;
	}

	public void setPole(String pole) {
		this.pole = pole;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
