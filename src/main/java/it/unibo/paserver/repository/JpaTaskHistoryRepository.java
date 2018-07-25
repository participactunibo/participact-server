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

import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("taskHistoryRepository")
public class JpaTaskHistoryRepository implements TaskHistoryRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private TaskReportRepository taskReportRepository;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaTaskHistoryRepository.class);

	@Override
	public TaskHistory findById(long id) {
		return entityManager.find(TaskHistory.class, id);
	}

	@Override
	public TaskHistory save(TaskHistory taskHistory) {
		if (taskHistory.getId() != null) {
			logger.trace("Merging taskHistory {}", taskHistory.toString());
			TaskReport taskreport = taskHistory.getTaskReport();
			taskreport.setCurrentState(taskHistory.getState());
			taskReportRepository.save(taskreport);
			return entityManager.merge(taskHistory);
		} else {
			logger.trace("Persisting taskHistory {}", taskHistory.toString());
			TaskReport taskreport = taskHistory.getTaskReport();
			taskreport.setCurrentState(taskHistory.getState());
			taskReportRepository.save(taskreport);
			entityManager.persist(taskHistory);
			return taskHistory;
		}
	}

	@Override
	public Long getTaskHistoriesCount() {
		String hql = "select count(id) from TaskHistory";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskHistoriesCount(long taskReportId) {
		String hql = "select count(id) from TaskHistory t where t.taskReport.id = :taskReportId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("taskReportId", taskReportId);
		return query.getSingleResult();
	}

	@Override
	public Long getTaskHistoriesCount(long userId, long taskId) {
		String hql = "select count(id) from TaskHistory t where t.taskReport.user.id = :userId and t.taskReport.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("userId", userId).setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<TaskHistory> getTaskHistories() {
		String hql = "from TaskHistory";
		TypedQuery<TaskHistory> query = entityManager.createQuery(hql,
				TaskHistory.class);
		return query.getResultList();
	}

	@Override
	public List<TaskHistory> getTaskHistories(long taskReportId) {
		String hql = "select t.history from TaskReport t where t.id = :taskReportId";
		TypedQuery<TaskHistory> query = entityManager.createQuery(hql,
				TaskHistory.class).setParameter("taskReportId", taskReportId);
		return query.getResultList();
	}

	@Override
	public List<TaskHistory> getTaskHistories(long userId, long taskId) {
		String hql = "select t.history from TaskReport t where t.task.id = :taskId and t.user.id = :userId";
		TypedQuery<TaskHistory> query = entityManager
				.createQuery(hql, TaskHistory.class)
				.setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public boolean deleteTaskHistory(long id) {
		TaskHistory taskHistory = findById(id);
		try {
			if (taskHistory != null) {
				entityManager.remove(taskHistory);
				return true;
			} else {
				logger.warn("Unable to find taskHistory {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

}
