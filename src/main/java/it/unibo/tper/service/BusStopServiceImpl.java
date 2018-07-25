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
import it.unibo.tper.repository.BusStopRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BusStopServiceImpl implements BusStopService{

	@Autowired
	BusStopRepository busStopRepository;

	@Override
	public BusStop findById(Long id) {
		return busStopRepository.findById(id);
	}

	@Override
	public BusStop findByCode(Integer code) {
		return busStopRepository.findByCode(code);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteBusStop(Long id) {
		return busStopRepository.deleteBusStop(id);
	}

	@Override
	@Transactional(readOnly = false)
	public BusStop save(BusStop busStop) {
		return busStopRepository.save(busStop);
	}




	@Override
	public Long getBusStopCount() {
		return busStopRepository.getBusStopCount();
	}

	@Override
	public List<? extends BusStop> getBusStops() {
		return busStopRepository.getBusStops();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateBusStopData(List<? extends BusStop> bs , DateTime threshold) {
		for(BusStop b : bs)
		{
			BusStop busStop = busStopRepository.findByCode(b.getCode());
			if(busStop != null)
				b.setId(busStop.getId());
			busStopRepository.save(b);
		}

		List<? extends BusStop> obsolete = busStopRepository.getObsoleteBusStops(threshold);

		for(BusStop b : obsolete)
			busStopRepository.deleteBusStop(b.getId());

	}

	@Override
	public List<? extends BusStop> getObsoleteBusStops(DateTime threshold) {

		return busStopRepository.getObsoleteBusStops(threshold);
	}

	@Override
	public List<? extends BusStop> getBusStopsByRadius(Double latitude,
			Double longitude, Double radius) {
		return busStopRepository.getBusStopsByRadius(latitude, longitude, radius);
	}

}
