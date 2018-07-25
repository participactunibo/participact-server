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
package it.unibo.tper.domain;

import org.joda.time.DateTime;

public interface IBusStop {
	
	Long getId();
	Integer getCode();
	String getName();
	String getMunicipality();
	Double getLatitude();
	Double getLongitude();
	DateTime getCreationTime();

	void setCreationTime(DateTime creationTime);	
	void setId(Long id);
	void setCode(Integer code);
	void setName(String name);
	void setMunicipality(String municipality);
	void setLatitude(Double latitude);
	void setLongitude(Double longitude);


}
