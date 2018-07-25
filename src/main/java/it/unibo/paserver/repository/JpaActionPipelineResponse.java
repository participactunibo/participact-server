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

import it.unibo.paserver.domain.ActionPipelineResponse;
import it.unibo.paserver.domain.GroupsContacts;
import it.unibo.paserver.domain.GroupsTask;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("actionPipelineResponse")
public class JpaActionPipelineResponse implements
		ActionPipelineResponseRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaGroupsRepository.class);

	@Override
	public ActionPipelineResponse findById(long id) {
		return entityManager.find(ActionPipelineResponse.class, id);
	}

	@Override
	public List<ActionPipelineResponse> getAll() {
		String hql = "select d from ActionPipelineResponse d";

		TypedQuery<ActionPipelineResponse> query = entityManager.createQuery(
				hql, ActionPipelineResponse.class);
		List<ActionPipelineResponse> result = query.getResultList();
		return result;
	}

	@Override
	public ActionPipelineResponse save(
			ActionPipelineResponse actionPipelineResponse) {
		return entityManager.merge(actionPipelineResponse);
	}

	@Override
	public boolean delete(long id) {
		ActionPipelineResponse group = findById(id);
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
		try {
			for (long l : getAllId()) {
				delete(l);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Long> getAllId() {
		ArrayList<Long> result = new ArrayList<Long>();
		List<ActionPipelineResponse> act = getAll();

		for (ActionPipelineResponse g : act)
			result.add(g.getData_id());

		return result;
	}

	@Override
	public List<ActionPipelineResponse> getResponseByTaskId(long id) {
		String hql = "select d from ActionPipelineResponse d where d.task_id=:id";
		TypedQuery<ActionPipelineResponse> query = entityManager.createQuery(hql,
				ActionPipelineResponse.class).setParameter("id", id);
		return query.getResultList();
	}

}
