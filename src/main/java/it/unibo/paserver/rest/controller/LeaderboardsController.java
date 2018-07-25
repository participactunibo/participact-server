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

import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.rest.ScoreRestResult;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.service.PointsService;
import it.unibo.paserver.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * REST controller providing leaderboards.
 * 
 *
 */
@Controller
public class LeaderboardsController {

	@Autowired
	FriendshipService friendshipService;
	@Autowired
	PointsService pointsService;
	@Autowired
	UserService userService;

	public static final String GlobalLeaderboard = "global";
	public static final String SociallLeaderboard = "social";

	private static final Logger logger = LoggerFactory
			.getLogger(LeaderboardsController.class);

	private static final int HowManyDays = 7;

	/**
	 * Returns Scores of Participact Users or friends.
	 * 
	 * @param principal Spring security user
	 * @param type "global" for all users, "social" for friends
	 * @return array of ScoreRestResult
	 * @see Principal
	 * @see ScoreRestResult
	 */
	@RequestMapping(value = "/rest/leaderboard", method = RequestMethod.GET)
	public @ResponseBody ScoreRestResult[] getLeaderBoard(Principal principal,
			@RequestParam("type") String type) {

		DateTime from = new DateTime().minusDays(HowManyDays);
		DateTime to = new DateTime();
		List<User> users = null;
		User user = userService.getUser(principal.getName());
		ArrayList<ScoreRestResult> resultList = new ArrayList<ScoreRestResult>();

		if (user != null) {

			if (GlobalLeaderboard.equalsIgnoreCase(type))
				users = userService.getUsers();

			else if (SociallLeaderboard.equalsIgnoreCase(type)) {
				users = friendshipService.getFriendsForUserAndStatus(
						user.getId(), FriendshipStatus.ACCEPTED, false);
				users.add(user);
			} else {
				logger.info(
						"Received {} leaderboard request from user {}, but {} type doesn't exist",
						type, user.getName(), type);
				return new ScoreRestResult[0];
			}

			for (User currentUser : users) {
				Score score = pointsService.getScoreByUserAndDates(
						currentUser.getId(), from, to);
				if (score.getValue() > 0) {
					ScoreRestResult currentUserResult = new ScoreRestResult(
							score);
					resultList.add(currentUserResult);
				}
			}

			// Collections.sort(resultList);
			ScoreRestResult[] result = new ScoreRestResult[resultList.size()];
			result = resultList.toArray(result);
			Arrays.sort(result);
			logger.info("Received {} leaderboard request from user {}", type,
					user.getName());
			return result;
		}

		else
			return new ScoreRestResult[0];

	}

}
