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

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.UserLogFile;
import it.unibo.paserver.repository.UserLogFileRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLogFileServiceImpl implements UserLogFileService {

	@Autowired
	UserLogFileRepository userLogFileRepository;

	@Override
	@Transactional(readOnly = true)
	public UserLogFile findById(long id) {
		return userLogFileRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public UserLogFile save(UserLogFile userLogFile) {
		return userLogFileRepository.save(userLogFile);
	}

	@Override
	@Transactional(readOnly = false)
	public UserLogFile merge(UserLogFile userLogFile) {
		return userLogFileRepository.merge(userLogFile);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserLogFile> getUserLogFiles() {
		return userLogFileRepository.getUserLogFiles();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteUserLogFile(long id) {
		return userLogFileRepository.deleteUserLogFile(id);
	}

	@Override
	@Transactional(readOnly = true)
	public BinaryDocument getLogFile(long userLogFileId) {
		return userLogFileRepository.getLogFile(userLogFileId);
	}

}
