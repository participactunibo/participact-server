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
package it.unibo.paserver.service;

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.SocialPresenceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of SocialPresenceService using SocialPresenceRepository
 * 
 *
 * @see SocialPresenceService
 * @see SocialPresenceRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class SocialPresenceServiceImpl implements SocialPresenceService {

	@Autowired
	SocialPresenceRepository socialPresenceRepository;

	@Override
	@Transactional(readOnly = false)
	public SocialPresence save(SocialPresence socialPresence) {
		return socialPresenceRepository.save(socialPresence);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresencesForUser(long userId) {
		return socialPresenceRepository.getSocialPresencesForUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public SocialPresence getSocialPresenceForUserAndSocialNetwork(long userId,
			SocialPresenceType socialNetwork) {
		return socialPresenceRepository
				.getSocialPresenceForUserAndSocialNetwork(userId, socialNetwork);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresences() {
		return socialPresenceRepository.getSocialPresences();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getSocialPresencesCount() {
		return socialPresenceRepository.getSocialPresencesCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<SocialPresence> getSocialPresencesForSocialNetwork(
			SocialPresenceType socialNetwork) {
		return socialPresenceRepository
				.getSocialPresencesForSocialNetwork(socialNetwork);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getFriendsOnSocialNetwork(
			SocialPresenceType socialNetwork, Set<String> socialIds) {

		List<SocialPresence> socialPresences = getSocialPresencesForSocialNetwork(socialNetwork);

		List<User> result = new ArrayList<User>();

		for (SocialPresence currentSocialPresence : socialPresences) {
			if (socialIds.contains(currentSocialPresence.getSocialId()))
				result.add(currentSocialPresence.getUser());
		}

		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public SocialPresence create(User user, SocialPresenceType socialNetwork,
			String socialId) {
		return socialPresenceRepository.create(user, socialNetwork, socialId);
	}

}
