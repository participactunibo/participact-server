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
@Table(appliesTo = "DataDeviceNetTraffic", indexes = {
		@Index(name = "devnet_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "devnet_ts", columnNames = { "sampletimestamp" }) })
public class DataDeviceNetTraffic extends Data {

	private static final long serialVersionUID = -3206231050724684881L;

	@NotNull
	private Long txBytes;

	@NotNull
	private Long rxBytes;

	public Long getTxBytes() {
		return txBytes;
	}

	public void setTxBytes(Long txBytes) {
		this.txBytes = txBytes;
	}

	public Long getRxBytes() {
		return rxBytes;
	}

	public void setRxBytes(Long rxBytes) {
		this.rxBytes = rxBytes;
	}
}
