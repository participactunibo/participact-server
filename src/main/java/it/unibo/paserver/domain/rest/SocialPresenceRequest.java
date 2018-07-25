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

/**
 * Utility Class.
 * Used to receive request from REST-friendly device which
 * specify his/her id on a Social Network
 * 
 *
 * @see SocialPresence
 *
 */
public class SocialPresenceRequest {

	private String socialId;

	/**
	 * Returns the User's id on Social Network.
	 * @return User's id on Social Network
	 */
	public String getSocialId() {
		return socialId;
	}

	/**
	 * Sets the User's id on Social Network.
	 * @param socialId User's id on Social Network.
	 */
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

}
