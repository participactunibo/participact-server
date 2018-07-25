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

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public class ResetPasswordForm implements Serializable {

	private static final long serialVersionUID = 3513914189160222353L;
	private String password;
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void validateChoosePassword(ValidationContext context) {
		if (context.getUserEvent().equals("change")) {
			MessageContext msg = context.getMessageContext();
			if (StringUtils.isEmpty(getPassword())) {
				msg.addMessage(new MessageBuilder().error().source("password")
						.code("required.resetPasswordForm").build());
			} else if (getPassword().length() < 8) {
				msg.addMessage(new MessageBuilder().error().source("password")
						.code("tooshort.resetPasswordForm.password").build());
			} else if (StringUtils.isEmpty(getConfirmPassword())) {
				msg.addMessage(new MessageBuilder().error()
						.source("confirmPassword")
						.code("required.resetPasswordForm").build());
			} else if (!getPassword().equals(getConfirmPassword())) {
				msg.addMessage(new MessageBuilder().error()
						.source("confirmPassword")
						.code("notequal.resetPasswordForm.confirmPassword")
						.build());
			}
		}
	}
}
