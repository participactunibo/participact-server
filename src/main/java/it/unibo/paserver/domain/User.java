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
package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

@Entity
@Table(name = "user_accounts")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User implements Serializable, Comparable<User> {

	private static MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder(
			"sha-256");
	private static final long serialVersionUID = -7480986502756652528L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Email
	@NotEmpty
	@Column(unique = true)
	private String officialEmail;

	@NotEmpty
	private String surname;

	@NotEmpty
	private String name;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate birthdate;

	@Column(unique = true)
	private String cf;

	@NotEmpty
	private String contactPhoneNumber;

	@NotEmpty
	private String currentAddress;

	@NotEmpty
	private String currentCity;

	@NotEmpty
	private String currentProvince;

	@NotEmpty
	private String currentZipCode;
	@NotEmpty
	private String documentId;
	@NotNull
	@Enumerated(EnumType.STRING)
	private DocumentIdType documentIdType;
	private String domicileAddress;

	private String domicileCity;

	private String domicileProvince;

	private String domicileZipCode;

	@Email
	private String facebookEmail;

	@Column(columnDefinition = "TEXT")
	private String gcmId;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	private Boolean hasIdScan = Boolean.FALSE;

	@NotNull
	private Boolean hasLastInvoiceScan = Boolean.FALSE;

	@NotNull
	private Boolean hasPhone = Boolean.FALSE;

	@NotNull
	private Boolean hasPresaConsegnaPhoneScan = Boolean.FALSE;

	@NotNull
	private Boolean hasPresaConsegnaSIMScan = Boolean.FALSE;

	@NotNull
	private Boolean hasPrivacyScan = Boolean.FALSE;

	@NotNull
	private Boolean hasSIM = Boolean.FALSE;

	private String homePhoneNumber;

	@Column(unique = true)
	private String iccid;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument idScan;

	private String imei;

	@NotNull
	private Boolean isActive = Boolean.TRUE;

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument lastInvoiceScan;

	private String originalPhoneOperator;
	@NotEmpty
	private String password;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument presaConsegnaPhoneScan;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument presaConsegnaSIMScan;
	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private BinaryDocument privacyScan;
	@Email
	@NotEmpty
	@Column(unique = true)
	private String projectEmail;
	private String projectPhoneNumber;
	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime registrationDateTime;
	@NotNull
	@Enumerated(EnumType.STRING)
	private SimStatus simStatus;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UniCity uniCity;
	@NotNull
	@Enumerated(EnumType.STRING)
	private UniCourse uniCourse;
	@NotEmpty
	private String uniDegree;
	@NotEmpty
	private String uniDepartment;
	@NotNull
	private Boolean uniIsSupplementaryYear;
	@NotNull
	@Enumerated(EnumType.STRING)
	private UniSchool uniSchool;
	private Integer uniYear;

	@NotNull
	private Boolean wantsPhone = Boolean.TRUE;

	@NotNull
	private Boolean isMyCompanyRegistered = Boolean.FALSE;

	@Column(unique = true)
	private String newIccid;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate receivedPhoneOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate returnedPhoneOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate receivedSIMOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate returnedSIMOn;

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
	private LocalDate activatedSIMOn;

	@Column(columnDefinition = "TEXT")
	private String notes;

	// Added for gamification
	@ManyToMany(targetEntity = AbstractBadge.class, fetch = FetchType.EAGER, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "User_Badges", joinColumns = { @JoinColumn(name = "user_Id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "badge_Id", referencedColumnName = "badge_id") })
	private Set<Badge> badges;

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public LocalDate getActivatedSIMOn() {
		return activatedSIMOn;
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

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public String getCf() {
		return cf;
	}

	public String getContactPhoneNumber() {
		return contactPhoneNumber;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public String getCurrentProvince() {
		return currentProvince;
	}

	public String getCurrentZipCode() {
		return currentZipCode;
	}

	public String getDocumentId() {
		return documentId;
	}

	public DocumentIdType getDocumentIdType() {
		return documentIdType;
	}

	public String getDomicileAddress() {
		return domicileAddress;
	}

	public String getDomicileCity() {
		return domicileCity;
	}

	public String getDomicileProvince() {
		return domicileProvince;
	}

	public String getDomicileZipCode() {
		return domicileZipCode;
	}

	public String getFacebookEmail() {
		return facebookEmail;
	}

	public String getGcmId() {
		return gcmId;
	}

	public Gender getGender() {
		return gender;
	}

	public Boolean getHasIdScan() {
		return hasIdScan;
	}

	public Boolean getHasLastInvoiceScan() {
		return hasLastInvoiceScan;
	}

	public Boolean getHasPhone() {
		return hasPhone;
	}

	public Boolean getHasPresaConsegnaPhoneScan() {
		return hasPresaConsegnaPhoneScan;
	}

	public Boolean getHasPresaConsegnaSIMScan() {
		return hasPresaConsegnaSIMScan;
	}

	public Boolean getHasPrivacyScan() {
		return hasPrivacyScan;
	}

	public Boolean getHasSIM() {
		return hasSIM;
	}

	public String getHomePhoneNumber() {
		return homePhoneNumber;
	}

	public String getIccid() {
		return iccid;
	}

	public Long getId() {
		return id;
	}

	public BinaryDocument getIdScan() {
		return idScan;
	}

	public String getImei() {
		return imei;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public BinaryDocument getLastInvoiceScan() {
		return lastInvoiceScan;
	}

	public String getName() {
		return name;
	}

	public String getNewIccid() {
		return newIccid;
	}

	public String getOfficialEmail() {
		return officialEmail;
	}

	public String getOriginalPhoneOperator() {
		return originalPhoneOperator;
	}

	public String getPassword() {
		return password;
	}

	public BinaryDocument getPresaConsegnaPhoneScan() {
		return presaConsegnaPhoneScan;
	}

	public BinaryDocument getPresaConsegnaSIMScan() {
		return presaConsegnaSIMScan;
	}

	public BinaryDocument getPrivacyScan() {
		return privacyScan;
	}

	public String getProjectEmail() {
		return projectEmail;
	}

	public String getProjectPhoneNumber() {
		return projectPhoneNumber;
	}

	public DateTime getRegistrationDateTime() {
		return registrationDateTime;
	}

	public String getSurname() {
		return surname;
	}

	public UniCity getUniCity() {
		return uniCity;
	}

	public UniCourse getUniCourse() {
		return uniCourse;
	}

	public String getUniDegree() {
		return uniDegree;
	}

	public String getUniDepartment() {
		return uniDepartment;
	}

	public Boolean getUniIsSupplementaryYear() {
		return uniIsSupplementaryYear;
	}

	public UniSchool getUniSchool() {
		return uniSchool;
	}

	public Integer getUniYear() {
		return uniYear;
	}

	public SimStatus getSimStatus() {
		return simStatus;
	}

	public Boolean getWantsPhone() {
		return wantsPhone;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Set<Badge> getBadges() {
		return badges;
	}

	public void setContactPhoneNumber(String contactPhoneNumber) {
		this.contactPhoneNumber = contactPhoneNumber;
	}

	public void setCredentials(String password) {
		String endocdedPw = encoder
				.encodePassword(password, getOfficialEmail());
		setPassword(endocdedPw);
	}

	public void setCredentials(String officialEmail, String password) {
		setOfficialEmail(officialEmail);
		String encodedPw = encoder.encodePassword(password, getOfficialEmail());
		setPassword(encodedPw);
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public void setCurrentProvince(String currentProvince) {
		this.currentProvince = currentProvince;
	}

	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setDocumentIdType(DocumentIdType documentIdType) {
		this.documentIdType = documentIdType;
	}

	public void setDomicileAddress(String domicileAddress) {
		this.domicileAddress = domicileAddress;
	}

	public void setDomicileCity(String domicileCity) {
		this.domicileCity = domicileCity;
	}

	public void setDomicileProvince(String domicileProvince) {
		this.domicileProvince = domicileProvince;
	}

	public void setDomicileZipCode(String domicileZipCode) {
		this.domicileZipCode = domicileZipCode;
	}

	public void setFacebookEmail(String facebookEmail) {
		this.facebookEmail = facebookEmail;
	}

	public void setGcmId(String gcmId) {
		this.gcmId = gcmId;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setHasIdScan(Boolean hasIdScan) {
		this.hasIdScan = hasIdScan;
	}

	public void setHasLastInvoiceScan(Boolean hasLastInvoiceScan) {
		this.hasLastInvoiceScan = hasLastInvoiceScan;
	}

	public void setHasPhone(Boolean hasPhone) {
		this.hasPhone = hasPhone;
	}

	public void setHasPresaConsegnaPhoneScan(Boolean hasPresaConsegnaPhoneScan) {
		this.hasPresaConsegnaPhoneScan = hasPresaConsegnaPhoneScan;
	}

	public void setHasPresaConsegnaSIMScan(Boolean hasPresaConsegnaSIMScan) {
		this.hasPresaConsegnaSIMScan = hasPresaConsegnaSIMScan;
	}

	public void setHasPrivacyScan(Boolean hasPrivacyScan) {
		this.hasPrivacyScan = hasPrivacyScan;
	}

	public void setHasSIM(Boolean hasSIM) {
		this.hasSIM = hasSIM;
	}

	public void setHomePhoneNumber(String homePhoneNumber) {
		this.homePhoneNumber = homePhoneNumber;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIdScan(BinaryDocument idScan) {
		this.idScan = idScan;
		if (idScan != null) {
			setHasIdScan(Boolean.TRUE);
			idScan.setOwner(this);
		} else {
			setHasIdScan(Boolean.FALSE);
		}
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setLastInvoiceScan(BinaryDocument lastInvoiceScan) {
		this.lastInvoiceScan = lastInvoiceScan;
		if (lastInvoiceScan != null) {
			setHasLastInvoiceScan(Boolean.TRUE);
			lastInvoiceScan.setOwner(this);
		} else {
			setHasLastInvoiceScan(Boolean.FALSE);
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNewIccid(String newIccid) {
		this.newIccid = newIccid;
	}

	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}

	public void setOriginalPhoneOperator(String originalPhoneOperator) {
		this.originalPhoneOperator = originalPhoneOperator;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPresaConsegnaPhoneScan(BinaryDocument presaConsegnaPhoneScan) {
		this.presaConsegnaPhoneScan = presaConsegnaPhoneScan;
		if (presaConsegnaPhoneScan != null) {
			setHasPresaConsegnaPhoneScan(Boolean.TRUE);
			presaConsegnaPhoneScan.setOwner(this);
		} else {
			setHasPresaConsegnaPhoneScan(Boolean.FALSE);
		}
	}

	public void setPresaConsegnaSIMScan(BinaryDocument presaConsegnaSIMScan) {
		this.presaConsegnaSIMScan = presaConsegnaSIMScan;
		if (presaConsegnaPhoneScan != null) {
			setHasPresaConsegnaSIMScan(Boolean.TRUE);
			presaConsegnaSIMScan.setOwner(this);
		} else {
			setHasPresaConsegnaSIMScan(Boolean.FALSE);
		}
	}

	public void setPrivacyScan(BinaryDocument privacyScan) {
		this.privacyScan = privacyScan;
		if (privacyScan != null) {
			setHasPrivacyScan(Boolean.TRUE);
			privacyScan.setOwner(this);
		} else {
			setHasPrivacyScan(Boolean.FALSE);
		}
	}

	public void setProjectEmail(String projectEmail) {
		this.projectEmail = projectEmail;
	}

	public void setProjectPhoneNumber(String projectPhoneNumber) {
		this.projectPhoneNumber = projectPhoneNumber;
	}

	public void setRegistrationDateTime(DateTime registrationDateTime) {
		this.registrationDateTime = registrationDateTime;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setUniCity(UniCity uniCity) {
		this.uniCity = uniCity;
	}

	public void setUniCourse(UniCourse uniCourse) {
		this.uniCourse = uniCourse;
	}

	public void setUniDegree(String uniDegree) {
		this.uniDegree = uniDegree;
	}

	public void setUniDepartment(String uniDepartment) {
		this.uniDepartment = uniDepartment;
	}

	public void setUniIsSupplementaryYear(Boolean uniIsSupplementaryYear) {
		this.uniIsSupplementaryYear = uniIsSupplementaryYear;
	}

	public void setUniSchool(UniSchool uniSchool) {
		this.uniSchool = uniSchool;
	}

	public void setUniYear(Integer uniYear) {
		this.uniYear = uniYear;
	}

	public void setSimStatus(SimStatus simStatus) {
		this.simStatus = simStatus;
	}

	public void setWantsPhone(Boolean wantsPhone) {
		this.wantsPhone = wantsPhone;
	}

	public Boolean getIsMyCompanyRegistered() {
		return isMyCompanyRegistered;
	}

	public void setIsMyCompanyRegistered(Boolean isMyCompanyRegistered) {
		this.isMyCompanyRegistered = isMyCompanyRegistered;
	}

	public void setBadges(Set<Badge> badges) {
		this.badges = badges;
	}

	@Override
	public int hashCode() {
		return getOfficialEmail().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User)) {
			return false;
		}
		User other = (User) o;
		return getOfficialEmail().equals(other.getOfficialEmail());
	}

	@Override
	public int compareTo(User o) {
		if (o == null) {
			return 1;
		}
		User u = (User) o;
		if (equals(u)) {
			return 0;
		}
		int surnameOrder = getSurname().toUpperCase().compareTo(
				u.getSurname().toUpperCase());
		if (surnameOrder != 0) {
			return surnameOrder;
		}
		int nameOrder = getName().toUpperCase().compareTo(
				u.getName().toUpperCase());
		if (nameOrder != 0) {
			return nameOrder;
		}
		return getBirthdate().compareTo(u.getBirthdate());
	}

}
