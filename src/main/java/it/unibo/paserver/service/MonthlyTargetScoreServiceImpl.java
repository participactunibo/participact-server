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

import it.unibo.paserver.domain.MonthlyTargetScore;
import it.unibo.paserver.repository.MonthlyTargetScoreRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MonthlyTargetScoreServiceImpl implements MonthlyTargetScoreService {

	@Autowired
	MonthlyTargetScoreRepository monthlyTargetScoreRepository;

	@Override
	@Transactional(readOnly = false)
	public MonthlyTargetScore save(MonthlyTargetScore monthlyTargetScore) {
		return monthlyTargetScoreRepository.save(monthlyTargetScore);
	}

	@Override
	@Transactional(readOnly = true)
	public MonthlyTargetScore findById(long id) {
		return monthlyTargetScoreRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public MonthlyTargetScore findByYearMonth(int year, int month) {
		return monthlyTargetScoreRepository.findByYearMonth(year, month);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		return monthlyTargetScoreRepository.delete(id);
	}

	@Override
	@Transactional(readOnly = false)
	public List<MonthlyTargetScore> getAll() {
		return monthlyTargetScoreRepository.getAll();
	}

}
