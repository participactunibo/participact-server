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
package it.unibo.paserver.gamificationlogic;

import it.unibo.paserver.domain.GroupsTask;
import it.unibo.paserver.domain.PipelineTaskProgress;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.GroupsTaskService;
import it.unibo.paserver.service.PipelineTaskProgressService;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("PointsStrategyGroup")
public class GroupStrategy implements PointsStrategy {

	private int id = 5;
	private String name = "Group Strategy";

	@Autowired
	PipelineTaskProgressService pipelineTaskProgressService;

	@Autowired
	GroupsTaskService groupsTaskService;

	@Autowired
	UserService userService;

	@Autowired
	private PointsService pointsService;

	@Override
	public Points computePoints(User user, Task task, PointsType type,
			boolean persist) {

		List<PipelineTaskProgress> pipelineTaskProgress = pipelineTaskProgressService
				.findByTaskStepId(task.getId());

		PipelineTaskProgress p = pipelineTaskProgress.get(0);

		List<GroupsTask> groupsTask = groupsTaskService
				.getGroupsTaskByGroupId(p.getGroup_id());

		ArrayList<Long> componentOfGroup = new ArrayList<Long>();

		for (GroupsTask g : groupsTask)
			componentOfGroup.add(g.getComponentOfGroupId());

		ArrayList<User> toAddPoints = new ArrayList<User>();

		for (long l : componentOfGroup)
			toAddPoints.add(userService.getUser(l));

		DateTime now = new DateTime();

		for (User u : toAddPoints) {
			if (persist)
				pointsService.create(u, task, now, 5, type);
		}

		if (persist)
			return pointsService.create(user, task, now, 20, type);

		else {
			Points result = new Points();
			result.setDate(now);
			result.setTask(task);
			result.setUser(user);
			result.setValue(20);
			result.setType(type);
			return result;
		}
	}

	@Override
	public Points computePoints(User user, Task task,
			List<Reputation> reputations, PointsType type, boolean persist) {
		DateTime now = new DateTime();
		Points result = new Points();
		result.setDate(now);
		result.setTask(task);
		result.setUser(user);
		result.setValue(20);
		result.setType(type);
		return result;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupStrategy other = (GroupStrategy) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
