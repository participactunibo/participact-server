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

import java.util.List;

import org.joda.time.DateTime;

public interface RecoverPasswordService {

	RecoverPassword findById(long id);

	RecoverPassword findByToken(String token);

	List<RecoverPassword> findByUser(long userId);

	List<RecoverPassword> findExpiredAt(DateTime instant);

	RecoverPassword save(RecoverPassword recoverPassword);

	boolean delete(long id);

}
