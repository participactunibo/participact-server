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
@Table(appliesTo = "DataGoogleActivityRecognition", indexes = { @Index(name = "dgar_user_sampletimestamp", columnNames = {
		"user_id", "sampletimestamp" }) })
public class DataGoogleActivityRecognition extends Data {

	private static final long serialVersionUID = 5025465812630425096L;
	@NotNull
	private String classifier_value;
	@NotNull
	private Integer confidence;

	public String getClassifier_value() {
		return classifier_value;
	}

	public void setClassifier_value(String classifier_value) {
		this.classifier_value = classifier_value;
	}

	public Integer getConfidence() {
		return confidence;
	}

	public void setConfidence(Integer confidence) {
		this.confidence = confidence;
	}
}
