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
package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.Role;

import org.joda.time.DateTime;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountBuilder extends EntityBuilder<Account> {

	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder(
			"sha-256");

	@Override
	void initEntity() {
		entity = new Account();
	}

	public AccountBuilder credentials(String username, String password) {
		entity.setUsername(username);
		entity.setPassword(encoder.encodePassword(password, username));
		return this;
	}

	public AccountBuilder creationDate(DateTime creationDate) {
		entity.setCreationDate(creationDate);
		return this;
	}

	public AccountBuilder addRole(Role role) {
		entity.addRole(role);
		return this;
	}

	@Override
	Account assembleEntity() {
		return entity;
	}

}
