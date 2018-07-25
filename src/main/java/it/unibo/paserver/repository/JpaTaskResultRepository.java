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
import it.unibo.paserver.domain.ActionActivityDetection;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataActivityRecognitionCompare;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("taskResultRepository")
public class JpaTaskResultRepository implements TaskResultRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaTaskResultRepository.class);

	@Override
	public TaskResult findById(long id) {
		return entityManager.find(TaskResult.class, id);
	}

	@Override
	public TaskResult findByUserAndTask(long userId, long taskId) {
		String hql = "from TaskResult t where t.taskReport.user.id = :userId and t.taskReport.task.id = :taskId";
		TypedQuery<TaskResult> query = entityManager
				.createQuery(hql, TaskResult.class)
				.setParameter("userId", userId).setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public TaskResult save(TaskResult taskResult) {
		if (taskResult.getId() != null) {
			logger.trace("Merging taskResult {}", taskResult.toString());
			return entityManager.merge(taskResult);
		} else {
			logger.trace("Persisting taskResult {}", taskResult.toString());
			entityManager.persist(taskResult);
			return taskResult;
		}
	}

	@Override
	public Long getTaskResultsCount() {
		String hql = "select count(id) from TaskResult";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskResultsCountByUser(long userId) {
		String hql = "select count(id) from TaskResult t where t.taskReport.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskResultsCountByTask(long taskId) {
		String hql = "select count(id) from TaskResult t where t.taskReport.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<TaskResult> getTaskResults() {
		String hql = "from TaskResult";
		TypedQuery<TaskResult> query = entityManager.createQuery(hql,
				TaskResult.class);
		return query.getResultList();
	}

	@Override
	public List<TaskResult> getTaskResultsByTask(long taskId) {
		String hql = "from TaskResult t where t.taskReport.task.id = :taskId";
		TypedQuery<TaskResult> query = entityManager.createQuery(hql,
				TaskResult.class).setParameter("taskId", taskId);
		return query.getResultList();

	}

	@Override
	public List<TaskResult> getTaskResultsByUser(long userId) {
		String hql = "from TaskResult t where t.taskReport.user.id = :userId";
		TypedQuery<TaskResult> query = entityManager.createQuery(hql,
				TaskResult.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public TaskResult updateTaskResult(TaskResult taskResult, boolean force) {
		Task task = taskResult.getTaskReport().getTask();
		TaskReport taskReport = taskResult.getTaskReport();
		boolean hasToUpdate = true;
		// update is we are forced to
		hasToUpdate = force;
		// update if there is no last known data update
		hasToUpdate = hasToUpdate || taskResult.getLastDataUpdate() == null;
		if (taskResult.getLastDataUpdate() != null
				&& taskReport.getExpirationDateTime() != null) {
			// update if the task is relatively recent and it has not been
			// updated in the last 30 minutes
			DateTime now = new DateTime();
			DateTime expiration = taskReport.getExpirationDateTime();
			if (expiration.isAfter(now)
					|| Days.daysBetween(expiration, now).getDays() < 60) {
				hasToUpdate = hasToUpdate
						|| (Minutes.minutesBetween(
								taskResult.getLastDataUpdate(), now)
								.getMinutes() > 30);
			}
			// update if the task is old and data has not been updated within 60
			// days from its expiration date
			if (expiration.isBefore(now)
					&& Days.daysBetween(expiration, now).getDays() >= 60) {
				hasToUpdate = hasToUpdate
						|| expiration.plusDays(60).isAfter(
								taskResult.getLastDataUpdate());
			}
		}
		// return the taskresult if we don't have to update its data
		if (!hasToUpdate) {
			logger.debug(
					"TaskResult {} last update {}, expiration {}, data not updated",
					taskResult.getId(), taskResult.getLastDataUpdate(),
					taskReport.getExpirationDateTime());
			return taskResult;
		}
		List<Data> newData = new ArrayList<Data>();
		for (Action action : task.getActions()) {
			Class<? extends Data> classData = null;
			if (action instanceof ActionSensing) {
				classData = ((ActionSensing) action).getDataClass();
			} else if (action instanceof ActionActivityDetection) {
				classData = DataActivityRecognitionCompare.class;
			}
			if (classData != null) {
				String hql = String
						.format("from %s d where d.user.id = :userId and d.sampleTimestamp >= :startTime and d.sampleTimestamp <= :endTime and d.id not in (select distinct r.id from TaskResult t join t.data r where t.id = :taskResultId)",
								classData.getSimpleName());
				TypedQuery<? extends Data> query = entityManager.createQuery(
						hql, classData);
				query.setParameter("userId", taskResult.getTaskReport()
						.getUser().getId());
				query.setParameter("startTime",
						taskReport.getAcceptedDateTime());
				query.setParameter("endTime",
						taskReport.getExpirationDateTime());
				query.setParameter("taskResultId", taskResult.getId());
				newData.addAll(query.getResultList());
			}
		}
		taskResult.getData().addAll(newData);
		taskResult.setLastDataUpdate(new DateTime());
		taskResult = save(taskResult);
		logger.debug("TaskResult {} data updated", taskResult.getId());
		return taskResult;
	}

	@Override
	public boolean deleteTaskResult(long id) {
		TaskResult taskResult = findById(id);
		try {
			if (taskResult != null) {
				entityManager.remove(taskResult);
				return true;
			} else {
				logger.warn("Unable to find taskResult {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public void flush() {
		entityManager.flush();
	}

	@Override
	public void clear() {
		entityManager.clear();
	}

	@Override
	public TaskResult addData(long taskId, long userId, Data data) {
		String hql = String
				.format("from TaskResult t where t.taskReport.task.id = :taskId and t.taskReport.user.id = :userId");
		TypedQuery<TaskResult> query = entityManager.createQuery(hql,
				TaskResult.class);
		query.setParameter("taskId", taskId);
		query.setParameter("userId", userId);
		TaskResult taskResult = query.getSingleResult();
		taskResult.getData().add(data);
		return save(taskResult);
	}

	@Override
	public TaskResult addData(long taskId, long userId,
			Collection<? extends Data> data) {
		String hql = String
				.format("from TaskResult t where t.taskReport.task.id = :taskId and t.taskReport.user.id = :userId");
		TypedQuery<TaskResult> query = entityManager.createQuery(hql,
				TaskResult.class);
		query.setParameter("taskId", taskId);
		query.setParameter("userId", userId);
		TaskResult taskResult = query.getSingleResult();
		taskResult.getData().addAll(data);
		return save(taskResult);
	}

}
