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

import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;

import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class UserBuilder extends EntityBuilder<User> {

	@Override
	void initEntity() {
		entity = new User();
	}

	@Override
	User assembleEntity() {
		return entity;
	}

	public UserBuilder setCredentials(String officialEmail, String password) {
		entity.setCredentials(officialEmail, password);
		return this;
	}

	public UserBuilder setId(Long id) {
		entity.setId(id);
		return this;
	}

	public UserBuilder setName(String name) {
		entity.setName(name);
		return this;
	}

	public UserBuilder setSurname(String surname) {
		entity.setSurname(surname);
		return this;
	}

	public UserBuilder setGender(Gender gender) {
		entity.setGender(gender);
		return this;
	}

	public UserBuilder setBirthdate(LocalDate birthdate) {
		entity.setBirthdate(birthdate);
		return this;
	}

	public UserBuilder setDocumentIdType(DocumentIdType documentIdType) {
		entity.setDocumentIdType(documentIdType);
		return this;
	}

	public UserBuilder setDocumentId(String documentId) {
		entity.setDocumentId(documentId);
		return this;
	}

	public UserBuilder setDomicileAddress(String domicileAddress) {
		entity.setDomicileAddress(domicileAddress);
		return this;
	}

	public UserBuilder setDomicileZipCode(String domicileZipCode) {
		entity.setDomicileZipCode(domicileZipCode);
		return this;
	}

	public UserBuilder setDomicileCity(String domicileCity) {
		entity.setDomicileCity(domicileCity);
		return this;
	}

	public UserBuilder setDomicileProvince(String domicileProvince) {
		entity.setDomicileProvince(domicileProvince);
		return this;
	}

	public UserBuilder setCurrentAddress(String currentAddress) {
		entity.setCurrentAddress(currentAddress);
		return this;
	}

	public UserBuilder setCurrentZipCode(String currentZipCode) {
		entity.setCurrentZipCode(currentZipCode);
		return this;
	}

	public UserBuilder setCurrentCity(String currentCity) {
		entity.setCurrentCity(currentCity);
		return this;
	}

	public UserBuilder setCurrentProvince(String currentProvince) {
		entity.setCurrentProvince(currentProvince);
		return this;
	}

	public UserBuilder setContactPhoneNumber(String contactPhoneNumber) {
		entity.setContactPhoneNumber(contactPhoneNumber);
		return this;
	}

	public UserBuilder setHomePhoneNumber(String homePhoneNumber) {
		entity.setHomePhoneNumber(homePhoneNumber);
		return this;
	}

	public UserBuilder setProjectPhoneNumber(String projectPhoneNumber) {
		entity.setProjectPhoneNumber(projectPhoneNumber);
		return this;
	}

	public UserBuilder setOfficialEmail(String officialEmail) {
		entity.setOfficialEmail(officialEmail);
		return this;
	}

	public UserBuilder setProjectEmail(String projectEmail) {
		entity.setProjectEmail(projectEmail);
		return this;
	}

	public UserBuilder setUniCity(UniCity uniCity) {
		entity.setUniCity(uniCity);
		return this;
	}

	public UserBuilder setUniSchool(UniSchool uniSchool) {
		entity.setUniSchool(uniSchool);
		return this;
	}

	public UserBuilder setUniDepartment(String uniDepartment) {
		entity.setUniDepartment(uniDepartment);
		return this;
	}

	public UserBuilder setUniDegree(String uniDegree) {
		entity.setUniDegree(uniDegree);
		return this;
	}

	public UserBuilder setUniCourse(UniCourse uniCourse) {
		entity.setUniCourse(uniCourse);
		return this;
	}

	public UserBuilder setUniIsSupplementaryYear(Boolean uniIsSupplementaryYear) {
		entity.setUniIsSupplementaryYear(uniIsSupplementaryYear);
		return this;
	}

	public UserBuilder setUniYear(Integer uniYear) {
		entity.setUniYear(uniYear);
		return this;
	}

	public UserBuilder setImei(String imei) {
		entity.setImei(imei);
		return this;
	}

	public UserBuilder setPassword(String password) {
		entity.setPassword(password);
		return this;
	}

	public UserBuilder setRegistrationDateTime(DateTime registrationDateTime) {
		entity.setRegistrationDateTime(registrationDateTime);
		return this;
	}

	public UserBuilder setGcmId(String gcmId) {
		entity.setGcmId(gcmId);
		return this;
	}

	public UserBuilder setFacebookEmail(String facebookEmail) {
		entity.setFacebookEmail(facebookEmail);
		return this;
	}

	public UserBuilder setSimStatus(SimStatus simStatus) {
		entity.setSimStatus(simStatus);
		return this;
	}

	public UserBuilder setWantsPhone(Boolean wantsPhone) {
		entity.setWantsPhone(wantsPhone);
		return this;
	}

	public UserBuilder setHasSIM(Boolean hasSIM) {
		entity.setHasSIM(hasSIM);
		return this;
	}

	public UserBuilder setHasPhone(Boolean hasPhone) {
		entity.setHasPhone(hasPhone);
		return this;
	}

	public UserBuilder setIccid(String iccid) {
		entity.setIccid(iccid);
		return this;
	}

	public UserBuilder setNewIccid(String newIccid) {
		entity.setNewIccid(newIccid);
		return this;
	}

	public UserBuilder setLastInvoiceScan(BinaryDocument lastInvoice) {
		entity.setLastInvoiceScan(lastInvoice);
		return this;
	}

	public UserBuilder setOriginalPhoneOperator(String originalPhoneOperator) {
		entity.setOriginalPhoneOperator(originalPhoneOperator);
		return this;
	}

	public UserBuilder setIdScan(BinaryDocument idScan) {
		entity.setIdScan(idScan);
		return this;
	}

	public UserBuilder setCf(String cf) {
		entity.setCf(cf);
		return this;
	}

	public UserBuilder setIsActive(Boolean isActive) {
		entity.setIsActive(isActive);
		return this;
	}

	public UserBuilder setPrivacyScan(BinaryDocument lastInvoice) {
		entity.setPrivacyScan(lastInvoice);
		return this;
	}

	public UserBuilder setPresaConsegnaPhoneScan(
			BinaryDocument presaConsegnaPhoneScan) {
		entity.setPresaConsegnaPhoneScan(presaConsegnaPhoneScan);
		return this;
	}

	public UserBuilder setPresaConsegnaSIMScan(
			BinaryDocument presaConsegnaSIMScan) {
		entity.setPresaConsegnaSIMScan(presaConsegnaSIMScan);
		return this;
	}

	public UserBuilder setIsMyCompanyRegistered(Boolean isMyCompanyRegistered) {
		entity.setIsMyCompanyRegistered(isMyCompanyRegistered);
		return this;
	}

	public UserBuilder setNotes(String notes) {
		entity.setNotes(notes);
		return this;
	}

	public UserBuilder setBadges(Set<Badge> badges) {
		entity.setBadges(badges);
		return this;
	}
}
