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

import it.unibo.paserver.domain.AuthenticationException;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.BinaryDocumentType;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.repository.UserRepository;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory
			.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional(readOnly = false)
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User login(String email, String password)
			throws AuthenticationException {
		User user = this.userRepository.findByEmail(email);
		if (email == null) {
			throw new AuthenticationException(
					"Wrong email/password combination.");
		}
		return user;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteUser(long id) {
		return userRepository.deleteUser(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getUsers() {
		return userRepository.getUsers();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getUserCount() {
		return userRepository.getUserCount();
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(Long id) {
		return userRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getIdScan(Long id) {
		return userRepository.getIdScan(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getLastInvoiceScan(Long id) {
		return userRepository.getLastInvoiceScan(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getPrivacyScan(Long id) {
		return userRepository.getPrivacyScan(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getPresaConsegnaPhone(Long id) {
		return userRepository.getPresaConsegnaPhone(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getPresaConsegnaSIM(Long id) {
		return userRepository.getPresaConsegnaSIM(id);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(Long id, boolean fullFetch) {
		if (!fullFetch) {
			return getUser(id);
		} else {
			User u = userRepository.findById(id);
			// do not remove the following two lines, they are needed to
			// initialzie additional
			// objects inside the hibernate transactional context
			int size = getDocSize(u);
			logger.info("Loaded user {} binary documents size {}",
					u.getOfficialEmail(), size);
			return u;
		}
	}

	protected int getDocSize(User u) {
		BinaryDocument bd = null;
		int size = 0;
		for (BinaryDocumentType type : BinaryDocumentType.values()) {
			switch (type) {
			case ID_SCAN:
				bd = u.getIdScan();
				break;
			case LAST_INVOICE:
				bd = u.getLastInvoiceScan();
				break;
			case PRESA_CONSEGNA_PHONE:
				bd = u.getPresaConsegnaPhoneScan();
				break;
			case PRESA_CONSEGNA_SIM:
				bd = u.getPresaConsegnaSIMScan();
				break;
			case PRIVACY:
				bd = u.getPrivacyScan();
				break;
			default:
				logger.error("Error: unknown document type {}", type);
			}
			if (bd == null || bd.getContent() == null) {
				size += 0;
			} else {
				size += bd.getContent().length;
			}
		}
		return size;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteBinaryDocument(long id) {
		return userRepository.deleteBinaryDocument(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getActiveUsers() {
		return userRepository.getActiveUsers();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> getWorkingUsers(int inputType, DateTime start) {
		return userRepository.getWorkingUsers(inputType,start);
	}
}
