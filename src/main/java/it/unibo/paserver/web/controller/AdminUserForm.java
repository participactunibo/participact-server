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
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.support.UserBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeTypes;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class AdminUserForm extends UserForm {

	private String name;
	private String surname;
	private String gender;
	private LocalDate birthdate;
	private SimStatus simStatus;
	private Boolean wantsPhone;
	private Boolean hasSIM;
	private Boolean hasPhone;
	private Boolean isActive;
	private String iccid;
	private String newIccid;
	private String imei;
	private String originalPhoneOperator;
	private String cf;
	private DocumentIdType documentIdType;
	private String documentId;
	private String officialEmail;
	private String projectPhoneNumber;

	private Boolean isMyCompanyRegistered;

	private UniCity uniCity;
	private UniSchool uniSchool;
	private String uniDepartment;
	private String uniDegree;
	private UniCourse uniCourse;
	private Boolean uniIsSupplementaryYear;
	private Integer uniYear;

	private CommonsMultipartFile idScan;
	protected StringBuilder idScanExt;
	protected StringBuilder idScanOrigFname;

	private CommonsMultipartFile lastInvoiceScan;
	protected StringBuilder lastInvoiceScanExt;
	protected StringBuilder lastInvoiceOrigFname;

	private CommonsMultipartFile privacyScan;
	protected StringBuilder privacyScanExt;
	protected StringBuilder privacyScanOrigFname;

	private CommonsMultipartFile presaConsegnaPhoneScan;
	protected StringBuilder presaConsegnaPhoneScanExt;
	protected StringBuilder presaConsegnaPhoneScanOrigFname;

	private CommonsMultipartFile presaConsegnaSIMScan;
	protected StringBuilder presaConsegnaSIMScanExt;
	protected StringBuilder presaConsegnaSIMScanOrigFname;

	private LocalDate receivedPhoneOn;
	private LocalDate returnedPhoneOn;
	private LocalDate receivedSIMOn;
	private LocalDate returnedSIMOn;
	private LocalDate activatedSIMOn;

	public AdminUserForm() {

		idScanExt = new StringBuilder();
		idScanOrigFname = new StringBuilder();

		lastInvoiceScanExt = new StringBuilder();
		lastInvoiceOrigFname = new StringBuilder();

		privacyScanExt = new StringBuilder();
		privacyScanOrigFname = new StringBuilder();

		presaConsegnaPhoneScanExt = new StringBuilder();
		presaConsegnaPhoneScanOrigFname = new StringBuilder();

		presaConsegnaSIMScanExt = new StringBuilder();
		presaConsegnaSIMScanOrigFname = new StringBuilder();
	}

	public Boolean getIsMyCompanyRegistered() {
		return isMyCompanyRegistered;
	}

	public void setIsMyCompanyRegistered(Boolean isMyCompanyRegistered) {
		this.isMyCompanyRegistered = isMyCompanyRegistered;
	}

	public String getOfficialEmail() {
		return StringUtils.stripToNull(officialEmail);
	}

	public void setOfficialEmail(String email) {
		this.officialEmail = email;
	}

	public LocalDate getActivatedSIMOn() {
		return activatedSIMOn;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return StringUtils.stripToNull(gender);
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Gender getGenderAsEnum() {
		try {
			return Gender.valueOf(getGender());
		} catch (Exception e) {
			return null;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = StringUtils.stripToNull(name);
	}

	public String getSurname() {
		return StringUtils.stripToNull(surname);
	}

	public void setSurname(String surname) {
		this.surname = StringUtils.stripToNull(surname);
	}

	public String getProjectPhoneNumber() {
		return StringUtils.stripToNull(projectPhoneNumber);
	}

	public void setProjectPhoneNumber(String projectPhoneNumber) {
		this.projectPhoneNumber = StringUtils.stripToNull(projectPhoneNumber);
	}

	public void setActivatedSIMOn(LocalDate activatedSIMOn) {
		this.activatedSIMOn = activatedSIMOn;
	}

	public LocalDate getReceivedPhoneOn() {
		return receivedPhoneOn;
	}

	public void setReceivedPhoneOn(LocalDate receivedPhoneOn) {
		this.receivedPhoneOn = receivedPhoneOn;
	}

	public LocalDate getReturnedPhoneOn() {
		return returnedPhoneOn;
	}

	public void setReturnedPhoneOn(LocalDate returnedPhoneOn) {
		this.returnedPhoneOn = returnedPhoneOn;
	}

	public LocalDate getReceivedSIMOn() {
		return receivedSIMOn;
	}

	public void setReceivedSIMOn(LocalDate receivedSIMOn) {
		this.receivedSIMOn = receivedSIMOn;
	}

	public LocalDate getReturnedSIMOn() {
		return returnedSIMOn;
	}

	public void setReturnedSIMOn(LocalDate returnedSIMOn) {
		this.returnedSIMOn = returnedSIMOn;
	}

	public void setIdScan(CommonsMultipartFile idScan) {
		this.idScan = idScan;
		setMultiPartData(idScan, idScanOrigFname, idScanExt);
	}

	public CommonsMultipartFile getIdScan() {
		return idScan;
	}

	public void setLastInvoiceScan(CommonsMultipartFile lastInvoiceScan) {
		this.lastInvoiceScan = lastInvoiceScan;
		setMultiPartData(lastInvoiceScan, lastInvoiceOrigFname,
				lastInvoiceScanExt);
	}

	public CommonsMultipartFile getLastInvoiceScan() {
		return lastInvoiceScan;
	}

	public void setPrivacyScan(CommonsMultipartFile privacyScan) {
		this.privacyScan = privacyScan;
		setMultiPartData(privacyScan, privacyScanOrigFname, privacyScanExt);
	}

	public CommonsMultipartFile getPrivacyScan() {
		return privacyScan;
	}

	public void setPresaConsegnaPhoneScan(
			CommonsMultipartFile presaConsegnaPhoneScan) {
		this.presaConsegnaPhoneScan = presaConsegnaPhoneScan;
		setMultiPartData(presaConsegnaPhoneScan,
				presaConsegnaPhoneScanOrigFname, presaConsegnaPhoneScanExt);
	}

	public CommonsMultipartFile getPresaConsegnaPhoneScan() {
		return presaConsegnaPhoneScan;
	}

	public void setPresaConsegnaSIMScan(
			CommonsMultipartFile presaConsegnaSIMScan) {
		this.presaConsegnaSIMScan = presaConsegnaSIMScan;
		setMultiPartData(presaConsegnaSIMScan, presaConsegnaSIMScanOrigFname,
				presaConsegnaSIMScanExt);
	}

	public CommonsMultipartFile getPresaConsegnaSIMScan() {
		return presaConsegnaSIMScan;
	}

	private void setMultiPartData(CommonsMultipartFile file,
			StringBuilder filename, StringBuilder extension) {
		if (StringUtils.isNotBlank(file.getOriginalFilename())) {
			filename.append(file.getOriginalFilename());
		} else {
			filename.append("data");
		}
		String ext = FilenameUtils.getExtension(file.getOriginalFilename());
		if (StringUtils.isNotBlank(ext)) {
			extension.append(ext);
		} else {
			extension.append("dat");
		}
	}

	public SimStatus getSimStatus() {
		return simStatus;
	}

	public void setSimStatus(SimStatus simStatus) {
		this.simStatus = simStatus;
	}

	public Boolean getWantsPhone() {
		return wantsPhone != null ? wantsPhone : Boolean.FALSE;
	}

	public void setWantsPhone(Boolean wantsPhone) {
		this.wantsPhone = wantsPhone;
	}

	public Boolean getHasSIM() {
		return hasSIM != null ? hasSIM : Boolean.FALSE;
	}

	public void setHasSIM(Boolean hasSIM) {
		this.hasSIM = hasSIM;
	}

	public Boolean getHasPhone() {
		return hasPhone != null ? hasPhone : Boolean.FALSE;
	}

	public void setHasPhone(Boolean hasPhone) {
		this.hasPhone = hasPhone;
	}

	public String getIccid() {
		return StringUtils.stripToNull(iccid);
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public String getOriginalPhoneOperator() {
		return StringUtils.stripToNull(originalPhoneOperator);
	}

	public void setOriginalPhoneOperator(String originalPhoneOperator) {
		this.originalPhoneOperator = originalPhoneOperator;
	}

	public String getCf() {
		return StringUtils.stripToNull(cf);
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getImei() {
		return StringUtils.stripToNull(imei);
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNewIccid() {
		return StringUtils.stripToNull(newIccid);
	}

	public void setNewIccid(String newIccid) {
		this.newIccid = newIccid;
	}

	public DocumentIdType getDocumentIdType() {
		return documentIdType;
	}

	public void setDocumentIdType(DocumentIdType documentIdType) {
		this.documentIdType = documentIdType;
	}

	public String getDocumentId() {
		return StringUtils.stripToNull(documentId);
	}

	public void setDocumentId(String documentId) {
		this.documentId = StringUtils.stripToNull(documentId);
		if (this.documentId != null) {
			this.documentId = this.documentId.toUpperCase();
		}
	}

	public UniSchool getUniSchool() {
		return uniSchool;
	}

	public void setUniSchool(UniSchool uniSchool) {
		this.uniSchool = uniSchool;
	}

	public String getUniDepartment() {
		return StringUtils.stripToNull(uniDepartment);
	}

	public void setUniDepartment(String uniDepartment) {
		this.uniDepartment = StringUtils.stripToNull(uniDepartment);
	}

	public String getUniDegree() {
		return StringUtils.stripToNull(uniDegree);
	}

	public void setUniDegree(String uniDegree) {
		this.uniDegree = StringUtils.stripToNull(uniDegree);
	}

	public UniCourse getUniCourse() {
		return uniCourse;
	}

	public void setUniCourse(UniCourse uniCourse) {
		this.uniCourse = uniCourse;
	}

	public Boolean getUniIsSupplementaryYear() {
		return uniIsSupplementaryYear;
	}

	public void setUniIsSupplementaryYear(Boolean uniIsSupplementaryYear) {
		this.uniIsSupplementaryYear = uniIsSupplementaryYear;
	}

	public Integer getUniYear() {
		return uniYear;
	}

	public void setUniYear(Integer uniYear) {
		this.uniYear = uniYear;
	}

	public UniCity getUniCity() {
		return uniCity;
	}

	public void setUniCity(UniCity uniCity) {
		this.uniCity = uniCity;
	}

	protected MediaType getContentType(String fileName,
			CommonsMultipartFile data) {
		try {
			Metadata metadata = new Metadata();
			metadata.add(Metadata.RESOURCE_NAME_KEY, fileName);
			MediaType mt = MimeTypes.getDefaultMimeTypes().detect(
					new ByteArrayInputStream(data.getBytes()), metadata);
			return mt;
		} catch (IOException e) {
			logger.error("Error while parsing mimetype", e);
			return MediaType.OCTET_STREAM;
		}
	}

	protected BinaryDocument setupBinaryDocument(UserBuilder ub,
			BinaryDocumentType type, CommonsMultipartFile file,
			StringBuilder fname, StringBuilder ext) {
		return setupBinaryDocument(ub, type, file, fname.toString(),
				ext.toString());
	}

	protected BinaryDocument setupBinaryDocument(UserBuilder ub,
			BinaryDocumentType type, CommonsMultipartFile file, String fname,
			String ext) {
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
				ub.setIdScan(bd);
				break;
			case LAST_INVOICE:
				ub.setLastInvoiceScan(bd);
				break;
			case PRIVACY:
				ub.setPrivacyScan(bd);
				break;
			case PRESA_CONSEGNA_PHONE:
				ub.setPresaConsegnaPhoneScan(bd);
				break;
			case PRESA_CONSEGNA_SIM:
				ub.setPresaConsegnaSIMScan(bd);
				break;
			default:
				logger.error("Unknown document {}", type);
			}
			return bd;
		} else {
			return null;
		}
	}

}
