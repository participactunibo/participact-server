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
package it.unibo.paserver.repository;

import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.SocialPresenceBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of SocialPresenceRepository using JPA.
 * 
 *
 * @see SocialPresenceRepository
 *
 */
@Repository("socialPresenceRepository")
public class JpaSocialPresenceRepository implements SocialPresenceRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaAccountRepository.class);

	@Override
	public SocialPresence save(SocialPresence socialPresence) {
		if (socialPresence.getId() != null) {
			logger.trace("Merging socialPresence {}", socialPresence.toString());
			return entityManager.merge(socialPresence);
		} else {
			logger.trace("Persisting socialPresence {}",
					socialPresence.toString());
			entityManager.persist(socialPresence);
			return socialPresence;
		}
	}

	@Override
	public List<SocialPresence> getSocialPresencesForUser(long userId) {
		String hql = "from SocialPresence t where t.user.id = :userId";
		TypedQuery<SocialPresence> query = entityManager.createQuery(hql,
				SocialPresence.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public SocialPresence getSocialPresenceForUserAndSocialNetwork(long userId,
			SocialPresenceType socialNetwork) {
		String hql = "from SocialPresence t where t.user.id = :userId and t.socialNetwork = :socialnetwork";
		TypedQuery<SocialPresence> query = entityManager
				.createQuery(hql, SocialPresence.class)
				.setParameter("userId", userId)
				.setParameter("socialnetwork", socialNetwork);
		return query.getSingleResult();
	}

	@Override
	public List<SocialPresence> getSocialPresencesForSocialNetwork(
			SocialPresenceType socialNetwork) {
		String hql = "from SocialPresence t where t.socialNetwork = :socialnetwork";
		TypedQuery<SocialPresence> query = entityManager.createQuery(hql,
				SocialPresence.class).setParameter("socialnetwork",
				socialNetwork);
		return query.getResultList();
	}

	@Override
	public List<SocialPresence> getSocialPresences() {
		String hql = "select c from socialpresence c";
		TypedQuery<SocialPresence> query = entityManager.createQuery(hql,
				SocialPresence.class);
		return query.getResultList();
	}

	@Override
	public Long getSocialPresencesCount() {
		String hql = "select count(id) from socialpresence c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public SocialPresence create(User user, SocialPresenceType socialNetwork,
			String socialId) {
		EntityBuilderManager.setEntityManager(entityManager);
		user = entityManager.merge(user);
		return new SocialPresenceBuilder().setUser(user)
				.setSocialNetwork(socialNetwork).setSocialId(socialId).build();
	}

}
