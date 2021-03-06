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
@Table(appliesTo = "DataAccelerometerClassifier", indexes = {
		@Index(name = "acceclass_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "acceclass_ts", columnNames = { "sampletimestamp" }) })
public class DataAccelerometerClassifier extends Data {

	private static final long serialVersionUID = 7324068068645508192L;

	@NotNull
	private String classifier_value;

	public String getValue() {
		return classifier_value;
	}

	public void setValue(String classifier_value) {
		this.classifier_value = classifier_value;
	}

}
