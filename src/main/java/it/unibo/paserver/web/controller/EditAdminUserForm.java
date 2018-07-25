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

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.User;

import java.util.Set;

import org.apache.tika.mime.MediaType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class EditAdminUserForm extends AdminUserForm {

	private static final Logger logger = LoggerFactory
			.getLogger(EditAdminUserForm.class);

	public void initFromUser(User u) {
		setBirthdate(u.getBirthdate());
		setCf(u.getCf());
		setContactPhoneNumber(u.getContactPhoneNumber());
		setCurrentAddress(u.getCurrentAddress());
		setCurrentCity(u.getCurrentCity());
		setCurrentProvince(u.getCurrentProvince());
		setCurrentZipCode(u.getCurrentZipCode());
		setDocumentId(u.getDocumentId());
		setDocumentIdType(u.getDocumentIdType());
		if (u.getDomicileAddress() == null) {
			setIsDomicileEqualToCurrentAddr(true);
		}
		setDomicileAddress(u.getDomicileAddress());
		setDomicileCity(u.getDomicileCity());
		setDomicileProvince(u.getDomicileProvince());
		setDomicileZipCode(u.getDomicileZipCode());
		setFacebookEmail(u.getFacebookEmail());
		setGender(u.getGender().toString());
		setHasPhone(u.getHasPhone());
		setHasSIM(u.getHasSIM());
		setHomePhoneNumber(u.getHomePhoneNumber());
		setIccid(u.getIccid());
		setNewIccid(u.getNewIccid());
		setImei(u.getImei());
		setIsMyCompanyRegistered(u.getIsMyCompanyRegistered());
		setIsActive(u.getIsActive());
		setName(u.getName());
		setNotes(u.getNotes());
		setOfficialEmail(u.getOfficialEmail());
		setOriginalPhoneOperator(u.getOriginalPhoneOperator());
		setProjectEmail(u.getProjectEmail());
		setProjectPhoneNumber(u.getProjectPhoneNumber());
		setSimStatus(u.getSimStatus());
		setSurname(u.getSurname());
		setUniCity(u.getUniCity());
		setUniCourse(u.getUniCourse());
		setUniDegree(u.getUniDegree());
		setUniDepartment(u.getUniDepartment());
		setUniIsSupplementaryYear(u.getUniIsSupplementaryYear());
		setUniSchool(u.getUniSchool());
		setUniYear(u.getUniYear());
		setWantsPhone(u.getWantsPhone());
		setReceivedPhoneOn(u.getReceivedPhoneOn());
		setReturnedPhoneOn(u.getReturnedPhoneOn());
		setReceivedSIMOn(u.getReceivedSIMOn());
		setReturnedSIMOn(u.getReturnedSIMOn());
		setActivatedSIMOn(u.getActivatedSIMOn());
	}

	public void updateUser(User u, Set<Long> documentsIdsToDelete) {

		if (getReceivedPhoneOn() != null) {
			u.setReceivedPhoneOn(getReceivedPhoneOn());
		} else if (u.getReceivedPhoneOn() != null
				&& getReceivedPhoneOn() == null) {
			u.setReceivedPhoneOn(null);
		} else if (u.getHasPhone() == false && getHasPhone() == true) {
			if (getReceivedPhoneOn() == null) {
				u.setReceivedPhoneOn(new LocalDate());
			} else {
				u.setReceivedPhoneOn(getReceivedPhoneOn());
			}
		}
		if (getReceivedSIMOn() != null) {
			u.setReceivedSIMOn(getReceivedSIMOn());
		} else if (u.getReceivedSIMOn() != null && getReceivedSIMOn() == null) {
			u.setReceivedSIMOn(null);
		} else if (u.getHasSIM() == false && getHasSIM() == true) {
			if (getReceivedSIMOn() == null) {
				u.setReceivedSIMOn(new LocalDate());
			} else {
				u.setReceivedSIMOn(getReceivedSIMOn());
			}
		}

		if (getReturnedPhoneOn() != null) {
			u.setReturnedPhoneOn(getReturnedPhoneOn());
		} else if (u.getReturnedPhoneOn() != null
				&& getReturnedPhoneOn() == null) {
			u.setReturnedPhoneOn(null);
		} else if (u.getHasPhone() == true && getHasPhone() == false) {
			if (getReturnedPhoneOn() == null) {
				u.setReturnedPhoneOn(new LocalDate());
			} else {
				u.setReturnedPhoneOn(getReturnedPhoneOn());
			}
		}

		if (getReturnedPhoneOn() != null) {
			u.setReturnedPhoneOn(getReturnedPhoneOn());
		} else if (u.getReturnedPhoneOn() != null
				&& getReturnedPhoneOn() == null) {
			u.setReturnedPhoneOn(null);
		} else if (u.getHasPhone() == true && getHasPhone() == false) {
			if (getReturnedPhoneOn() == null) {
				u.setReturnedPhoneOn(new LocalDate());
			} else {
				u.setReturnedPhoneOn(getReturnedPhoneOn());
			}
		}

		if (getReturnedPhoneOn() != null) {
			u.setReturnedPhoneOn(getReturnedPhoneOn());
		} else if (u.getReturnedPhoneOn() != null
				&& getReturnedPhoneOn() == null) {
			u.setReturnedPhoneOn(null);
		} else if (u.getHasPhone() == true && getHasPhone() == false) {
			if (getReturnedPhoneOn() == null) {
				u.setReturnedPhoneOn(new LocalDate());
			} else {
				u.setReturnedPhoneOn(getReturnedPhoneOn());
			}
		}
		if (getReturnedSIMOn() != null) {
			u.setReturnedSIMOn(getReturnedSIMOn());
		} else if (u.getReturnedSIMOn() != null && getReturnedSIMOn() == null) {
			u.setReturnedSIMOn(null);
		} else if (u.getHasSIM() == true && getHasSIM() == false) {
			if (getReturnedSIMOn() == null) {
				u.setReturnedSIMOn(new LocalDate());
			} else {
				u.setReturnedSIMOn(getReturnedSIMOn());
			}
		}
		u.setActivatedSIMOn(getActivatedSIMOn());
		u.setBirthdate(getBirthdate());
		u.setContactPhoneNumber(getContactPhoneNumber());
		if (getPassword() != null && getPassword().length() > 0) {
			u.setCredentials(getOfficialEmail(), getPassword());
		}
		u.setCurrentAddress(getCurrentAddress());
		u.setCurrentCity(getCurrentCity());
		u.setCurrentProvince(getCurrentProvince());
		u.setCurrentZipCode(getCurrentZipCode());
		u.setDocumentId(getDocumentId());
		u.setDocumentIdType(getDocumentIdType());
		u.setDomicileAddress(getDomicileAddress());
		u.setDomicileCity(getDomicileCity());
		u.setDomicileProvince(getDomicileProvince());
		u.setDomicileZipCode(getDomicileZipCode());
		u.setGender(getGenderAsEnum());
		u.setHomePhoneNumber(getHomePhoneNumber());
		u.setName(getName());
		u.setNotes(getNotes());
		u.setOfficialEmail(getOfficialEmail());
		u.setProjectEmail(getProjectEmail());
		u.setProjectPhoneNumber(getProjectPhoneNumber());
		u.setSurname(getSurname());
		u.setUniCity(getUniCity());
		u.setUniCourse(getUniCourse());
		u.setUniDegree(getUniDegree());
		u.setUniDepartment(getUniDepartment());
		u.setUniIsSupplementaryYear(getUniIsSupplementaryYear());
		u.setUniSchool(getUniSchool());
		u.setUniYear(getUniYear());
		u.setFacebookEmail(getFacebookEmail());
		u.setSimStatus(getSimStatus());
		u.setWantsPhone(getWantsPhone());
		u.setHasSIM(getHasSIM());
		u.setHasPhone(getHasPhone());
		u.setWantsPhone(getWantsPhone());
		u.setIsActive(getIsActive());
		u.setIccid(getIccid());
		u.setNewIccid(getNewIccid());
		u.setImei(getImei());
		u.setIsMyCompanyRegistered(getIsMyCompanyRegistered());
		u.setOriginalPhoneOperator(getOriginalPhoneOperator());
		u.setCf(getCf());
		setupBinaryDocument(u, BinaryDocumentType.ID_SCAN, getIdScan(),
				idScanOrigFname, idScanExt, documentsIdsToDelete);
		setupBinaryDocument(u, BinaryDocumentType.LAST_INVOICE,
				getLastInvoiceScan(), lastInvoiceOrigFname, lastInvoiceScanExt,
				documentsIdsToDelete);
		setupBinaryDocument(u, BinaryDocumentType.PRIVACY, getPrivacyScan(),
				privacyScanOrigFname, privacyScanExt, documentsIdsToDelete);
		setupBinaryDocument(u, BinaryDocumentType.PRESA_CONSEGNA_PHONE,
				getPresaConsegnaPhoneScan(), presaConsegnaPhoneScanOrigFname,
				presaConsegnaPhoneScanExt, documentsIdsToDelete);
		setupBinaryDocument(u, BinaryDocumentType.PRESA_CONSEGNA_SIM,
				getPresaConsegnaSIMScan(), presaConsegnaSIMScanOrigFname,
				presaConsegnaSIMScanExt, documentsIdsToDelete);

	}

	protected BinaryDocument setupBinaryDocument(User u,
			BinaryDocumentType type, CommonsMultipartFile file,
			StringBuilder fname, StringBuilder ext,
			Set<Long> documentsIdsToDelete) {
		return setupBinaryDocument(u, type, file, fname.toString(),
				ext.toString(), documentsIdsToDelete);
	}

	protected BinaryDocument setupBinaryDocument(User user,
			BinaryDocumentType type, CommonsMultipartFile file, String fname,
			String ext, Set<Long> documentsIdsToDelete) {
		if (file != null && file.getOriginalFilename().length() > 0) {
			BinaryDocument bd = new BinaryDocument();
			bd.setContent(file.getBytes());
			MediaType mt = getContentType(fname, file);
			bd.setContentType(mt.getType());
			bd.setContentSubtype(mt.getSubtype());
			bd.setCreated(new DateTime());
			bd.setFilename(String.format("%s %s %s.%s", getSurname(),
					getName(), type.getFilenameSub(), ext));
			switch (type) {
			case ID_SCAN:
				updateIdsToDelete(user.getIdScan(), documentsIdsToDelete);
				user.setIdScan(bd);
				break;
			case LAST_INVOICE:
				updateIdsToDelete(user.getLastInvoiceScan(),
						documentsIdsToDelete);
				user.setLastInvoiceScan(bd);
				break;
			case PRIVACY:
				updateIdsToDelete(user.getPrivacyScan(), documentsIdsToDelete);
				user.setPrivacyScan(bd);
				break;
			case PRESA_CONSEGNA_PHONE:
				updateIdsToDelete(user.getPresaConsegnaPhoneScan(),
						documentsIdsToDelete);
				user.setPresaConsegnaPhoneScan(bd);
				break;
			case PRESA_CONSEGNA_SIM:
				updateIdsToDelete(user.getPresaConsegnaSIMScan(),
						documentsIdsToDelete);
				user.setPresaConsegnaSIMScan(bd);
				break;
			default:
				logger.error("Unknown document {}", type);
			}
			return bd;
		} else {
			return null;
		}
	}

	private void updateIdsToDelete(BinaryDocument bd, Set<Long> idsToDelete) {
		if (bd != null && bd.getId() != null) {
			idsToDelete.add(bd.getId());
		}
	}
}
