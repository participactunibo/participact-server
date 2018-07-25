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

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.User;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public class JpaUserRepository implements UserRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaUserRepository.class);

	@Override
	public User findByEmail(String email) {
		String hql = "select c from User c where c.officialEmail=:officialEmail";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class)
				.setParameter("officialEmail", email);
		List<User> users = query.getResultList();
		return users.size() == 1 ? users.get(0) : null;
	}

	@Override
	public User findById(long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User save(User user) {
		// if (user.getId() != null) {
		logger.trace("Merging user {}", user.toString());
		return entityManager.merge(user);
		// } else {
		// logger.trace("Persisting user {}", user.toString());
		// entityManager.persist(user);
		// return user;
		// }
	}

	@Override
	public Long getUserCount() {
		String hql = "select count(id) from User c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<User> getUsers() {
		String hql = "select c from User c";
		TypedQuery<User> query = entityManager.createQuery(hql, User.class);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public boolean deleteUser(long id) {
		User user = findById(id);
		try {
			if (user != null) {
				entityManager.remove(user);
				return true;
			} else {
				logger.warn("Unable to find user {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}

	@Override
	public BinaryDocument getIdScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.ID_SCAN);
	}

	@Override
	public BinaryDocument getLastInvoiceScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.LAST_INVOICE);
	}

	@Override
	public BinaryDocument getPrivacyScan(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRIVACY);
	}

	@Override
	public BinaryDocument getPresaConsegnaPhone(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRESA_CONSEGNA_PHONE);
	}

	@Override
	public BinaryDocument getPresaConsegnaSIM(Long id) {
		return getBinaryDocument(id, BinaryDocumentType.PRESA_CONSEGNA_SIM);
	}

	private BinaryDocument getBinaryDocument(Long id, BinaryDocumentType type) {
		User u = findById(id);
		if (u == null) {
			return null;
		}
		BinaryDocument bd = null;
		switch (type) {
		case ID_SCAN:
			bd = u.getIdScan();
			break;
		case LAST_INVOICE:
			bd = u.getLastInvoiceScan();
			break;
		case PRIVACY:
			bd = u.getPrivacyScan();
			break;
		case PRESA_CONSEGNA_PHONE:
			bd = u.getPresaConsegnaPhoneScan();
			break;
		case PRESA_CONSEGNA_SIM:
			bd = u.getPresaConsegnaSIMScan();
			break;
		default:
			bd = null;
		}
		if (bd != null) {
			// do not remove the next two line, they are needed to initialize
			// persistent
			// objects within the hibernate transactional context
			int size = bd.getContent().length;
			logger.trace("Retrieved user {} document {} {} bytes", id, type,
					size);
		}
		return bd;
	}

	@Override
	public boolean deleteBinaryDocument(long id) {
		BinaryDocument doc = entityManager.find(BinaryDocument.class, id);
		try {
			if (doc != null) {
				entityManager.remove(doc);
				return true;
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		logger.warn("Unable to find binary document {}", id);
		return false;
	}

	@Override
	public List<String> getActiveUsers() {
		String hql = "select officialEmail from User c where c.isActive = true";
		TypedQuery<String> query = entityManager.createQuery(hql, String.class);
		return query.getResultList();
	}

	@Override
	public List<User> getWorkingUsers(int inputType, DateTime start) {
		String hql = "select u.* from actionsensing a, task_actions t, taskreport r, user_accounts u where a.input_type = :inputType and r.currentstate = 'RUNNING' and a.action_id = t.action_id and r.task_id = t.task_id and r.user_id = u.id and r.accepteddatetime > :start";
		Query query = entityManager.createNativeQuery(hql,User.class).setParameter("inputType", inputType).setParameter("start", start.toDate());
		return query.getResultList();
	}

}
