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
package it.unibo.paserver.domain.flat;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionGeofence;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Question;

import java.io.Serializable;
import java.util.List;

public class ActionFlat implements Serializable {

	private static final long serialVersionUID = 9097140818477794937L;

	private Long id;

	private String name;

	private Integer numeric_threshold;

	private Integer duration_threshold;

	private Integer input_type;

	private List<Question> questions;

	private ActionType type;

	private String title;

	private String descriptionGeofence;

	private String interestPointString;
	
	

	public String getDescriptionGeofence() {
		return descriptionGeofence;
	}

	public void setDescriptionGeofence(String descriptionGeofence) {
		this.descriptionGeofence = descriptionGeofence;
	}

	public String getInterestPointString() {
		return interestPointString;
	}

	public void setInterestPointString(String interestPointString) {
		this.interestPointString = interestPointString;
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

	private String description;

	public ActionFlat() {
	}

	public ActionFlat(Action action) {
	}

	public ActionFlat(ActionActivityDetection action) {
		init(action);
		this.setType(ActionType.ACTIVITY_DETECTION);
	}

	public ActionFlat(ActionSensing action) {
		init(action);
		this.input_type = action.getInput_type();
		this.setType(ActionType.SENSING_MOST);
	}

	public ActionFlat(ActionPhoto action) {
		init(action);
		this.setType(ActionType.PHOTO);
	}

	public ActionFlat(ActionQuestionaire action) {
		init(action);
		this.questions = action.getQuestions();
		this.setTitle(action.getTitle());
		this.setDescription(action.getDescription());
		this.setType(ActionType.QUESTIONNAIRE);
	}

	public ActionFlat(ActionGeofence action) {
		init(action);
		this.setDescriptionGeofence(action.getDescription());
		this.setInterestPointString(action.getInterestPoints());
		this.setType(ActionType.GEOFENCE);
	}

	private void init(Action action) {
		this.id = action.getId();
		this.name = action.getName();
		this.duration_threshold = action.getDuration_threshold();
		this.numeric_threshold = action.getNumeric_threshold();
		this.input_type = -1;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

}
