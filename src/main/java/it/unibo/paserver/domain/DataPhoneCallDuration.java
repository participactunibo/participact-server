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
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
@Table(appliesTo = "DataPhoneCallDuration", indexes = {
		@Index(name = "phonedur_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "phonedur_ts", columnNames = { "sampletimestamp" }) })
public class DataPhoneCallDuration extends Data {

	private static final long serialVersionUID = 4562522041518010186L;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime callStart;

	@NotNull
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime callEnd;

	@NotNull
	private Boolean isIncoming;

	@NotNull
	private String phoneNumber;

	public Boolean getIsIncoming() {
		return isIncoming;
	}

	public void setIsIncoming(Boolean isIncoming) {
		this.isIncoming = isIncoming;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public DateTime getCallStart() {
		return callStart;
	}

	public void setCallStart(DateTime callStart) {
		this.callStart = callStart;
	}

	public DateTime getCallEnd() {
		return callEnd;
	}

	public void setCallEnd(DateTime callEnd) {
		this.callEnd = callEnd;
	}

}
