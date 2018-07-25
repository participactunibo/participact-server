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

import it.unibo.paserver.web.controller.AddInterestPointForm;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class AddInterestPointValidator implements Validator {

	@Override
	public boolean supports(Class<?> cls) {
		return (AddInterestPointForm.class).isAssignableFrom(cls);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "lat", "required",
				new Object[] { "Latitudine" });
		ValidationUtils.rejectIfEmpty(errors, "lon", "required",
				new Object[] { "Longitudine" });
		ValidationUtils.rejectIfEmpty(errors, "description", "required",
				new Object[] { "Descrizione" });
		ValidationUtils.rejectIfEmpty(errors, "interestPointArea", "required",
				new Object[] { "interestPointArea" });

		if (!errors.hasFieldErrors("lat")) {
			Double latit = ((AddInterestPointForm) target).getLat();
			if (latit < 0 || latit > 90) {
				errors.rejectValue("lat", "invalid");
			}
		}
		if (!errors.hasFieldErrors("lon")) {
			Double lon = ((AddInterestPointForm) target).getLon();
			if (lon < 0 || lon > 180) {
				errors.rejectValue("lon", "invalid");
			}
		}
		if (!errors.hasFieldErrors("interestPointArea")) {
			String interestPointArea = ((AddInterestPointForm) target).getInterestPointArea();
			if (interestPointArea.equals("")) {
				errors.rejectValue("interestPointArea", "invalid");
			}
		}
		

	}

}
