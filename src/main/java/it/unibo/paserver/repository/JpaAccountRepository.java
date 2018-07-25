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

import it.unibo.paserver.domain.Account;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("accountRepository")
public class JpaAccountRepository implements AccountRepository {

	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(JpaAccountRepository.class);

	@Override
	public Account findByUsername(String username) {
		String hql = "select c from Account c where c.username=:username";
		TypedQuery<Account> query = entityManager.createQuery(hql,
				Account.class).setParameter("username", username);
		List<Account> accounts = query.getResultList();
		return accounts.size() == 1 ? accounts.get(0) : null;
	}

	@Override
	public Account findById(long id) {
		return entityManager.find(Account.class, id);
	}

	@Override
	public Account save(Account account) {
		// if (account.getId() != null) {
		logger.trace("Merging account {}", account.toString());
		return entityManager.merge(account);
		// } else {
		// logger.trace("Persisting account {}", account.toString());
		// entityManager.persist(account);
		// return account;
		// }
	}

	@Override
	public Long getAccountsCount() {
		String hql = "select count(id) from Account c";
		TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
		return query.getSingleResult();
	}

	@Override
	public List<Account> getAccounts() {
		String hql = "select c from Account c";
		TypedQuery<Account> query = entityManager.createQuery(hql,
				Account.class);
		List<Account> accounts = query.getResultList();
		return accounts;
	}

	@Override
	public boolean deleteAccount(long id) {
		Account account = findById(id);
		try {
			if (account != null) {
				entityManager.remove(account);
				return true;
			} else {
				logger.warn("Unable to find account {}", id);
			}
		} catch (Exception e) {
			logger.info("Exception: {}", e);
		}
		return false;
	}
}
