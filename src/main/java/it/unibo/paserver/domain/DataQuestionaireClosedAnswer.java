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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class DataQuestionaireClosedAnswer extends Data {

	private static final long serialVersionUID = -1900866387953714053L;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "closed_answer_id")
	private ClosedAnswer closedAnswer;

	@NotNull
	private Boolean closedAnswerValue;

	public Boolean getClosedAnswerValue() {
		return closedAnswerValue;
	}

	public void setClosedAnswerValue(Boolean closedAnswerValue) {
		this.closedAnswerValue = closedAnswerValue;
	}

	public ClosedAnswer getClosedAnswer() {
		return closedAnswer;
	}

	public void setClosedAnswer(ClosedAnswer closedAnswer) {
		this.closedAnswer = closedAnswer;
	}

	public Question getQuestion() {
		return closedAnswer.getQuestion();
	}

	@Override
	public String toString() {
		return String.format("Closed answer %d to question %d (%s) : %s",
				getId(), getQuestion().getId(), getClosedAnswer()
						.getAnswerDescription(), getClosedAnswerValue());
	}
}
