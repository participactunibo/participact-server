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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DataQuestionaireOpenAnswer extends Data {

	private static final long serialVersionUID = 1780396465805694468L;

	@NotNull
	@JoinColumn(name = "question_id")
	@ManyToOne(fetch = FetchType.EAGER)
	private Question question;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String openAnswerValue;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getOpenAnswerValue() {
		return openAnswerValue;
	}

	public void setOpenAnswerValue(String openAnswerValue) {
		this.openAnswerValue = openAnswerValue;
	}

	@Override
	public String toString() {
		return String.format("Open answer %d to question %d: %s", getId(),
				getQuestion().getId(), getOpenAnswerValue());
	}
}
