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
package it.unibo.paserver.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.PointsStrategyForTaskService;
import it.unibo.paserver.service.PointsStrategyService;
import it.unibo.paserver.service.ReputationService;
import it.unibo.paserver.service.SocialPresenceService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class StatisticsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private TaskUserService taskUserService;
	
	@Autowired
	private SocialPresenceService socialPresenceService;
	
	@Autowired
	private FriendshipService friendshipService;
	
	@Autowired
	private PointsService pointsService;
	
	@Autowired
	private ReputationService reputationService;
	
	@Autowired
	private PointsStrategyForTaskService pointsStrategyForTaskService;
	
	@Autowired
	private PointsStrategyService pointsStrategyService;
	
	@Autowired
	private TaskReportService taskReportService;
	
	@RequestMapping(value = "/protected/statistics", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("protected/statistics/main");
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/statistics/tasksCount", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView tasksCount(ModelAndView modelAndView) {			

		//Recupero Dati
		long userTasks = taskUserService.getTaskUsersCount();
		long adminTasks = taskService.getTasksByDate(new DateTime(2015,2,15,0,0), new DateTime(), "admin").size();


		modelAndView.setViewName("protected/statistics/tasksCount");
		modelAndView.addObject("userTasks", userTasks);
		modelAndView.addObject("adminTasks", adminTasks);
		return modelAndView;

	}
	
	@RequestMapping(value = "/protected/statistics/taskStatus", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView taskStatus(ModelAndView modelAndView) {	

		int year = Calendar.getInstance().get(Calendar.YEAR);
		int month = Calendar.getInstance().get(Calendar.MONTH);
		
		DateTime currentMonth = new DateTime(year, month+1, 1, 0, 0);
						
		List<User> users = userService.getWorkingUsers(11, currentMonth);
		Set<User> userSet = new HashSet<User>(users);
		
		double sumTaskAcceptedOnWorkersRatio = 0;
		double sumTaskRefusedOnWorkersRatio = 0;
		double sumTaskIgnoredOnWorkersRatio = 0;

		double meanTaskUserAccepted = 0;
		double meanTaskUserRefused = 0;
		double meanTaskUserIgnored = 0;

		double meanTaskAccepted = 0;
		double meanTaskRefused = 0;
		double meanTaskIgnored = 0;

		double meanTaskOldAccepted = 0;
		double meanTaskOldRefused = 0;
		double meanTaskOldIgnored = 0;

		double meanTaskNewAccepted = 0;
		double meanTaskNewRefused = 0;
		double meanTaskNewIgnored = 0;
		
		
		
		List<TaskUser> userTasks = taskUserService.getTaskUser();
		double den = 0;
		for(TaskUser tu : userTasks)
		{
			int numTaskIgnored = 0;
			int numTaskAccepted = 0;
			int numTaskRefused = 0;

			List<TaskReport> tReports = taskReportService.getTaskReportsByTask(tu.getTask().getId());
			for(TaskReport tr : tReports)
			{
				if(!userSet.contains(tr.getUser()))
					continue;
				
				if(tr.getCurrentState().equals(TaskState.IGNORED))
					numTaskIgnored++;
				else if(tr.getCurrentState().equals(TaskState.REJECTED))
					numTaskRefused++;
				else if(tr.getCurrentState().equals(TaskState.AVAILABLE))
					continue;
				else
					numTaskAccepted++;
			}
			double numWokers = numTaskIgnored+numTaskRefused+numTaskAccepted;
			if(numWokers > 0){	
				sumTaskAcceptedOnWorkersRatio = sumTaskAcceptedOnWorkersRatio + (numTaskAccepted/numWokers); 
				sumTaskRefusedOnWorkersRatio = sumTaskRefusedOnWorkersRatio + (numTaskRefused/numWokers);
				sumTaskIgnoredOnWorkersRatio = sumTaskIgnoredOnWorkersRatio + (numTaskIgnored/numWokers);
				den++;
			}
		}
		if(den > 0)
		{
			meanTaskUserAccepted = sumTaskAcceptedOnWorkersRatio / den;
			meanTaskUserIgnored = sumTaskIgnoredOnWorkersRatio / den;
			meanTaskUserRefused = sumTaskRefusedOnWorkersRatio / den;
			double tot = meanTaskUserAccepted + meanTaskUserIgnored + meanTaskUserRefused;
			if(tot!=0)
			{
				meanTaskUserAccepted = (meanTaskUserAccepted / tot) * 100;
				meanTaskUserIgnored = (meanTaskUserIgnored/tot) * 100;
				meanTaskUserRefused = (meanTaskUserRefused/tot) * 100;
			}
		}
		
		sumTaskAcceptedOnWorkersRatio = 0; 
		sumTaskRefusedOnWorkersRatio = 0;
		sumTaskIgnoredOnWorkersRatio = 0;
	
		List<Task> adminTask = taskService.getTasksByDate(new DateTime(2015, 2, 15, 0, 0), new DateTime(), "admin");
		den = 0;
		for(Task t : adminTask)
		{
			int numTaskIgnored = 0;
			int numTaskAccepted = 0;
			int numTaskRefused = 0;


			List<TaskReport> tReports = taskReportService.getTaskReportsByTask(t.getId());
			for(TaskReport tr : tReports)
			{
				
				if(!userSet.contains(tr.getUser()))
					continue;
				
				if(tr.getCurrentState().equals(TaskState.IGNORED))
					numTaskIgnored++;
				else if(tr.getCurrentState().equals(TaskState.REJECTED))
					numTaskRefused++;
				else if(tr.getCurrentState().equals(TaskState.AVAILABLE))
					continue;
				else
					numTaskAccepted++;
			}
			double numWokers = numTaskIgnored+numTaskRefused+numTaskAccepted;
			if(numWokers > 0){
				sumTaskAcceptedOnWorkersRatio = sumTaskAcceptedOnWorkersRatio + (numTaskAccepted/numWokers); 
				sumTaskRefusedOnWorkersRatio = sumTaskRefusedOnWorkersRatio + (numTaskRefused/numWokers);
				sumTaskIgnoredOnWorkersRatio = sumTaskIgnoredOnWorkersRatio + (numTaskIgnored/numWokers);
				den++;
			}
		}
		if(den > 0)
		{
			meanTaskAccepted = sumTaskAcceptedOnWorkersRatio / den;
			meanTaskIgnored = sumTaskIgnoredOnWorkersRatio / den;
			meanTaskRefused = sumTaskRefusedOnWorkersRatio / den;
			double tot = meanTaskAccepted + meanTaskIgnored + meanTaskRefused;
			if(tot > 0)
			{
				meanTaskAccepted = (meanTaskAccepted / tot) * 100;
				meanTaskIgnored = (meanTaskIgnored / tot) * 100;
				meanTaskRefused = (meanTaskRefused / tot) * 100;
			}
		}
		sumTaskAcceptedOnWorkersRatio = 0; 
		sumTaskRefusedOnWorkersRatio = 0;
		sumTaskIgnoredOnWorkersRatio = 0;


		//second part on historical profile
		//take all task before 15/02 and all task after 15/02 
		//let's see some statistics on refused ignored and accepted

		DateTime startOld = new DateTime().minusMonths(3);
		
		List<Task> oldTasks = taskService.getTasksByDate(startOld, new DateTime(2015,2,15,0,0), "all");
		List<Task> newTasks = taskService.getTasksByDate(new DateTime(2015,2,15,0,0),new DateTime(), "all");

		den = 0;
		for(Task t : oldTasks)
		{
			int numTaskIgnored = 0;
			int numTaskAccepted = 0;
			int numTaskRefused = 0;

			List<TaskReport> tReports = taskReportService.getTaskReportsByTask(t.getId());
			for(TaskReport tr : tReports)
			{
				if(!userSet.contains(tr.getUser()))
					continue;
				
				if(tr.getCurrentState().equals(TaskState.IGNORED))
					numTaskIgnored++;
				else if(tr.getCurrentState().equals(TaskState.REJECTED))
					numTaskRefused++;
				else if(tr.getCurrentState().equals(TaskState.AVAILABLE))
					continue;
				else
					numTaskAccepted++;
			}
			double numWorkers = numTaskIgnored+numTaskRefused+numTaskAccepted;
			if(numWorkers > 0)
			{
				sumTaskAcceptedOnWorkersRatio = sumTaskAcceptedOnWorkersRatio + (numTaskAccepted/numWorkers); 
				sumTaskRefusedOnWorkersRatio = sumTaskRefusedOnWorkersRatio + (numTaskRefused/numWorkers);
				sumTaskIgnoredOnWorkersRatio = sumTaskIgnoredOnWorkersRatio + (numTaskIgnored/numWorkers);
				den++;
			}
					
		}
		if(den > 0)
		{
			meanTaskOldAccepted = sumTaskAcceptedOnWorkersRatio / den;
			meanTaskOldIgnored = sumTaskIgnoredOnWorkersRatio / den;
			meanTaskOldRefused = sumTaskRefusedOnWorkersRatio / den;
			double tot = meanTaskOldAccepted + meanTaskOldIgnored + meanTaskOldRefused;
			if(tot > 0)
			{
				meanTaskOldAccepted = (meanTaskOldAccepted / tot) * 100;
				meanTaskOldIgnored = (meanTaskOldIgnored / tot) * 100;
				meanTaskOldRefused = (meanTaskOldRefused / tot) * 100;
			}
		}
		
		sumTaskAcceptedOnWorkersRatio = 0; 
		sumTaskRefusedOnWorkersRatio = 0;
		sumTaskIgnoredOnWorkersRatio = 0;
		den = 0;
		for(Task t : newTasks)
		{
			int numTaskIgnored = 0;
			int numTaskAccepted = 0;
			int numTaskRefused = 0;

			List<TaskReport> tReports = taskReportService.getTaskReportsByTask(t.getId());
			for(TaskReport tr : tReports)
			{
				if(!userSet.contains(tr.getUser()))
					continue;
				
				if(tr.getCurrentState().equals(TaskState.IGNORED))
					numTaskIgnored++;
				else if(tr.getCurrentState().equals(TaskState.REJECTED))
					numTaskRefused++;
				else if(tr.getCurrentState().equals(TaskState.AVAILABLE))
					continue;
				else
					numTaskAccepted++;
			}
			double numWorkers = numTaskIgnored+numTaskRefused+numTaskAccepted;
			if(numWorkers > 0)
			{
				sumTaskAcceptedOnWorkersRatio = sumTaskAcceptedOnWorkersRatio + (numTaskAccepted/numWorkers); 
				sumTaskRefusedOnWorkersRatio = sumTaskRefusedOnWorkersRatio + (numTaskRefused/numWorkers);
				sumTaskIgnoredOnWorkersRatio = sumTaskIgnoredOnWorkersRatio + (numTaskIgnored/numWorkers);
				den++;
			}
			
		}
		if(den > 0)
		{
			meanTaskNewAccepted = sumTaskAcceptedOnWorkersRatio / den;
			meanTaskNewIgnored = sumTaskIgnoredOnWorkersRatio / den;
			meanTaskNewRefused = sumTaskRefusedOnWorkersRatio / den;
			double tot = meanTaskNewAccepted + meanTaskNewIgnored + meanTaskNewRefused;
			if(tot > 0)
			{
				meanTaskNewAccepted = (meanTaskNewAccepted / tot) * 100;
				meanTaskNewIgnored = (meanTaskNewIgnored / tot) * 100;
				meanTaskNewRefused = (meanTaskNewRefused / tot) * 100;
			}
		}


		modelAndView.addObject("meantaskUserAccepted", meanTaskUserAccepted);
		modelAndView.addObject("meantaskUserIgnored", meanTaskUserIgnored);
		modelAndView.addObject("meantaskUserRefused", meanTaskUserRefused);

		modelAndView.addObject("meanTaskAccepted", meanTaskAccepted);
		modelAndView.addObject("meanTaskIgnored", meanTaskIgnored);
		modelAndView.addObject("meanTaskRefused", meanTaskRefused);		
		
		modelAndView.addObject("meanTaskOldAccepted", meanTaskOldAccepted);
		modelAndView.addObject("meanTaskOldIgnored", meanTaskOldIgnored);
		modelAndView.addObject("meanTaskOldRefused", meanTaskOldRefused);	
		
		modelAndView.addObject("meanTaskNewAccepted", meanTaskNewAccepted);
		modelAndView.addObject("meanTaskNewIgnored", meanTaskNewIgnored);
		modelAndView.addObject("meanTaskNewRefused", meanTaskNewRefused);	
		modelAndView.setViewName("protected/statistics/taskStatus");

		return modelAndView;	
	}
	
	@RequestMapping(value = "/protected/statistics/actionsForUserTask", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView actionsForUserTask(ModelAndView modelAndView) {
		
		List<TaskUser> tasksUsers = taskUserService.getTaskUser();
		List<Task> allTasks = new ArrayList<Task>(tasksUsers.size());
		for(TaskUser currenTaskUser : tasksUsers)
			allTasks.add(currenTaskUser.getTask());
		
		Map<ActionType, Integer> tasksPerActionTypeMap = new HashMap<ActionType, Integer>();
		
		for(Task task: allTasks) {
			Set<Action> actions =  task.getActions();
			for(Action action : actions) {
				ActionType actionType = action.getType();
				if(tasksPerActionTypeMap.containsKey(actionType))
					tasksPerActionTypeMap.put(actionType, tasksPerActionTypeMap.get(actionType)+1);
				else
					tasksPerActionTypeMap.put(actionType,1);
			}
		}
		
		modelAndView.setViewName("protected/statistics/actionsForUserTask");
		modelAndView.addObject("tasksPerActionTypeMap", tasksPerActionTypeMap);
		return modelAndView;	
	}
	
	@RequestMapping(value = "/protected/statistics/averagePoints", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@Transactional(readOnly = true)
	public ModelAndView averagePoints(ModelAndView modelAndView) {	
		
		List<Points> allPoints = pointsService.getPoints();
		double averagePoints = 0;
		Map<Long, Integer> completedPointsMap = new HashMap<Long, Integer>();
		Map<Long, Integer> approvedPointsMap = new HashMap<Long, Integer>();
		Map<Long, Integer> successUsersMap = new HashMap<Long, Integer>();
		
		for (Points current: allPoints) {
			long taskId = current.getTask().getId();
			
			if(current.getType()==PointsType.USER_TASK_APPROVED)
				approvedPointsMap.put(taskId, current.getValue());
			else if(current.getType()==PointsType.TASK_COMPLETED_WITH_SUCCESS) {
				if(completedPointsMap.containsKey(taskId))
					completedPointsMap.put(taskId, completedPointsMap.get(taskId)+current.getValue());
				else
					completedPointsMap.put(taskId, current.getValue());
				
				if(successUsersMap.containsKey(taskId))
					successUsersMap.put(taskId, successUsersMap.get(taskId)+1);
				else
					successUsersMap.put(taskId, 1);
			}
			
		}
		Set<Long> completedIds = completedPointsMap.keySet();
		Set<Long> approvedIdSet = approvedPointsMap.keySet();
		Set<Long> tasksIds = new HashSet<Long>();
		for(Long id :completedIds) {
			if(!tasksIds.contains(id))
				tasksIds.add(id);
		}
		
		for(Long id: approvedIdSet) {
			if(!tasksIds.contains(id))
				tasksIds.add(id);
		}
		
		
		
		double totalAverageForTaskPoints = 0;
		double totalTasks = tasksIds.size();
		for(Long taskId : tasksIds) {
		
			double total=0;
			double users=0;
			
			if(approvedPointsMap.containsKey(taskId)) {
				total+=approvedPointsMap.get(taskId);
				users++;
			}
			
			if(successUsersMap.containsKey(taskId)) {
				total+=completedPointsMap.get(taskId);
				users+=successUsersMap.get(taskId);
							
			}
			if(users>0)
				totalAverageForTaskPoints+=total/users;
		}
		
		averagePoints = totalAverageForTaskPoints/totalTasks;
		
		List<User> allUsers = userService.getUsers();
		
		Map<Long, Integer> pointsForUserMap = new HashMap<Long, Integer>();
		
		DateTime to = new DateTime();
		DateTime from = new DateTime(2015,2,15,0,0);
		
		for(User currentUser :allUsers) {
			
			Score scoreForUser = pointsService.getScoreByUserAndDates(currentUser.getId(), from, to);
			
			if(pointsForUserMap.containsKey(currentUser.getId()))
				pointsForUserMap.put(currentUser.getId(), pointsForUserMap.get(currentUser.getId())+scoreForUser.getValue());
			else 
				pointsForUserMap.put(currentUser.getId(), scoreForUser.getValue());
			
		}
		
		double days = Days.daysBetween(from, to).getDays();
		Map<Long, Double> pointsForUserDoubleMap = new HashMap<Long, Double>();
		
		
		for(Long userId: pointsForUserMap.keySet()) {
			double mean = pointsForUserMap.get(userId) / days;
			pointsForUserDoubleMap.put(userId, mean);
		}
		
		double totalMeans = 0;
		Set<Long> allIds = pointsForUserDoubleMap.keySet();
		int totalIdsCount = allIds.size();
		for(Long currentId : allIds) {
			totalMeans+=pointsForUserDoubleMap.get(currentId);
		}
		
		double meanForDay = totalMeans/totalIdsCount;
		
		modelAndView.setViewName("protected/statistics/averagePoints");
		modelAndView.addObject("averagePoints", averagePoints);
		modelAndView.addObject("meanForDay", meanForDay);
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/statistics/pointStrategies", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView pointStrategies(ModelAndView modelAndView) {			
		
		List<PointsStrategyForTask> pointsStrategies = pointsStrategyForTaskService.getPointsStrategyForTasks();
		
		int averageLevelOk = 0;
		int averageReputationOk = 0;
		int sumLevelOk = 0;
		int sumReputationOk = 0;
		
		int averageLevelIgnored = 0;
		int averageReputationIgnored = 0;
		int sumLevelIgnored = 0;
		int sumReputationIgnored = 0;
		
		int averageLevelRejected = 0;
		int averageReputationRejected = 0;
		int sumLevelRejected = 0;
		int sumReputationRejected = 0;
		
		double totalAverageLevel =0;
		double totalAverageReputation = 0;
		double totalSumLevel = 0;
		double totalSumReputation = 0;
		
		for(PointsStrategyForTask current: pointsStrategies) {
			Task task = current.getTask();
			int strategyId = current.getStrategyId();
			List<TaskReport> reports = taskReportService.getTaskReportsByTask(task.getId());
			for (TaskReport currentReport : reports) {
				TaskState currentState = currentReport.getCurrentState();
				if (currentState == TaskState.ACCEPTED || currentState == TaskState.COMPLETED_WITH_FAILURE || currentState == TaskState.COMPLETED_WITH_SUCCESS || currentState == TaskState.RUNNING ) {
					switch (strategyId) {
					case 1:
						averageLevelOk++;
						break;

					case 2:
						averageReputationOk++;
						break;
						
					case 3:
						sumLevelOk++;
						break;
					
					case 4:
						sumReputationOk++;
						break;
						
					default:
						break;
					}
				} else if (currentState == TaskState.IGNORED) {
					switch (strategyId) {
					case 1:
						averageLevelIgnored++;
						break;

					case 2:
						averageReputationIgnored++;
						break;
						
					case 3:
						sumLevelIgnored++;
						break;
					
					case 4:
						sumReputationIgnored++;
						break;
						
					default:
						break;
					}
					
					
				} else if(currentState == TaskState.REJECTED) {
					switch (strategyId) {
					case 1:
						averageLevelRejected++;
						break;

					case 2:
						averageReputationRejected++;
						break;
						
					case 3:
						sumLevelRejected++;
						break;
					
					case 4:
						sumReputationRejected++;
						break;
						
					default:
						break;
					}
								
				}
			}
			
		}
		
		totalAverageLevel = averageLevelOk + averageLevelIgnored + averageLevelRejected;
		totalAverageReputation = averageReputationOk + averageReputationIgnored + averageReputationRejected;
		totalSumLevel = sumLevelOk + sumLevelIgnored + sumLevelRejected;
		totalSumReputation = sumReputationOk + sumReputationIgnored + sumReputationRejected;
		
		double averageLevelOkRatio =0;
		double averageLevelIgnoredRatio=0;
		double averageLevelRejectedRatio=0;
		
		double averageReputationOkRatio =0;
		double averageReputationIgnoredRatio=0;
		double averageReputationRejectedRatio =0;
		
		double sumLevelOkRatio=0;
		double sumLevelIgnoredRatio=0;
		double sumLevelRejectedRatio=0;
		
		double sumReputationOkRatio=0;
		double sumReputationIgnoredRatio=0;
		double sumReputationRejectedRatio=0;
		
		if(totalAverageLevel!=0) {
			averageLevelOkRatio = 100*((double)averageLevelOk) / totalAverageLevel;
			averageLevelIgnoredRatio =100* ((double)averageLevelIgnored) / totalAverageLevel;
			averageLevelRejectedRatio =100* ((double)averageLevelRejected) / totalAverageLevel;
		}
		
		if(totalAverageReputation!=0) {
			averageReputationOkRatio =100* ((double)averageReputationOk) / totalAverageReputation;
			averageReputationIgnoredRatio =100* ((double)averageReputationIgnored) / totalAverageReputation;
			averageReputationRejectedRatio =100* ((double)averageReputationRejected) / totalAverageReputation;
		}
		
		if(totalSumLevel!=0) {
			sumLevelOkRatio =100* ((double)sumLevelOk) / totalSumLevel;
			sumLevelIgnoredRatio = 100*((double)sumLevelIgnored) / totalSumLevel;
			sumLevelRejectedRatio =100* ((double)sumLevelRejected) / totalSumLevel;
		}
		
		if(totalSumReputation!=0) {
			sumReputationOkRatio = 100*((double)sumReputationOk) / totalSumReputation;
			sumReputationIgnoredRatio = 100*((double)sumReputationIgnored) / totalSumReputation;
			sumReputationRejectedRatio =100* ((double)sumReputationRejected) / totalSumReputation;
		}
		
		modelAndView.setViewName("protected/statistics/pointStrategies");
		
		modelAndView.addObject("averageLevelOk", averageLevelOk);
		modelAndView.addObject("averageReputationOk", averageReputationOk);
		modelAndView.addObject("sumLevelOk", sumLevelOk);
		modelAndView.addObject("sumReputationOk", sumReputationOk);
		modelAndView.addObject("averageLevelIgnored", averageLevelIgnored);
		modelAndView.addObject("averageReputationIgnored", averageReputationIgnored);
		modelAndView.addObject("sumLevelIgnored", sumLevelIgnored);
		modelAndView.addObject("sumReputationIgnored", sumReputationIgnored);
		modelAndView.addObject("averageLevelRejected", averageLevelRejected);
		modelAndView.addObject("averageReputationRejected", averageReputationRejected);
		modelAndView.addObject("sumLevelRejected", sumLevelRejected);
		modelAndView.addObject("sumReputationRejected", sumReputationRejected);
		
		modelAndView.addObject("averageLevelOkRatio", averageLevelOkRatio);
		modelAndView.addObject("averageLevelIgnoredRatio", averageLevelIgnoredRatio);
		modelAndView.addObject("averageLevelRejectedRatio", averageLevelRejectedRatio);
		modelAndView.addObject("averageReputationOkRatio", averageReputationOkRatio);
		modelAndView.addObject("averageReputationIgnoredRatio", averageReputationIgnoredRatio);
		modelAndView.addObject("averageReputationRejectedRatio", averageReputationRejectedRatio);
		modelAndView.addObject("sumLevelOkRatio", sumLevelOkRatio);
		modelAndView.addObject("sumLevelIgnoredRatio", sumLevelIgnoredRatio);
		modelAndView.addObject("sumLevelRejectedRatio", sumLevelRejectedRatio);
		modelAndView.addObject("sumReputationOkRatio", sumReputationOkRatio);
		modelAndView.addObject("sumReputationIgnoredRatio", sumReputationIgnoredRatio);
		modelAndView.addObject("sumReputationRejectedRatio", sumReputationRejectedRatio);
		
		return modelAndView;
		
	}
	
	@RequestMapping(value = "/protected/statistics/friendships", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView friendships(ModelAndView modelAndView) {
		
		int accepted = 0;
		int pending = 0;
		int rejected = 0;
		double averageFriends= 0;
		
		List<Friendship> allFriendships = friendshipService.getFriendships();
		
		Map<User, Integer> acceptedMap = new HashMap<User, Integer>();
		
		
		if(allFriendships!=null) {
			for (Friendship current: allFriendships) {
				FriendshipStatus status = current.getStatus();
				if (status==FriendshipStatus.PENDING)
					pending++;
				else if (status==FriendshipStatus.REJECTED)
					rejected++;
				
				else if(status==FriendshipStatus.ACCEPTED) {
					accepted++;
					
					if (acceptedMap.containsKey(current.getSender()))
						acceptedMap.put(current.getSender(), acceptedMap.get(current.getSender())+1);
					else
						acceptedMap.put(current.getSender(), 1);
					
					if (acceptedMap.containsKey(current.getReceiver()))
						acceptedMap.put(current.getReceiver(), acceptedMap.get(current.getReceiver())+1);
					else		
						acceptedMap.put(current.getReceiver(), 1);
				}
			}
			
			double totalAccepted = 0;
			Set<User> users = acceptedMap.keySet();
			double totalUsers = users.size();
			for(User currentUser : users) {
				totalAccepted+=acceptedMap.get(currentUser);
			}
			averageFriends = totalAccepted/totalUsers;
		}
		
		
		modelAndView.setViewName("protected/statistics/friendships");
		modelAndView.addObject("acceptedCount", accepted);
		modelAndView.addObject("pendingCount", pending);
		modelAndView.addObject("rejectedCount", rejected);
		modelAndView.addObject("averageFriends", averageFriends);
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/statistics/socialLogin", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView socialLogin(ModelAndView modelAndView) {
		int twitterCount =0;
		List<SocialPresence> twitter = socialPresenceService.getSocialPresencesForSocialNetwork(SocialPresenceType.TWITTER);
		if(twitter!=null)
			twitterCount=twitter.size();
		
		int facebookCount =0;
		List<SocialPresence> facebook = socialPresenceService.getSocialPresencesForSocialNetwork(SocialPresenceType.FACEBOOK);
		if(facebook!=null)
			facebookCount=facebook.size();
		
		modelAndView.setViewName("protected/statistics/socialLogin");
		modelAndView.addObject("twitterCount", twitterCount);
		modelAndView.addObject("facebookCount", facebookCount);
		
		return modelAndView;	
	}
	
	@RequestMapping(value = "/protected/statistics/reputations", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView reputations(ModelAndView modelAndView) {
		
		List<Reputation> allReputations = reputationService.getReputations();
		
		Map<ActionType, Integer> totalsMap = new HashMap<ActionType, Integer>();
		Map<ActionType, Integer> countMap = new HashMap<ActionType, Integer>();
		Map<ActionType, Double> resultMap = new HashMap<ActionType, Double>();
		
		for (Reputation reputation : allReputations) {
			int reputationValue = reputation.getValue();
			if(reputationValue!=Reputation.INIT_VALUE) {
				ActionType actionType = reputation.getActionType();
				if(totalsMap.containsKey(actionType))
					totalsMap.put(actionType, totalsMap.get(actionType)+reputationValue);
				else
					totalsMap.put(actionType, reputationValue);
				if(countMap.containsKey(actionType))
					countMap.put(actionType, countMap.get(actionType)+1);
				else
					countMap.put(actionType, 1);
			}
			
		}
		
		Set<ActionType> actionTypes = totalsMap.keySet();
		for(ActionType current : actionTypes){
			double total = totalsMap.get(current);
			double count = countMap.get(current);
			double mean = total/count;
			resultMap.put(current, mean);
		}
		
		modelAndView.setViewName("protected/statistics/reputations");
		modelAndView.addObject("resultMap", resultMap);
		
		return modelAndView;	
	}
	

}
