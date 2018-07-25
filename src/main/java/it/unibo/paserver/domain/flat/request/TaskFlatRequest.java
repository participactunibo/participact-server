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


import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.LocationAwareTask;
import it.unibo.paserver.domain.Task;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;



public class TaskFlatRequest {
	
	private String name;
	
	private String description;
	
	private Long duration;
	
	private Long sensingDuration;

	private Double latitude;

	private Double longitude;

	private Double radius;

	private Boolean canBeRefused;	

	private String type;

	private String notificationArea;

	private String activationArea;
	
	private DateTime deadline;

	private DateTime start;
	
	private Set<ActionFlatRequest> actions;
	
	private Set<Long> idFriends;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Long getSensingDuration() {
		return sensingDuration;
	}

	public void setSensingDuration(Long sensingDuration) {
		this.sensingDuration = sensingDuration;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	public Boolean getCanBeRefused() {
		return canBeRefused;
	}

	public void setCanBeRefused(Boolean canBeRefused) {
		this.canBeRefused = canBeRefused;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotificationArea() {
		return notificationArea;
	}

	public void setNotificationArea(String notificationArea) {
		this.notificationArea = notificationArea;
	}

	public String getActivationArea() {
		return activationArea;
	}

	public void setActivationArea(String activationArea) {
		this.activationArea = activationArea;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(DateTime deadline) {
		this.deadline = deadline;
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
	}

	public Set<ActionFlatRequest> getActions() {
		return actions;
	}

	public void setActions(Set<ActionFlatRequest> actions) {
		this.actions = actions;
	}
	
	public Set<Long> getIdFriends() {
		return idFriends;
	}

	public void setIdFriends(Set<Long> idFriends) {
		this.idFriends = idFriends;
	}
		
	public Task getTask() {
		Set<Action> actions = new HashSet<Action>();
		Set<ActionFlatRequest> actionFlatRequests = this.getActions();
		for (ActionFlatRequest actionFlatRequest : actionFlatRequests) {
			Action action = actionFlatRequest.getAction(actionFlatRequest
					.getType());
			actions.add(action);
		}


		if(this.getType().equals(LocationAwareTask.class.getSimpleName()))
		{
			LocationAwareTask t = new LocationAwareTask();
			t.setLatitude(this.getLatitude());
			t.setLongitude(this.getLatitude());
			t.setRadius(this.getRadius());
			t.setActivationArea(this.getActivationArea());
			t.setCanBeRefused(this.getCanBeRefused());
			t.setDeadline(this.getDeadline());
			t.setDescription(this.getDescription());
			t.setDuration(this.getDuration());
			t.setName(this.getName());
			t.setNotificationArea(this.getNotificationArea());
			t.setSensingDuration(this.getSensingDuration());
			t.setStart(this.getStart());
			t.setActions(actions);
			return t;			
		}
		else if (this.getType().equals(Task.class.getSimpleName()))
		{
			Task t = new Task();
			t.setActivationArea(this.getActivationArea());
			t.setCanBeRefused(this.getCanBeRefused());
			t.setDeadline(this.getDeadline());
			t.setDescription(this.getDescription());
			t.setDuration(this.getDuration());
			t.setName(this.getName());
			t.setNotificationArea(this.getNotificationArea());
			t.setSensingDuration(this.getSensingDuration());
			t.setStart(this.getStart());
			t.setActions(actions);
			return t;
		}
		return null;
	}

	

	
	

}
