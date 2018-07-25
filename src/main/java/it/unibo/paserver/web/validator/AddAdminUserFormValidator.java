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
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.AddAdminUserForm;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddAdminUserFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (AddAdminUserForm.class).isAssignableFrom(clazz);
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
		ValidationUtils.rejectIfEmpty(errors, "password", "required");
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");

		AddAdminUserForm auf = (AddAdminUserForm) target;

		if (auf.getIsDomicileEqualToCurrentAddr()) {
			if (StringUtils.isNotBlank(auf.getDomicileAddress())) {
				errors.rejectValue("domicileAddress", "mustbeempty");
			}
			if (StringUtils.isNotBlank(auf.getDomicileCity())) {
				errors.rejectValue("domicileCity", "mustbeempty");
			}
			if (StringUtils.isNotBlank(auf.getDomicileProvince())) {
				errors.rejectValue("domicileProvince", "mustbeempty");
			}
			if (StringUtils.isNotBlank(auf.getDomicileZipCode())) {
				errors.rejectValue("domicileZipCode", "mustbeempty");
			}
		} else {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"domicileAddress", "required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"domicileZipCode", "required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domicileCity",
					"required");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"domicileProvince", "required");
		}

		if (!errors.hasFieldErrors("officialEmail")) {
			String username = ((AddAdminUserForm) target).getOfficialEmail();
			if (!username.matches(EMAIL_PATTERN_UNIBO)) {
				errors.rejectValue("officialEmail", "invalid");
			} else if (userService.getUser(username) != null) {
				// check for duplicate usernames
				errors.rejectValue("officialEmail", "duplicate");
			}
		}

		if (!errors.hasFieldErrors("projectEmail")) {
			String username = ((AddAdminUserForm) target).getOfficialEmail();
			if (!username.matches(EMAIL_PATTERN)) {
				errors.rejectValue("projectEmail", "invalid");
			} else if (userService.getUser(username) != null) {
				// check for duplicate usernames
				errors.rejectValue("projectEmail", "duplicate");
			}
		}

		if (!errors.hasFieldErrors("password")) {
			if (auf.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
			}
		}

		if (!errors.hasFieldErrors("password")
				&& !errors.hasFieldErrors("confirmPassword")) {
			if (!auf.getPassword().equals(auf.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "dontmatch");
			}
		}

		if (!errors.hasFieldErrors("birthdate")) {
			LocalDate birthdate = auf.getBirthdate();
			LocalDate maximumBirthdate = new LocalDate(1998, 1, 1);
			LocalDate minimumBirthdate = new LocalDate(1930, 1, 1);
			if (birthdate.isAfter(maximumBirthdate)) {
				errors.rejectValue("birthdate", "tooyoung");
			} else if (birthdate.isBefore(minimumBirthdate)) {
				errors.rejectValue("birthdate", "tooold");
			}
		}

		if (!errors.hasFieldErrors("gender")) {
			if (auf.getGenderAsEnum() == null) {
				errors.rejectValue("gender", "invalid");
			}
		}

		if (!errors.hasFieldErrors("uniCourse")
				&& !errors.hasFieldErrors("uniIsSupplementaryYear")
				&& !errors.hasFieldErrors("uniYear")) {
			UniCourse course = auf.getUniCourse();
			Integer year = auf.getUniYear();
			if (auf.getUniIsSupplementaryYear()) {
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

		if (auf.getSimStatus() == SimStatus.PORTABILITY) {
			if (auf.getCf() == null) {
				errors.rejectValue("cf", "required");
			}
			if (auf.getOriginalPhoneOperator() == null) {
				errors.rejectValue("originalPhoneOperator", "required");
			}
			if (auf.getProjectPhoneNumber() == null) {
				errors.rejectValue("projectPhoneNumber", "required");
			}
		}

		if (auf.getSimStatus() == SimStatus.NEW_SIM) {
			if (auf.getCf() != null) {
				errors.rejectValue("cf", "mustbeempty");
			}
			if (auf.getOriginalPhoneOperator() != null) {
				errors.rejectValue("originalPhoneOperator", "mustbeempty");
			}
			if (auf.getIccid() != null) {
				errors.rejectValue("iccid", "mustbeempty");
			}
			if (auf.getProjectPhoneNumber() != null) {
				errors.rejectValue("projectPhoneNumber", "mustbeempty");
			}
		}

		if (auf.getSimStatus() == SimStatus.NO) {
			if (auf.getCf() != null) {
				errors.rejectValue("cf", "mustbeempty2");
			}
			if (auf.getOriginalPhoneOperator() != null) {
				errors.rejectValue("originalPhoneOperator", "mustbeempty2");
			}
			if (auf.getIccid() != null) {
				errors.rejectValue("iccid", "mustbeempty2");
			}
			if (auf.getProjectPhoneNumber() != null) {
				errors.rejectValue("projectPhoneNumber", "mustbeempty2");
			}
		}
	}

}
