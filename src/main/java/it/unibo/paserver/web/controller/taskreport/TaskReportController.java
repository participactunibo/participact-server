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
package it.unibo.paserver.web.controller.taskreport;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataActivityRecognitionCompare;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.DataQuestionaireClosedAnswer;
import it.unibo.paserver.domain.DataQuestionaireOpenAnswer;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.controller.taskreport.DisplayQuestion.AnswerResult;
import it.unibo.paserver.web.functions.PAServerUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.NoResultException;

import org.json.JSONException;
import org.json.JSONWriter;
import org.mapfish.geo.MfGeoJSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.PrecisionModel;

@Controller
public class TaskReportController {

	@Autowired
	TaskService taskService;

	@Autowired
	TaskUserService taskUserService;
	@Autowired
	UserService userService;

	@Autowired
	ActionService actionService;

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	TaskResultService taskResultService;

	@Autowired
	DataService dataService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskReportController.class);

	@RequestMapping(value = "/protected/taskreport/show/task/{taskId}/user/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView showTaskReport(@PathVariable Long userId,
			@PathVariable Long taskId, ModelAndView modelAndView) {
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(userId,
					taskId);
			modelAndView.addObject("taskReport", taskReport);
			List<Action> orderedActions = new ArrayList<Action>();
			orderedActions.addAll(taskReport.getTask().getActions());
			Collections.sort(orderedActions, new Comparator<Action>() {

				@Override
				public int compare(Action o1, Action o2) {
					String o1name = PAServerUtils.actionToString(o1);
					String o2name = PAServerUtils.actionToString(o2);
					return o1name.compareTo(o2name);
				}
			});
			modelAndView.addObject("orderedActions", orderedActions);
			modelAndView.setViewName("/protected/taskreport/show");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, userId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/user", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showUserTaskReport(Principal principal,
			@PathVariable Long taskId, ModelAndView modelAndView) {
		// return the TaskReport of the specified task assigned to user
		User user = userService.getUser(principal.getName());

		try {
			if (user == null)
				throw new ResourceNotFoundException();
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), taskId);
			if (taskReport == null)
				throw new ResourceNotFoundException();
			List<Action> actions = new ArrayList<Action>();
			actions.addAll(taskReport.getTask().getActions());
			Collections.sort(actions, new Comparator<Action>() {
				@Override
				public int compare(Action o1, Action o2) {
					String o1name = PAServerUtils.actionToString(o1);
					String o2name = PAServerUtils.actionToString(o2);
					return o1name.compareTo(o2name);
				}

			});
			modelAndView.addObject("isUserTaskReport", true);
			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("orderedActions", actions);
			modelAndView.setViewName("protected/webuser/taskreport/show");

			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, user.getId());
			throw new ResourceNotFoundException(e);
		}

	}

	@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/user/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showTaskReport(Principal principal,
			@PathVariable Long taskId, @PathVariable Long userId,
			ModelAndView modelAndView) {
		User user = userService.getUser(principal.getName());
		try {

			if (user == null)
				throw new ResourceNotFoundException();
			
			TaskUser taskUser = taskUserService.getTaskUserByTaskId(taskId);

			TaskReport taskReport = taskReportService
					.findByUserAndTaskAndOwner(userId, taskId, user.getId());

			List<Action> orderedActions = new ArrayList<Action>();
			orderedActions.addAll(taskReport.getTask().getActions());
			Collections.sort(orderedActions, new Comparator<Action>() {

				@Override
				public int compare(Action o1, Action o2) {
					String o1name = PAServerUtils.actionToString(o1);
					String o2name = PAServerUtils.actionToString(o2);
					return o1name.compareTo(o2name);
				}
			});
			modelAndView.addObject("isUserTaskReport", false);
			modelAndView.addObject("taskUserId",taskUser.getId());

			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("orderedActions", orderedActions);
			modelAndView.setViewName("/protected/webuser/taskreport/show");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, userId);
			throw new ResourceNotFoundException(e);
		}

	}

	@RequestMapping(value = "/protected/taskreport/show/task/{taskId}/action/{actionId}/allphotos", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView showTaskReportPhoto(@PathVariable Long taskId,
			@PathVariable Long actionId, ModelAndView modelAndView) {

		Task task = taskService.findById(taskId);
		List<DataPhoto> dataPhotos = dataService.getDataPhotoByTaskAction(
				taskId, actionId);
		modelAndView.addObject("dataToShow", dataPhotos);
		modelAndView.addObject("task", task);
		modelAndView.setViewName("/protected/data/showallphotos");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/webuser/taskreport/show/assignedtask/{taskId}/action/{actionId}/allphotos", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView showTaskReportPhoto(Principal principal,
			@PathVariable Long taskId, @PathVariable Long actionId,
			ModelAndView modelAndView) {

		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), taskId);
			TaskResult taskResult = taskResultService
					.getTaskResultByTaskReport(taskReport.getId(), true);
			List dataToShow = new ArrayList();
			Action action = actionService.findById(actionId);
			if (action == null) {
				throw new ResourceNotFoundException();
			}
			if (action instanceof ActionPhoto) {
				List<DataPhoto> candidates = taskResult
						.getData(DataPhoto.class);
				dataToShow = new ArrayList<DataPhoto>();
				for (DataPhoto dp : candidates) {
					if (dp.getActionId().equals(actionId)) {
						dataToShow.add(dp);
					}
				}
			}
			
			

		//	modelAndView.addObject("isUserTaskData", true);
			modelAndView.addObject("dataToShow", dataToShow);
			modelAndView.addObject("task", taskReport.getTask());
			modelAndView.setViewName("/protected/data/showallphotos");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find task for task {}, user {}", taskId,
					user.getId());
			throw new ResourceNotFoundException(e);

		}

	}

	/*@RequestMapping(value = "/protected/webuser/taskreport/show/taskuser/{taskId}/action/{actionId}/allphotos", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView showTaskUserReportPhoto(Principal principal,
			@PathVariable Long taskId, @PathVariable Long actionId,
			ModelAndView modelAndView) {

		User user = userService.getUser(principal.getName());

		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskUser task = taskUserService.getTaskUserByTaskId(taskId);
			if(!user.equals(task.getOwner()))
				throw new ResourceNotFoundException();
			List<DataPhoto> dataPhotos = dataService.getDataPhotoByTaskAction(
					task.getId(), actionId);
			
			

			modelAndView.addObject("isUserTaskData", false);
			modelAndView.addObject("dataToShow", dataPhotos);
			modelAndView.addObject("taskUser", task);
			modelAndView.setViewName("/protected/data/showallphotos");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find task for task {}, user {}", taskId,
					user.getId());
			throw new ResourceNotFoundException(e);

		}

	}*/

/*	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/user/{userId}/action/{actionId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView showTaskReportData(Principal principal,
			@PathVariable Long userId, @PathVariable Long taskId,
			@PathVariable Long actionId, ModelAndView modelAndView) {

		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskUser taskUser = taskUserService.getTaskUserByTaskId(taskId);
			TaskReport taskReport = taskReportService
					.findByUserAndTaskAndOwner(userId, taskUser.getTask().getId(), user.getId());

			TaskResult taskResult = taskResultService
					.getTaskResultByTaskReport(taskReport.getId(), true);
			List dataToShow = new ArrayList();
			Action action = actionService.findById(actionId);
			if (action == null) {
				throw new ResourceNotFoundException();
			}
			String dataName = null;
			if (action instanceof ActionSensing) {
				// process sensing actions
				dataName = ((ActionSensing) action).getDataClass()
						.getSimpleName();
				Class<? extends Data> dataType = ((ActionSensing) action)
						.getDataClass();
				dataToShow = taskResult.getData(dataType);
				if (dataType.equals(DataLocation.class)) {
					modelAndView.addObject("geojsonurl", String.format(
							"protected/webuser/geo/task/%d/user/%d", taskId,
							userId));
				}
			} else if (action instanceof ActionActivityDetection) {
				dataName = DataActivityRecognitionCompare.class.getSimpleName();
				dataToShow = taskResult
						.getData(DataActivityRecognitionCompare.class);
			} else if (action instanceof ActionPhoto) {
				dataName = DataPhoto.class.getSimpleName();
				List<DataPhoto> candidates = taskResult
						.getData(DataPhoto.class);
				dataToShow = new ArrayList<DataPhoto>();
				for (DataPhoto dp : candidates) {
					if (dp.getActionId().equals(actionId)) {
						dataToShow.add(dp);
					}
				}
			} else if (action instanceof ActionQuestionaire) {
				// process questionnaires
				dataName = "questionnaire";
				List<DataQuestionaireOpenAnswer> oa_candidate = taskResult
						.getData(DataQuestionaireOpenAnswer.class);
				List<DataQuestionaireClosedAnswer> ca_candidate = taskResult
						.getData(DataQuestionaireClosedAnswer.class);
				for (Question q : ((ActionQuestionaire) action).getQuestions()) {
					if (q.getIsClosedAnswers()) {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(true);
						dq.setQuestion(q.getQuestion());
						for (ClosedAnswer caitem : q.getClosed_answers()) {
							DataQuestionaireClosedAnswer answer = searchAnswer(
									q, caitem, ca_candidate);
							AnswerResult ar = new AnswerResult();
							ar.setAnswer(caitem.getAnswerDescription());
							if (answer != null) {
								ar.setResult(answer.getClosedAnswerValue());
							} else {
								ar.setResult(null);
							}
							dq.getAnswers().add(ar);
						}
						dataToShow.add(dq);
					} else {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(false);
						dq.setQuestion(q.getQuestion());
						DataQuestionaireOpenAnswer oa = searchAnswer(q,
								oa_candidate);
						if (oa != null) {
							dq.setOpenanswer(oa.getOpenAnswerValue());
						} else {
							dq.setOpenanswer("NULL");
						}
						dataToShow.add(dq);
					}
				}
				modelAndView.addObject("questionnairedescription", taskReport
						.getTask().getDescription());
			} else {
				logger.error("Unknown action data type {}", action.getClass()
						.getSimpleName());
			}
			modelAndView.addObject("dataName", dataName);
			modelAndView.addObject("taskUserId",taskUser.getId());
			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("dataToShow", dataToShow);
			modelAndView.addObject("isUserTaskData", false);
			modelAndView.setViewName("/protected/data/show");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, userId);
			throw new ResourceNotFoundException(e);
		}

	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/action/{actionId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView showTaskReportData(Principal principal,
			@PathVariable Long taskId, @PathVariable Long actionId,
			ModelAndView modelAndView) {

		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), taskId);
			TaskResult taskResult = taskResultService
					.getTaskResultByTaskReport(taskReport.getId(), true);
			List dataToShow = new ArrayList();
			Action action = actionService.findById(actionId);
			if (action == null) {
				throw new ResourceNotFoundException();
			}
			String dataName = null;
			if (action instanceof ActionSensing) {
				// process sensing actions
				dataName = ((ActionSensing) action).getDataClass()
						.getSimpleName();
				Class<? extends Data> dataType = ((ActionSensing) action)
						.getDataClass();
				dataToShow = taskResult.getData(dataType);
				if (dataType.equals(DataLocation.class)) {
					modelAndView.addObject("geojsonurl", String.format(
							"protected/webuser/geo/task/%d/user", taskId));
				}
			} else if (action instanceof ActionActivityDetection) {
				dataName = DataActivityRecognitionCompare.class.getSimpleName();
				dataToShow = taskResult
						.getData(DataActivityRecognitionCompare.class);
			} else if (action instanceof ActionPhoto) {
				dataName = DataPhoto.class.getSimpleName();
				List<DataPhoto> candidates = taskResult
						.getData(DataPhoto.class);
				dataToShow = new ArrayList<DataPhoto>();
				for (DataPhoto dp : candidates) {
					if (dp.getActionId().equals(actionId)) {
						dataToShow.add(dp);
					}
				}
			} else if (action instanceof ActionQuestionaire) {
				// process questionnaires
				dataName = "questionnaire";
				List<DataQuestionaireOpenAnswer> oa_candidate = taskResult
						.getData(DataQuestionaireOpenAnswer.class);
				List<DataQuestionaireClosedAnswer> ca_candidate = taskResult
						.getData(DataQuestionaireClosedAnswer.class);
				for (Question q : ((ActionQuestionaire) action).getQuestions()) {
					if (q.getIsClosedAnswers()) {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(true);
						dq.setQuestion(q.getQuestion());
						for (ClosedAnswer caitem : q.getClosed_answers()) {
							DataQuestionaireClosedAnswer answer = searchAnswer(
									q, caitem, ca_candidate);
							AnswerResult ar = new AnswerResult();
							ar.setAnswer(caitem.getAnswerDescription());
							if (answer != null) {
								ar.setResult(answer.getClosedAnswerValue());
							} else {
								ar.setResult(null);
							}
							dq.getAnswers().add(ar);
						}
						dataToShow.add(dq);
					} else {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(false);
						dq.setQuestion(q.getQuestion());
						DataQuestionaireOpenAnswer oa = searchAnswer(q,
								oa_candidate);
						if (oa != null) {
							dq.setOpenanswer(oa.getOpenAnswerValue());
						} else {
							dq.setOpenanswer("NULL");
						}
						dataToShow.add(dq);
					}
				}
				modelAndView.addObject("questionnairedescription", taskReport
						.getTask().getDescription());
			} else {
				logger.error("Unknown action data type {}", action.getClass()
						.getSimpleName());
			}
			//modelAndView.addObject("isUserTaskData", true);
			modelAndView.addObject("dataName", dataName);
			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("dataToShow", dataToShow);
			modelAndView.setViewName("/protected/data/show");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, user.getId());
			throw new ResourceNotFoundException(e);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/protected/taskreport/show/task/{taskId}/user/{userId}/action/{actionId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView showTaskReportData(@PathVariable Long userId,
			@PathVariable Long taskId, @PathVariable Long actionId,
			ModelAndView modelAndView) {
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(userId,
					taskId);
			TaskResult taskResult = taskResultService
					.getTaskResultByTaskReport(taskReport.getId(), true);
			List dataToShow = new ArrayList();
			Action action = actionService.findById(actionId);
			if (action == null) {
				throw new ResourceNotFoundException();
			}
			String dataName = null;
			if (action instanceof ActionSensing) {
				// process sensing actions
				dataName = ((ActionSensing) action).getDataClass()
						.getSimpleName();
				Class<? extends Data> dataType = ((ActionSensing) action)
						.getDataClass();
				dataToShow = taskResult.getData(dataType);
				if (dataType.equals(DataLocation.class)) {
					modelAndView.addObject("geojsonurl", String.format(
							"protected/geo/task/%d/user/%d", taskId, userId));
				}
			} else if (action instanceof ActionActivityDetection) {
				dataName = DataActivityRecognitionCompare.class.getSimpleName();
				dataToShow = taskResult
						.getData(DataActivityRecognitionCompare.class);
			} else if (action instanceof ActionPhoto) {
				dataName = DataPhoto.class.getSimpleName();
				List<DataPhoto> candidates = taskResult
						.getData(DataPhoto.class);
				dataToShow = new ArrayList<DataPhoto>();
				for (DataPhoto dp : candidates) {
					if (dp.getActionId().equals(actionId)) {
						dataToShow.add(dp);
					}
				}
			} else if (action instanceof ActionQuestionaire) {
				// process questionnaires
				dataName = "questionnaire";
				List<DataQuestionaireOpenAnswer> oa_candidate = taskResult
						.getData(DataQuestionaireOpenAnswer.class);
				List<DataQuestionaireClosedAnswer> ca_candidate = taskResult
						.getData(DataQuestionaireClosedAnswer.class);
				for (Question q : ((ActionQuestionaire) action).getQuestions()) {
					if (q.getIsClosedAnswers()) {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(true);
						dq.setQuestion(q.getQuestion());
						for (ClosedAnswer caitem : q.getClosed_answers()) {
							DataQuestionaireClosedAnswer answer = searchAnswer(
									q, caitem, ca_candidate);
							AnswerResult ar = new AnswerResult();
							ar.setAnswer(caitem.getAnswerDescription());
							if (answer != null) {
								ar.setResult(answer.getClosedAnswerValue());
							} else {
								ar.setResult(null);
							}
							dq.getAnswers().add(ar);
						}
						dataToShow.add(dq);
					} else {
						DisplayQuestion dq = new DisplayQuestion();
						dq.setIsClosed(false);
						dq.setQuestion(q.getQuestion());
						DataQuestionaireOpenAnswer oa = searchAnswer(q,
								oa_candidate);
						if (oa != null) {
							dq.setOpenanswer(oa.getOpenAnswerValue());
						} else {
							dq.setOpenanswer("NULL");
						}
						dataToShow.add(dq);
					}
				}
				modelAndView.addObject("questionnairedescription", taskReport
						.getTask().getDescription());
			} else {
				logger.error("Unknown action data type {}", action.getClass()
						.getSimpleName());
			}
			//modelAndView.addObject("isUserTaskData", false);
			modelAndView.addObject("dataName", dataName);
			modelAndView.addObject("taskReport", taskReport);
			modelAndView.addObject("dataToShow", dataToShow);
			modelAndView.setViewName("/protected/data/show");
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport for task {}, user {}",
					taskId, userId);
			throw new ResourceNotFoundException(e);
		}
	}

	private DataQuestionaireOpenAnswer searchAnswer(Question q,
			Iterable<DataQuestionaireOpenAnswer> oa) {
		for (DataQuestionaireOpenAnswer oaitem : oa) {
			if (oaitem.getQuestion().getId().equals(q.getId())) {
				return oaitem;
			}
		}
		return null;
	}

	private DataQuestionaireClosedAnswer searchAnswer(Question q,
			ClosedAnswer ca, Iterable<DataQuestionaireClosedAnswer> ica) {
		for (DataQuestionaireClosedAnswer oaitem : ica) {
			if (oaitem.getQuestion().getId().equals(q.getId())
					&& oaitem.getClosedAnswer().getId().equals(ca.getId())) {
				return oaitem;
			}
		}
		return null;
	}

	@RequestMapping(value = "/protected/taskreport/show/{taskReportId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView showTaskReport(@PathVariable Long taskReportId,
			ModelAndView modelAndView) {
		try {
			TaskReport taskReport = taskReportService.findById(taskReportId);
			modelAndView
					.setViewName(String
							.format("redirect:/protected/taskreport/show/task/%d/user/%d",
									taskReport.getTask().getId(), taskReport
											.getUser().getId()));
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskReport {}", taskReportId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/taskreport/updateresult/{taskResultId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView updateTaskResultData(@PathVariable Long taskResultId,
			ModelAndView modelAndView) {
		try {
			taskResultService.updateTaskResult(taskResultId);
			TaskReport taskReport = taskResultService.findById(taskResultId)
					.getTaskReport();
			modelAndView
					.setViewName(String
							.format("redirect:/protected/taskreport/show/task/%d/user/%d",
									taskReport.getTask().getId(), taskReport
											.getUser().getId()));
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskResult {}", taskResultId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/webuser/taskreport/task/{taskId}/updateresult/{taskResultId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView updateTaskResultData(Principal principal,
			@PathVariable Long taskId, @PathVariable Long taskResultId,
			ModelAndView modelAndView) {

		User user = userService.getUser(principal.getName());
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), taskId);

			taskResultService.updateTaskResult(taskResultId);

			modelAndView.setViewName(String.format(
					"redirect:/protected/webuser/taskreport/show/task/%d/user",
					taskReport.getTask().getId()));
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskResult {}", taskResultId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/webuser/taskreport/task/{taskId}/user/{userId}/updateresult/{taskResultId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ModelAndView updateTaskResultData(Principal principal,
			@PathVariable Long userId, @PathVariable Long taskId,
			@PathVariable Long taskResultId, ModelAndView modelAndView) {
		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskReport taskReport = taskReportService
					.findByUserAndTaskAndOwner(userId, taskId, user.getId());

			taskResultService.updateTaskResult(taskResultId);

			modelAndView
					.setViewName(String
							.format("redirect:/protected/webuser/taskreport/show/task/%d/user/%d",
									taskReport.getTask().getId(), taskReport
	
									.getUser().getId()));
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find taskResult {}", taskResultId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/webuser/geo/task/{taskId}/user", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public @ResponseBody ResponseEntity<String> showGeoData(
			Principal principal, @PathVariable Long taskId) {

		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), taskId);
			return getGeoData(taskReport);
		} catch (NoResultException e) {
			logger.error("Could not find geo data for user {}, task {}",
					user.getId(), taskId);
			throw new ResourceNotFoundException(e);

		}

	}

	/*@RequestMapping(value = "/protected/webuser/geo/task/{taskId}/user/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public @ResponseBody ResponseEntity<String> showGeoData(
			Principal principal, @PathVariable Long userId,
			@PathVariable Long taskId) {

		User user = userService.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskReport taskReport = taskReportService
					.findByUserAndTaskAndOwner(userId, taskId, user.getId());
			return getGeoData(taskReport);
		} catch (NoResultException e) {
			logger.error("Could not find geo data for user {}, task {}",
					userId, taskId);
			throw new ResourceNotFoundException(e);

		}

	}*/

	private ResponseEntity<String> getGeoData(TaskReport taskReport) {

		TaskResult taskResult = taskResultService.getTaskResultByTaskReport(
				taskReport.getId(), true);
		List<DataLocation> data = taskResult.getData(DataLocation.class);
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (DataLocation point : data) {
			coordinates.add(new Coordinate(point.getLongitude(), point
					.getLatitude()));
		}

		// encode points as a path
		OutputStreamWriter osw = null;
		ByteArrayOutputStream baos = null;
		try {
			GeometryFactory gf = new GeometryFactory(new PrecisionModel(
					PrecisionModel.FLOATING), 4326);
			LineString path = gf.createLineString(coordinates
					.toArray(new Coordinate[coordinates.size()]));
			baos = new ByteArrayOutputStream();
			osw = new OutputStreamWriter(baos);
			JSONWriter jsonWriter = new JSONWriter(osw);
			MfGeoJSONWriter mfGeoJSONWriter = new MfGeoJSONWriter(jsonWriter);
			try {
				mfGeoJSONWriter.encodeGeometry(path);
			} catch (JSONException e) {
				logger.error("Error while encoding geometry", e);
				throw new ResourceNotFoundException();
			}
		} finally {
			try {
				osw.close();
			} catch (IOException e) {
				logger.error("Error while closing stream", e);
				throw new ResourceNotFoundException();
			}
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		BufferedReader br = new BufferedReader(new InputStreamReader(bais));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
		} catch (IOException e) {
			logger.error("Error while reading json string", e);
			throw new ResourceNotFoundException();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				logger.error("Error while closing buffered reader", e);
				throw new ResourceNotFoundException();
			}
		}
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String result = sb.toString();
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/protected/geo/task/{taskId}/user/{userId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public @ResponseBody ResponseEntity<String> showGeoData(
			@PathVariable Long userId, @PathVariable Long taskId) {
		try {
			TaskReport taskReport = taskReportService.findByUserAndTask(userId,
					taskId);
			return getGeoData(taskReport);
		} catch (NoResultException e) {
			logger.error("Could not find geo data for user {}, task {}",
					userId, taskId);
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/photo/{imageId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public @ResponseBody ResponseEntity<byte[]> getImage(
			@PathVariable Long imageId) {
		try {
			BinaryDocument file = dataService.getPhotoContent(imageId);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(new MediaType(file.getContentType(), file
					.getContentSubtype()));
			return new ResponseEntity<byte[]>(file.getContent(), headers,
					HttpStatus.OK);
		} catch (NoResultException e) {
			logger.warn("Unable to find photo with id {}", imageId);
			throw new ResourceNotFoundException();
		}
	}

	/*@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/user/{userId}/photo/{imageId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public @ResponseBody ResponseEntity<byte[]> getImage(Principal principal,
			@PathVariable Long taskId, @PathVariable Long userId,
			@PathVariable Long imageId) {
		try {
			User user = userService.getUser(principal.getName());
			if (user == null)
				throw new ResourceNotFoundException();

			TaskReport taskReport = taskReportService
					.findByUserAndTaskAndOwner(userId, taskId, user.getId());
			BinaryDocument file = dataService.getPhotoContent(imageId);
			if (taskReport.getUser().equals(file.getOwner())) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType(file.getContentType(),
						file.getContentSubtype()));
				return new ResponseEntity<byte[]>(file.getContent(), headers,
						HttpStatus.OK);
			} else
				return null;
		} catch (NoResultException e) {
			logger.warn("Unable to find photo with id {}", imageId);
			throw new ResourceNotFoundException();
		}
	}*/

	@RequestMapping(value = "/protected/webuser/taskreport/show/task/{taskId}/photo/{imageId}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public @ResponseBody ResponseEntity<byte[]> getImage(Principal principal,
			@PathVariable Long taskId, @PathVariable Long imageId) {
		try {
			User user = userService.getUser(principal.getName());
			if (user == null)
				throw new ResourceNotFoundException();

			BinaryDocument file = dataService.getPhotoContent(imageId);
			if (user.equals(file.getOwner())) {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(new MediaType(file.getContentType(),
						file.getContentSubtype()));
				return new ResponseEntity<byte[]>(file.getContent(), headers,
						HttpStatus.OK);
			} else
				return null;
		} catch (NoResultException e) {
			logger.warn("Unable to find photo with id {}", imageId);
			throw new ResourceNotFoundException();
		}

	}

}
