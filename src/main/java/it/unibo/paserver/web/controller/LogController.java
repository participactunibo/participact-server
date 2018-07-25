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
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.UserLogFile;
import it.unibo.paserver.service.UserLogFileService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.paserver.web.validator.SelectUsersFormValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@Controller
public class LogController {
	
	@Autowired
	GCMController gcmContoller;

	@Autowired
	UserLogFileService userLogFileService;

	@Autowired
	SelectUsersFormValidator selectUsersFormValidator;

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory
			.getLogger(LogController.class);

	@ModelAttribute("selectUsersForm")
	public SelectUsersForm getSelectUsersForm() {
		return new SelectUsersForm();
	}

	@InitBinder("selectUsersForm")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(selectUsersFormValidator);
	}

	@RequestMapping(value = "/protected/logs/list", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listLogs(ModelAndView modelAndView) {
		List<UserLogFile> logFiles = userLogFileService.getUserLogFiles();
		modelAndView.addObject("logs", logFiles);
		modelAndView.setViewName("/protected/logs/list");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/logs/get", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView getLogs(ModelAndView modelAndView) {
		modelAndView.addObject("selectUsersForm", new SelectUsersForm());
		modelAndView.setViewName("/protected/logs/selectUsers");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/logs/request", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView sendRequest(
			@ModelAttribute @Validated SelectUsersForm selectUsersForm,
			BindingResult bindingResult, ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/protected/logs/selectUsers");
			return modelAndView;
		}
		String[] userStrings = selectUsersForm.getUserList().split(
				"[^a-zA-Z0-9@.]");
		List<String> userEmails = Arrays.asList(userStrings);
		logger.info("Sending log request to users {}",
				StringUtils.join(userEmails, ", "));
		gcmContoller.notifyUsers(PANotification.Type.LOG_UPLOAD_REQUEST, userEmails);
		modelAndView.setViewName("/protected/logs/confirmation");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/logs/download/{logId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<byte[]> downloadLog(@PathVariable Long logId) {
		try {
			BinaryDocument bd = userLogFileService.getLogFile(logId);
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(new MediaType(bd.getContentType(), bd
					.getContentSubtype()));
			return new ResponseEntity<byte[]>(bd.getContent(), httpHeaders,
					HttpStatus.OK);
		} catch (NoResultException e) {
			throw new ResourceNotFoundException(e);
		}
	}

	@RequestMapping(value = "/protected/logs/delete", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteLog(ModelAndView modelAndView,
			@RequestParam Long id, RedirectAttributes redirectAttributes) {
		userLogFileService.deleteUserLogFile(id);
		modelAndView.setViewName("redirect:/protected/logs/list");
		return modelAndView;
	}

	
}
