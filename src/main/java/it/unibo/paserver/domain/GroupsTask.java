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

@Table(name = "groupstask")
@Entity
public class GroupsTask implements Serializable {

	private static final long serialVersionUID = 556439058694411146L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long dataId;

	private long groupId;

	private long componentOfGroupId;

	public long getDataId() {
		return dataId;
	}

	public void setDataId(long id) {
		this.dataId = id;
	}

	public long getComponentOfGroupId() {
		return componentOfGroupId;
	}

	public void setComponentOfGroupId(long componentOfGroupId) {
		this.componentOfGroupId = componentOfGroupId;
	}

	public String toString() {
		return this.dataId + " " + this.groupId + " " + this.componentOfGroupId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
}
