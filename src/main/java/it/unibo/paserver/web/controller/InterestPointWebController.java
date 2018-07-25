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

import java.util.List;

import it.unibo.paserver.domain.InterestPoint;
import it.unibo.paserver.domain.support.InterestPointBuilder;
import it.unibo.paserver.service.InterestPointService;
import it.unibo.paserver.web.validator.AddInterestPointValidator;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class InterestPointWebController {
	@Autowired
	private InterestPointService interestPointService;

	@ModelAttribute("addInterestPointForm")
	public AddInterestPointForm getAddInterestPointForm() {
		return new AddInterestPointForm();
	}

	@InitBinder("addInterestPointForm")
	public void initBinder(WebDataBinder binder) {
		binder.setRequiredFields("description");
		binder.setValidator(new AddInterestPointValidator());
	}

	private static final Logger logger = LoggerFactory
			.getLogger(InterestPointWebController.class);

	@RequestMapping(value = "/protected/interestPoint", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listBadges(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/interestPoint");
		@SuppressWarnings("unchecked")
		List<InterestPoint> interestPoints = (List<InterestPoint>) interestPointService
				.getInterestPoint();
		modelAndView.addObject("interestPoint", interestPoints);
		modelAndView.addObject("totalInterestPoint", interestPoints.size());
		return modelAndView;
	}

	@RequestMapping(value = "/protected/interestPoint/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView InterestPointForm(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/interestPoint/add");
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/interestPoint/addByMaps", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView InterestPointMapsForm(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/interestPoint/addByMaps");
		return modelAndView;
	}
	
	@RequestMapping(value = "/protected/interestPoint/addInterestPointByMaps", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView InterestPointAddByMaps(
			@ModelAttribute @Validated AddInterestPointForm addInterestPointForm,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errormessage", String.format(
					"Interest Point \"%s\" not created, Error Format value",
					addInterestPointForm.getDescription()));
			modelAndView.setViewName("redirect:/protected/interestPoint/addByMaps");
			return modelAndView;
		}

		InterestPoint interestPoint = interestPointService.findByLatLon(
				addInterestPointForm.getLat(), addInterestPointForm.getLon());

		if (interestPoint != null) {
			bindingResult.rejectValue("lat", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			bindingResult.rejectValue("lon", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			modelAndView.setViewName("/protected/interestPoint/addByMaps");
		} else if ((interestPoint = interestPointService
				.findByDescription(addInterestPointForm.getDescription())) != null) {
			bindingResult.rejectValue("description", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			modelAndView.setViewName("/protected/interestPoint/addByMaps");

		} else {

			InterestPointBuilder ipBuilder = new InterestPointBuilder();
			ipBuilder.setDescription(addInterestPointForm.getDescription());
			ipBuilder.setLat(addInterestPointForm.getLat());
			ipBuilder.setLon(addInterestPointForm.getLon());
			ipBuilder.setInterestPointAreea(addInterestPointForm.getInterestPointArea());

			InterestPoint newInterestPoint = ipBuilder.build(true);
			
			InterestPoint ip = interestPointService.save(newInterestPoint);
			redirectAttributes.addFlashAttribute("successmessage", String
					.format("Interest Point \"%s\" successfully created",
							ip.getDescription()));
			modelAndView.setViewName("redirect:/protected/interestPoint");
		}
		return modelAndView;
	}
	
	
	

	@RequestMapping(value = "/protected/interestPoint/addInterestPoint", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView InterestPointAdd(
			@ModelAttribute @Validated AddInterestPointForm addInterestPointForm,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("errormessage", String.format(
					"Interest Point \"%s\" not created, Error Format value",
					addInterestPointForm.getDescription()));
			modelAndView.setViewName("redirect:/protected/interestPoint/add");
			return modelAndView;
		}

		InterestPoint interestPoint = interestPointService.findByLatLon(
				addInterestPointForm.getLat(), addInterestPointForm.getLon());

		if (interestPoint != null) {
			bindingResult.rejectValue("lat", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			bindingResult.rejectValue("lon", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			modelAndView.setViewName("/protected/interestPoint/add");
		} else if ((interestPoint = interestPointService
				.findByDescription(addInterestPointForm.getDescription())) != null) {
			bindingResult.rejectValue("description", "alreadyexists",
					new String[] { addInterestPointForm.getDescription() },
					"alreadyexists");
			modelAndView.setViewName("/protected/interestPoint/add");

		} else if(addInterestPointForm.getInterestPointArea().equals("")){
			bindingResult.rejectValue("interestPointArea", "notNull",
					new String[] { addInterestPointForm.getDescription() },
					"notNull");
			modelAndView.setViewName("/protected/interestPoint/add");
			
		}else {

			InterestPointBuilder ipBuilder = new InterestPointBuilder();
			ipBuilder.setDescription(addInterestPointForm.getDescription());
			ipBuilder.setLat(addInterestPointForm.getLat());
			ipBuilder.setLon(addInterestPointForm.getLon());
			ipBuilder.setInterestPointAreea(addInterestPointForm.getInterestPointArea());
			
			InterestPoint newInterestPoint = ipBuilder.build(true);
			
			InterestPoint ip = interestPointService.save(newInterestPoint);
			redirectAttributes.addFlashAttribute("successmessage", String
					.format("Interest Point \"%s\" successfully created",
							ip.getDescription()));
			modelAndView.setViewName("redirect:/protected/interestPoint");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/protected/interestPoint/delete", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView deleteInterestPoint(@RequestParam Integer id,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {

		logger.trace("Received request to delete Interest Point {}", id);
		if (interestPointService.deleteInterestPoint(new Long(id))) {
			redirectAttributes.addFlashAttribute("successmessage", String
					.format("Interest Point #\"%d\" successfully deleted", id));
		} else {
			String msg = String
					.format("Unabe to delete InterestPoint #\"%d\", please consult logs for further information",
							id);
			redirectAttributes.addFlashAttribute("errormessage", msg);
		}
		modelAndView.setViewName("redirect:/protected/interestPoint");
		return modelAndView;
	}

}
