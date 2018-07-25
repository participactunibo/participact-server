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

import it.unibo.paserver.domain.GeobadgeCollected;
import it.unibo.paserver.repository.GeobadgeCollectedRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GeobadgeCollectedServiceImpl implements GeobadgeCollectedService {
	
	@Autowired
	GeobadgeCollectedRepository geobadgeCollectedRepository;

	@Override
	public GeobadgeCollected findById(Long id) {
		return geobadgeCollectedRepository.findById(id);
	}

	@Override
	public GeobadgeCollected findByDescription(String description) {
		return geobadgeCollectedRepository.findByDescription(description);
	}

	@Override
	public boolean deleteGeobadge(Long id) {
		
		return geobadgeCollectedRepository.deleteGeobadge(id);
	}

	@Override
	public GeobadgeCollected save(GeobadgeCollected geobadgeCollected) {
		return geobadgeCollectedRepository.save(geobadgeCollected);
	}

	@Override
	public List<? extends GeobadgeCollected> getGeobadgeCollected() {
		
		return geobadgeCollectedRepository.getGeobadgeCollected();
	}

	@Override
	public List<GeobadgeCollected> getGeobadgeCollectedByIdUser(
			Long id) {
		
		return geobadgeCollectedRepository.getGeobadgeCollectedByIdUser(id);
	}

}
