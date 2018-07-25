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

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "datagroups")
@Entity
public class DataGroups implements Serializable {

	private static final long serialVersionUID = 556439058794411146L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dataid;

	private long userid;

	private long idusergroup;

	private String type;

	public long getId_user_group() {
		return idusergroup;
	}

	public void setId_user_group(long id_user_group) {
		this.idusergroup = id_user_group;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public long getUser_id() {
		return userid;
	}

	public void setUser_id(long user_id) {
		this.userid = user_id;
	}

	public long getData_id() {
		return dataid;
	}

	public void setData_id(long data_id) {
		this.dataid = data_id;
	}
}
