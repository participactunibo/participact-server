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
package it.unibo.paserver.service;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.AuthenticationException;
import it.unibo.paserver.repository.AccountRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	@Transactional(readOnly = true)
	public Account login(String username, String password)
			throws AuthenticationException {
		Account account = this.accountRepository.findByUsername(username);
		if (account == null) {
			throw new AuthenticationException(
					"Wrong username/password combination.");
		}

		return account;
	}

	@Override
	@Transactional(readOnly = true)
	public Account getAccount(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = false)
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	@Transactional(readOnly = true)
	public Long getAccountsCount() {
		return accountRepository.getAccountsCount();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Account> getAccounts() {
		return accountRepository.getAccounts();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteAccount(long id) {
		return accountRepository.deleteAccount(id);
	}

}
