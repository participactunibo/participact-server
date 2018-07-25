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

import it.unibo.paserver.domain.ClientSWVersion;
import it.unibo.paserver.repository.ClientSWVersionRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientSWVersionServiceImpl implements ClientSWVersionService {

	@Autowired
	ClientSWVersionRepository clientSWVersionRepository;

	@Override
	@Transactional(readOnly = true)
	public ClientSWVersion findById(long id) {
		return clientSWVersionRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = false)
	public ClientSWVersion save(ClientSWVersion clientSWVersion) {
		return clientSWVersionRepository.save(clientSWVersion);
	}

	@Override
	@Transactional(readOnly = true)
	public List<ClientSWVersion> getClientSWVersions() {
		return clientSWVersionRepository.getClientSWVersions();
	}

	/**
	 * Retrieves the latest available version or null if there are no versions
	 * available.
	 */
	@Override
	@Transactional(readOnly = true)
	public ClientSWVersion getLatestVersion() {
		return clientSWVersionRepository.getLatestVersion();
	}

	@Override
	@Transactional(readOnly = false)
	public boolean deleteClientSWVersion(long id) {
		return clientSWVersionRepository.deleteClientSWVersion(id);
	}

}
