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

import it.unibo.participact.domain.PANotification;
import it.unibo.participact.domain.PANotificationConst;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.validator.SelectUsersGCMFormValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

@Controller
public class GCMController {

	@Autowired
	SelectUsersGCMFormValidator selectUsersGCMFormValidator;

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(GCMController.class);

	@ModelAttribute("selectUsersGCMForm")
	public SelectUsersGCMForm getSelectUsersGCMForm() {
		return new SelectUsersGCMForm();
	}

	@InitBinder("selectUsersGCMForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(selectUsersGCMFormValidator);
	}

	@RequestMapping(value = "/protected/gcm/send", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView getGCMSend(ModelAndView modelAndView) {
		addObjects(modelAndView);
		modelAndView.setViewName("/protected/gcm/selectUsers");
		return modelAndView;
	}

	private void addObjects(ModelAndView modelAndView) {
		modelAndView.addObject("selectUsersGCMForm", new SelectUsersGCMForm());
		Map<String, String> gcmTypes = new LinkedHashMap<String, String>();
		for (PANotification.Type gcmType : PANotification.Type.values()) {
			gcmTypes.put(gcmType.toString(), gcmType.toString());
		}
		modelAndView.addObject("gcmTypes", gcmTypes);
	}

	@RequestMapping(value = "/protected/gcm/send", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView sendRequest(
			@ModelAttribute @Validated SelectUsersGCMForm selectUsersGCMForm,
			BindingResult bindingResult, ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			addObjects(modelAndView);
			modelAndView.addObject("selectUsersGCMForm", selectUsersGCMForm);
			modelAndView.setViewName("/protected/gcm/selectUsers");
			return modelAndView;
		}
		String[] userStrings = selectUsersGCMForm.getUserList().split(
				"[^a-zA-Z0-9@.]");
		List<String> userEmails = Arrays.asList(userStrings);
		logger.info("Sending GCM request {} to users {}", selectUsersGCMForm
				.getGcmType().toString(), StringUtils.join(userEmails, ", "));
		notifyUsers(selectUsersGCMForm.getGcmType(), userEmails);
		modelAndView.setViewName("/protected/gcm/confirmation");
		return modelAndView;
	}

	/*
	@Async
	public void notifyTaskUserValutation(TaskUser task, String user)
	{
		Message.Builder mb = new Message.Builder();
		mb.collapseKey("NEW_TASK")
		.delayWhileIdle(true);
		if(task.getValutation().equals(TaskValutation.APPROVED))
		{
			
		}
		else{
			mb.addData(PANotification.KEY,
					PANotification.Type.TASK_NEGATIVE_VALUTATION.toString())
					.addData(PANotificationConst.TASK_ID, task.getId().toString());
		}
		
				
		mb.restrictedPackageName(PANotificationConst.PACKAGE_NAME);
		Message msg = mb.build();
		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
		List<String> gcmids = new ArrayList<String>();
		User u = userService.getUser(user);
		if (u == null) {
			logger.error("Unable to notify user {}: user not found", user);
		} else {
			String gcmid = u.getGcmId();
			if (gcmid == null) {
				logger.error(
						"Unable to notify user {}: no GCMid available",
						u.getOfficialEmail());
			} else {
				gcmids.add(gcmid);
				logger.info("Added user {} with gcmid {}", user,
						StringUtils.abbreviate(gcmid, 25));
			}
		}

		try {
			if (gcmids.size() > 0) {
				sender.send(msg, gcmids, 8);
			} else {
				logger.error("No GCMid found");
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}

	}

	@Async
	public void notifyNewTaskCreated(Task task, Collection<String> users){
		Message.Builder mb = new Message.Builder();
		mb.collapseKey("NEW_TASK")
		.delayWhileIdle(true)
		.addData(PANotification.KEY,
				PANotification.Type.NEW_TASK.toString())
				.addData(PANotificationConst.TASK_ID, task.getId().toString())
				.restrictedPackageName(PANotificationConst.PACKAGE_NAME);
		Message msg = mb.build();
		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
		List<String> gcmids = new ArrayList<String>();
		for (String user : users) {
			User u = userService.getUser(user);
			if (u == null) {
				logger.error("Unable to notify user {}: user not found", user);
			} else {
				String gcmid = u.getGcmId();
				if (gcmid == null) {
					logger.error(
							"Unable to notify user {}: no GCMid available",
							u.getOfficialEmail());
				} else {
					gcmids.add(gcmid);
					logger.info("Added user {} with gcmid {}", user,
							StringUtils.abbreviate(gcmid, 25));
				}
			}
		}
		try {
			if (gcmids.size() > 0) {
				sender.send(msg, gcmids, 8);
			} else {
				logger.error("No GCMid found");
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}
	}
*/
	@Async
	public void notifyUsers(PANotification.Type gcmType,
			Collection<String> users) {
		Message.Builder mb = new Message.Builder();
		mb.collapseKey("NEW_TASK").delayWhileIdle(true)
		.addData(PANotification.KEY, gcmType.toString())
		.restrictedPackageName(PANotificationConst.PACKAGE_NAME);
		Message msg = mb.build();
		Sender sender = new Sender(PANotificationConst.DEBUG_API_KEY);
		List<String> gcmids = new ArrayList<String>();
		for (String user : users) {
			if (StringUtils.isBlank(user)) {
				continue;
			}
			User u = userService.getUser(user);
			if (u == null) {
				logger.error("Unable to notify user {}: user not found", user);
			} else {
				String gcmid = u.getGcmId();
				if (gcmid == null) {
					logger.error(
							"Unable to notify user {}: no GCMid available",
							u.getOfficialEmail());
				} else {
					gcmids.add(gcmid);
					logger.info("Added user {} with gcmid {}", user,
							StringUtils.abbreviate(gcmid, 25));
				}
			}
		}
		try {
			if (gcmids.size() > 0) {
				sender.send(msg, gcmids, 8);
			} else {
				logger.error("No GCMid found");
			}
		} catch (IOException e) {
			logger.error("Error", e);
		}
	}
	
}
