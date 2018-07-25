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

import java.util.ArrayList;
import java.util.List;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Question;

public class ActionFlatRequest {
	
	private String name;

	private Integer numeric_threshold;

	private Integer duration_threshold;

	private Integer input_type;

	private List<QuestionRequest> questions;

	private ActionType type;

	private String title;

	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumeric_threshold() {
		return numeric_threshold;
	}

	public void setNumeric_threshold(Integer numeric_threshold) {
		this.numeric_threshold = numeric_threshold;
	}

	public Integer getDuration_threshold() {
		return duration_threshold;
	}

	public void setDuration_threshold(Integer duration_threshold) {
		this.duration_threshold = duration_threshold;
	}

	public Integer getInput_type() {
		return input_type;
	}

	public void setInput_type(Integer input_type) {
		this.input_type = input_type;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Action getAction(ActionType type) {
		if (this.getType().equals(ActionType.SENSING_MOST)) {
			ActionSensing action = new ActionSensing();
			action.setName(this.getName());
	//		action.setDuration_threshold(this.getDuration_threshold());
			action.setNumeric_threshold(this.getNumeric_threshold());
			action.setInput_type(this.getInput_type());
			return action;

		} else if (this.getType().equals(ActionType.PHOTO)) {
			ActionPhoto action = new ActionPhoto();
			action.setName(this.getName());
			action.setDuration_threshold(this.getDuration_threshold());
			action.setNumeric_threshold(this.getNumeric_threshold());
			return action;

		} else if (this.getType().equals(ActionType.QUESTIONNAIRE)) {
			ActionQuestionaire action = new ActionQuestionaire();
			action.setName(this.getName());
			action.setDuration_threshold(this.getDuration_threshold());
			action.setNumeric_threshold(this.getNumeric_threshold());
			action.setDescription(this.getDescription());
			List<Question> toAdd = new ArrayList<Question>();
			for(QuestionRequest q : this.getQuestions())
				toAdd.add(q.getQuestionModel());			
			action.setQuestions(toAdd);
			action.setTitle(this.getTitle());
			return action;

		} else if (this.getType().equals(ActionType.ACTIVITY_DETECTION)) {
			ActionActivityDetection action = new ActionActivityDetection();
			action.setName(this.getName());
			action.setDuration_threshold(this.getDuration_threshold());
			action.setNumeric_threshold(this.getNumeric_threshold());
			return action;
		} else
			return null;

	}

	public List<QuestionRequest> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionRequest> questions) {
		this.questions = questions;
	}

}
