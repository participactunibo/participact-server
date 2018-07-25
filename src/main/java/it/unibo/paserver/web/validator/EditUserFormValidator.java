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

import it.unibo.paserver.web.controller.EditUserForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EditUserFormValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (EditUserForm.class).isAssignableFrom(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {

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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectEmail",
				"required");

		EditUserForm euf = (EditUserForm) target;

		if (!errors.hasFieldErrors("projectEmail")) {
			String projectEmail = euf.getProjectEmail();
			if (!projectEmail.matches(EMAIL_PATTERN)) {
				errors.rejectValue("projectEmail", "invalid");
			}
		}

		if (!errors.hasFieldErrors("password")) {
			if (euf.getPassword() != null && euf.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
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

	}

}
