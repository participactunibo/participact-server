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

import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.support.JsonDateTimeDeserializer;
import it.unibo.paserver.domain.support.JsonDateTimeSerializer;
import it.unibo.paserver.domain.support.Pipeline;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
// @JsonIgnoreProperties({"taskReport", "deadline"})
@JsonIgnoreProperties({ "taskReport" })
public class Task implements Serializable {

	private static final long serialVersionUID = -1195490657879014668L;

	@Id
	@Column(name = "task_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String name;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String description;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@JsonDeserialize(using = JsonDateTimeDeserializer.class)
	@JsonSerialize(using = JsonDateTimeSerializer.class)
	private DateTime deadline;

	// @NotNull
	// private Integer points;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "Task_Actions", joinColumns = { @JoinColumn(name = "task_id") }, inverseJoinColumns = { @JoinColumn(name = "action_id") })
	private Set<Action> actions;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "task")
	private Set<TaskReport> taskReport;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime start;

	@NotNull
	private Long duration;

	private Long sensingDuration;

	private Boolean canBeRefused;

	@Column(columnDefinition = "TEXT")
	private String notificationArea;

	@Column(columnDefinition = "TEXT")
	private String activationArea;

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

	public Task() {
		taskReport = new LinkedHashSet<TaskReport>();
		canBeRefused = Boolean.TRUE;
		actions = new LinkedHashSet<Action>();
	}

	public DateTime getStart() {
		return start;
	}

	public void setStart(DateTime start) {
		this.start = start;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(DateTime deadline) {
		this.deadline = deadline;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public Set<TaskReport> getTaskReport() {
		return taskReport;
	}

	public void setTaskReport(Set<TaskReport> taskReport) {
		this.taskReport = taskReport;
	}

	public Boolean getCanBeRefused() {
		return canBeRefused;
	}

	public void setCanBeRefused(Boolean canBeRefused) {
		this.canBeRefused = canBeRefused;
	}

	@JsonIgnore
	public boolean getHasPhotos() {
		for (Action action : actions) {
			if (action instanceof ActionPhoto) {
				return true;
			}
		}
		return false;
	}

	public TaskFlat convertToTaskFlat() {
		return new TaskFlat(this);
	}

	public boolean hasPipelineType(Pipeline.Type dataType) {
		if (getActions() == null) {
			return false;
		}
		for (Action a : getActions()) {
			if (a instanceof ActionSensing) {
				if (((ActionSensing) a).getInput_type() == dataType.toInt()) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasActivityDetection() {
		for (Action action : getActions()) {
			if (action instanceof ActionActivityDetection)
				return true;
		}
		return false;
	}

	public boolean isExpired() {
		return getDeadline().isBefore(new DateTime());
	}

	@Override
	public int hashCode() {
		int hash = 0;
		if (id != null) {
			hash += id * 37;
		}
		if (duration != null) {
			hash += duration * 3;
		}
		if (name != null) {
			hash += name.hashCode() * 97;
		}
		return hash;
	}
}
