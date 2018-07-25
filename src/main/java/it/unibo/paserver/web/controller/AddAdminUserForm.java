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

import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.support.UserBuilder;

public class AddAdminUserForm extends AdminUserForm {

	public AddAdminUserForm() {
		super();
	}

	public UserBuilder setAllFields(UserBuilder ub) {
		ub.setBirthdate(getBirthdate());
		ub.setContactPhoneNumber(getContactPhoneNumber());
		ub.setCredentials(getOfficialEmail(), getPassword());
		ub.setCurrentAddress(getCurrentAddress());
		ub.setCurrentCity(getCurrentCity());
		ub.setCurrentProvince(getCurrentProvince());
		ub.setCurrentZipCode(getCurrentZipCode());
		ub.setDocumentId(getDocumentId());
		ub.setDocumentIdType(getDocumentIdType());
		ub.setDomicileAddress(getDomicileAddress());
		ub.setDomicileCity(getDomicileCity());
		ub.setDomicileProvince(getDomicileProvince());
		ub.setDomicileZipCode(getDomicileZipCode());
		ub.setGender(getGenderAsEnum());
		ub.setHomePhoneNumber(getHomePhoneNumber());
		ub.setName(getName());
		ub.setNotes(getNotes());
		ub.setOfficialEmail(getOfficialEmail());
		ub.setProjectEmail(getProjectEmail());
		ub.setProjectPhoneNumber(getProjectPhoneNumber());
		ub.setSurname(getSurname());
		ub.setUniCity(getUniCity());
		ub.setUniCourse(getUniCourse());
		ub.setUniDegree(getUniDegree());
		ub.setUniDepartment(getUniDepartment());
		ub.setUniIsSupplementaryYear(getUniIsSupplementaryYear());
		ub.setUniSchool(getUniSchool());
		ub.setUniYear(getUniYear());
		ub.setFacebookEmail(getFacebookEmail());
		ub.setSimStatus(getSimStatus());
		ub.setWantsPhone(getWantsPhone());
		ub.setHasSIM(getHasSIM());
		ub.setWantsPhone(getWantsPhone());
		ub.setIsActive(getIsActive());
		ub.setIccid(getIccid());
		ub.setOriginalPhoneOperator(getOriginalPhoneOperator());
		ub.setCf(getCf());
		ub.setImei(getImei());
		ub.setIsMyCompanyRegistered(getIsMyCompanyRegistered());
		setupBinaryDocument(ub, BinaryDocumentType.ID_SCAN, getIdScan(),
				idScanOrigFname, idScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.LAST_INVOICE,
				getLastInvoiceScan(), lastInvoiceOrigFname, lastInvoiceScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRIVACY, getPrivacyScan(),
				privacyScanOrigFname, privacyScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRESA_CONSEGNA_PHONE,
				getPresaConsegnaPhoneScan(), presaConsegnaPhoneScanOrigFname,
				presaConsegnaPhoneScanExt);
		setupBinaryDocument(ub, BinaryDocumentType.PRESA_CONSEGNA_SIM,
				getPresaConsegnaSIMScan(), presaConsegnaSIMScanOrigFname,
				presaConsegnaSIMScanExt);
		return ub;
	}

}
