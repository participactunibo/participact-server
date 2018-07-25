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
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class TaskUser implements Serializable{

	private static final long serialVersionUID = -4339350368096489878L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.REFRESH })
	@JoinColumn(name = "user_id")
	private User owner;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Task task;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE,
			CascadeType.REFRESH })
	private Set<User> usersToAssign;

	public Set<User> getUsersToAssign() {
		return usersToAssign;
	}

	public void setUsersToAssign(Set<User> usersToAssign) {
		this.usersToAssign = usersToAssign;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@NotNull
	@Enumerated(EnumType.STRING)
	private TaskValutation valutation = TaskValutation.PENDING;

	public TaskValutation getValutation() {
		return valutation;
	}

	public void setValutation(TaskValutation valutation) {
		this.valutation = valutation;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
