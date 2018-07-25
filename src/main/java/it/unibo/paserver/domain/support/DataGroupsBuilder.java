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
package it.unibo.paserver.domain.support;

import org.springframework.stereotype.Component;

import it.unibo.paserver.domain.DataGroups;

@Component
public class DataGroupsBuilder extends EntityBuilder<DataGroups> {

	@Override
	void initEntity() {
		entity = new DataGroups();

	}

	@Override
	DataGroups assembleEntity() {
		return entity;
	}

	public DataGroupsBuilder setData_id(long data_id) {
		entity.setData_id(data_id);
		return this;
	}

	public DataGroupsBuilder setId_user_group(long id_user_group) {
		entity.setId_user_group(id_user_group);
		return this;
	}

	public DataGroupsBuilder setType(String type) {
		entity.setType(type);
		return this;
	}

	public DataGroupsBuilder setUser_id(long user_id) {
		entity.setUser_id(user_id);
		return this;
	}
}
