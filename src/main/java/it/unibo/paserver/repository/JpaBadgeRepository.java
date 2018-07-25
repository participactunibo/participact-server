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

import it.unibo.paserver.domain.AbstractBadge;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.BadgeTaskBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
/**
 * Implementation of BadgeRepository using JPA.
 * 
 *
 * @see BadgeRepository
 *
 */
@Repository("badgeRepository")
public class JpaBadgeRepository implements BadgeRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaAccountRepository.class);

	@Override
	public Badge findById(long id) {
		return entityManager.find(AbstractBadge.class, id);
	}

	@Override
	public Badge save(Badge badge) {
		if (badge.getId() != null) {
			logger.trace("Merging badge {}", badge.toString());
			return entityManager.merge(badge);
		} else {
			logger.trace("Persisting badge {}", badge.toString());
			entityManager.persist(badge);
			return badge;
		}
	}

	@Override
	public Set<? extends Badge> getBadgesForUser(long userId) {
		String hql = "select t.badges from User t where t.id = :userId";
		@SuppressWarnings("rawtypes")
		TypedQuery<Set> query = entityManager.createQuery(hql, Set.class)
				.setParameter("userId", userId);

		Set<AbstractBadge> resultSet = new HashSet<AbstractBadge>();
		
		@SuppressWarnings("rawtypes")
		List<Set> tempResultList = query.getResultList();
		int size = tempResultList.size();
		for(int i=0;i<size;i++)
			resultSet.add((AbstractBadge) tempResultList.get(i));
		
		return resultSet;
	}

	@Override
	public List<? extends Badge> getBadges() {
		String hql = "select c from AbstractBadge c";
		TypedQuery<Badge> query = entityManager.createQuery(hql, Badge.class);
		return query.getResultList();
	}

	@Override
	public Long getBadgesCount() {
		String hql = "select count(badge_id) from badge c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<BadgeActions> getBadgesForActionType(ActionType actionType) {
		String hql = "select t from BadgeActions t where t.actionType = :actionType";
		TypedQuery<BadgeActions> query = entityManager.createQuery(hql,
				BadgeActions.class).setParameter("actionType", actionType);

		return query.getResultList();
	}

	@Override
	public BadgeActions getBadgeForActionTypeAndQuantity(ActionType actionType,
			int quantity) {
		String hql = "select t from BadgeActions t where t.actionType = :actionType and t.quantity = :quantity";
		TypedQuery<BadgeActions> query = entityManager
				.createQuery(hql, BadgeActions.class)
				.setParameter("actionType", actionType)
				.setParameter("quantity", quantity);

		return query.getSingleResult();
	}

	@Override
	public BadgeTask getBadgeForTask(long taskId) {
		String hql = "select t from BadgeTask t where t.task.id = :taskId";
		TypedQuery<BadgeTask> query = entityManager.createQuery(hql,
				BadgeTask.class).setParameter("taskId", taskId);

		return query.getSingleResult();
	}

	@Override
	public Badge createBadgeTask(Task task, String title, String description) {
		EntityBuilderManager.setEntityManager(entityManager);
		task = entityManager.merge(task);
		return new BadgeTaskBuilder().setTask(task).setTitle(title)
				.setDescription(description).build();
	}

	@Override
	public Badge createBadgeAction(ActionType actionType, int quantity,
			String title, String description) {
		EntityBuilderManager.setEntityManager(entityManager);
		return new BadgeActionBuilder().setActionType(actionType)
				.setQuantity(quantity).setTitle(title)
				.setDescription(description).build();
	}

	@Override
	public boolean deleteBadge(long id) {
		Badge badge = findById(id);
		try {
			if (badge != null) {
				entityManager.remove(badge);
				return true;
			} else {
				logger.warn("Unable to find badge {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public List<BadgeActions> getBadgeForActionTypeAndMaxQuantity(
			ActionType actionType, int quantity) {
		String hql = "select t from BadgeActions t where t.actionType = :actionType and t.quantity <= :quantity";
		TypedQuery<BadgeActions> query = entityManager
				.createQuery(hql, BadgeActions.class)
				.setParameter("actionType", actionType)
				.setParameter("quantity", quantity);

		return query.getResultList();
	}

}
