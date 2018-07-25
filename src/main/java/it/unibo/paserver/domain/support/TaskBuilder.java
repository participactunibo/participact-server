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

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;

import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TaskBuilder extends EntityBuilder<Task> {

	@Override
	void initEntity() {
		entity = new Task();

	}

	public TaskBuilder setId(long id) {
		entity.setId(id);
		return this;
	}

	public TaskBuilder setActivationArea(String activationArea) {
		entity.setActivationArea(activationArea);
		return this;
	}

	public TaskBuilder setCanBeRefused(boolean canBeRefused) {
		entity.setCanBeRefused(canBeRefused);
		return this;
	}

	public TaskBuilder setActions(Set<Action> actions) {
		entity.setActions(actions);
		return this;
	}

	public TaskBuilder setDeadline(DateTime deadline) {
		entity.setDeadline(deadline);
		return this;
	}

	public TaskBuilder setDescription(String description) {
		entity.setDescription(description);
		return this;
	}

	public TaskBuilder setDuration(Long duration) {
		entity.setDuration(duration);
		return this;
	}

	public TaskBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	public TaskBuilder setNotificationArea(String notificationArea) {
		entity.setNotificationArea(notificationArea);
		return this;
	}

	public TaskBuilder setSensingDuration(long sensingDuration) {
		entity.setSensingDuration(sensingDuration);
		return this;
	}

	public TaskBuilder setStart(DateTime start) {
		entity.setStart(start);
		return this;
	}

	public TaskBuilder setTaskReport(Set<TaskReport> taskReport) {
		entity.setTaskReport(taskReport);
		return this;
	}

	@Override
	Task assembleEntity() {
		return entity;
	}

}
