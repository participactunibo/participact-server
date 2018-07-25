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

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataPhoneCallEvent", indexes = {
		@Index(name = "phoneevt_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "phoneevt_ts", columnNames = { "sampletimestamp" }) })
public class DataPhoneCallEvent extends Data {

	private static final long serialVersionUID = -837842733892409270L;

	@NotNull
	private Boolean isStart;

	@NotNull
	private Boolean isIncomingCall;

	@NotNull
	private String phoneNumber;

	public Boolean getIsStart() {
		return isStart;
	}

	public void setIsStart(Boolean isStart) {
		this.isStart = isStart;
	}

	public Boolean getIsIncomingCall() {
		return isIncomingCall;
	}

	public void setIsIncomingCall(Boolean isIncomingCall) {
		this.isIncomingCall = isIncomingCall;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
