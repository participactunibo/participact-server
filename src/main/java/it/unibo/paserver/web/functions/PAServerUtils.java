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
package it.unibo.paserver.web.functions;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.support.Pipeline;

import java.util.concurrent.TimeUnit;

public class PAServerUtils {

	public static String minutesToReadableTime(Long minutes) {
		if (minutes == null) {
			return "N/A";
		}
		long seconds = Math.abs(minutes) * 60;
		long resday = TimeUnit.SECONDS.toDays(seconds);
		long reshours = TimeUnit.SECONDS.toHours(seconds)
				- TimeUnit.DAYS.toHours(resday);
		long resminutes = TimeUnit.SECONDS.toMinutes(seconds)
				- TimeUnit.DAYS.toMinutes(resday)
				- TimeUnit.HOURS.toMinutes(reshours);
		String result = String.format("%dd %dh %dm", resday, reshours,
				resminutes);
		if (minutes < 0) {
			result = "-" + result;
		}
		return result;
	}

	public static String actionToString(Action action) {
		if (action == null) {
			return "N/A";
		}
		if (action instanceof ActionSensing) {
			ActionSensing as = (ActionSensing) action;
			return String.format("Sensing action: %s - (name: \"%s\")",
					Pipeline.Type.fromInt(as.getInput_type()), as.getName());
		} else if (action instanceof ActionPhoto) {
			return String.format("Photo: Name: %s - Number of photos: %d",
					action.getName(), action.getNumeric_threshold());
		} else if (action instanceof ActionQuestionaire) {
			ActionQuestionaire aq = (ActionQuestionaire) action;
			return String.format("Questionnaire \"%s\" - Description: \"%s\"",
					aq.getTitle(), aq.getDescription());
		}
		return String
				.format("Action %s - %s - Duration threshold %d - Numeric threshold %d",
						action.getClass().getSimpleName(), action.getName(),
						action.getDuration_threshold(),
						action.getNumeric_threshold());
	}

	public static String actionType(Action action) {
		return action.getClass().getSimpleName();
	}
}
