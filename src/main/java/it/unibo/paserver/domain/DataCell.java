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

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataCell", indexes = {
		@Index(name = "cell_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "cell_ts", columnNames = { "sampletimestamp" }) })
public class DataCell extends Data {

	private static final long serialVersionUID = -7540541783183107870L;

	@NotNull
	private String phoneType;
	@NotNull
	private int gsmCellId;
	@NotNull
	private int gsmLac;
	@NotNull
	private int baseStationId;
	@NotNull
	private int baseStationLatitude;
	@NotNull
	private int baseStationLongitude;
	@NotNull
	private int baseNetworkId;
	@NotNull
	private int baseSystemId;

	public String getPhoneType() {
		return phoneType;
	}

	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}

	public int getGsmCellId() {
		return gsmCellId;
	}

	public void setGsmCellId(int gsmCellId) {
		this.gsmCellId = gsmCellId;
	}

	public int getGsmLac() {
		return gsmLac;
	}

	public void setGsmLac(int gsmLac) {
		this.gsmLac = gsmLac;
	}

	public int getBaseStationId() {
		return baseStationId;
	}

	public void setBaseStationId(int baseStationId) {
		this.baseStationId = baseStationId;
	}

	public int getBaseStationLatitude() {
		return baseStationLatitude;
	}

	public void setBaseStationLatitude(int baseStationLatitude) {
		this.baseStationLatitude = baseStationLatitude;
	}

	public int getBaseStationLongitude() {
		return baseStationLongitude;
	}

	public void setBaseStationLongitude(int baseStationLongitude) {
		this.baseStationLongitude = baseStationLongitude;
	}

	public int getBaseNetworkId() {
		return baseNetworkId;
	}

	public void setBaseNetworkId(int baseNetworkId) {
		this.baseNetworkId = baseNetworkId;
	}

	public int getBaseSystemId() {
		return baseSystemId;
	}

	public void setBaseSystemId(int baseSystemId) {
		this.baseSystemId = baseSystemId;
	}

}
