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
@Table(appliesTo = "DataBattery", indexes = {
		@Index(name = "batt_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "batt_ts", columnNames = { "sampletimestamp" }) })
public class DataBattery extends Data {

	private static final long serialVersionUID = 1569401021247631204L;

	@NotNull
	private int level;

	@NotNull
	private int scale;

	@NotNull
	private int temperature;

	@NotNull
	private int voltage;

	@NotNull
	private int plugged;

	@NotNull
	private int status;

	@NotNull
	private int health;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getPlugged() {
		return plugged;
	}

	public void setPlugged(int plugged) {
		this.plugged = plugged;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
