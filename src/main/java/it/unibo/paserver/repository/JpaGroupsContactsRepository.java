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

import it.unibo.paserver.domain.GroupsContacts;
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
@Repository("groupsContactsRepository")
public class JpaGroupsContactsRepository implements GroupsContactsRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaGroupsRepository.class);

	@Override
	public GroupsContacts findById(long id) {
		return entityManager.find(GroupsContacts.class, id);
	}

	@Override
	public GroupsContacts save(GroupsContacts group) {
		return entityManager.merge(group);
	}

	@Override
	public List<GroupsContacts> getAll() {
		String hql = "select d from GroupsContacts d";

		TypedQuery<GroupsContacts> query = entityManager.createQuery(hql,
				GroupsContacts.class);
		List<GroupsContacts> groups = query.getResultList();
		return groups;
	}

	@Override
	public boolean delete(long id) {
		GroupsContacts group = findById(id);
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
		List<GroupsContacts> groups = getAll();

		for (GroupsContacts g : groups)
			result.add(g.getDataId());

		return result;
	}

}
