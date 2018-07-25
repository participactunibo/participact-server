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
@Repository("groupsTaskRepository")
public class JpaGroupsTaskRepository implements GroupsTaskRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaGroupsTaskRepository.class);

	@Override
	public GroupsTask findById(long id) {
		return entityManager.find(GroupsTask.class, id);
	}

	@Override
	public GroupsTask save(GroupsTask group) {
		if (group.getDataId() != 0) {
			logger.trace("Merging {}", group.toString());
			return entityManager.merge(group);
		} else {
			logger.trace("Persisting {}", group.toString());
			entityManager.persist(group);
			return group;
		}
	}

	@Override
	public List<GroupsTask> getAllGroupsTasks() {
		String hql = "select d from GroupsTask d";

		TypedQuery<GroupsTask> query = entityManager.createQuery(hql,
				GroupsTask.class);
		List<GroupsTask> groups = query.getResultList();
		return groups;
	}

	@Override
	public boolean deleteGroups(long id) {
		GroupsTask group = findById(id);
		try {
			if (group != null) {
				Object managed = entityManager.merge(group);
				entityManager.remove(managed);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<GroupsTask> getGroupsTaskByUserId(long id) {
		String hql = "select d from GroupsTask d where d.componentOfGroupId=:id";
		TypedQuery<GroupsTask> query = entityManager.createQuery(hql,
				GroupsTask.class).setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public boolean deleteAllGroups() {
		try {
			ArrayList<Long> toDelete = new ArrayList<Long>();
			List<GroupsTask> groups = getAllGroupsTasks();

			for (GroupsTask g : groups)
				toDelete.add(g.getDataId());

			for (long l : toDelete) {
				deleteGroups(l);
			}
		} catch (Exception e) {
			return false;
		}
		return true;

	}

	@Override
	public List<GroupsTask> getGroupsTaskByGroupId(long id) {
		String hql = "select d from GroupsTask d where d.groupId=:id";
		TypedQuery<GroupsTask> query = entityManager.createQuery(hql,
				GroupsTask.class).setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public boolean isUserPresentInGroup(long id) {
		String hql = "select d from GroupsTask d where d.componentOfGroupId=:id";
		TypedQuery<GroupsTask> query = entityManager.createQuery(hql,
				GroupsTask.class).setParameter("id", id);
		return query.getResultList().size() > 0;
	}
}
