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

import it.unibo.paserver.web.controller.AddAccountForm;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddAccountFormValidator implements Validator {

	private static final String USERNAME_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*$";

	@Override
	public boolean supports(Class<?> clazz) {
		return (AddAccountForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "username", "required",
				new Object[] { "Username" });
		ValidationUtils.rejectIfEmpty(errors, "password", "required",
				new Object[] { "password" });
		ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required",
				new Object[] { "confirmPassword" });
		if (!errors.hasFieldErrors("username")) {
			String username = ((AddAccountForm) target).getUsername();
			if (!username.matches(USERNAME_PATTERN)) {
				errors.rejectValue("username", "invalid");
			}
		}
		AddAccountForm account = (AddAccountForm) target;
		if (!errors.hasFieldErrors("password")) {
			if (account.getPassword().length() < 8) {
				errors.rejectValue("password", "tooshort");
			}
		}

		if (!errors.hasFieldErrors("password")
				&& !errors.hasFieldErrors("confirmPassword")) {
			if (!account.getPassword().equals(account.getConfirmPassword())) {
				errors.rejectValue("confirmPassword", "dontmatch");
			}
		}
	}

}
