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

import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.support.Pipeline.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("taskReportRepository")
public class JpaTaskReportRepository implements TaskReportRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaTaskReportRepository.class);

	@Override
	public TaskReport findById(long id) {
		return entityManager.find(TaskReport.class, id);
	}

	@Override
	public TaskReport findByUserAndTask(long userId, long taskId) {
		String hql = "from TaskReport t where t.task.id = :taskId and t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager
				.createQuery(hql, TaskReport.class)
				.setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public TaskReport save(TaskReport taskReport) {
		if (taskReport.getId() != null) {
			logger.trace("Merging taskReport {}", taskReport.toString());
			return entityManager.merge(taskReport);
		} else {
			logger.trace("Persisting taskReport {}", taskReport.toString());
			entityManager.persist(taskReport);
			return taskReport;
		}
	}

	@Override
	public Long getTaskReportsCount() {
		String hql = "select count(id) from TaskReport";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByUser(long userId) {
		String hql = "select count(id) from TaskReport t where t.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskReportsCountByTask(long taskId) {
		String hql = "select count(id) from TaskReport t where t.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<TaskReport> getTaskReports() {
		String hql = "from TaskReport";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql,
				TaskReport.class);
		return query.getResultList();
	}

	@Override
	public List<TaskReport> getTaskReportsByUser(long userId) {
		String hql = "from TaskReport t where t.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql,
				TaskReport.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(long taskId) {
		String hql = "from TaskReport t where t.task.id = :taskId";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql,
				TaskReport.class).setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public boolean deleteTaskReport(long id) {
		TaskReport taskReport = findById(id);
		try {
			if (taskReport != null) {
				entityManager.remove(taskReport);
				return true;
			} else {
				logger.warn("Unable to find taskReport {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public List<TaskReport> getTaskReportsForData(long userId, Type dataType,
			DateTime sampleTimestamp) {
		List<TaskReport> result = new ArrayList<TaskReport>();
		String hql = "from TaskReport t where t.user.id = :userId and t.acceptedDateTime <= :sampleDateTime and t.expirationDateTime >= :sampleDateTime";
		TypedQuery<TaskReport> query = entityManager
				.createQuery(hql, TaskReport.class)
				.setParameter("userId", userId)
				.setParameter("sampleDateTime", sampleTimestamp);
		List<TaskReport> queryResult = query.getResultList();

		// filter task reports by actual acceptance and expiration datetimes
		for (TaskReport r : queryResult) {
			if (r.getTask().hasPipelineType(dataType)) {
				result.add(r);
			}
		}
		return result;
	}

	@Override
	public List<TaskReport> getExpiredTaskReportStillAvailable(DateTime now) {
		String hql = "from TaskReport t where t.task.deadline < :now and t.currentState = :available";
		TypedQuery<TaskReport> query = entityManager
				.createQuery(hql, TaskReport.class).setParameter("now", now)
				.setParameter("available", TaskState.AVAILABLE);
		List<TaskReport> queryResult = query.getResultList();
		return queryResult;
	}

	@Override
	public Set<String> getAssignedOfficialEmailByTask(long taskId) {
		String hql = "select t.user.officialEmail from TaskReport t where t.task.id = :taskId";
		TypedQuery<String> query = entityManager.createQuery(hql, String.class)
				.setParameter("taskId", taskId);
		List<String> listResult = query.getResultList();
		Set<String> result = new TreeSet<String>();
		result.addAll(listResult);
		return result;
	}

	@Override
	public TaskReport findByUserAndTaskAndOwner(Long userId, Long taskId,
			Long ownerId) {
		String hql = "select tr from TaskUser t join t.task.taskReport tr where t.owner.id = :ownerId and t.task.id = :taskId and tr.user.id = :userId";
		TypedQuery<TaskReport> query = entityManager
				.createQuery(hql, TaskReport.class)
				.setParameter("ownerId", ownerId)
				.setParameter("taskId", taskId).setParameter("userId", userId);
		TaskReport tr = query.getSingleResult();
		return tr;
	}

	@Override
	public List<TaskReport> getTaskReportsByTask(Long id, TaskState state) {
		String hql = "from TaskReport t where t.task.id = :taskId and t.currentState = :currentState";
		TypedQuery<TaskReport> query = entityManager.createQuery(hql,
				TaskReport.class).setParameter("taskId", id).setParameter("currentState", state);
		return query.getResultList();
	}

}
