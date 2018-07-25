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

import it.unibo.paserver.domain.AbstractBadge;
import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.GeobadgeCollected;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.BadgeService;
import it.unibo.paserver.service.GeobadgeCollectedService;
import it.unibo.paserver.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST controller for Badges.
 * 
 *
 *
 */
@Controller
public class BadgeController {

	@Autowired
	private BadgeService badgeService;
	@Autowired
	private UserService userService;
	
	@Autowired
	GeobadgeCollectedService geobadgeCollectedService;

	private static final Logger logger = LoggerFactory
			.getLogger(BadgeController.class);

	/**
	 * Returns all Badges.
	 * 
	 * @return array of all AbstractBadges saved in database
	 * @see AbstractBadge 
	 */
	@RequestMapping(value = "/rest/badges", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getAllbadges() {

		logger.info("Received request for all badges");

		@SuppressWarnings("unchecked")
		List<AbstractBadge> resultList = (List<AbstractBadge>) badgeService
				.getBadges();
		AbstractBadge[] result = new AbstractBadge[resultList.size()];
		result = resultList.toArray(result);
		Arrays.sort(result);
		return result;
	}

	/**
	 * Returns Badge for given id.
	 * 
	 * @param id Badge id
	 * @return AbstractBadge for given id
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/{id}", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge getBadgeById(@PathVariable long id) {
		AbstractBadge result = (AbstractBadge) badgeService.findById(id);
		logger.info("Received request for badge {}", id);
		return result;
	}

	/**
	 * Returns all unlocked Badges of user.
	 * 
	 * @param principal Spring Security user
	 * @return array of AbstractBadge unlocked by user
	 * @see Principal
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/user/me", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getBadgeByUser(Principal principal) {
		User user = userService.getUser(principal.getName());
		if (user != null) {
			
			return getBadgeByUser(user.getId());
		}
		logger.info(
				"Received request for badge of user {}, but user doesn't exist",
				principal.getName());
		return new AbstractBadge[0];
	}

	/**
	 * Returns all unlocked Badges by given User id
	 * 
	 * @param id User id
	 * @return array of AbstractBadge unlocked by given User id
	 * @see AbstractBadge
	 */
	@RequestMapping(value = "/rest/badges/user/{id}", method = RequestMethod.GET)
	public @ResponseBody AbstractBadge[] getBadgeByUser(@PathVariable long id) {
		@SuppressWarnings("unchecked")
		Set<AbstractBadge> resultSet = (Set<AbstractBadge>) badgeService
				.getBadgesForUser(id);
			
		List<GeobadgeCollected> geobadgeCollected = geobadgeCollectedService.getGeobadgeCollectedByIdUser(id);
		List<GeobadgeCollected> geobadgeUnique = new ArrayList<GeobadgeCollected>();
		AbstractBadge ba;
		for(GeobadgeCollected geo : geobadgeCollected){
			if(!geobadgeUnique.contains(geo)){
				geobadgeUnique.add(geo);
				ba = new BadgeTask();
				ba.setTitle("GeoBadge");
				ba.setDescription(geo.getDescription());
				resultSet.add(ba);
				
			}
		}
		AbstractBadge[] result = new AbstractBadge[resultSet.size()];
		result = resultSet.toArray(result);
		Arrays.sort(result);
		logger.info("Received request for badge of user {}", id);
		return result;
	}

}
