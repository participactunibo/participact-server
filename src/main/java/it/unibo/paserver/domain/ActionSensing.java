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
import it.unibo.paserver.domain.support.Pipeline;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class ActionSensing extends Action {

	private static final long serialVersionUID = 4946939022575803236L;

	@NotNull
	private Integer input_type;

	public Integer getInput_type() {
		return input_type;
	}

	public void setInput_type(Integer input_type) {
		this.input_type = input_type;
	}

	public ActionFlat convertToActionFlat() {
		return new ActionFlat(this);
	}

	public Pipeline.Type getPipelineType() {
		return Pipeline.Type.fromInt(getInput_type());
	}

	public Class<? extends Data> getDataClass() {
		return getPipelineType().getDataClass();
	}
}
