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

import it.unibo.paserver.gamificationlogic.PointsStrategy;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Administrator can choose a PointsStrategy for each Task, this class map the
 * association in the database.
 * 
 *
 * @see PointsStrategy
 * @see Task
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class PointsStrategyForTask implements Serializable {

	private static final long serialVersionUID = 7389273072816861608L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_id")
	private Task task;

	private Integer strategyId;

	/**
	 * Returns the id.
	 * 
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the associated Task.
	 * 
	 * @return the associated Task
	 * @see Task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * Sets the associated Task.
	 * 
	 * @param task
	 *            the associated Task
	 * @see Task
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	/**
	 * Returns the strategy id.
	 * 
	 * @return the strategy id
	 * @see PointsStrategy
	 */
	public Integer getStrategyId() {
		return strategyId;
	}

	/**
	 * Sets the strategy id.
	 * 
	 * @param strategyId
	 *            the strategy id
	 * @see PointsStrategy
	 */
	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((strategyId == null) ? 0 : strategyId.hashCode());
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PointsStrategyForTask other = (PointsStrategyForTask) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (strategyId == null) {
			if (other.strategyId != null)
				return false;
		} else if (!strategyId.equals(other.strategyId))
			return false;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}

}
