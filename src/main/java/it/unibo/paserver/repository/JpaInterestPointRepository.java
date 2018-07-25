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

import it.unibo.paserver.domain.InterestPoint;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("InterestPointRepository")
@Transactional
public class JpaInterestPointRepository implements InterestPointRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	private static final Logger logger = LoggerFactory
			.getLogger(JpaInterestPointRepository.class);

	@Override
	public InterestPoint findById(Long id) {
		return entityManager.find(InterestPoint.class, id);
	}

	@Override
	public InterestPoint findByDescription(String description) {
		try {
			String hql= "select ip from InterestPoint ip where ip.description= :description";
			
			TypedQuery<InterestPoint> query = entityManager.createQuery(hql, InterestPoint.class).setParameter("description", description);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public InterestPoint findByLat(Double lat) {
		try {
			String hql= "select ip from InterestPoint ip where ip.lat= :lat";
			TypedQuery<InterestPoint> query = entityManager.createQuery(hql, InterestPoint.class).setParameter("lat", lat);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public InterestPoint findByLon(Double lon) {
		try {
			String hql= "select ip from InterestPoint ip where ip.lon= :lon";
			TypedQuery<InterestPoint> query = entityManager.createQuery(hql, InterestPoint.class).setParameter("lon", lon);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean deleteInterestPoint(Long id) {
		InterestPoint interestPoint = findById(id);
		try {
			if(interestPoint != null){
				entityManager.remove(interestPoint);
				logger.info("Delete InterestPoint {}", id);
				return true;
			}else {
				logger.warn("Unable to find ad Interest Point {}", id);
			}
		} catch (Exception e) {
			logger.warn("Excepiont: {}", e);
		}
		return false;
	}

	@Override
	@Transactional
	public InterestPoint save(InterestPoint interestPoint) {
		if(interestPoint.getId() != null){
			logger.info("Merging interest Point {}", interestPoint.toString());
			return entityManager.merge(interestPoint);
			
		}else{
			entityManager.persist(interestPoint);
			logger.info("Persisting Interest Point {}", interestPoint.toString());
			return interestPoint;
		}
	}

	@Override
	public List<? extends InterestPoint> getInterestPoint() {
		String hql = "select ip from InterestPoint ip";
		TypedQuery<InterestPoint> query = entityManager.createQuery(hql, InterestPoint.class);
		return query.getResultList();
	}

	@Override
	public InterestPoint findByLatLon(Double lat, Double lon) {
		try {
			String hql= "select ip from InterestPoint ip where ip.lat= :lat and ip.lon= :lon";
			TypedQuery<InterestPoint> query = entityManager.createQuery(hql, InterestPoint.class).setParameter("lat", lat).setParameter("lon", lon);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
