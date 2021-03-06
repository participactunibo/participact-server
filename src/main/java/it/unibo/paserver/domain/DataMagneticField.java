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
@Table(appliesTo = "DataMagneticField", indexes = {
		@Index(name = "magfield_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "magfield_ts", columnNames = { "sampletimestamp" }) })
public class DataMagneticField extends Data {

	private static final long serialVersionUID = -7734250913687773940L;

	@NotNull
	private float magneticFieldX;

	@NotNull
	private float magneticFieldY;

	@NotNull
	private float magneticFieldZ;

	public float getMagneticFieldX() {
		return magneticFieldX;
	}

	public void setMagneticFieldX(float magneticFieldX) {
		this.magneticFieldX = magneticFieldX;
	}

	public float getMagneticFieldY() {
		return magneticFieldY;
	}

	public void setMagneticFieldY(float magneticFieldY) {
		this.magneticFieldY = magneticFieldY;
	}

	public float getMagneticFieldZ() {
		return magneticFieldZ;
	}

	public void setMagneticFieldZ(float magneticFieldZ) {
		this.magneticFieldZ = magneticFieldZ;
	}

}
