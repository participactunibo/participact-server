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

import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.PointsStrategyForTaskBuilder;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * Implementation of PointsStrategyForTaskRepository using JPA.
 * 
 *
 * @see PointsStrategyForTaskRepository
 *
 */
@Repository("pointsStrategyForTaskRepository")
public class JpaPointsStrategyForTaskRepository implements
		PointsStrategyForTaskRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaPointsStrategyForTaskRepository.class);

	@Override
	public PointsStrategyForTask save(
			PointsStrategyForTask pointsStrategyForTask) {
		if (pointsStrategyForTask.getId() != null) {
			logger.trace("Merging pointsStrategyForTask {}",
					pointsStrategyForTask.toString());
			return entityManager.merge(pointsStrategyForTask);
		} else {
			logger.trace("Persisting pointsStrategyForTask {}",
					pointsStrategyForTask.toString());
			entityManager.persist(pointsStrategyForTask);
			return pointsStrategyForTask;
		}
	}

	@Override
	public PointsStrategyForTask create(Task task, Integer strategyId) {
		EntityBuilderManager.setEntityManager(entityManager);
		task = entityManager.merge(task);
		return new PointsStrategyForTaskBuilder().setTask(task)
				.setStrategyId(strategyId).build();
	}

	@Override
	public PointsStrategyForTask getPointsStrategyForTaskByTask(long taskId) {
		String hql = "from PointsStrategyForTask t where t.task.id = :taskId";
		TypedQuery<PointsStrategyForTask> query = entityManager.createQuery(
				hql, PointsStrategyForTask.class)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<PointsStrategyForTask> getPointsStrategyForTasks() {
		String hql = "select c from PointsStrategyForTask c";
		TypedQuery<PointsStrategyForTask> query = entityManager.createQuery(
				hql, PointsStrategyForTask.class);
		return query.getResultList();
	}

	@Override
	public Long getPointsStrategyForTaskCount() {
		String hql = "select count(id) from PointsStrategyForTask c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

}
