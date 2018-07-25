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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * AbstractBadge is the abstract base class for all badges. An AbstractBadge
 * object encapsulates the title, the description and a list of users who
 * currently have unlocked the badge.
 * 
 *
 *
 */
@Entity
@Table(name = "badge")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class AbstractBadge implements Serializable, Badge,
		Comparable<Badge> {

	private static final long serialVersionUID = -742994218355625826L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "badge_id")
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String title;

	@NotEmpty
	@Column(unique = true)
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "badges", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	private List<User> users;

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		AbstractBadge other = (AbstractBadge) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public int compareTo(Badge o) {
		if (o == null)
			return 1;
		Badge badge = (Badge) o;
		if (equals(o))
			return 0;

		int compareTitle = title.compareTo(badge.getTitle());

		if (compareTitle != 0)
			return compareTitle;

		return description.compareTo(badge.getDescription());

	}

}
