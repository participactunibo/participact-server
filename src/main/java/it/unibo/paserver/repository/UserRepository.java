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
package it.unibo.paserver.repository;

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.User;

import java.util.List;

import org.joda.time.DateTime;

public interface UserRepository {

	User findByEmail(String email);

	User findById(long id);

	User save(User user);

	/**
	 * Gets the total number of accounts.
	 * 
	 * @return Total accounts count.
	 */
	Long getUserCount();

	List<User> getUsers();

	List<String> getActiveUsers();

	boolean deleteUser(long id);

	BinaryDocument getIdScan(Long id);

	BinaryDocument getLastInvoiceScan(Long id);

	BinaryDocument getPrivacyScan(Long id);

	BinaryDocument getPresaConsegnaPhone(Long id);

	BinaryDocument getPresaConsegnaSIM(Long id);

	boolean deleteBinaryDocument(long id);
	
	public List<User> getWorkingUsers(int inputType, DateTime start);

}
