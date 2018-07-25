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

import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.SelectUsersGCMForm;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SelectUsersGCMFormValidator implements Validator {

	@Autowired
	UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return (SelectUsersGCMForm.class).isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userList", "required");
		ValidationUtils.rejectIfEmpty(errors, "gcmType", "required");

		if (errors.hasErrors()) {
			return;
		}
		SelectUsersGCMForm form = (SelectUsersGCMForm) target;
		String[] userStrings = form.getUserList().split("[^a-zA-Z0-9@.]");
		List<String> unknown = new ArrayList<String>();
		List<String> nogcmid = new ArrayList<String>();

		for (String s : userStrings) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			User u = userService.getUser(s);
			if (u == null) {
				unknown.add(s);
			} else if (u.getGcmId() == null) {
				nogcmid.add(s);
			}
		}

		if (unknown.size() > 0) {
			String unknownUsersString = StringUtils.join(unknown, ", ");
			errors.rejectValue("userList", "unknown",
					new String[] { unknownUsersString }, "Unknown users");
		}
		if (nogcmid.size() > 0) {
			String nogcmidUsersString = StringUtils.join(nogcmid, ", ");
			errors.rejectValue("userList", "nogcmid",
					new String[] { nogcmidUsersString }, "Unknown users");
		}
	}

}
