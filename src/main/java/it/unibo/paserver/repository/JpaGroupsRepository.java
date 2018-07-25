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

import it.unibo.paserver.domain.DataGroups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("groupsRepository")
public class JpaGroupsRepository implements GroupsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaGroupsRepository.class);

	@Override
	public DataGroups findById(long id) {
		return entityManager.find(DataGroups.class, id);
	}

	@Override
	public DataGroups save(DataGroups group) {
		return entityManager.merge(group);
	}

	@Override
	public List<DataGroups> getGroupsByUserId(long id) {
		String hql = "select d from DataGroups d where d.userid=:id";
		TypedQuery<DataGroups> query = entityManager.createQuery(hql,
				DataGroups.class).setParameter("id", id);
		return query.getResultList();
	}

	@Override
	public List<DataGroups> getAllGroups() {
		String hql = "select d from DataGroups d";

		TypedQuery<DataGroups> query = entityManager.createQuery(hql,
				DataGroups.class);
		List<DataGroups> groups = query.getResultList();
		return groups;
	}

	@Override
	public boolean deleteGroups(long id) {

		DataGroups group = findById(id);
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
	public boolean deleteAllGroups() {
		return false;
	}

	@Override
	public List<Long> getAllId() {
		ArrayList<Long> result = new ArrayList<Long>();
		List<DataGroups> groups = getAllGroups();

		for (DataGroups g : groups)
			result.add(g.getData_id());

		return result;
	}

	@Override
	public long getNextId() {
		String hql = "select count(*) from DataGroups";

		Query q = entityManager.createQuery(hql);

		long resultQuery = (Long) q.getSingleResult();

		return resultQuery + 1;
	}

	@Override
	public List<Long> getAllGroupsId() {
		ArrayList<Long> result = new ArrayList<Long>();
		List<DataGroups> groups = getAllGroups();

		for (DataGroups g : groups) {
			if (!result.contains(g.getUser_id()))
				result.add(g.getUser_id());
		}

		return result;
	}
}
