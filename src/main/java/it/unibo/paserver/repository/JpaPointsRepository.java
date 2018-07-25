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

import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.PointsBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of PointsRepository using JPA.
 * 
 *
 * @see PointsRepository
 *
 */
@Repository("pointsRepository")
public class JpaPointsRepository implements PointsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaPointsRepository.class);

	@Override
	public Points save(Points points) {
		if (points.getId() != null) {
			logger.trace("Merging points {}", points.toString());
			return entityManager.merge(points);
		} else {
			logger.trace("Persisting points {}", points.toString());
			entityManager.persist(points);
			return points;
		}
	}

	@Override
	public List<Points> getPointsByUserAndDates(long userId, DateTime from,
			DateTime to) {

		// from = from.minusDays(1);

		String hql = "from Points t where t.user.id = :userId and t.date >= :from and t.date <= :to";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class)
				.setParameter("userId", userId).setParameter("from", from)
				.setParameter("to", to);
		return query.getResultList();
	}

	@Override
	public List<Points> getPoints() {
		String hql = "select c from Points c";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class);
		return query.getResultList();
	}

	@Override
	public Long getPointsCount() {
		String hql = "select count(id) from Points c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Points create(User user, Task task, DateTime dateTime, Integer value, PointsType type) {
		EntityBuilderManager.setEntityManager(entityManager);
		user = entityManager.merge(user);
		task = entityManager.merge(task);
		return new PointsBuilder().setTask(task).setUser(user)
				.setDate(dateTime).setValue(value).setType(type).build();
	}

	@Override
	public List<Points> getPointsByUserAndTask(long userId, long taskId) {
		String hql = "from Points t where t.user.id = :userId and t.task.id = :taskId";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class)
				.setParameter("userId", userId).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public Points getPointsByUserAndTaskAndType(long userId, long taskId,
			PointsType type) {
		String hql = "from Points t where t.user.id = :userId and t.task.id = :taskId and t.type = :type";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class)
				.setParameter("userId", userId).setParameter("taskId", taskId).setParameter("type", type);
		return query.getSingleResult();
	}

	@Override
	public List<Points> getPointsByUserAndType(long userId, PointsType type) {
		String hql = "from Points t where t.user.id = :userId and t.type = :type";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class)
				.setParameter("userId", userId).setParameter("type", type);
		return query.getResultList();
	}

	@Override
	public List<Points> getPointsByTaskAndType(long taskId, PointsType type) {
		String hql = "from Points t where t.type = :type and t.task.id = :taskId";
		TypedQuery<Points> query = entityManager.createQuery(hql, Points.class)
				.setParameter("type", type).setParameter("taskId", taskId);
		return query.getResultList();
	}

}
