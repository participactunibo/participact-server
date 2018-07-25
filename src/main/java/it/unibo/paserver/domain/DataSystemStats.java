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

import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataSystemStats", indexes = {
		@Index(name = "sysstat_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "sysstat_ts", columnNames = { "sampletimestamp" }) })
public class DataSystemStats extends Data {

	private static final long serialVersionUID = -6840772171803572959L;

	private Long BOOT_TIME;

	private Long CONTEXT_SWITCHES;

	private Float CPU_FREQUENCY;

	private Long CPU_HARDIRQ;

	private Long CPU_IDLE;

	private Long CPU_IOWAIT;

	private Long CPU_NICED;

	private Long CPU_SOFTIRQ;

	private Long CPU_SYSTEM;

	private Long CPU_USER;

	private Long MEM_ACTIVE;

	private Long MEM_FREE;

	private Long MEM_INACTIVE;

	private Long MEM_TOTAL;

	private Long PROCESSES;

	public Long getBOOT_TIME() {
		return BOOT_TIME;
	}

	public void setBOOT_TIME(Long BOOT_TIME) {
		this.BOOT_TIME = BOOT_TIME;
	}

	public Long getCONTEXT_SWITCHES() {
		return CONTEXT_SWITCHES;
	}

	public void setCONTEXT_SWITCHES(Long CONTEXT_SWITCHES) {
		this.CONTEXT_SWITCHES = CONTEXT_SWITCHES;
	}

	public Float getCPU_FREQUENCY() {
		return CPU_FREQUENCY;
	}

	public void setCPU_FREQUENCY(Float CPU_FREQUENCY) {
		this.CPU_FREQUENCY = CPU_FREQUENCY;
	}

	public Long getCPU_HARDIRQ() {
		return CPU_HARDIRQ;
	}

	public void setCPU_HARDIRQ(Long CPU_HARDIRQ) {
		this.CPU_HARDIRQ = CPU_HARDIRQ;
	}

	public Long getCPU_IDLE() {
		return CPU_IDLE;
	}

	public void setCPU_IDLE(Long CPU_IDLE) {
		this.CPU_IDLE = CPU_IDLE;
	}

	public Long getCPU_IOWAIT() {
		return CPU_IOWAIT;
	}

	public void setCPU_IOWAIT(Long CPU_IOWAIT) {
		this.CPU_IOWAIT = CPU_IOWAIT;
	}

	public Long getCPU_NICED() {
		return CPU_NICED;
	}

	public void setCPU_NICED(Long CPU_NICED) {
		this.CPU_NICED = CPU_NICED;
	}

	public Long getCPU_SOFTIRQ() {
		return CPU_SOFTIRQ;
	}

	public void setCPU_SOFTIRQ(Long CPU_SOFTIRQ) {
		this.CPU_SOFTIRQ = CPU_SOFTIRQ;
	}

	public Long getCPU_SYSTEM() {
		return CPU_SYSTEM;
	}

	public void setCPU_SYSTEM(Long CPU_SYSTEM) {
		this.CPU_SYSTEM = CPU_SYSTEM;
	}

	public Long getCPU_USER() {
		return CPU_USER;
	}

	public void setCPU_USER(Long CPU_USER) {
		this.CPU_USER = CPU_USER;
	}

	public Long getMEM_ACTIVE() {
		return MEM_ACTIVE;
	}

	public void setMEM_ACTIVE(Long MEM_ACTIVE) {
		this.MEM_ACTIVE = MEM_ACTIVE;
	}

	public Long getMEM_FREE() {
		return MEM_FREE;
	}

	public void setMEM_FREE(Long MEM_FREE) {
		this.MEM_FREE = MEM_FREE;
	}

	public Long getMEM_INACTIVE() {
		return MEM_INACTIVE;
	}

	public void setMEM_INACTIVE(Long MEM_INACTIVE) {
		this.MEM_INACTIVE = MEM_INACTIVE;
	}

	public Long getMEM_TOTAL() {
		return MEM_TOTAL;
	}

	public void setMEM_TOTAL(Long MEM_TOTAL) {
		this.MEM_TOTAL = MEM_TOTAL;
	}

	public Long getPROCESSES() {
		return PROCESSES;
	}

	public void setPROCESSES(Long PROCESSES) {
		this.PROCESSES = PROCESSES;
	}

}
