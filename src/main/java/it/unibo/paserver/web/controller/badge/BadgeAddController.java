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
package it.unibo.paserver.web.controller.badge;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.BadgeService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.GCMController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

@Controller
public class BadgeAddController {

	@Autowired
	private BadgeService badgeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskReportService taskReportService;
	@Autowired
	private GCMController gcmController;
	@Autowired
	private UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(BadgeAddController.class);

	public BadgeHolder initBadgeHolder() {
		return new BadgeHolder();
	}

	public Event validateBadgeDetails(BadgeHolder holder,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();
		if (StringUtils.isBlank(holder.getTitle())) {
			messageBuilder.source("holder.badge.title");
			messageBuilder.code("required.badge");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(holder);
		}

		if (StringUtils.isBlank(holder.getDescription())) {
			messageBuilder.source("holder.badge.description");
			messageBuilder.code("required.badge");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(holder);
		}

		return new EventFactorySupport().success(holder);

	}

	public Event validateActionsBadgeDetails(BadgeActions badge,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (badge.getActionType() == null) {
			messageBuilder.source("badge.actionType");
			messageBuilder.code("required.badge");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(badge);

		}

		if (badge.getQuantity() <= 0) {
			messageBuilder.source("badge.quantity");
			messageBuilder.code("invalid.badge.quantity");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(badge);
		}

		try {
			BadgeActions testIfAlreadyPresent = badgeService
					.getBadgeForActionTypeAndQuantity(badge.getActionType(),
							badge.getQuantity());
			if (testIfAlreadyPresent != null) {
				messageBuilder.source("badge.quantity");
				messageBuilder.code("invalid.badge.quantity");
				messageContext.addMessage(messageBuilder.build());
				return new EventFactorySupport().error(badge);
			}

		} catch (NoResultException e) {

			return new EventFactorySupport().success(badge);

		}

		return new EventFactorySupport().success(badge);

	}

	public Event validateTaskBadgeDetails(IdTaskHolder id,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (id.getId() <= 0) {
			messageBuilder.source("badge.task");
			messageBuilder.code("required.badge");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(id);
		}

		return new EventFactorySupport().success(id);

	}

	public Map<ActionType, String> initAvailableActionTypes() {
		Map<ActionType, String> result = new LinkedHashMap<ActionType, String>();

		// Get available pipelines in alphabetical order
		List<ActionType> atypes = new ArrayList<ActionType>();
		for (ActionType atype : ActionType.values()) {
			atypes.add(atype);
		}
		Collections.sort(atypes);

		result.put(null, "");
		for (ActionType currentActionType : atypes) {
			result.put(currentActionType, currentActionType.toString());
		}

		return result;
	}

	public Map<Long, String> initAvailableTasks() {
		Map<Long, String> result = new LinkedHashMap<Long, String>();

		List<Task> allTasks = taskService.getTasks();
				
		Collections.sort(allTasks, new TaskComparator());
		
		result.put(null, "");
		
		for (Task currentTask : allTasks)
			result.put(currentTask.getId(), currentTask.getName());
		return result;
	}

	public BadgeActions initBadgeActions(BadgeHolder holder) {
		BadgeActions result = new BadgeActions();
		result.setTitle(holder.getTitle());
		result.setDescription(holder.getDescription());
		return result;
	}

	public IdTaskHolder initIdTaskHolder() {
		IdTaskHolder holder = new IdTaskHolder();
		holder.setId(-1);
		return holder;
	}

	public void saveBadge(Badge badge) {
		badgeService.save(badge);

	}

	public void saveBadge(BadgeHolder badgeHolder, IdTaskHolder idTaskHolder) {
		Task task = taskService.findById(idTaskHolder.getId());
		Badge newBadge = badgeService.createBadgeTask(task, badgeHolder.getTitle(),
				badgeHolder.getDescription());
		List<TaskReport> reports = taskReportService.getTaskReportsByTask(task.getId(), TaskState.COMPLETED_WITH_SUCCESS);
		
		if(reports==null)
			return;
		
		List<String> usersMailList = new ArrayList<String>(reports.size());
		for(TaskReport currentReport: reports) {
			String mailString = currentReport.getUser().getOfficialEmail();
			User user = userService.getUser(mailString);
			if(user!=null) {
				if(!user.getBadges().contains(newBadge)) {
					usersMailList.add(mailString);
					user.getBadges().add(newBadge);
					userService.save(user);
				}
			}
		}
		
		if(usersMailList.size()>0)
			gcmController.notifyUsers(PANotification.Type.NEW_BADGE, usersMailList);
		
	}
	
	private class TaskComparator implements Comparator<Task> {
	    public int compare(Task obj1, Task obj2) {
	    	return obj1.getName().toUpperCase().compareTo(obj2.getName().toUpperCase());
	    } 
	}

}
