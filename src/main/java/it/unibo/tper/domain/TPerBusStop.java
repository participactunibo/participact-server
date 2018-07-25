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



import java.util.ArrayList;
import java.util.List;

import it.unibo.tper.opendata.domain.response.BusStopResponse;
import it.unibo.tper.opendata.domain.response.TPerBusStopResponse;
import it.unibo.tper.ws.domain.extensions.FermateResponse;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

@Entity
@Table(name="tper_bus_stop")
public class TPerBusStop extends BusStop {
	
	private static final long serialVersionUID = 4344890749260020232L;
	private String site;
	@NotNull
	private Integer coordinateX;
	@NotNull
	private Integer coordinateY;
	@NotNull
	private Integer zoneCode;	
	@ElementCollection(fetch=FetchType.EAGER)
	@NotNull
	private List<String> lines;
	
	
	
	

	public TPerBusStop() {
		
	}
	
	public TPerBusStop(FermateResponse.Table tperResponseBusStop)
	{
				
		this.setCode(tperResponseBusStop.getCodiceFermata());
		this.setName(tperResponseBusStop.getDenominazione());
		this.setMunicipality(tperResponseBusStop.getComune());
		this.setCoordinateX(tperResponseBusStop.getCoordinataX());
		this.setCoordinateY(tperResponseBusStop.getCoordinataY());
		this.setSite(tperResponseBusStop.getUbicazione());
		this.setZoneCode(tperResponseBusStop.getCodiceZona());
		this.setLatitude(tperResponseBusStop.getLatitudine());
		this.setLongitude(tperResponseBusStop.getLongitudine());
		this.setCreationTime(new DateTime());
		this.setLines(new ArrayList<String>());
	}
	

	public List<String> getLines() {
		return lines;
	}

	public void setLines(List<String> lines) {
		this.lines = lines;
	}

	
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getCoordinateX() {
		return coordinateX;
	}
	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}
	public Integer getCoordinateY() {
		return coordinateY;
	}
	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}
	public Integer getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(Integer zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Override
	public BusStopResponse converToBusStopResponse() {
		return new TPerBusStopResponse(this);
	}
	
	
	


}
