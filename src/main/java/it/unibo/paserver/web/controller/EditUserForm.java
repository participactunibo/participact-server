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

import it.unibo.paserver.domain.User;

public class EditUserForm extends UserForm {

	public void initFromUsers(User u) {
		setContactPhoneNumber(u.getContactPhoneNumber());
		setCurrentAddress(u.getCurrentAddress());
		setCurrentCity(u.getCurrentCity());
		setCurrentProvince(u.getCurrentProvince());
		setCurrentZipCode(u.getCurrentZipCode());
		if (u.getDomicileAddress() == null) {
			setIsDomicileEqualToCurrentAddr(true);
		}
		setDomicileAddress(u.getDomicileAddress());
		setDomicileCity(u.getDomicileCity());
		setDomicileProvince(u.getDomicileProvince());
		setDomicileZipCode(u.getDomicileZipCode());
		setHomePhoneNumber(u.getHomePhoneNumber());
		setNotes(u.getNotes());
		setProjectEmail(u.getProjectEmail());
		setFacebookEmail(u.getFacebookEmail());

	}

	public void updateUser(User u) {
		u.setContactPhoneNumber(getContactPhoneNumber());
		/*if (getPassword() != null && getPassword().length() > 0) {
			u.setCredentials(u.getOfficialEmail(), getPassword());
		}*/
		u.setCurrentAddress(getCurrentAddress());
		u.setCurrentCity(getCurrentCity());
		u.setCurrentProvince(getCurrentProvince());
		u.setCurrentZipCode(getCurrentZipCode());
		u.setDomicileAddress(getDomicileAddress());
		u.setDomicileCity(getDomicileCity());
		u.setDomicileProvince(getDomicileProvince());
		u.setDomicileZipCode(getDomicileZipCode());
		u.setHomePhoneNumber(getHomePhoneNumber());
		u.setNotes(getNotes());
		u.setProjectEmail(getProjectEmail());
		u.setFacebookEmail(getFacebookEmail());

	}

}
