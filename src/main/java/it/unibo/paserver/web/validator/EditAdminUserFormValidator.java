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
package it.unibo.paserver.web.validator;

import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.web.controller.EditAdminUserForm;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditAdminUserFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	private static final String IMEI_PATTERN = "^[0-9]{14,16}$";
	private static final String ICCID_PATTERN = "^[0-9]{18,20}$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (EditAdminUserForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "surname", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "required");
		ValidationUtils.rejectIfEmpty(errors, "birthdate", "required");
		ValidationUtils.rejectIfEmpty(errors, "documentIdType", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "simStatus",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "documentId",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentAddress",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentZipCode",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentCity",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currentProvince",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactPhoneNumber",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "officialEmail",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectEmail",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uniSchool",
				"required");
		ValidationUtils
				.rejectIfEmptyOrWhitespace(errors, "uniCity", "required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uniDepartment",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uniDegree",
				"required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "uniCourse",
				"required");
		ValidationUtils.rejectIfEmpty(errors, "uniIsSupplementaryYear",
				"required");

		EditAdminUserForm euf = (EditAdminUserForm) target;

		if (!errors.hasFieldErrors("officialEmail")) {
			String username = euf.getOfficialEmail();
			if (!username.matches(EMAIL_PATTERN_UNIBO)) {
				errors.rejectValue("officialEmail", "invalid");
			}
		}

		if (!errors.hasFieldErrors("projectEmail")) {
			String username = euf.getOfficialEmail();
			if (!username.matches(EMAIL_PATTERN)) {
				errors.rejectValue("projectEmail", "invalid");
			}
		}

		if (!errors.hasFieldErrors("password")) {
			if (euf.getPassword() != null && euf.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
			}
		}

		if (!errors.hasFieldErrors("birthdate")) {
			LocalDate birthdate = euf.getBirthdate();
			LocalDate maximumBirthdate = new LocalDate(1998, 1, 1);
			LocalDate minimumBirthdate = new LocalDate(1930, 1, 1);
			if (birthdate.isAfter(maximumBirthdate)) {
				errors.rejectValue("birthdate", "tooyoung");
			} else if (birthdate.isBefore(minimumBirthdate)) {
				errors.rejectValue("birthdate", "tooold");
			}
		}

		if (!errors.hasFieldErrors("gender")) {
			if (euf.getGenderAsEnum() == null) {
				errors.rejectValue("gender", "invalid");
			}
		}

		if (!errors.hasFieldErrors("uniCourse")
				&& !errors.hasFieldErrors("uniIsSupplementaryYear")
				&& !errors.hasFieldErrors("uniYear")) {
			UniCourse course = euf.getUniCourse();
			Integer year = euf.getUniYear();
			if (euf.getUniIsSupplementaryYear()) {
				// student is "fuori corso"
				if (year != null) {
					errors.rejectValue("uniYear", "mustbenull");
				}
			} else {
				// student is "in corso"
				if (year == null) {
					errors.rejectValue("uniYear", "required");
				} else if (year < 1) {
					errors.rejectValue("uniYear", "invalid");
				} else {
					if (course == UniCourse.TRIENNALE && year > 3) {
						errors.rejectValue("uniYear", "triennaletoobig");
					} else if (course == UniCourse.MAGISTRALE && year > 2) {
						errors.rejectValue("uniYear", "magistraletoobig");
					}
				}
			}
		}

		if (euf.getSimStatus() == SimStatus.PORTABILITY) {
			if (euf.getCf() == null) {
				errors.rejectValue("cf", "required");
			}
			if (euf.getOriginalPhoneOperator() == null) {
				errors.rejectValue("originalPhoneOperator", "required");
			}
			if (euf.getProjectPhoneNumber() == null) {
				errors.rejectValue("projectPhoneNumber", "required");
			}
		}

		if (euf.getSimStatus() == SimStatus.NEW_SIM) {
			if (euf.getOriginalPhoneOperator() != null) {
				errors.rejectValue("originalPhoneOperator", "mustbeempty");
			}
			if (euf.getIccid() != null) {
				errors.rejectValue("iccid", "mustbeempty");
			}
		}

		if (euf.getSimStatus() == SimStatus.NO) {
			if (euf.getOriginalPhoneOperator() != null) {
				errors.rejectValue("originalPhoneOperator", "mustbeempty2");
			}
			if (euf.getIccid() != null) {
				errors.rejectValue("iccid", "mustbeempty2");
			}
		}

		if (euf.getNewIccid() != null) {
			String iccid = euf.getNewIccid();
			if (!iccid.matches(ICCID_PATTERN)) {
				errors.rejectValue("newIccid", "invalid");
			}
		}

		if (euf.getIccid() != null) {
			String iccid = euf.getIccid();
			if (!iccid.matches(ICCID_PATTERN)) {
				errors.rejectValue("iccid", "invalid");
			}
		}

		if (euf.getImei() != null) {
			String imei = euf.getImei();
			if (!imei.matches(IMEI_PATTERN)) {
				errors.rejectValue("imei", "invalid");
			}
		}

		if ((euf.getPassword() != null && euf.getPassword() == null)
				|| (euf.getPassword() == null && euf.getPassword() != null)) {
			errors.rejectValue("confirmPassword", "required");
			errors.rejectValue("password", "required");
		} else if (euf.getPassword() != null && euf.getPassword() != null) {
			if (!euf.getPassword().equals(euf.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "dontmatch");
			}
		}

		if (euf.getHasPhone() == true && StringUtils.isBlank(euf.getImei())) {
			errors.rejectValue("imei", "required");
		}

		if (euf.getHasSIM() == true && StringUtils.isBlank(euf.getNewIccid())) {
			errors.rejectValue("newIccid", "required");
		}
	}

}
