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

import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.User;

import java.util.List;

/**
 * Service for FriendshipService entities.
 * 
 *
 * @see Friendship
 *
 */
public interface FriendshipService {

	/**
	 * Saves the given Friendship.
	 * 
	 * @param friendship Friendship to persist
	 * @return saved Friendship
	 * @see Friendship
	 */
	Friendship save(Friendship friendship);

	/**
	 * Creates a new Friendship with given arguments.
	 * 
	 * @param sender Friendship sender
	 * @param receiver Friendship receiver
	 * @param status Friendship status
	 * @return the new Friendship
	 * @see Friendship
	 * @see User
	 * @see FriendshipStatus
	 */
	Friendship create(User sender, User receiver, FriendshipStatus status);

	/**
	 * Returns all Friendships for given User id.
	 * 
	 * @param userId User id
	 * @return List of Friendship for given User id
	 * @see Friendship
	 * @see User
	 */
	List<Friendship> getFriendshipsForUser(long userId);

	/**
	 * Returns Friendships for given User id and status.
	 * 
	 * @param userId User id
	 * @param status Friendship status
	 * @return List of Friendship for given User id and status
	 * @see Friendship
	 * @see User
	 * @see FriendshipStatus
	 */
	List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status);

	/**
	 * Returns Friendships for given User id, status and role.
	 * 
	 * @param userId User id
	 * @param status Friendship status
	 * @param sender true if you want Friendships where given User is the sender, false if you want Friendships where given User is the receiver
	 * @return Friendships for given User id and status and role
	 * @see Friendship
	 * @see FriendshipStatus
	 */
	List<Friendship> getFriendshipsForUserAndStatus(long userId,
			FriendshipStatus status, boolean sender);

	/**
	 * Returns Friends for given User id and status.
	 * 
	 * @param userId User id
	 * @param status Friendship status
	 * @param sender true if you want Friendships where given User is the sender, false if you want Friendships where given User is the receiver
	 * @return List of User who are friends with given User id and status
	 * @see User
	 * @see FriendshipStatus
	 */
	List<User> getFriendsForUserAndStatus(long userId, FriendshipStatus status,
			boolean sender);

	/**
	 * Returns Friendship for given sender and receiver.
	 * 
	 * @param senderId sender id
	 * @param receiverId receiver id
	 * @return Friendship for given sender and receiver (if any)
	 * @see Friendship
	 */
	Friendship getFriendshipForSenderAndReceiver(long senderId, long receiverId);

	/**
	 * Returns all Friendships in database.
	 * 
	 * @return all Friendships in database
	 * @see Friendship 
	 */
	List<Friendship> getFriendships();

	/**
	 * Returns the amount of Friendships saved in database.
	 * 
	 * @return the amount of Friendships saved in database
	 */
	Long getFriendshipsCount();

}
