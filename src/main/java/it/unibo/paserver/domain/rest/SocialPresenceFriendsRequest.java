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
package it.unibo.paserver.domain.rest;

import java.util.Set;

/**
 * Utility Class.
 * Used to receive request from REST-friendly device which
 * specify a Set of id, representing his/her friends on
 * a Social Network.
 * 
 *
 * @see SocialPresence
 *
 */
public class SocialPresenceFriendsRequest {

	Set<String> ids;

	/**
	 * Returns friends ids.
	 * 
	 * @return friends ids
	 */
	public Set<String> getIds() {
		return ids;
	}

	/**
	 * Sets friends ids.
	 * @param ids friends ids
	 */
	public void setIds(Set<String> ids) {
		this.ids = ids;
	}

}
