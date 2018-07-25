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

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="pipelinetaskprogress")
@Entity
public class PipelineTaskProgress implements Serializable{

	private static final long serialVersionUID = -114684910353595659L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long data_id;
	
	private long task_pipeline_id;
	
	private long group_id;
	
	private int task_step;
	
	private long task_step_id;

	public long getData_id() {
		return data_id;
	}

	public void setData_id(long data_id) {
		this.data_id = data_id;
	}

	public long getTask_pipeline_id() {
		return task_pipeline_id;
	}

	public void setTask_pipeline_id(long task_pipeline_id) {
		this.task_pipeline_id = task_pipeline_id;
	}

	public long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}

	public int getTask_step() {
		return task_step;
	}

	public void setTask_step(int task_step) {
		this.task_step = task_step;
	}

	public long getTask_step_id() {
		return task_step_id;
	}

	public void setTask_step_id(long task_step_id) {
		this.task_step_id = task_step_id;
	}
}
