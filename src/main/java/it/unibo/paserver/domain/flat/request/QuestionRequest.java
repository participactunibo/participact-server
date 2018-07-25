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

import java.util.ArrayList;
import java.util.List;

public class QuestionRequest {

	private String question;

	private  Integer question_order;

	private List<ClosedAnswerRequest> closed_answers;

	private Boolean isClosedAnswers;

	private Boolean isMultipleAnswers;


	public void setQuestion(String question) {
		this.question = question;
	}

	public void setQuestion_order(Integer question_order) {
		this.question_order = question_order;
	}

	public void setClosed_answers(List<ClosedAnswerRequest> closed_answers) {
		this.closed_answers = closed_answers;
	}

	public void setIsClosedAnswers(Boolean isClosedAnswers) {
		this.isClosedAnswers = isClosedAnswers;
	}

	public void setIsMultipleAnswers(Boolean isMultipleAnswers) {
		this.isMultipleAnswers = isMultipleAnswers;
	}

	public String getQuestion() {
		return question;
	}

	public Integer getQuestion_order() {
		return question_order;
	}

	public List<ClosedAnswerRequest> getClosed_answers() {
		return closed_answers;
	}

	public Boolean getIsClosedAnswers() {
		return isClosedAnswers;
	}

	public Boolean getIsMultipleAnswers() {
		return isMultipleAnswers;
	}

	public Question getQuestionModel() {
		Question result = new Question();
		if(!this.isClosedAnswers)
		{
			result.setQuestion(this.getQuestion());
			result.setQuestionOrder(this.question_order);
			result.setIsClosedAnswers(false);
			result.setIsMultipleAnswers(false);
		}
		else
		{
			result.setQuestion(this.question);
			result.setQuestionOrder(this.getQuestion_order());
			result.setIsClosedAnswers(true);
			result.setIsMultipleAnswers(this.isMultipleAnswers);
			List<ClosedAnswer> toAdd = new ArrayList<ClosedAnswer>();
			for(ClosedAnswerRequest c : this.getClosed_answers())
			{
				ClosedAnswer cl = c.getClosedAnswer(result);
				toAdd.add(cl);				
			}
			result.setClosed_answers(toAdd);
		}
		
		
		return result;
	}

}
