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

import it.unibo.paserver.domain.PipelineTaskProgress;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("pipelineTaskProgressRepository")
public class JpaPipelineTaskProgress implements PipelineTaskProgressRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaPipelineTaskProgress.class);

	@Override
	public PipelineTaskProgress findById(long id) {
		return entityManager.find(PipelineTaskProgress.class, id);
	}

	@Override
	public PipelineTaskProgress save(PipelineTaskProgress pipelineTaskProgress) {
		if (pipelineTaskProgress.getData_id() != 0) {
			logger.trace("Merging {}", pipelineTaskProgress.toString());
			return entityManager.merge(pipelineTaskProgress);
		} else {
			logger.trace("Persisting {}", pipelineTaskProgress.toString());
			entityManager.persist(pipelineTaskProgress);
			return pipelineTaskProgress;
		}
	}

	@Override
	public List<PipelineTaskProgress> getAll() {
		String hql = "select d from PipelineTaskProgress d";
		TypedQuery<PipelineTaskProgress> query = entityManager.createQuery(hql,
				PipelineTaskProgress.class);
		return query.getResultList();
	}

	@Override
	public boolean delete(long id) {
		PipelineTaskProgress group = findById(id);
		try {
			if (group != null) {
				Object managed = entityManager.merge(group);
				entityManager.remove(managed);
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteAll() {
		boolean result = true;
		for (Long l : getAllId()) {
			result = delete(l);
			if (!result)
				return result;
		}
		return result;
	}

	@Override
	public List<Long> getAllId() {
		ArrayList<Long> result = new ArrayList<Long>();
		List<PipelineTaskProgress> list = getAll();

		for (PipelineTaskProgress g : list)
			result.add(g.getData_id());

		return result;
	}

	@Override
	public List<PipelineTaskProgress> findByTaskStepId(long id) {
		String hql = "select d from PipelineTaskProgress d where d.task_step_id=:id";
		TypedQuery<PipelineTaskProgress> query = entityManager.createQuery(hql,
				PipelineTaskProgress.class).setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public int getMaxStepByPipelineId(long id, long groupId) {
		String hql = "select max(d.task_step) from PipelineTaskProgress d where d.task_pipeline_id=:id and d.group_id=:groupId";
		Query query = entityManager.createQuery(hql).setParameter("id", id).setParameter("groupId", groupId);
		int result = (Integer) query.getSingleResult();

		return result;
	}

	@Override
	public List<String> getAllResponseByActionPipelineId(long id) {
		// String hql =
		// "select a.allresponse from actionpipelinetask_allresponse a where a.actionpipelinetask_action_id=:id";
		// Query query = entityManager.createQuery(hql).setParameter("id", id);
		// List<String> result = (List<String>) query.getResultList();
		//
		// return result;
		return null;
	}

	@Override
	public Set<Long> getAllPipelineTaskId() {
		HashSet<Long> result = new HashSet<Long>();

		List<PipelineTaskProgress> list = getAll();

		for (PipelineTaskProgress p : list)
			result.add(p.getTask_pipeline_id());

		return result;
	}
	
	@Override
	public Set<Long> getAllStepTaskId() {
		HashSet<Long> result = new HashSet<Long>();

		List<PipelineTaskProgress> list = getAll();

		for (PipelineTaskProgress p : list)
			result.add(p.getTask_step_id());

		return result;
	}
}
