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
package it.unibo.paserver.manteinance;

import it.unibo.paserver.domain.RecoverPassword;
import it.unibo.paserver.service.RecoverPasswordService;

import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RecoverPasswordManteinance {

	private static final Logger logger = LoggerFactory
			.getLogger(RecoverPasswordManteinance.class);

	@Autowired
	private RecoverPasswordService recoverPasswordService;

	@Scheduled(fixedDelay = 3600000)
	public void purgeOldRequests() {
		logger.info("Purging expired password recovery requests");
		List<RecoverPassword> oldRequests = recoverPasswordService
				.findExpiredAt(new DateTime());
		for (RecoverPassword rp : oldRequests) {
			recoverPasswordService.delete(rp.getId());
		}
	}
}
