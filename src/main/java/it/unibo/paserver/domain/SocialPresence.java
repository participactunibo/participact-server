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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * SocialPresence is used to create a connection between
 * a Participact User, and a social network user.
 * 
 *
 * @see User
 *
 */
@Entity
public class SocialPresence implements Serializable {

	/**
	 * The supported social networks,
	 * to date Facebook, Twitter and Google.
	 *
	 *
	 */
	public enum SocialPresenceType {
		FACEBOOK, GOOGLE, TWITTER
	};

	private static final long serialVersionUID = 2404331121449906954L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private User user;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SocialPresenceType socialNetwork;

	@NotEmpty
	private String socialId;

	/**
	 * Returns the id on the social network of the User.
	 * 
	 * @return the id on the social network of the User
	 */
	public String getSocialId() {
		return socialId;
	}

	/**
	 * Sets the id on the social network of the User.
	 * 
	 * @param socialId the id on the social network of the User
	 */
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	/**
	 * Returns the Participact User.
	 * 
	 * @return the Participact User
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Sets the Participact User.
	 * 
	 * @param user the Participact User
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Returns the social network to which the SocialPresence is associated.
	 * 
	 * @return the social network to which the SocialPresence is associated
	 * @see SocialPresenceType
	 */
	public SocialPresenceType getSocialNetwork() {
		return socialNetwork;
	}

	/**
	 * Sets the social network to which the SocialPresence is associated.
	 * 
	 * @param socialNetwork the social network to which the SocialPresence is associated
	 * @see SocialPresenceType
	 */
	public void setSocialNetwork(SocialPresenceType socialNetwork) {
		this.socialNetwork = socialNetwork;
	}

	/**
	 * Returns the id.
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id the id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((socialId == null) ? 0 : socialId.hashCode());
		result = prime * result
				+ ((socialNetwork == null) ? 0 : socialNetwork.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		SocialPresence other = (SocialPresence) obj;
		if (socialId == null) {
			if (other.socialId != null)
				return false;
		} else if (!socialId.equals(other.socialId))
			return false;
		if (socialNetwork != other.socialNetwork)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
