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
package it.unibo.tper.opendata.domain.response;

import it.unibo.tper.domain.TPerBusStop;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TPerBusStopResponse extends BusStopResponse {
	
	@JsonProperty("location")
	private String site;
	@JsonProperty("zone_code")
	private Integer zoneCode;	
	private List<String> lines;

	public TPerBusStopResponse(TPerBusStop tPerBusStop) {
		this.setCode(tPerBusStop.getCode());
		this.setName(tPerBusStop.getName());
		this.setMunicipality(tPerBusStop.getMunicipality());
		this.setPosition(new GPSPosition(tPerBusStop.getLatitude(),tPerBusStop.getLongitude()));
		this.setSite(tPerBusStop.getSite());
		this.setZoneCode(tPerBusStop.getZoneCode());
		this.setLines(tPerBusStop.getLines());
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Integer getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(Integer zoneCode) {
		this.zoneCode = zoneCode;
	}
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}

}
