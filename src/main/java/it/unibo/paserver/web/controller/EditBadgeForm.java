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
package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.Badge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EditBadgeForm extends BadgeForm {

	private static final Logger logger = LoggerFactory
			.getLogger(EditBadgeForm.class);

	public void initFromBadge(Badge badge) {
		setTitle(badge.getTitle());
		setDescription(badge.getDescription());
	}

	public void updateBadge(Badge badge) {

		if (getTitle() != null) {
			badge.setTitle(getTitle());
		} else if (badge.getTitle() != null && getTitle() == null) {
			badge.setTitle("");
		}

		if (getDescription() != null) {
			badge.setDescription(getDescription());
		} else if (badge.getDescription() != null && getDescription() == null) {
			badge.setDescription("");
		}

	}
}
