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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserForm {

	protected static final Logger logger = LoggerFactory
			.getLogger(UserForm.class);

	private String domicileAddress;
	private String domicileZipCode;
	private String domicileCity;
	private String domicileProvince;
	private Boolean isDomicileEqualToCurrentAddr;
	private String currentAddress;
	private String currentZipCode;
	private String currentCity;
	private String currentProvince;
	private String contactPhoneNumber;
	private String homePhoneNumber;
	private String projectEmail;
	private String facebookEmail;

	private String password;
	private String confirmPassword;

	private String notes;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public UserForm() {

	}

	public Boolean getIsDomicileEqualToCurrentAddr() {
		return isDomicileEqualToCurrentAddr != null ? isDomicileEqualToCurrentAddr
				: (getCurrentAddress() == null);
	}

	public void setIsDomicileEqualToCurrentAddr(
			Boolean isDomicileEqualToCurrentAddr) {
		this.isDomicileEqualToCurrentAddr = isDomicileEqualToCurrentAddr;
	}

	public String getDomicileAddress() {
		return StringUtils.stripToNull(domicileAddress);
	}

	public void setDomicileAddress(String address) {
		this.domicileAddress = StringUtils.stripToNull(address);
	}

	public String getDomicileZipCode() {
		return StringUtils.stripToNull(domicileZipCode);
	}

	public void setDomicileZipCode(String zipCode) {
		this.domicileZipCode = StringUtils.stripToNull(zipCode);
	}

	public String getDomicileCity() {
		return StringUtils.stripToNull(domicileCity);
	}

	public void setDomicileCity(String city) {
		this.domicileCity = StringUtils.stripToNull(city);
	}

	public String getDomicileProvince() {
		return StringUtils.stripToNull(domicileProvince);
	}

	public void setDomicileProvince(String province) {
		this.domicileProvince = StringUtils.stripToNull(province);
	}

	public String getContactPhoneNumber() {
		return StringUtils.stripToNull(contactPhoneNumber);
	}

	public void setContactPhoneNumber(String phoneNumber) {
		this.contactPhoneNumber = StringUtils.stripToNull(phoneNumber);
	}

	public String getHomePhoneNumber() {
		return StringUtils.stripToNull(homePhoneNumber);
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public String getPassword() {
		return StringUtils.stripToNull(password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return StringUtils.stripToNull(confirmPassword);
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentAddress() {
		return StringUtils.stripToNull(currentAddress);
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = StringUtils.stripToNull(currentAddress);
	}

	public String getCurrentZipCode() {
		return StringUtils.stripToNull(currentZipCode);
	}

	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = StringUtils.stripToNull(currentZipCode);
	}

	public String getCurrentCity() {
		return StringUtils.stripToNull(currentCity);
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = StringUtils.stripToNull(currentCity);
	}

	public String getCurrentProvince() {
		return StringUtils.stripToNull(currentProvince);
	}

	public void setCurrentProvince(String currentProvince) {
		this.currentProvince = StringUtils.stripToNull(currentProvince);
	}

	public String getProjectEmail() {
		return StringUtils.stripToNull(projectEmail);
	}

	public void setProjectEmail(String projectEmail) {
		this.projectEmail = projectEmail;
	}

	public String getFacebookEmail() {
		return StringUtils.stripToNull(facebookEmail);
	}

	public void setFacebookEmail(String facebookEmail) {
		this.facebookEmail = facebookEmail;
	}
}
