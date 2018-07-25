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

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.FriendshipBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of FriendshipRepository using JPA.
 * 
 *
 * @see FriendshipRepository
 *
 */
@Repository("friendshipRepository")
public class JpaFriendshipRepository implements FriendshipRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaAccountRepository.class);

	@Override
	public Friendship save(Friendship friendship) {
		if (friendship.getId() != null) {
			logger.trace("Merging friendship {}", friendship.toString());
			return entityManager.merge(friendship);
		} else {
			logger.trace("Persisting friendship {}", friendship.toString());
			entityManager.persist(friendship);
			return friendship;
		}
	}

	@Override
	public List<Friendship> getFriendshipsForUser(long userId) {
		String hql = "from Friendship t where t.receiver.id = :userId or t.sender.id = :userId";
		TypedQuery<Friendship> query = entityManager.createQuery(hql,
				Friendship.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status) {
		String hql = "from Friendship t where ( t.receiver.id = :userId or t.sender.id = :userId ) and t.status = :fiendshipstatus";
		TypedQuery<Friendship> query = entityManager
				.createQuery(hql, Friendship.class)
				.setParameter("userId", userId)
				.setParameter("fiendshipstatus", status);
		return query.getResultList();
	}

	@Override
	public List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status, boolean sender) {
		String hql = null;
		if (sender)
			hql = "from Friendship t where t.sender.id = :userId and t.status = :fiendshipstatus";

		else
			hql = "from Friendship t where t.receiver.id = :userId and t.status = :fiendshipstatus";

		TypedQuery<Friendship> query = entityManager
				.createQuery(hql, Friendship.class)
				.setParameter("userId", userId)
				.setParameter("fiendshipstatus", status);
		return query.getResultList();
	}

	@Override
	public List<Friendship> getFriendships() {
		String hql = "select c from Friendship c";
		TypedQuery<Friendship> query = entityManager.createQuery(hql,
				Friendship.class);
		return query.getResultList();
	}

	@Override
	public Long getFriendshipsCount() {
		String hql = "select count(id) from Friendship c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Friendship getFriendshipForSenderAndReceiver(long senderId,
			long receiverId) {
		String hql = "from Friendship t where t.receiver.id = :receiverId and t.sender.id = :senderId";
		TypedQuery<Friendship> query = entityManager
				.createQuery(hql, Friendship.class)
				.setParameter("receiverId", receiverId)
				.setParameter("senderId", senderId);
		return query.getSingleResult();
	}

	@Override
	public Friendship create(User sender, User receiver, FriendshipStatus status) {
		EntityBuilderManager.setEntityManager(entityManager);
		sender = entityManager.merge(sender);
		receiver = entityManager.merge(receiver);
		return new FriendshipBuilder().setReceiver(receiver).setSender(sender)
				.setStatus(status).build();
	}

}
