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

import it.unibo.paserver.domain.ClientSWVersion;
import it.unibo.paserver.service.ClientSWVersionService;
import it.unibo.paserver.web.validator.ClientSWVersionValidator;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClientSWVersionController {

	@Autowired
	ClientSWVersionService clientSWVersionService;

	private static final Logger logger = LoggerFactory
			.getLogger(ClientSWVersionController.class);

	@ModelAttribute("clientSWVersion")
	public ClientSWVersion getClientSWVersion() {
		return new ClientSWVersion();
	}

	@InitBinder("clientSWVersion")
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(new ClientSWVersionValidator());
	}

	@RequestMapping(value = "/protected/clientversion/list", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listClientSWVersion(ModelAndView modelAndView) {
		List<ClientSWVersion> csvs = clientSWVersionService
				.getClientSWVersions();
		modelAndView.addObject("csvs", csvs);
		modelAndView.addObject("latestVersion", getLatestVersion());
		modelAndView.setViewName("/protected/clientversion/list");
		logger.info("Requested list of client software versions");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/clientversion/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addClientSWVersionForm(ModelAndView modelAndView) {
		ClientSWVersion clientSWVersion = new ClientSWVersion();
		clientSWVersion.setVersion(getLatestVersion() + 1);
		modelAndView.addObject("clientSWVersion", clientSWVersion);
		modelAndView.setViewName("/protected/clientversion/add");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/clientversion/add", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView addClientSWVersion(
			@ModelAttribute @Validated ClientSWVersion clientSWVersion,
			BindingResult bindingResult, RedirectAttributes redirectAttributes,
			ModelAndView modelAndView) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/protected/clientversion/add");
			return modelAndView;
		}
		clientSWVersion.setCreationDate(new DateTime());
		clientSWVersionService.save(clientSWVersion);
		redirectAttributes.addFlashAttribute("successcreation",
				"successcreation");
		modelAndView.setViewName("redirect:/protected/clientversion/list");
		return modelAndView;
	}

	private Integer getLatestVersion() {
		ClientSWVersion csv = clientSWVersionService.getLatestVersion();
		Integer latestVersion = 1;
		if (csv != null) {
			latestVersion = csv.getVersion();
		}
		return latestVersion;
	}
}
