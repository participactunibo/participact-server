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

import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.repository.RecoverPasswordRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecoverPasswordServiceImpl implements RecoverPasswordService {

	@Autowired
	private RecoverPasswordRepository recoverPasswordRepository;

	@Override
	@Transactional(readOnly = true)
	public RecoverPassword findById(long id) {
		return recoverPasswordRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public RecoverPassword findByToken(String token) {
		try {
			return recoverPasswordRepository.findByToken(token);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<RecoverPassword> findByUser(long userId) {
		return recoverPasswordRepository.findByUser(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<RecoverPassword> findExpiredAt(DateTime instant) {
		return recoverPasswordRepository.findExpiredAt(instant);
	}

	@Override
	@Transactional(readOnly = false)
	public RecoverPassword save(RecoverPassword recoverPassword) {
		return recoverPasswordRepository.save(recoverPassword);
	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(long id) {
		return recoverPasswordRepository.delete(id);
	}

}
