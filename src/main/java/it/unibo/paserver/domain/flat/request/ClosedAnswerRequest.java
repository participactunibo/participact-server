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
package it.unibo.paserver.domain.flat.request;

import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Question;

public class ClosedAnswerRequest {
	
		private String answerDescription;

	    private Integer answerOrder;

		public String getAnswerDescription() {
			return answerDescription;
		}

		public void setAnswerDescription(String answerDescription) {
			this.answerDescription = answerDescription;
		}

		public Integer getAnswerOrder() {
			return answerOrder;
		}

		public void setAnswerOrder(Integer answerOrder) {
			this.answerOrder = answerOrder;
		}

		public ClosedAnswer getClosedAnswer(Question question) {
			ClosedAnswer result = new ClosedAnswer();
			result.setAnswerDescription(this.answerDescription);
			result.setAnswerOrder(this.answerOrder);
			result.setQuestion(question);
			return result;
		}

}
