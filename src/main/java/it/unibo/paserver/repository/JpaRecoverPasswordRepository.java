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

import it.unibo.paserver.domain.RecoverPassword;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("recoverPasswordRepository")
public class JpaRecoverPasswordRepository implements RecoverPasswordRepository {

	private static final Logger logger = LoggerFactory
			.getLogger(JpaRecoverPasswordRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public RecoverPassword findById(long id) {
		return entityManager.find(RecoverPassword.class, id);
	}

	@Override
	public RecoverPassword findByToken(String token) {
		if (token == null) {
			return null;
		}
		String hql = "select r from RecoverPassword r where r.token = :token";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("token", token);
		return query.getSingleResult();
	}

	@Override
	public List<RecoverPassword> findByUser(long userId) {
		String hql = "select r from RecoverPassword r where r.user.id = :userId";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("userId", userId);
		return query.getResultList();
	}

	@Override
	public List<RecoverPassword> findExpiredAt(DateTime instant) {
		if (instant == null) {
			return new ArrayList<RecoverPassword>();
		}
		String hql = "select r from RecoverPassword r where r.endValidity < :instant";
		TypedQuery<RecoverPassword> query = entityManager.createQuery(hql,
				RecoverPassword.class).setParameter("instant", instant);
		return query.getResultList();
	}

	@Override
	public RecoverPassword save(RecoverPassword recoverPassword) {
		if (recoverPassword.getStartValidity().isAfter(
				recoverPassword.getEndValidity())) {
			logger.warn(
					"RecoverPassword start validity time ({}) is after end validity time ({})",
					recoverPassword.getStartValidity(),
					recoverPassword.getEndValidity());
		}
		return entityManager.merge(recoverPassword);
	}

	@Override
	public boolean delete(long id) {
		RecoverPassword rp = findById(id);
		if (rp == null) {
			logger.info("Unable to remove RecoverPassword id {}", id);
			return false;
		}
		logger.debug("Removed RecoverPassword id {}", id);
		entityManager.remove(rp);
		return true;
	}
}
