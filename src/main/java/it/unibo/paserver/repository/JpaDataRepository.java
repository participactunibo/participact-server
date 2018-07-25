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

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.support.DataPhotoComparator;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("dataRepository")
public class JpaDataRepository implements DataRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaDataRepository.class);

	@Override
	public Data findById(long id) {
		return entityManager.find(Data.class, id);
	}

	@Override
	public <T extends Data> T save(T data) {
		if (data.getId() != null) {
			logger.trace("Merging data {}", data.toString());
			return entityManager.merge(data);
		} else {
			logger.trace("Persisting data {}", data.toString());
			entityManager.persist(data);
			return data;
		}
	}

	@Override
	public Long getDataCount() {
		String hql = "select count(id) from Data";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public Long getDataCountByUser(long userId) {
		String hql = "select count(d.id) from TaskResult t join t.data d where t.taskReport.user.id = :userId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("userId", userId);
		return query.getSingleResult();
	}

	@Override
	public Long getDataCountByTask(long taskId) {
		String hql = "select count(d.id) from TaskResult t join t.data d where t.taskReport.task.id = :taskId";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class)
				.setParameter("taskId", taskId);
		return query.getSingleResult();
	}

	@Override
	public List<Data> getData() {
		String hql = "from Data";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getData(Class<T> clazz) {
		String hql = String.format("from %s", clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByUser(long userId) {
		String hql = "select r.taskResult.data from TaskReport r where r.user.id = :userId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class)
				.setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByUser(long userId, Class<T> clazz) {
		String hql = String.format("from %s d where d.user.id = :userId)",
				clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz)
				.setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByTask(long taskId) {
		String hql = "select t.taskResult.data from TaskReport t where t.task.id = :taskId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByTask(long taskId, Class<T> clazz) {
		String hql = String
				.format("from %s d where d.id in (select distinct r.id from TaskResult t join t.data r where t.taskReport.task.id = :taskId)",
						clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByUserAndTask(long userId, long taskId) {
		String hql = "select t.taskResult.data from TaskReport t where t.task.id = :taskId and t.user.id= :userId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class)
				.setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByUserAndTask(long userId,
			long taskId, Class<T> clazz) {
		String hql = String
				.format("from %s d where d.id in(select distinct r.id from TaskResult t join t.data r where t.taskReport.task.id = :taskId and t.taskReport.user.id = :userId)",
						clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz)
				.setParameter("taskId", taskId).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<Data> getDataByTaskReport(long taskReportId) {
		String hql = "select t.taskResult.data from TaskReport t where t.id = :taskReportId";
		TypedQuery<Data> query = entityManager.createQuery(hql, Data.class)
				.setParameter("taskReportId", taskReportId);
		return query.getResultList();
	}

	@Override
	public <T extends Data> List<T> getDataByTaskReport(long taskReportId,
			Class<T> clazz) {
		String hql = String
				.format("from %s d where d.id in (select distinct r.id from TaskResult t join t.data r where t.id = :taskReportId)",
						clazz.getSimpleName());
		TypedQuery<T> query = entityManager.createQuery(hql, clazz)
				.setParameter("taskReportId", taskReportId);
		return query.getResultList();
	}

	@Override
	public boolean deleteData(long id) {
		Data data = findById(id);
		try {
			if (data != null) {
				entityManager.remove(data);
				return true;
			} else {
				logger.warn("Unable to find data {}", id);
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
	public <T extends Data> T merge(T data) {
		return entityManager.merge(data);
	}

	@Override
	public BinaryDocument getPhotoContent(Long id) {
		try {
			DataPhoto dataPhoto = (DataPhoto) findById(id);
			BinaryDocument bd = dataPhoto.getFile();
			Hibernate.initialize(bd);
			return bd;
		} catch (ClassCastException e) {
			logger.error("Unable to get DataPhoto {}", id);
			throw new NoResultException();
		}
	}

	@Override
	public List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId) {
		String hql = "from DataPhoto d where d.actionId = :actionId and d.taskId = :taskId";
		TypedQuery<DataPhoto> query = entityManager
				.createQuery(hql, DataPhoto.class)
				.setParameter("taskId", taskId)
				.setParameter("actionId", actionId);
		List<DataPhoto> result = query.getResultList();
		Collections.sort(result, new DataPhotoComparator());
		return result;
	}

	

}
