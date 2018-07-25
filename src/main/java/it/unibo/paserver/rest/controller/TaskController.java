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
package it.unibo.paserver.rest.controller;

import it.unibo.participact.domain.PANotification;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionPipelineResponse;
import it.unibo.paserver.domain.ActionPipelineTask;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.MonthlyTargetScore;
import it.unibo.paserver.domain.PipelineTaskProgress;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.GroupTaskList;
import it.unibo.paserver.domain.GroupsTask;
import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.StatisticsMessage;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskFlatList;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskList;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.flat.request.TaskFlatRequest;
import it.unibo.paserver.gamificationlogic.GroupStrategy;
import it.unibo.paserver.gamificationlogic.PointsStrategy;
import it.unibo.paserver.gamificationlogic.PointsStrategyBySumLevel;
import it.unibo.paserver.gamificationlogic.ReputationStrategy;
import it.unibo.paserver.pipelinetask.BaseStrategy;
import it.unibo.paserver.pipelinetask.PipelineTaskStrategy;
import it.unibo.paserver.pipelinetask.PipelineTaskUtils;
import it.unibo.paserver.service.ActionPipelineResponseService;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.BadgeService;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.GroupsTaskService;
import it.unibo.paserver.service.MonthlyTargetScoreService;
import it.unibo.paserver.service.PipelineTaskProgressService;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.PointsStrategyForTaskService;
import it.unibo.paserver.service.PointsStrategyService;
import it.unibo.paserver.service.ReputationService;
import it.unibo.paserver.service.TaskHistoryService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.controller.GCMController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.action.EventFactorySupport;

@Controller
public class TaskController {

	@Autowired
	UserService userService;
	@Autowired
	TaskService taskService;
	@Autowired
	ActionService actionService;
	@Autowired
	TaskReportService taskReportService;
	@Autowired
	TaskHistoryService taskHistoryService;
	@Autowired
	MonthlyTargetScoreService monthlyTargetScoreService;
	@Autowired
	ReputationService reputationService;
	@Autowired
	BadgeService badgeService;
	@Autowired
	@Qualifier("ReputationStrategyTrivial")
	ReputationStrategy reputationStrategy;
	@Autowired
	PointsService pointsService;
	@Autowired
	TaskUserService taskUserService;
	@Autowired
	PointsStrategyForTaskService pointsStrategyForTaskService;
	@Autowired
	PointsStrategyService pointsStrategyService;
	@Autowired
	GCMController gcmController;
	@Autowired
	FriendshipService friendshipService;

	@Autowired
	PipelineTaskProgressService pipelineTaskProgressService;

	@Autowired
	ActionPipelineResponseService actionPipelineResponseService;

	@Autowired
	GroupsTaskService groupsTaskService;

	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder(
			"sha-256");
	private static final Logger logger = LoggerFactory
			.getLogger(TaskController.class);

	@RequestMapping(value = "rest/taskflat", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody TaskFlat createTask(
			@RequestBody @Valid TaskFlatRequest taskFlatRequest,
			Principal principal) {
		User user = userService.getUser(principal.getName());
		if (user != null) {

			try {
				Task t = taskFlatRequest.getTask();
				TaskUser taskUser = new TaskUser();
				taskUser.setUsersToAssign(new HashSet<User>());

				Set<Long> friendIds = taskFlatRequest.getIdFriends();
				if (friendIds != null && friendIds.size() > 0) {
					List<Friendship> friendsReceiver = friendshipService
							.getFriendshipsForUserAndStatus(user.getId(),
									FriendshipStatus.ACCEPTED, true);
					List<Friendship> friendsSender = friendshipService
							.getFriendshipsForUserAndStatus(user.getId(),
									FriendshipStatus.ACCEPTED, false);

					List<Long> friendsReceiverIds = new ArrayList<Long>();
					List<Long> friendsSenderIds = new ArrayList<Long>();

					for (Friendship f : friendsReceiver)
						friendsReceiverIds.add(f.getReceiver().getId());
					for (Friendship f : friendsSender)
						friendsSenderIds.add(f.getSender().getId());
					Set<User> usersToAssign = new HashSet<User>();
					for (Long id : friendIds) {
						if (!(friendsReceiverIds.contains(id) || friendsSenderIds
								.contains(id)))
							throw new Exception();
						else {
							User u = userService.getUser(id);
							usersToAssign.add(u);
						}
					}
					taskUser.setUsersToAssign(usersToAssign);

				}
				taskUser.setOwner(user);
				taskUser.setTask(t);
				taskUser = taskUserService.save(taskUser);
				return new TaskFlat(taskUser.getTask());

			} catch (Exception e) {
				logger.error("Ids supplied are not friends", e);
				return new TaskFlat();
			}

		} else
			throw new ResourceNotFoundException(String.format(
					"User %s not found", principal.getName()));

	}

	@RequestMapping(value = "/rest/task", method = RequestMethod.GET)
	public @ResponseBody TaskList getTask(ModelAndView modelAndView,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("state") String state) {
		TaskList result = null;
		try {
			User user = userService.getUser(email);
			String pass = encoder.encodePassword(password, email);
			if (user != null) {
				if (pass.equals(user.getPassword())) {
					List<Task> tasks = taskService.getTasksByUser(user.getId(),
							TaskState.valueOf(state));
					result = new TaskList(tasks);
				}
			}
			return result;
		} catch (Exception e) {
			logger.error("Error while retrieving task in state {} for user {}",
					state, email);
			logger.error("Error while retrieving task", e);
			result = new TaskList();
			result.setList(new ArrayList<Task>());
			return result;
		}
	}

	@RequestMapping(value = "/rest/taskflat", method = RequestMethod.GET)
	public @ResponseBody TaskFlatList getTaskFlat(ModelAndView modelAndView,
			Principal principal, @RequestParam("state") String state,
			@RequestParam(required = false, value = "type") String type) {

		try {
			TaskFlatList result = null;
			User user = userService.getUser(principal.getName());
			if (user != null) {
				List<Task> tasks;
				List<TaskFlat> taskflat = new ArrayList<TaskFlat>();
				if (TaskState.AVAILABLE.toString().equals(state)) {
					// search available tasks: start time is before this instant
					// and
					// deadline is after this instant

					// this if is for retro compatibility
					if (type == null)
						tasks = taskService.getAvailableTasksByUser(
								user.getId(), TaskState.AVAILABLE,
								new DateTime());
					else if (type.equals("all")) {
						tasks = taskService.getAvailableTasksByUser(
								user.getId(), TaskState.AVAILABLE,
								new DateTime());
					} else if (type.equals("admin"))
						tasks = taskService.getAvailableAdminTasksByUser(
								user.getId(), TaskState.AVAILABLE,
								new DateTime());
					else if (type.equals("user"))
						tasks = taskService.getAvailableUserTasksByUser(
								user.getId(), TaskState.AVAILABLE,
								new DateTime());

					else
						throw new Exception();
					long userId = user.getId();

					for (Task currentTask : tasks) {
						TaskFlat currentFlat = currentTask.convertToTaskFlat();
						int points = 0;
						Set<Action> actions = currentTask.getActions();
						List<Reputation> fakeReputations = new ArrayList<Reputation>();
						for (Action currentAction : actions) {
							Reputation currentReputation = reputationService
									.getReputationByUserAndActionType(userId,
											currentAction.getType());
							currentReputation = reputationStrategy
									.updateReputation(currentReputation,
											TaskState.ACCEPTED, false); // fake
							// accept
							currentReputation = reputationStrategy
									.updateReputation(currentReputation,
											TaskState.COMPLETED_WITH_SUCCESS,
											false); // fake
							// success
							fakeReputations.add(currentReputation);
						}
						PointsStrategyForTask pointsStrategyForTask;
						PointsStrategy pointsStrategy;
						try {
							pointsStrategyForTask = pointsStrategyForTaskService
									.getPointsStrategyForTaskByTask(currentTask
											.getId());
							pointsStrategy = pointsStrategyService
									.getStrategyById(pointsStrategyForTask
											.getStrategyId());
						} catch (NoResultException e) {
							pointsStrategy = pointsStrategyService
									.getAllStrategies().get(0); // using default
																// strategy with
																// old tasks
						}

						if (pointsStrategy != null) {
							Points pointsWithSuccess = pointsStrategy
									.computePoints(
											user,
											currentTask,
											fakeReputations,
											PointsType.TASK_COMPLETED_WITH_SUCCESS,
											false);
							points = pointsWithSuccess.getValue();
							currentFlat.setPoints(points);
						}

						try {
							Points pointsIfTaskUserApproved = pointsService
									.getPointsByUserAndTaskAndType(userId,
											currentTask.getId(),
											PointsType.USER_TASK_APPROVED);
							if (pointsIfTaskUserApproved != null) {
								currentFlat.setPoints(currentFlat.getPoints()
										+ pointsIfTaskUserApproved.getValue());
							}
						} catch (NoResultException e) {

						}

						taskflat.add(currentFlat);
					}

				} else {
					// convertire ognuno in flat e reperire punti da db se
					// completati con successo, altrimenti i punti sono 0
					if (type == null) {
						tasks = taskService.getTasksByUser(user.getId(),
								TaskState.valueOf(state));
					} else if (type.equals("all")) {
						tasks = taskService.getTasksByUser(user.getId(),
								TaskState.valueOf(state));
					} else if (type.equals("admin")) {
						tasks = taskService.getAdminTasksByUser(user.getId(),
								TaskState.valueOf(state));
					} else if (type.equals("user")) {
						tasks = taskService.getUserTasksByUser(user.getId(),
								TaskState.valueOf(state));

					} else
						throw new Exception();

					for (Task currentTask : tasks) {
						TaskFlat currentFlat = currentTask.convertToTaskFlat();
						int points = 0;

						try {
							List<Points> pointsWithSuccessOrApproved = pointsService
									.getPointsByUserAndTask(user.getId(),
											currentTask.getId());
							if (pointsWithSuccessOrApproved != null) {
								for (Points current : pointsWithSuccessOrApproved)
									points += current.getValue();
							}
						} catch (NoResultException e) {

						}

						currentFlat.setPoints(points);
						taskflat.add(currentFlat);

					}
				}

				result = new TaskFlatList(taskflat);
			}
			return result;
		} catch (Exception e) {
			logger.error("Error while retrieving new tasks", e);
			return new TaskFlatList(new ArrayList<TaskFlat>());
		}
	}

	@RequestMapping(value = "rest/createdTaskflat/{state}", method = RequestMethod.GET)
	public @ResponseBody Map<String, List<TaskFlat>> getCreatedTaskFlatByState(
			ModelAndView modelAndView, Principal principal,
			@PathVariable("state") String state) {
		try {
			User user = userService.getUser(principal.getName());
			if (user != null) {
				List<Task> tasks = taskService.getTaskbyOwner(user.getId());
				Map<String, List<TaskFlat>> result = new HashMap<String, List<TaskFlat>>();
				int size;
				for (Task t : tasks) {
					size = taskReportService.getTaskReportsByTask(t.getId(),
							TaskState.valueOf(state)).size();
					if (size > 0) {
						if (result.containsKey("" + size))
							result.get("" + size).add(t.convertToTaskFlat());
						else {
							List<TaskFlat> temp = new ArrayList<TaskFlat>();
							temp.add(t.convertToTaskFlat());
							result.put(
									""
											+ taskReportService
													.getTaskReportsByTask(
															t.getId()).size(),
									temp);

						}
					}

				}
				return result;

			} else
				throw new ResourceNotFoundException(String.format(
						"User %s not found", principal.getName()));
		} catch (Exception e) {
			logger.error("Error while retrieving new tasks", e);
			return new HashMap<String, List<TaskFlat>>();
		}
	}

	// task created by user
	@RequestMapping(value = "rest/createdTaskflat", method = RequestMethod.GET)
	public @ResponseBody TaskFlatList getCreatedTaskFlat(
			ModelAndView modelAndView, Principal principal,
			@RequestParam("valutation") String valutation) {
		try {
			User user = userService.getUser(principal.getName());
			if (user != null) {
				List<Task> tasks = taskService.getTaskbyOwner(user.getId(),
						TaskValutation.valueOf(valutation));
				List<TaskFlat> taskFlat = new ArrayList<TaskFlat>();
				for (Task t : tasks)
					taskFlat.add(t.convertToTaskFlat());
				return new TaskFlatList(taskFlat);
			} else
				throw new ResourceNotFoundException(String.format(
						"User %s not found", principal.getName()));
		} catch (Exception e) {
			logger.error("Error while retrieving new tasks", e);
			return new TaskFlatList(new ArrayList<TaskFlat>());
		}

	}

	@RequestMapping(value = "/rest/acceptTask", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage acceptTask(ModelAndView modelAndView,
			Principal principal, @RequestParam("taskId") Long taskId) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				TaskReport taskReport = taskReportService.findByUserAndTask(
						user.getId(), taskId);
				TaskHistory taskHistory = new TaskHistory();
				taskHistory.setState(TaskState.RUNNING);
				taskHistory.setTaskReport(taskReport);
				taskHistory.setTimestamp(new DateTime());
				taskReport.setAcceptedDateTime(new DateTime());
				Long duration = taskReport.getTask().getDuration();
				if (duration != null) {
					DateTime expiryDateTime = taskReport.getAcceptedDateTime()
							.plusMinutes(duration.intValue());
					taskReport.setExpirationDateTime(expiryDateTime);
				}
				taskReport.addHistory(taskHistory);
				taskReport = taskReportService.save(taskReport);
				Task acceptedTask = taskReport.getTask();
				Set<Action> taskActions = acceptedTask.getActions();
				for (Action currentAction : taskActions) {
					ActionType currentType = currentAction.getType();
					Reputation currentReputation = reputationService
							.getReputationByUserAndActionType(user.getId(),
									currentType);
					currentReputation = reputationStrategy.updateReputation(
							currentReputation, TaskState.ACCEPTED, true);
					logger.info(
							"User {} has now reputation of {} for action {}",
							user.getOfficialEmail(),
							currentReputation.getValue(),
							currentAction.getType());
				}

				logger.info("User {} accepted task {}",
						user.getOfficialEmail(), taskId);
				response.setResultCode(200);
				return response;
			} catch (NoResultException e) {
				logger.error(
						"User {} ({}) tried to accept task {}, but I wasn't able to find the task or the taskReport.",
						user.getId(), user.getOfficialEmail(), taskId);
			}
		}
		response.setResultCode(500);
		return response;
	}

	@RequestMapping(value = "/rest/rejectTask", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage rejectTask(ModelAndView modelAndView,
			Principal principal, @RequestParam("taskId") Long taskId) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				logger.info("User {} refused task {}", user.getOfficialEmail(),
						taskId);
				TaskReport taskReport = taskReportService.findByUserAndTask(
						user.getId(), taskId);
				TaskHistory taskHistory = new TaskHistory();
				taskHistory.setState(TaskState.REJECTED);
				taskHistory.setTaskReport(taskReport);
				taskHistory.setTimestamp(new DateTime());
				taskReport.addHistory(taskHistory);
				taskReport = taskReportService.save(taskReport);
				Task acceptedTask = taskReport.getTask();
				Set<Action> actions = acceptedTask.getActions();
				for (Action currentAction : actions) {
					ActionType currentType = currentAction.getType();
					Reputation currentReputation = reputationService
							.getReputationByUserAndActionType(user.getId(),
									currentType);
					reputationStrategy.updateReputation(currentReputation,
							TaskState.REJECTED, true);
					logger.info(
							"User {} has now reputation of {} for action {}",
							user.getOfficialEmail(),
							currentReputation.getValue(),
							currentAction.getType());
				}
				response.setResultCode(200);
				return response;
			} catch (NoResultException e) {
				logger.error(
						"User {} ({}) tried to accept task {}, but I wasn't able to find the task or the taskReport.",
						user.getId(), user.getOfficialEmail(), taskId);
				logger.error("Error {}", e);
			}
		}
		response.setResultCode(500);
		return response;
	}

	@RequestMapping(value = "/rest/completeTask", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage completeTask(
			ModelAndView modelAndView, Principal principal,
			@RequestParam("taskId") Long taskId) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				TaskReport taskReport = taskReportService.findByUserAndTask(
						user.getId(), taskId);
				List<TaskHistory> pastHistory = taskReport.getHistory();
				if (pastHistory != null && pastHistory.size() > 0) {
					for (TaskHistory currentHistory : pastHistory) {
						if (currentHistory.getState() == TaskState.COMPLETED_WITH_SUCCESS) {
							logger.info(
									"User {} has already completed task {}",
									user.getOfficialEmail(), taskId);
							response.setResultCode(200);
							return response;
						}
					}
				}
				logger.info("User {} completed task {}",
						user.getOfficialEmail(), taskId);
				TaskHistory taskHistory = new TaskHistory();
				taskHistory.setState(TaskState.COMPLETED_WITH_SUCCESS);
				taskHistory.setTaskReport(taskReport);
				taskHistory.setTimestamp(new DateTime(System
						.currentTimeMillis()));
				taskReport.addHistory(taskHistory);
				taskReport = taskReportService.save(taskReport);
				Task acceptedTask = taskReport.getTask();
				Set<Action> taskActions = acceptedTask.getActions();
				for (Action currentAction : taskActions) {
					ActionType currentType = currentAction.getType();
					Reputation currentReputation = reputationService
							.getReputationByUserAndActionType(user.getId(),
									currentType);
					reputationStrategy.updateReputation(currentReputation,
							TaskState.COMPLETED_WITH_SUCCESS, true);
					logger.info(
							"User {} has now reputation of {} for action {}",
							user.getOfficialEmail(),
							currentReputation.getValue(),
							currentAction.getType());
				}

				sendNextStepOfPipelineTask(acceptedTask, user);

				PointsStrategyForTask pointsStrategyForTask;
				PointsStrategy pointsStrategy;

				try {
					pointsStrategyForTask = pointsStrategyForTaskService
							.getPointsStrategyForTaskByTask(acceptedTask
									.getId());
					pointsStrategy = pointsStrategyService
							.getStrategyById(pointsStrategyForTask
									.getStrategyId());

				} catch (NoResultException e) {
					pointsStrategy = pointsStrategyService.getAllStrategies()
							.get(0); // using default strategy with old tasks

				}

				Points earned = pointsStrategy.computePoints(user,
						acceptedTask, PointsType.TASK_COMPLETED_WITH_SUCCESS,
						true);

				logger.info(
						"User {} ({}) gained {} points completing task ({}). Points computed using strategy {} ({})",
						user.getId(), user.getOfficialEmail(),
						earned.getValue(), acceptedTask.getId(),
						pointsStrategy.getName(), pointsStrategy.getId());

				Map<ActionType, Integer> mapActionCountSuccess = new HashMap<ActionType, Integer>();

				List<TaskReport> userReports = taskReportService
						.getTaskReportsByUser(user.getId());
				for (TaskReport currentReport : userReports) {
					if (currentReport.getCurrentState() == TaskState.COMPLETED_WITH_SUCCESS) {
						Set<Action> reportActions = currentReport.getTask()
								.getActions();
						for (Action currentReportAction : reportActions) {
							if (mapActionCountSuccess
									.containsKey(currentReportAction.getType())) {
								mapActionCountSuccess
										.put(currentReportAction.getType(),
												mapActionCountSuccess
														.get(currentReportAction
																.getType()) + 1);
							} else {
								mapActionCountSuccess.put(
										currentReportAction.getType(), 1);
							}
						}
					}// report with success
				}

				Set<Badge> newBadges = new HashSet<Badge>();

				for (Action currentTaskAction : taskActions) {
					if (mapActionCountSuccess.containsKey(currentTaskAction
							.getType())) {
						try {
							List<BadgeActions> newBadgeActions = badgeService
									.getBadgeForActionTypeAndMaxQuantity(
											currentTaskAction.getType(),
											mapActionCountSuccess
													.get(currentTaskAction
															.getType()));

							if (newBadgeActions != null) {
								for (BadgeActions currentBadgeAction : newBadgeActions) {
									if (!user.getBadges().contains(
											currentBadgeAction)) {
										newBadges.add(currentBadgeAction);
										logger.info(
												"User {} ({}) has unlocked the badge {} ({}).",
												user.getId(),
												user.getOfficialEmail(),
												currentBadgeAction.getId(),
												currentBadgeAction.getTitle());
									}

								}
							}

						} catch (NoResultException e) {
							logger.info(
									"No new badges for actiontype {} and quantity {}",
									currentTaskAction.getType(),
									mapActionCountSuccess.get(currentTaskAction
											.getType()));
						}
					}
				}

				try {
					Badge taskBadge = badgeService.getBadgeForTask(taskId);
					newBadges.add(taskBadge);
					logger.info("User {} ({}) has unlocked the badge {} ({}).",
							user.getId(), user.getOfficialEmail(),
							taskBadge.getId(), taskBadge.getTitle());

				} catch (NoResultException e) {

				}

				if (!newBadges.isEmpty()) {
					user.getBadges().addAll(newBadges);
					List<String> maiList = new ArrayList<String>(1);
					maiList.add(user.getOfficialEmail());
					gcmController.notifyUsers(PANotification.Type.NEW_BADGE,
							maiList);
					userService.save(user);
				}
				response.setResultCode(200);
				return response;
			} catch (NoResultException e) {
				logger.error(
						"User {} claims to have completed task {} which is unknown",
						user.getOfficialEmail(), taskId);
				response.setResultCode(200);
				return response;
			}
		}
		logger.error(
				"Unable to handle requesto to complete task with id {} user id {} (principal {})",
				taskId, principal.getName());
		response.setResultCode(500);
		return response;
	}

	@RequestMapping(value = "/rest/completeTaskWithFailure", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage completeTaskWithUnsuccess(
			ModelAndView modelAndView, Principal principal,
			@RequestParam("taskId") Long taskId,
			@RequestParam("sensingProgress") Long sensingProgress,
			@RequestParam("photoProgress") Integer photoProgress,
			@RequestParam("questionnaireProgress") Integer questionnaireProgress) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				TaskReport taskReport = taskReportService.findByUserAndTask(
						user.getId(), taskId);
				logger.info("User {} failed task {}", user.getOfficialEmail(),
						taskId);
				TaskHistory taskHistory = new TaskHistory();
				taskHistory.setState(TaskState.COMPLETED_WITH_FAILURE);
				taskHistory.setTaskReport(taskReport);
				taskHistory.setTimestamp(new DateTime(System
						.currentTimeMillis()));
				taskReport.addHistory(taskHistory);
				taskReportService.save(taskReport);
				Task acceptedTask = taskReport.getTask();
				Set<Action> actions = acceptedTask.getActions();
				for (Action currentAction : actions) {
					ActionType currentType = currentAction.getType();
					Reputation currentReputation = reputationService
							.getReputationByUserAndActionType(user.getId(),
									currentType);
					reputationStrategy.updateReputation(currentReputation,
							TaskState.COMPLETED_WITH_FAILURE, true);
					logger.info(
							"User {} has now reputation of {} for action {}",
							user.getOfficialEmail(),
							currentReputation.getValue(),
							currentAction.getType());
				}
				response.setResultCode(200);
				return response;
			} catch (NoResultException e) {
				logger.error(
						"User {} claims to have failed task {} which is unknown",
						user.getOfficialEmail(), taskId);
				response.setResultCode(200);
				return response;
			}
		}
		response.setResultCode(500);
		return response;
	}

	@RequestMapping(value = "/rest/statistics", method = RequestMethod.GET)
	public @ResponseBody StatisticsMessage getStatistics(
			ModelAndView modelAndView, Principal principal) {
		User user = userService.getUser(principal.getName());
		StatisticsMessage message = new StatisticsMessage();
		List<TaskReport> trs = taskReportService.getTaskReportsByUser(user
				.getId());

		List<Task> createdTask = taskService.getTaskbyOwner(user.getId());

		// group created task by month
		Map<String, List<Task>> taskMap = new TreeMap<String, List<Task>>();
		for (Task t : createdTask) {
			int year = t.getStart().getYear();
			int month = t.getStart().getMonthOfYear();
			String yearmonth = String.format("%04d %02d", year, month);
			if (!taskMap.containsKey(yearmonth)) {
				taskMap.put(yearmonth, new LinkedList<Task>());
			}
			taskMap.get(yearmonth).add(t);
		}

		// group taskreports by month
		Map<String, List<TaskReport>> taskReportMap = new TreeMap<String, List<TaskReport>>();
		for (TaskReport tr : trs) {
			int year = tr.getTask().getStart().getYear();
			int month = tr.getTask().getStart().getMonthOfYear();
			String yearmonth = String.format("%04d %02d", year, month);
			if (!taskReportMap.containsKey(yearmonth)) {
				taskReportMap.put(yearmonth, new LinkedList<TaskReport>());
			}
			taskReportMap.get(yearmonth).add(tr);
		}

		// order yearmonths according to their natural ordering
		Set<String> mergedMonths = new HashSet<String>();
		mergedMonths.addAll(taskReportMap.keySet());
		mergedMonths.addAll(taskMap.keySet());

		List<String> orderedYearMonth = new ArrayList<String>(mergedMonths);
		// orderedYearMonth.addAll(taskReportMap.keySet());
		Collections.sort(orderedYearMonth);
		Collections.reverse(orderedYearMonth);

		for (String yearmonth : orderedYearMonth) {
			trs = taskReportMap.get(yearmonth);
			createdTask = taskMap.get(yearmonth);
			Integer completedTasks = 0;
			Integer failedTasks = 0;
			Integer rejectedTasks = 0;
			Integer points = 0;
			Integer createdTaskNum = 0;
			Integer year = Integer.parseInt(yearmonth.split(" ")[0]);
			Integer month = Integer.parseInt(yearmonth.split(" ")[1]);

			DateTime dt = new DateTime().withYear(year).withMonthOfYear(month);
			DateTime from = dt.withDayOfMonth(1).withTimeAtStartOfDay();
			DateTime to = from.plusMonths(1).withTimeAtStartOfDay();

			if (trs != null) {

				for (TaskReport tr : trs) {
					switch (tr.getCurrentState()) {
					case COMPLETED_WITH_SUCCESS:
						completedTasks++;
						// points += tr.getTask().getPoints();
						break;
					case COMPLETED_WITH_FAILURE:
						failedTasks++;
						break;
					case REJECTED:
						rejectedTasks++;
						break;
					default:
					}
				}
			}

			List<Points> allPoints = pointsService.getPointsByUserAndDates(
					user.getId(), from, to);
			for (Points currentPoints : allPoints)
				points += currentPoints.getValue();

			if (createdTask != null)
				createdTaskNum = createdTask.size();

			Properties monthProperties = new Properties();
			monthProperties.setProperty("yearmonth", yearmonth);
			monthProperties.setProperty("createdTasks",
					createdTaskNum.toString());
			monthProperties.setProperty("completedTasks",
					completedTasks.toString());
			monthProperties.setProperty("failedTasks", failedTasks.toString());
			monthProperties.setProperty("rejectedTasks",
					rejectedTasks.toString());
			monthProperties.setProperty("points", points.toString());

			try {
				MonthlyTargetScore monthlyTargetScore = monthlyTargetScoreService
						.findByYearMonth(year, month);
				monthProperties.setProperty("threshold", monthlyTargetScore
						.getTargetScore().toString());
			} catch (Exception e) {

			}
			message.getList().add(monthProperties);
		}

		return message;
	}

	private void sendNextStepOfPipelineTask(Task task, User user) {
		List<PipelineTaskProgress> pipelineTaskProgress = pipelineTaskProgressService
				.findByTaskStepId(task.getId());

		for (PipelineTaskProgress p : pipelineTaskProgress) {
			Task pipelineTask = taskService.findById(p.getTask_pipeline_id());
			ActionPipelineTask action = (ActionPipelineTask) pipelineTask
					.getActions().toArray()[0];

			List<String> steps = getSteps(p.getTask_pipeline_id());

			int currentStep = pipelineTaskProgressService
					.getMaxStepByPipelineId(p.getTask_pipeline_id(),
							p.getGroup_id());

			PipelineTaskStrategy strategy = new BaseStrategy();

			List<GroupsTask> groups = groupsTaskService.getAllGroupsTasks();
			ArrayList<Long> usersOfGroup = new ArrayList<Long>();

			for (GroupsTask gt : groups) {
				if (gt.getGroupId() == p.getGroup_id())
					usersOfGroup.add(gt.getComponentOfGroupId());
			}

			if (currentStep < steps.size() && currentStep == p.getTask_step()) {
				ArrayList<Long> userToNotSend = new ArrayList<Long>();
				userToNotSend.add(user.getId());

				List<Long> toSend = strategy.getUserToSendTask(usersOfGroup,
						userToNotSend);

				String userList = "";

				for (long l : toSend)
					userList += userService.getUser(l).getOfficialEmail()
							+ "\n";

				Collection<String> users = getAsUsers(userList, null, null);

				long duration = task.getDeadline().getMillis()
						- task.getStart().getMillis();

				Task tmpTask = new Task();
				tmpTask.setCanBeRefused(task.getCanBeRefused());
				tmpTask.setStart(task.getDeadline());
				DateTime end = new DateTime(tmpTask.getStart().getMillis()
						+ duration);
				tmpTask.setDeadline(end);
				tmpTask.setDuration(task.getDuration());
				tmpTask.setSensingDuration(task.getDuration());
				tmpTask.setActions(new LinkedHashSet<Action>());

				task.setDeadline(DateTime.now());
				taskService.save(task);

				StringTokenizer tok = new StringTokenizer(task.getName(), "-");
				String desc = tok.nextToken().trim() + " - "
						+ PipelineTaskUtils.STEP + (currentStep + 1) + " | "
						+ PipelineTaskUtils.GROUP + p.getGroup_id();

				tmpTask.setName(desc);
				String description = PipelineTaskUtils.INTRO + "\n"
						+ PipelineTaskUtils.STEP + (currentStep + 1) + "\n";
				tmpTask.setDescription(description);
				purgeDuration(tmpTask);

				ActionQuestionaire actionq = new ActionQuestionaire();
				Question question = new Question();
				int questionOrder = actionq.getNextQuestionOrder();
				question.setQuestionOrder(questionOrder);

				StringTokenizer token = new StringTokenizer(
						action.getQuestion(), "\n");

				String quest = token.nextToken()
						+ "\nRisposte ai precedenti step:";

				for (int i = 0; i < currentStep; i++)
					quest += "\nStep " + (i + 1) + "--> " + steps.get(i) + " ";

				question.setQuestion(quest);
				actionq.getQuestions().add(question);
				question.setIsClosedAnswers(false);
				question.setIsMultipleAnswers(false);
				actionq.setTitle(action.getTitle());
				actionq.setDescription(action.getTitle());

				tmpTask.getActions().add(actionq);

				// assign task to users and save it to DB
				tmpTask = taskService.assignTaskToUsers(tmpTask, users);

				GroupStrategy strategyHolder = new GroupStrategy();

				pointsStrategyForTaskService.create(tmpTask,
						strategyHolder.getId());

				// notify users about the new task via GCM
				gcmController.notifyUsers(PANotification.Type.NEW_TASK, users);

				PipelineTaskProgress pipelineTaskProgress2 = new PipelineTaskProgress();
				pipelineTaskProgress2.setGroup_id(p.getGroup_id());
				pipelineTaskProgress2.setTask_pipeline_id(p
						.getTask_pipeline_id());
				pipelineTaskProgress2.setTask_step(currentStep + 1);
				pipelineTaskProgress2.setTask_step_id(tmpTask.getId());

				pipelineTaskProgressService.save(pipelineTaskProgress2);

				new EventFactorySupport().success(tmpTask);
			}
		}
	}

	private List<String> getSteps(long taskId) {
		ArrayList<String> result = new ArrayList<String>();

		List<ActionPipelineResponse> list = actionPipelineResponseService
				.getResponseByTaskId(taskId);
		for (ActionPipelineResponse a : list)
			result.add(a.getResponde());

		return result;
	}

	public void purgeDuration(Task task) {
		// remove sensing duration if there are no sensing actions
		boolean hasSensing = false;
		for (Action a : task.getActions()) {
			if (a instanceof ActionSensing) {
				hasSensing = true;
				break;
			}
		}
		if (!hasSensing) {
			task.setSensingDuration(null);
		}
	}

	public Collection<String> getAsUsers(String userList,
			Collection<String> unknownStrings, Collection<String> inactiveUsers) {
		Set<String> users = new LinkedHashSet<String>();
		if (userList == null || userList.length() == 0) {
			return users;
		}
		String[] userStrings = userList.split("[^a-zA-Z0-9@.]");
		Arrays.sort(userStrings);
		for (String userString : userStrings) {
			// discard blank lines
			if (StringUtils.isBlank(userString)) {
				continue;
			}
			User u = null;
			// look for user by id and official email
			u = userService.getUser(userString);

			if (u == null) {
				// the string does not identify a user
				if (unknownStrings != null) {
					unknownStrings.add(userString);
				}
				continue;
			} else {
				if (u.getIsActive()) {
					users.add(userString);
				} else {
					// the user is not active
					if (inactiveUsers != null) {
						inactiveUsers.add(userString);
					}
				}
			}
		}
		return users;
	}
}
