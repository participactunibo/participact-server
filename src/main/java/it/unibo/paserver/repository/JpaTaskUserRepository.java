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

import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
@Repository
public class JpaTaskUserRepository implements TaskUserRepository {


	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaTaskUserRepository.class);
	
	
	@Override
	public TaskUser findById(long id) {
		return entityManager.find(TaskUser.class, id);
	}

	@Override
	public TaskUser save(TaskUser taskUser) {
		if (taskUser.getId() != null) {
			logger.trace("Merging taskUser {}", taskUser.toString());
			TaskUser t = entityManager.merge(taskUser);
			entityManager.flush();
			return t;
		} else {
			logger.trace("Persisting taskUser {}", taskUser.toString());
			entityManager.persist(taskUser);
			entityManager.flush();

			return taskUser;
		}
	}

	@Override
	public Long getTaskUsersCount() {
		String hql = "select count(id) from TaskUser";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	

	@Override
	public List<TaskUser> getTaskUsersByOwner(long ownerId,
			TaskValutation currentTaskValutation) {
		String hql = "select t from TaskUser t  where t.owner.id = :ownerId and t.valutation = :currentTaskValutation";

		List<TaskUser> result = entityManager.createQuery(hql, TaskUser.class)
				.setParameter("ownerId", ownerId)
				.setParameter("currentTaskValutation", currentTaskValutation)
				.getResultList();
		return result;
	}

	@Override
	public List<TaskUser> getTaskUser() {
		String hql = "from TaskUser";
		List<TaskUser> result = entityManager.createQuery(hql).getResultList();
		return result;
	}

	@Override
	public List<TaskUser> getTaskUserByOwner(long ownerId) {
		String hql = "select t from TaskUser t where t.owner.id = :ownerId";
		List<TaskUser> result = entityManager.createQuery(hql, TaskUser.class)
				.setParameter("ownerId", ownerId).getResultList();
		return result;
	}

	@Override
	public boolean deleteTask(long id) {
		TaskUser task = findById(id);
		try {
			if (task != null) {
				entityManager.remove(task);
				return true;
			} else {
				logger.warn("Unable to find taskUser {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public TaskUser getTaskUserByTaskId(long id) {
		String hql = "select t from TaskUser t where t.task.id = :id";
		TypedQuery<TaskUser> query = entityManager.createQuery(hql, TaskUser.class)
				.setParameter("id", id);
		return query.getSingleResult();
	}
}
