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
package it.unibo.tper.service;

import it.unibo.tper.domain.BusStop;

import java.util.List;

import org.joda.time.DateTime;

public interface BusStopService {
	
	BusStop findById(Long id);
	BusStop findByCode(Integer code);
	boolean deleteBusStop(Long id);
	BusStop save(BusStop busStop);
	Long getBusStopCount();
	List<? extends BusStop> getBusStops();
	List<? extends BusStop> getObsoleteBusStops(DateTime threshold);
	void updateBusStopData(List<? extends BusStop> bs, DateTime threshold);
	List< ? extends BusStop> getBusStopsByRadius(Double latitude, Double longitude, Double radius);

	
}
