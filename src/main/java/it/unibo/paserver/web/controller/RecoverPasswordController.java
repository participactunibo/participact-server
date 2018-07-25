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

import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.RecoverPasswordService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.validator.RecoverPasswordFormValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletRequest;

import org.apache.commons.mail.HtmlEmail;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.execution.Event;

/**
 * Handles requests for password recoveries.
 */
@Controller
public class RecoverPasswordController implements ResourceLoaderAware {

	private static final Logger logger = LoggerFactory
			.getLogger(RecoverPasswordController.class);

	@Autowired
	private RecoverPasswordFormValidator recoverPasswordFormValidator;

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	@Autowired
	private SecureRandom secureRandom;

	@Autowired
	private UserService userService;

	private ResourceLoader resourceLoader;

	@ModelAttribute
	private RecoverPasswordForm getRecoverPasswordForm() {
		return new RecoverPasswordForm();
	}

	@InitBinder("recoverPasswordForm")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(recoverPasswordFormValidator);
	}

	public RecoverPassword getRecoverPassword(ExternalContext context) {
		String token = context.getRequestParameterMap().get("token");
		RecoverPassword recoverPassword = recoverPasswordService
				.findByToken(token);
		return recoverPassword;
	}

	public Event setPassword(RecoverPassword rp, ResetPasswordForm rpf) {
		User u = rp.getUser();
		u.setCredentials(rpf.getPassword());
		userService.save(u);
		logger.info("Changed password for user {}", u.getOfficialEmail());
		return new EventFactorySupport().success(rpf);
	}

	public ResetPasswordForm initResetPasswordForm() {
		return new ResetPasswordForm();
	}

	@RequestMapping(value = "/recoverpassword", method = RequestMethod.GET)
	public ModelAndView recoverPasswordGet(
			@ModelAttribute RecoverPasswordForm recoverPasswordForm,
			ModelAndView model) {
		model.setViewName("/recoverpassword");
		model.addObject(recoverPasswordForm);
		return model;
	}

	@RequestMapping(value = "/recoverpassword", method = RequestMethod.POST)
	public ModelAndView recoverPasswordPost(
			@ModelAttribute @Validated RecoverPasswordForm recoverPasswordForm,
			BindingResult bindingResult, ServletRequest servletRequest,
			ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			modelAndView.addObject("formerror", Boolean.TRUE);
			modelAndView.setViewName("/recoverpassword");
			return modelAndView;
		}
		modelAndView.addObject("targetemail", recoverPasswordForm.getEmail());
		modelAndView.setViewName("/recoverpasswordcompleted");
		User u = userService.getUser(recoverPasswordForm.getEmail());
		if (u != null) {
			logger.info(
					"Created password recovery request for user {} from IP {}",
					u.getOfficialEmail(), servletRequest.getRemoteAddr());
			setupRecovery(u, servletRequest);
		}
		return modelAndView;
	}

	private String secureRandomString() {
		return new BigInteger(128, secureRandom).toString(Character.MAX_RADIX);
	}

	@Async
	private void setupRecovery(User u, ServletRequest servletRequest) {
		try {
			String token = secureRandomString();
			RecoverPassword recoverPassword = new RecoverPassword();
			DateTime now = new DateTime();
			DateTime end = now.plusHours(1);
			recoverPassword.setStartValidity(now);
			recoverPassword.setEndValidity(end);
			recoverPassword.setUser(u);
			recoverPassword.setToken(token);
			recoverPassword.setUser(u);
			recoverPassword = recoverPasswordService.save(recoverPassword);
			logger.debug(
					"Created recover password id {} for user {} with token {}",
					recoverPassword.getId(), u.getOfficialEmail(), token);
			String url = String.format("https://%s/resetpassword?token=%s",
					servletRequest.getServerName(), token);
			if (servletRequest.getServerPort() != 80
					&& servletRequest.getServerPort() != 8080) {
				url = String.format("https://%s:%d/resetpassword?token=%s",
						servletRequest.getServerName(),
						servletRequest.getServerPort(), token);
			}
			String ip = servletRequest.getRemoteAddr();
			String rcptTo = u.getOfficialEmail();
			Resource htmlres = resourceLoader
					.getResource("classpath:/META-INF/recoverpassword_mail_html.txt");
			Resource txtres = resourceLoader
					.getResource("classpath:/META-INF/recoverpassword_mail_txt.txt");
			String htmltemplate = resourceToString(htmlres);
			String txttemplate = resourceToString(txtres);
			HtmlEmail email = new HtmlEmail();
			email.setHostName("mama.ing.unibo.it");
			email.addTo(rcptTo);
			email.setFrom("participact@unibo.it");
			email.addBcc("participact@unibo.it");
			email.setSubject("Recupero password ParticipAct");
			String htmlcontent = setupEmail(htmltemplate, u, url, ip);
			String txtcontent = setupEmail(txttemplate, u, url, ip);
			email.setHtmlMsg(htmlcontent);
			email.setTextMsg(txtcontent);
			email.send();
		} catch (Exception e) {
			logger.error("Error while setting up password recovery e-mail", e);
		}
	}

	private String resourceToString(Resource res) throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(res.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					logger.error("Error while closing resource", e);
				}
			}
		}
	}

	private String setupEmail(String text, User u, String url, String ip) {
		String result = text.replaceAll("\\$name\\$", u.getName());
		result = result.replaceAll("\\$url\\$", url).replaceAll("\\$ip\\$", ip);
		return result;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
}
