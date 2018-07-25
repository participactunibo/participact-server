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

import it.unibo.paserver.domain.ClientSWVersion;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("clientSWVersionRepository")
public class JpaClientSWVersionRepository implements ClientSWVersionRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaClientSWVersionRepository.class);

	@Override
	public ClientSWVersion findById(long id) {
		return entityManager.find(ClientSWVersion.class, id);
	}

	@Override
	public ClientSWVersion save(ClientSWVersion clientSWVersion) {
		if (clientSWVersion.getId() != null) {
			logger.trace("Merging clientSWVersion {}",
					clientSWVersion.toString());
			return entityManager.merge(clientSWVersion);
		} else {
			logger.trace("Persisting clientSWVersion {}",
					clientSWVersion.toString());
			entityManager.persist(clientSWVersion);
			return clientSWVersion;
		}
	}

	@Override
	public List<ClientSWVersion> getClientSWVersions() {
		String hql = "from ClientSWVersion";
		TypedQuery<ClientSWVersion> query = entityManager.createQuery(hql,
				ClientSWVersion.class);
		return query.getResultList();
	}

	@Override
	public boolean deleteClientSWVersion(long id) {
		ClientSWVersion clientSWVersion = findById(id);
		try {
			if (clientSWVersion != null) {
				entityManager.remove(clientSWVersion);
				return true;
			} else {
				logger.warn("Unable to find ClientSWVersion {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public ClientSWVersion getLatestVersion() {
		String hql = "from ClientSWVersion csv order by csv.creationDate desc";
		TypedQuery<ClientSWVersion> query = entityManager.createQuery(hql,
				ClientSWVersion.class);
		query.setMaxResults(1);
		List<ClientSWVersion> result = query.getResultList();
		if (result.size() < 1) {
			return null;
		} else {
			return result.get(0);
		}
	}

}
