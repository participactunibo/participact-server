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
package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.User;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TaskReportBuilder extends EntityBuilder<TaskReport> {

	@Override
	void initEntity() {
		entity = new TaskReport();
	}

	public TaskReportBuilder setAcceptedDateTime(DateTime acceptedDateTime) {
		entity.setAcceptedDateTime(acceptedDateTime);
		return this;
	}

	public TaskReportBuilder setCurrentState(TaskState currentState) {
		entity.setCurrentState(currentState);
		return this;
	}

	public TaskReportBuilder setExpirationDateTime(DateTime expirationDateTime) {
		entity.setExpirationDateTime(expirationDateTime);
		return this;
	}

	public TaskReportBuilder setHistory(List<TaskHistory> history) {
		entity.setHistory(history);
		return this;
	}

	public TaskReportBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskReportBuilder setTask(Task task) {
		entity.setTask(task);
		return this;
	}

	public TaskReportBuilder setTaskResult(TaskResult taskResult) {
		entity.setTaskResult(taskResult);
		return this;
	}

	public TaskReportBuilder setUser(User user) {
		entity.setUser(user);
		return this;
	}

	@Override
	TaskReport assembleEntity() {
		return entity;
	}

}
