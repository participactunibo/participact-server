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
package it.unibo.paserver.service;

import it.unibo.paserver.domain.InterestPoint;
import it.unibo.paserver.repository.InterestPointRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class InterestPointServiceImpl implements InterestPointService{
	
	@Autowired
	InterestPointRepository interestPointRepository;

	@Override
	public InterestPoint findById(Long id) {
		
		return interestPointRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public InterestPoint findByDescription(String description) {
		
		return interestPointRepository.findByDescription(description);
	}

	@Override
	@Transactional(readOnly = true)
	public InterestPoint findByLat(Double lat) {
		
		return interestPointRepository.findByLat(lat);
	}

	@Override
	@Transactional(readOnly = true)
	public InterestPoint findByLon(Double lon) {
		
		return interestPointRepository.findByLon(lon);
	}

	@Override
	@Transactional
	public boolean deleteInterestPoint(Long id) {
		
		return interestPointRepository.deleteInterestPoint(id);
	}

	@Override
	@Transactional
	public InterestPoint save(InterestPoint interestPoint) {
		
		return interestPointRepository.save(interestPoint);
	}

	@Override
	@Transactional(readOnly = true)
	public List<? extends InterestPoint> getInterestPoint() {
		
		return interestPointRepository.getInterestPoint();
	}

	@Override
	@Transactional(readOnly = true)
	public InterestPoint findByLatLon(Double lat, Double lon) {
		return interestPointRepository.findByLatLon(lat, lon);
	}

}
