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

import it.unibo.paserver.domain.GeobadgeCollected;
import it.unibo.paserver.domain.InterestPoint;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("GeobadgeCollectedRepository")
@Transactional
public class JpaGeobadgeCollectedRepository implements GeobadgeCollectedRepository{
	
	private static final Logger logger = LoggerFactory
			.getLogger(JpaGeobadgeCollectedRepository.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public GeobadgeCollected findById(Long id) {
		return entityManager.find(GeobadgeCollected.class, id);
	}

	@Override
	public GeobadgeCollected findByDescription(String description) {
		try {
			String hql= "select ip from GeobadgeCollected ip where ip.description= :description";
			
			TypedQuery<GeobadgeCollected> query = entityManager.createQuery(hql, GeobadgeCollected.class).setParameter("description", description);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteGeobadge(Long id) {
		GeobadgeCollected geobadgeCollected = findById(id);
		try {
			if(geobadgeCollected != null){
				entityManager.remove(geobadgeCollected);
				logger.info("Delete GeobadgeCollected {}", id);
				return true;
			}else {
				logger.warn("Unable to find ad GeobadgeCollected {}", id);
			}
		} catch (Exception e) {
			logger.warn("Excepiont: {}", e);
		}
		return false;
	}

	@Override
	@Transactional
	public GeobadgeCollected save(GeobadgeCollected geobadgeCollected) {
		if(geobadgeCollected.getId() != null){
			logger.info("Merging GeobadgeCollected {}", geobadgeCollected.toString());
			return entityManager.merge(geobadgeCollected);
			
		}else{
			entityManager.persist(geobadgeCollected);
			logger.info("Persisting Interest Point {}", geobadgeCollected.toString());
			return geobadgeCollected;
		}
	}

	@Override
	public List<? extends GeobadgeCollected> getGeobadgeCollected() {
		String hql = "select ip from GeobadgeCollected ip";
		TypedQuery<GeobadgeCollected> query = entityManager.createQuery(hql, GeobadgeCollected.class);
		return query.getResultList();
	}

	@Override
	public List<GeobadgeCollected> getGeobadgeCollectedByIdUser(
			Long iduser) {
		try {
			String hql= "select ip from GeobadgeCollected ip where ip.idUser= :iduser";
			
			TypedQuery<GeobadgeCollected> query = entityManager.createQuery(hql, GeobadgeCollected.class).setParameter("iduser", iduser);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	

}
