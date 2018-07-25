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

import it.unibo.paserver.domain.Action;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("actionRepository")
public class JpaActionRepository implements ActionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaActionRepository.class);

	@Override
	public Action findById(long id) {
		return entityManager.find(Action.class, id);
	}

	@Override
	public Action findByName(String name) {
		String hql = "from Action a where a.name = :actionName";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class)
				.setParameter("actionName", name);
		return query.getSingleResult();
	}

	@Override
	public Action save(Action action) {
		if (action.getId() != null) {
			logger.trace("Merging action {}", action.toString());
			return entityManager.merge(action);
		} else {
			logger.trace("Persisting action {}", action.toString());
			entityManager.persist(action);
			return action;
		}
	}

	@Override
	public Long getActionsCount() {
		String hql = "select count(id) from Action";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getActionsCount(long taskId) {
		String hql = "select count(a.id) from Task t join t.actions a where t.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<Action> getActions() {
		String hql = "from Action";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class);
		return query.getResultList();
	}

	@Override
	public List<Action> getActions(long taskId) {
		String hql = "select t.actions from Task t where t.id = :taskId";
		TypedQuery<Action> query = entityManager.createQuery(hql, Action.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public boolean deleteAction(long id) {
		Action action = findById(id);
		try {
			if (action != null) {
				entityManager.remove(action);
				return true;
			} else {
				logger.warn("Unable to find action {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

}
