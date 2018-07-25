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

import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.validator.PointsDatesFormValidator;

import java.util.ArrayList;
import java.util.Collections;
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
public class PointsWebController {

	@Autowired
	private PointsService pointsService;

	@Autowired
	private PointsDatesFormValidator pointsDatesFormValidator;

	@Autowired
	private UserService userService;

	@ModelAttribute("datesForm")
	public PointsDatesForm getPointsDatesForm() {
		return new PointsDatesForm();
	}

	@InitBinder("datesForm")
	public void initBinderEdit(WebDataBinder binder) {
		binder.setValidator(pointsDatesFormValidator);
	}

	private static final Logger logger = LoggerFactory
			.getLogger(PointsWebController.class);

	@RequestMapping(value = "/protected/points", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listBadges(ModelAndView modelAndView) {
		modelAndView.setViewName("/protected/points");
		List<User> users = userService.getUsers();
		List<Score> scores = new ArrayList<Score>();
		DateTime now = new DateTime();
		DateTime monthAgo = new DateTime().minusMonths(1);
		for (User currentUser : users) {
			Score score = pointsService.getScoreByUserAndDates(
					currentUser.getId(), monthAgo, now);
			scores.add(score);
		}
		Collections.sort(scores);
		modelAndView.addObject("scores", scores);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/points", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView postBadgeEdit(
			@ModelAttribute @Validated PointsDatesForm pdf,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {
		modelAndView.setViewName("/protected/points");
		List<User> users = userService.getUsers();
		List<Score> scores = new ArrayList<Score>();
		DateTime from = pdf.getStart();
		DateTime to = pdf.getEnd();
		for (User currentUser : users) {
			Score score = pointsService.getScoreByUserAndDates(
					currentUser.getId(), from, to);
			scores.add(score);
		}
		Collections.sort(scores);
		modelAndView.addObject("scores", scores);
		return modelAndView;
	}
}
