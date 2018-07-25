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
package it.unibo.paserver.web.security;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ServerUserDetailsService implements UserDetailsService {

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		if (StringUtils.isBlank(username)) {
			throw new UsernameNotFoundException("Username was empty");
		}
		Account account = accountService.getAccount(username);

		if (account != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			for (Role role : account.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role
						.toString()));
			}
			account.setLastLogin(new DateTime());
			accountService.save(account);
			return new AccountAdminDetails(account, grantedAuthorities);
		}

		User user = userService.getUser(username);
		if (user != null) {
			List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
			grantedAuthorities.add(new SimpleGrantedAuthority(Role.ROLE_USER
					.toString()));
			return new AccountUserDetails(user, grantedAuthorities);
		}

		throw new UsernameNotFoundException("Username not found");
	}

}
