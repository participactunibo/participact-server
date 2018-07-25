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

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.PipelineTaskProgressService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TaskManageController {

	@Autowired
	UserService userServive;

	@Autowired
	TaskUserService taskUserService;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	PipelineTaskProgressService pipelineTaskProgressService;

	private static final Logger logger = LoggerFactory
			.getLogger(TaskManageController.class);

	@RequestMapping(value = "/protected/task", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView index(ModelAndView modelAndView) {
		List<Task> adminTasks = taskService.getAdminTasks();
		List<TaskUser> userTasks = taskUserService.getTaskUser();

		Set<Long> pipelineTaskId = pipelineTaskProgressService
				.getAllPipelineTaskId();

		pipelineTaskId.addAll(pipelineTaskProgressService.getAllStepTaskId());

		List<Task> adminTasksClear = new ArrayList<Task>();

		for (Task t : adminTasks)
			if (!pipelineTaskId.contains(t.getId()))
				adminTasksClear.add(t);

		modelAndView.addObject("adminTasks", adminTasksClear);
		modelAndView.addObject("userTasks", userTasks);
		modelAndView.addObject("totalTasks",
				adminTasks.size() + userTasks.size() - pipelineTaskId.size());
		modelAndView.setViewName("/protected/task/tasks");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/pipelinetask", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView indexpipeline(ModelAndView modelAndView) {
		List<Task> adminTasks = taskService.getAdminTasks();
		List<TaskUser> userTasks = taskUserService.getTaskUser();

		Set<Long> stepTaskId = pipelineTaskProgressService.getAllStepTaskId();

		List<Task> adminTasksClear = new ArrayList<Task>();

		for (Task t : adminTasks)
			if (stepTaskId.contains(t.getId()))
				adminTasksClear.add(t);

		modelAndView.addObject("adminTasks", adminTasksClear);
		modelAndView.addObject("userTasks", userTasks);
		modelAndView.addObject("totalTasks", stepTaskId.size());
		modelAndView.setViewName("/protected/task/pipelinetasks");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/webuser/task", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView index(Principal principal, ModelAndView modelAndView) {
		// Setting task assigned to User and eventually task reports to get
		// state of task
		User user = userServive.getUser(principal.getName());
		if (user == null) {
			throw new ResourceNotFoundException();
		}
		List<Task> assignedTasks = taskService.getTasksByUser(user.getId());
		List<Task> assignedTasksToReturn = new ArrayList<Task>();

		List<TaskUser> userTask = taskUserService.getTaskUserByOwner(user
				.getId());

		for (Task t : assignedTasks) {

			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), t.getId());

			Set<TaskReport> taskReportsByUser = new HashSet<TaskReport>();
			taskReportsByUser.add(taskReport);
			t.setTaskReport(taskReportsByUser);
			assignedTasksToReturn.add(t);
		}

		modelAndView.addObject("assignedTask", assignedTasksToReturn);
		modelAndView.addObject("userTask", userTask);
		modelAndView.addObject("totalTasks", assignedTasksToReturn.size()
				+ userTask.size());

		modelAndView.setViewName("/protected/webuser/task/tasks");

		return modelAndView;
	}

	@RequestMapping(value = "/protected/task/show/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW')")
	public ModelAndView index(@PathVariable Long id, ModelAndView modelAndView) {
		Task task = taskService.findById(id);
		if (task == null) {
			throw new ResourceNotFoundException();
		}

		modelAndView.addObject("task", task);
		List<TaskReport> taskReports = taskReportService
				.getTaskReportsByTask(task.getId());
		List<Long> actionPhotos = new ArrayList<Long>();
		for (Action action : task.getActions()) {
			if (action instanceof ActionPhoto) {
				actionPhotos.add(action.getId());
			}
		}
		modelAndView.addObject("actionPhotos", actionPhotos);
		modelAndView.addObject("taskReports", taskReports);
		modelAndView.setViewName("/protected/task/show");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/webuser/taskuser/show/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showTaskUserDetails(Principal principal,
			@PathVariable Long id, ModelAndView modelAndView) {
		User user = userServive.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			TaskUser taskUser = taskUserService.findById(id);
			if (taskUser == null)
				throw new ResourceNotFoundException();
			if (!user.equals(taskUser.getOwner()))
				throw new IllegalArgumentException();
			List<TaskReport> taskReports = taskReportService
					.getTaskReportsByTask(taskUser.getTask().getId());

			int len = taskReports.size();

			for (int i = 0; i < len; i++) {
				if (taskReports.get(i).getUser().equals(user)) {
					taskReports.remove(i);
					break;
				}
			}

			List<Long> actionPhotos = new ArrayList<Long>();
			for (Action action : taskUser.getTask().getActions()) {
				if (action instanceof ActionPhoto) {
					actionPhotos.add(action.getId());
				}
			}

			modelAndView.addObject("actionPhotos", actionPhotos);
			modelAndView.addObject("taskUser", taskUser);
			modelAndView.setViewName("/protected/webuser/task/showtaskuser");
			modelAndView.addObject("taskReports", taskReports);
			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find user task for user task {}", id);
			throw new ResourceNotFoundException(e);

		}

	}

	@RequestMapping(value = "/protected/webuser/task/show/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView showTaskDetails(Principal principal,
			@PathVariable Long id, ModelAndView modelAndView) {
		User user = userServive.getUser(principal.getName());
		if (user == null)
			throw new ResourceNotFoundException();
		try {
			Task task = taskService.findById(id);
			TaskReport taskReport = taskReportService.findByUserAndTask(
					user.getId(), id);
			modelAndView.addObject("taskReport", taskReport);
			List<Long> actionPhotos = new ArrayList<Long>();
			for (Action action : task.getActions()) {

				if (action instanceof ActionPhoto) {
					actionPhotos.add(action.getId());
				}
			}

			modelAndView.addObject("actionPhotos", actionPhotos);
			modelAndView.addObject("task", task);
			modelAndView.setViewName("/protected/webuser/task/show");

			return modelAndView;
		} catch (NoResultException e) {
			logger.warn("Unable to find task for task {}", id);
			throw new ResourceNotFoundException(e);

		}

	}

	@RequestMapping(value = "/protected/task/{taskid}/stats", method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_VIEW','ROLE_USER')")
	public @ResponseBody ResponseEntity<String> showStats(
			@PathVariable Long taskid) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		String result = "[]";
		List<TaskReport> taskReports = taskReportService
				.getTaskReportsByTask(taskid);
		Map<String, Integer> statuses = new HashMap<String, Integer>();
		for (TaskReport tr : taskReports) {
			TaskState state = tr.getCurrentState();
			String stateStr = "null";
			if (state != null) {
				stateStr = state.toString();
			}
			if (statuses.containsKey(stateStr)) {
				statuses.put(stateStr, statuses.get(stateStr) + 1);
			} else {
				statuses.put(stateStr, 1);
			}
		}
		List<Object> container = new ArrayList<Object>();
		for (String stateStr : statuses.keySet()) {
			List<Object> pair = new ArrayList<Object>(2);
			pair.add(stateStr);
			pair.add(statuses.get(stateStr));
			container.add(pair);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(container);
		} catch (JsonProcessingException e) {
			logger.error("Error while writing JSON", e);
		}
		return new ResponseEntity<String>(result, headers, HttpStatus.OK);
	}
}
