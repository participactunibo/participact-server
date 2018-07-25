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
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("taskRepository")
public class JpaTaskRepository implements TaskRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaTaskRepository.class);

	@Override
	public Task findById(long id) {
		return entityManager.find(Task.class, id);
	}

	@Override
	public Task save(Task task) {
		if (task.getId() != null) {
			logger.trace("Merging task {}", task.toString());
			Task t = entityManager.merge(task);
			entityManager.flush();
			return t;
		} else {
			logger.trace("Persisting task {}", task.toString());
			entityManager.persist(task);
			return task;
		}
	}

	@Override
	public Long getTasksCount() {
		String hql = "select count(id) from Task";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getTasksCount(long userId) {
		String hql = "select count(t.task.id) from TaskReport t where t.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public List<Task> getTasks() {
		String hql = "from Task";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);
		return query.getResultList();
	}

	@Override
	public List<Task> getTasksByUser(long userId) {
		String hql = "select t.task from TaskReport t where t.user.id = :userId";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", userId);
		return query.getResultList();
	}

	

	@Override
	public List<Task> getTasksByAction(long actionId) {
		String hql = "select t from Task t join t.actions a where a.id = :actionId";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("actionId", actionId);
		return query.getResultList();
	}

	@Override
	public boolean deleteTask(long id) {
		Task task = findById(id);
		try {
			if (task != null) {
				entityManager.remove(task);
				return true;
			} else {
				logger.warn("Unable to find task {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public List<Task> getAvailableTasksByUser(long userId,
			TaskState taskCurrentState, DateTime dateTime) {
		String hql = "select t.task from TaskReport t where t.user.id = :userId and t.currentState = :taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", userId)
				.setParameter("taskstate", taskCurrentState)
				.setParameter("startTime", dateTime)
				.setParameter("endTime", dateTime);
		List<Task> result = query.getResultList();
		logger.debug("Search for user {}, target time: {}: {} hits", userId,
				dateTime, result.size());
		return result;
	}

	@Override
	public List<Task> getTasksByOwner(long ownerId) {
		String hql = "select t.task from TaskUser t where t.owner.id = :ownerId";
		List<Task> result = entityManager.createQuery(hql, Task.class)
				.setParameter("ownerId", ownerId).getResultList();
		return result;
	}

	

	@Override
	public Task findByIdAndOwner(Long taskId, Long ownerId) {
		String hql = "select t.task from TaskUser t where t.task.id = :taskId and t.owner.id = :ownerId";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("ownerId", ownerId)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<Task> getTasksByOwner(long ownerId,
			TaskValutation currentTaskValutation) {
		String hql = "select t.task from TaskUser t  where t.owner.id = :ownerId and t.valutation = :currentTaskValutation";

		List<Task> result = entityManager.createQuery(hql, Task.class)
				.setParameter("ownerId", ownerId)
				.setParameter("currentTaskValutation", currentTaskValutation)
				.getResultList();
		return result;
	}

	@Override
	public List<Task> getTaskByAdmin() {
		String hql = "select t from Task t where t.id not in (select tu.task.id from TaskUser tu) ";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class);				
		return query.getResultList();
	}

	@Override
	public List<Task> getAvailableAdminTasksByUser(Long id,	TaskState taskState, DateTime dateTime) {
		String hql = "select t.task from TaskReport t where t.task.id not in (select tu.task.id from TaskUser tu) and t.user.id = :userId and t.currentState = :taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", id)
				.setParameter("taskstate", taskState)
				.setParameter("startTime", dateTime)
				.setParameter("endTime", dateTime);
		List<Task> result = query.getResultList();
		logger.debug("Search for user {}, target time: {}: {} hits", id,
				dateTime, result.size());
		return result;
	}

	@Override
	public List<Task> getAvailableUserTasksByUser(Long id, TaskState taskState,	DateTime dateTime) {
		String hql = "select t.task from TaskReport t where t.task.id in (select tu.task.id from TaskUser tu) and t.user.id = :userId and t.currentState = :taskstate and t.task.start <= :startTime and t.task.deadline >= :endTime";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", id)
				.setParameter("taskstate", taskState)
				.setParameter("startTime", dateTime)
				.setParameter("endTime", dateTime);
		List<Task> result = query.getResultList();
		logger.debug("Search for user {}, target time: {}: {} hits", id,
				dateTime, result.size());
		return result;
	}
	
	@Override
	public List<Task> getTasksByUser(long userId, TaskState taskCurrentState) {
		String hql = "select t.task from TaskReport t where t.user.id = :userId and t.currentState = :taskstate";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", userId)
				.setParameter("taskstate", taskCurrentState);
		return query.getResultList();
	}

	@Override
	public List<Task> getAdminTasksByUser(Long userId, TaskState taskState) {
		String hql = "select t.task from TaskReport t where t.task.id not in (select tu.task.id from TaskUser tu) and t.user.id = :userId and t.currentState = :taskstate";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", userId)
				.setParameter("taskstate", taskState);
		List<Task> result = query.getResultList();
		logger.debug("Search for user {}, target time: {}: {} hits", userId, result.size());
		return result;
	}

	@Override
	public List<Task> getUserTasksByUser(Long userId, TaskState taskState) {
		String hql = "select t.task from TaskReport t where t.task.id in (select tu.task.id from TaskUser tu) and t.user.id = :userId and t.currentState = :taskstate";
		TypedQuery<Task> query = entityManager.createQuery(hql, Task.class)
				.setParameter("userId", userId)
				.setParameter("taskstate", taskState);
		List<Task> result = query.getResultList();
		logger.debug("Search for user {}, target time: {}: {} hits",userId, result.size());
		return result;
	}

	@Override
	public List<Task> getTasksByDate(DateTime start, DateTime end) {
		String hql = "select t from Task t where t.start >= :start and t.start < :end";
		List<Task> result = entityManager.createQuery(hql, Task.class)
				.setParameter("start",start).setParameter("end", end).getResultList();				
		return result;	
	}

	@Override
	public List<Task> getTasksUserByDate(DateTime start, DateTime end) {
		String hql = "select t.task from TaskUser t where t.task.start >= :start and t.task.start < :end";
		List<Task> result = entityManager.createQuery(hql, Task.class)
				.setParameter("start",start).setParameter("end", end).getResultList();
		return result;
	}

	@Override
	public List<Task> getTaskByAdmin(DateTime start, DateTime end) {
		String hql = "select t from Task t where t.id not in (select tu.task.id from TaskUser tu) and t.start >= :start and t.start < :end";
		List<Task> result = entityManager.createQuery(hql, Task.class)
				.setParameter("start",start).setParameter("end", end).getResultList();				
		return result;		
	}

	

	

	

}
