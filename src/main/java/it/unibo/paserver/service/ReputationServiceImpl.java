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

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Level;
import it.unibo.paserver.domain.Reputation;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.ReputationRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of ReputationService using ReputationRepository.
 * 
 *
 * @see ReputationService
 * @see ReputationRepository
 *
 */
@Service
@Transactional(readOnly = true)
public class ReputationServiceImpl implements ReputationService {

	@Autowired
	ReputationRepository reputationRepository;

	@Override
	@Transactional(readOnly = false)
	public Reputation save(Reputation reputation) {
		return reputationRepository.save(reputation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reputation> getReputationsByUser(long userId) {
		return reputationRepository.getReputationsByUser(userId);
	}

	@Override
	@Transactional(readOnly = false)
	public Reputation getReputationByUserAndActionType(long userId,
			ActionType actionType) {
		return reputationRepository.getReputationByUserAndActionType(userId,
				actionType);
	}

	@Override
	@Transactional(readOnly = true)
	public Level getLevelByUserAndActionType(long userId, ActionType actionType) {

		Reputation reputation = getReputationByUserAndActionType(userId,
				actionType);

		return it.unibo.paserver.domain.Level
				.getLevelFromReputation(reputation);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Reputation> getReputations() {
		return reputationRepository.getReputations();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getReputationsCount() {
		return reputationRepository.getReputationsCount();
	}

	@Override
	@Transactional(readOnly = false)
	public Reputation create(User user, ActionType actionType, int value) {
		return reputationRepository.create(user, actionType, value);
	}

}
