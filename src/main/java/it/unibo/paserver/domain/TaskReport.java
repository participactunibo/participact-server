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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaskReport implements Serializable {

	private static final long serialVersionUID = -6980945555990044969L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "task_report_id")
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private Task task;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "taskReport")
	private TaskResult taskResult;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "taskReport")
	@OrderBy("timestamp ASC")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<TaskHistory> history;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskState currentState = TaskState.UNKNOWN;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime acceptedDateTime;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime expirationDateTime;

	public TaskReport() {
		taskResult = new TaskResult();
		taskResult.setTaskReport(this);
		history = new ArrayList<TaskHistory>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<TaskHistory> getHistory() {
		return history;
	}

	public void setHistory(List<TaskHistory> history) {
		this.history = history;
	}

	public TaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TaskResult taskResult) {
		this.taskResult = taskResult;
	}

	public TaskState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(TaskState currentState) {
		this.currentState = currentState;
	}

	public DateTime getAcceptedDateTime() {
		return acceptedDateTime;
	}

	public void setAcceptedDateTime(DateTime acceptedDateTime) {
		this.acceptedDateTime = acceptedDateTime;
	}

	public DateTime getExpirationDateTime() {
		return expirationDateTime;
	}

	public void setExpirationDateTime(DateTime expirationDateTime) {
		this.expirationDateTime = expirationDateTime;
	}

	public void addHistory(TaskHistory taskHistory) {
		if (taskHistory == null) {
			throw new NullPointerException("taskHistory");
		}
		if (taskHistory.getState() == null) {
			throw new NullPointerException("taskHistory.getState");
		}
		getHistory().add(taskHistory);
		setCurrentState(taskHistory.getState());
	}

	public TaskHistory getTaskHistoryByState(TaskState taskState) {

		if (history == null) {
			return null;
		}

		for (TaskHistory taskHistory : history) {
			if (taskHistory.getState() == taskState) {
				return taskHistory;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		if (id != null) {
			hash += id * 37;
		}
		if (user != null) {
			hash += user.hashCode() * 101;
		}
		if (task != null) {
			hash += task.hashCode() * 97;
		}
		return hash;
	}
}
