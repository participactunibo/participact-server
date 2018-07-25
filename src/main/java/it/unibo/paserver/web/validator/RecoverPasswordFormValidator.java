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

import it.unibo.paserver.web.controller.RecoverPasswordForm;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RecoverPasswordFormValidator implements Validator {

	private static final String EMAIL_PATTERN_UNIBO = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@studio\\.unibo\\.it$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (RecoverPasswordForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "required");
		RecoverPasswordForm rpf = (RecoverPasswordForm) target;

		if (!errors.hasFieldErrors("email")) {
			String username = rpf.getEmail();
			if (!username.matches(EMAIL_PATTERN_UNIBO)) {
				errors.rejectValue("email", "invalid");
			}
		}

	}

}
