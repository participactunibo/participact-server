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
package it.unibo.paserver.web.controller;

import org.hibernate.validator.constraints.NotEmpty;

public class AddAccountForm {

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String confirmPassword;
	private Boolean roleView;
	private Boolean roleAdmin;

	public AddAccountForm() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Boolean getRoleView() {
		return roleView;
	}

	public void setRoleView(Boolean roleView) {
		this.roleView = roleView;
	}

	public Boolean getRoleAdmin() {
		return roleAdmin;
	}

	public void setRoleAdmin(Boolean roleAdmin) {
		this.roleAdmin = roleAdmin;
	}

	@Override
	public String toString() {
		return username;
	}
}
