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

import it.unibo.paserver.domain.support.TwitterStatus;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Controller
public class TwitterController {

	private static final String OAUTH_CONSUMER_KEY = "6RXduk1K0qVp1KySa2jwgA";
	private static final String OAUTH_CONSUMER_SECRET = "DG7cH3h2YsCHFj6A23qqozZwbiDNN0VAkUd9cCsaq0";
	private static final String OAUTH_ACCESS_TOKEN = "1433054970-1uzccYY2iEZ9t5RfjgjP5wwcat9RQUafnvhC16U";
	private static final String OAUTH_ACCESS_TOKEN_SECRET = "X2fWvhTqWnkN0ajNrHvZ2mQLk2ASyaBy6kISYtC6cs";
	private static final int MAX_TWEETS = 3;
	private static final int TWITTER_CACHE_IN_SECONDS = 600;

	private static final Logger logger = LoggerFactory
			.getLogger(TwitterController.class);

	private DateTime lastUpdate;
	private List<TwitterStatus> lastStatuses;

	@RequestMapping(value = "/rest/twitter", method = RequestMethod.GET)
	public @ResponseBody List<TwitterStatus> getTwitter() {
		logger.info("Requested latest tweets");
		if (lastStatuses == null
				|| Seconds.secondsBetween(lastUpdate, new DateTime())
						.getSeconds() > TWITTER_CACHE_IN_SECONDS) {
			lastUpdate = new DateTime();
			lastStatuses = getRecentStatus();
		}
		return lastStatuses;
	}

	private List<TwitterStatus> getRecentStatus() {
		List<TwitterStatus> result = new ArrayList<TwitterStatus>();
		logger.info("Retrieving tweet feed from @participact");
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(OAUTH_CONSUMER_KEY)
				.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET)
				.setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
				.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		try {

			ResponseList<Status> homeTweets = twitter.getUserTimeline();
			int counter = 0;
			for (Status status : homeTweets) {
				TwitterStatus ts = new TwitterStatus();
				ts.setCreatedAt(new DateTime(status.getCreatedAt()));
				ts.setText(status.getText());
				result.add(ts);
				counter++;
				if (counter > MAX_TWEETS) {
					break;
				}
			}

		} catch (TwitterException te) {
			logger.error("Exception while retrieving tweets", te);
		}
		return result;
	}

}
