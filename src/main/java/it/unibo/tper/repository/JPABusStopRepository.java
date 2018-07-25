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
package it.unibo.tper.repository;

import java.util.List;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.tper.domain.BusStop;

@Repository("busStopRepository")
public class JPABusStopRepository implements BusStopRepository{

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JPABusStopRepository.class);
	
	@Override
	public BusStop findById(Long id) {
		return entityManager.find(BusStop.class, id);
	}

	@Override
	public BusStop findByCode(Integer code) {
		try
		{
		String hql = "select bs from BusStop bs where bs.code = :code";
		TypedQuery<BusStop> query = entityManager.createQuery(hql, BusStop.class)
				.setParameter("code", code);
		return query.getSingleResult();
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public boolean deleteBusStop(Long id) {
		BusStop busStop = findById(id);
		try {
			if (busStop != null) {
				entityManager.remove(busStop);	
				logger.info("Deleted busstop {}", id);
				return true;
			} else {
				logger.warn("Unable to find busstop {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public BusStop save(BusStop busStop) {
		if (busStop.getId() != null) {
			logger.info("Merging bus {}", busStop.toString());
			return entityManager.merge(busStop);
		} else {
			logger.info("Persisting bus {}", busStop.toString());
			entityManager.persist(busStop);
			return busStop;
		}
	}

	
	

	@Override
	public Long getBusStopCount() {
		String hql = "select count(bs.id) from BusStop bs";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<? extends BusStop> getBusStops() {
		String hql = "select bs from BusStop bs";
		TypedQuery<BusStop> query = entityManager.createQuery(hql, BusStop.class);
		return query.getResultList();
	}

	@Override
	public List<? extends BusStop> getObsoleteBusStops(DateTime threshold) {
		String hql = "select bs from BusStop bs where bs.creationTime < :threshold";
		TypedQuery<BusStop> query = entityManager.createQuery(hql, BusStop.class).setParameter("threshold", threshold);
		return query.getResultList();	}

	@Override
	public List<? extends BusStop> getBusStopsByRadius(Double latitude,	Double longitude, Double radius) {
		String hql = "select res.* from ( select bs.*, ((3959* acos( cos(radians(:latitude)) * cos(radians(bs.latitude)) * cos( radians(bs.longitude) - radians(:longitude) ) + sin(radians(:latitude)) * sin(radians(bs.latitude))  )) *1000) as distance from bus_stop bs) as res where distance < :radius order by distance";
		//String hql = "SELECT bs.*, (3959* acos( cos(radians(:latitude)) * cos(radians(bs.latitude)) * cos( radians(bs.longitude) - radians(:longitude) ) + sin(radians(:latitude)) * sin(radians(bs.latitude))  )) *1000 AS distance FROM bus_stop AS bs GROUP BY bs.bus_stop_id HAVING (3959* acos( cos(radians(:latitude)) * cos(radians(bs.latitude)) * cos( radians(bs.longitude) - radians(:longitude) ) + sin(radians(:latitude)) * sin(radians(bs.latitude))  )) *1000 < :radius ORDER BY distance";
		javax.persistence.Query q = entityManager.createNativeQuery(hql,BusStop.class)
				.setParameter("latitude", latitude)
				.setParameter("longitude", longitude)
				.setParameter("radius", radius);		
		List<BusStop> result = q.getResultList();
		return result;
	}


}
