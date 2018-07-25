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
package it.unibo.paserver.web.controller.task;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.gamificationlogic.PointsStrategy;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.PointsStrategyForTaskService;
import it.unibo.paserver.service.PointsStrategyService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.GCMController;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;

@Controller
public class TaskValutateController {

	@Autowired
	GCMController gcmController;
	
	@Autowired
	TaskUserService taskService;
	
	@Autowired
	PointsStrategyService pointsStrategyService;
	
	@Autowired
	PointsStrategyForTaskService pointsStrategyForTaskService;
	
	@Autowired
	PointsService pointsService;
	
	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskValutateController.class);

	public TaskUser getTaskUser(long taskId) {
		return taskService.findById(taskId);
	}
	
	public  List<String> getAllUsers() {
		List<String> result;
		List<User> allUsers = userService.getUsers();
		if(allUsers!=null) {
			result = new ArrayList<String>(allUsers.size());
			for(User currentUser:allUsers)
				result.add(currentUser.getOfficialEmail());
			return result;
		}
		else {
			result = new ArrayList<String>(1);
			result.add("");
			return result;
		}
	}
	
	public String initAllUsers() {
		List<String> list = getAllUsers();
		String resultString = "[";
		int len = list.size();
		for(int i=0;i<len;i++){
			resultString+="\""+list.get(i)+"\"";
			if(!(i==len-1))
				resultString+=",";
		}
		resultString+="]";
		return resultString;
	}

	public void valutateTask(TaskUser task, Boolean approved,StrategyHolder strategyHolder) {
		if(approved)
		{
		taskService.valutateTaskUser(task, true);
		pointsStrategyForTaskService.create(task.getTask(), strategyHolder.getId());
		List<String> user = new ArrayList<String>();
		user.add(task.getOwner().getOfficialEmail());
		gcmController.notifyUsers(PANotification.Type.TASK_POSITIVE_VALUTATION, user);
		}
		else
		{
			taskService.valutateTaskUser(task, false);
			List<String> user = new ArrayList<String>();
			user.add(task.getOwner().getOfficialEmail());
			gcmController.notifyUsers(PANotification.Type.TASK_NEGATIVE_VALUTATION, user);
		}

	}

	public Map<Integer, String> initAvailablePointsStrategies() {
		Map<Integer, String> result = new LinkedHashMap<Integer, String>();

		List<PointsStrategy> allStrategies = pointsStrategyService.getAllStrategies();
		result.put(null, "");

		for (PointsStrategy currentStrategy : allStrategies)
			result.put(currentStrategy.getId(), currentStrategy.getName());
		return result;
	}
	
	public StrategyHolder initStategyHolder() {
		return new StrategyHolder();
	}
	
	public ApprovedPointsHolder initApprovedPointsHolder() {
		return new ApprovedPointsHolder();
	}
	
	public Event validateSelectedStrategy(StrategyHolder strategyHolder,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (strategyHolder==null || strategyHolder.getId()==null || strategyHolder.getId() <= 0) {
			messageBuilder.source("task");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(strategyHolder);
		}

		return new EventFactorySupport().success(strategyHolder);

	}
	
	public Event validateApprovedPoints(ApprovedPointsHolder approvedPointsHolder,
			MessageContext messageContext) {
		MessageBuilder messageBuilder = new MessageBuilder().error();

		if (approvedPointsHolder==null || approvedPointsHolder.getValue() <= 0) {
			messageBuilder.source("value");
			messageBuilder.code("required.task");
			messageContext.addMessage(messageBuilder.build());
			return new EventFactorySupport().error(approvedPointsHolder);
		}

		return new EventFactorySupport().success(approvedPointsHolder);
	}
	
	public void assignApprovedPoints(TaskUser userTask, ApprovedPointsHolder approvedPointsHolder) {
		
		pointsService.create(userTask.getOwner(), userTask.getTask(), new DateTime(), approvedPointsHolder.getValue(), PointsType.USER_TASK_APPROVED);
		
	}
}
