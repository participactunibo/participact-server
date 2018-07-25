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
package it.unibo.paserver.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * A BadgeTask is unlocked when the user completes with success
 * the associated Task
 * 
 *
 * @see Task
 *
 */
@Entity
public class BadgeTask extends AbstractBadge {

	private static final long serialVersionUID = 7337607012950805028L;

	@JsonIgnore
	@OneToOne(optional = true, fetch = FetchType.EAGER, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	private Task task;

	/**
	 * Returns the associated Task object.
	 * 
	 * @return the associated Task object
	 * @see Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the associated Task object.
	 * 
	 * @param task the associated Task object
	 * @see Task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

}
