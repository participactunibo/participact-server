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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DataLocationAvg;
import it.unibo.paserver.groups.GroupUtils;

import org.joda.time.DateTime;

import sun.util.locale.StringTokenIterator;

@Repository("datalocationRepository")
public class JpaDatalocationRepository implements DatalocationRepository {

	@PersistenceContext
	EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaGroupsRepository.class);

	@Override
	public List<DataLocationAvg> getDataLocationAvg(DateTime startHour,
			DateTime endHour) {

		String hql = "select dl.user.id, avg(dl.latitude), avg(dl.longitude) "
				+ "from DataLocation dl "
				+ "where dl.sampleTimestamp between :startHour and :endHour "
				+ "and dl.accuracy < :accuracy " + "group by dl.user.id "
				+ "order by dl.user.id";

		Query q = entityManager.createQuery(hql)
				.setParameter("accuracy", GroupUtils.MAXACCURACY)
				.setParameter("startHour", startHour).setParameter("endHour", endHour);

		List<Object[]> resultQuery = q.getResultList();
		
		ArrayList<DataLocationAvg> result = new ArrayList<DataLocationAvg>();

		for (Object[] o : resultQuery) {
			DataLocationAvg tmp = new DataLocationAvg();
			tmp.setId((Long) o[0]);
			tmp.setLatitude((Double) o[1]);
			tmp.setLongitude((Double) o[2]);
			result.add(tmp);
		}

		return result;
	}

}
