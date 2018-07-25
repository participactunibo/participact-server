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

import it.unibo.paserver.domain.MonthlyTargetScore;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

@Repository("monthlyTargetScoreRepository")
public class JpaMonthlyTargetScoreRepository implements
		MonthlyTargetScoreRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public MonthlyTargetScore save(MonthlyTargetScore monthlyTargetScore) {
		return entityManager.merge(monthlyTargetScore);
	}

	@Override
	public MonthlyTargetScore findById(long id) {
		return entityManager.find(MonthlyTargetScore.class, id);
	}

	@Override
	public MonthlyTargetScore findByYearMonth(int year, int month) {
		String hql = "from MonthlyTargetScore t where t.year = :year and t.month = :month";
		TypedQuery<MonthlyTargetScore> query = entityManager
				.createQuery(hql, MonthlyTargetScore.class)
				.setParameter("year", year).setParameter("month", month);
		return query.getSingleResult();
	}

	@Override
	public boolean delete(long id) {
		try {
			MonthlyTargetScore target = findById(id);
			entityManager.remove(target);
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public List<MonthlyTargetScore> getAll() {
		String hql = "from MonthlyTargetScore t order by t.year desc, t.month desc";
		TypedQuery<MonthlyTargetScore> query = entityManager.createQuery(hql,
				MonthlyTargetScore.class);
		return query.getResultList();
	}

}
