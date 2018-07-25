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
package it.unibo.paserver.repository.test;

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.FriendshipBuilder;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.repository.FriendshipRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		TestDataContextConfiguration.class })
@Transactional
public class JpaFriendshipRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private FriendshipRepository friendshipRepository;

	private User john2;
	private User john3;
	private User mario;

	private FriendshipStatus statusJohn2John3;
	private FriendshipStatus statusJohn2Mario;
	private FriendshipStatus statusMarioJohn3;

	private Friendship friendshipJohn2John3;
	private Friendship friendshipJohn2Mario;
	private Friendship friendshipMarioJohn3;

	private Set<Friendship> john2Friendships;
	private Set<Friendship> john3Friendships;
	private Set<Friendship> marioFriendships;

	private Action action;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		action = new ActionSensing();
		action.setDuration_threshold(20);
		action.setNumeric_threshold(0);
		((ActionSensing) action).setInput_type(Pipeline.Type.LOCATION.toInt());
		action.setName("Geolocation");

		john2Friendships = new HashSet<Friendship>();
		john3Friendships = new HashSet<Friendship>();
		marioFriendships = new HashSet<Friendship>();

		Badge badgeOne = new BadgeActionBuilder()
				.setActionType(action.getType())
				.setDescription("Prima Descrizione Badge Utente")
				.setTitle("Primo Titolo Badge Utente").setQuantity(1).build();
		Badge badgeTwo = new BadgeActionBuilder()
				.setActionType(action.getType())
				.setDescription("Seconda Descrizione Badge Utente")
				.setTitle("Secondo Titolo Badge Utente").setQuantity(1).build();
		Set<Badge> badgesForUsers = new HashSet<Badge>(2);
		badgesForUsers.add(badgeOne);
		badgesForUsers.add(badgeTwo);
		john2 = new UserBuilder().setCredentials("john2@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430912")
				.setName("Mario").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Rossi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRK")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("john2@studio.unibo.it")
				.setProjectEmail("john2@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badgesForUsers)
				.build();

		john3 = new UserBuilder().setCredentials("john3@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430913")
				.setName("Mario").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Rossi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRV")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("john3@studio.unibo.it")
				.setProjectEmail("john6@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badgesForUsers)
				.build(true);

		mario = new UserBuilder().setCredentials("mario@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430914")
				.setName("Mario").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Rossi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRP")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("mario@studio.unibo.it")
				.setProjectEmail("mario@gmail.com")
				.setProjectPhoneNumber("+39333012315")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badgesForUsers)
				.build(true);

		statusJohn2John3 = FriendshipStatus.ACCEPTED;
		friendshipJohn2John3 = new FriendshipBuilder().setSender(john2)
				.setReceiver(john3).setStatus(statusJohn2John3).build();
		john2Friendships.add(friendshipJohn2John3);
		john3Friendships.add(friendshipJohn2John3);

		statusJohn2Mario = FriendshipStatus.PENDING;
		friendshipJohn2Mario = new FriendshipBuilder().setSender(john2)
				.setReceiver(mario).setStatus(statusJohn2Mario).build();
		john2Friendships.add(friendshipJohn2Mario);
		marioFriendships.add(friendshipJohn2Mario);

		statusMarioJohn3 = FriendshipStatus.REJECTED;
		friendshipMarioJohn3 = new FriendshipBuilder().setSender(mario)
				.setReceiver(john3).setStatus(statusMarioJohn3).build();
		john3Friendships.add(friendshipMarioJohn3);
		marioFriendships.add(friendshipMarioJohn3);

	}

	@Test
	public void testGetFriendshipsForUser() {
		List<Friendship> foundJohn2 = friendshipRepository
				.getFriendshipsForUser(john2.getId());
		Set<Friendship> foundJohn2Set = new HashSet<Friendship>(foundJohn2);
		assertEquals(john2Friendships, foundJohn2Set);

		List<Friendship> foundJohn3 = friendshipRepository
				.getFriendshipsForUser(john3.getId());
		Set<Friendship> foundJohn3Set = new HashSet<Friendship>(foundJohn3);
		assertEquals(john3Friendships, foundJohn3Set);

		List<Friendship> foundMario = friendshipRepository
				.getFriendshipsForUser(mario.getId());
		Set<Friendship> foundMarioSet = new HashSet<Friendship>(foundMario);
		assertEquals(marioFriendships, foundMarioSet);
	}

	@Test
	public void testGetFriendshipsForUserAndStatus() {
		HashSet<Friendship> tempSet = new HashSet<Friendship>();
		List<Friendship> foundJohn2 = friendshipRepository
				.getFriendshipsForUserAndStatus(john2.getId(),
						FriendshipStatus.ACCEPTED, true);
		Set<Friendship> foundJohn2Set = new HashSet<Friendship>(foundJohn2);
		tempSet.add(friendshipJohn2John3);
		assertEquals(tempSet, foundJohn2Set);

		List<Friendship> foundJohn3 = friendshipRepository
				.getFriendshipsForUserAndStatus(john3.getId(),
						FriendshipStatus.REJECTED, false);
		Set<Friendship> foundJohn3Set = new HashSet<Friendship>(foundJohn3);
		tempSet.clear();
		tempSet.add(friendshipMarioJohn3);
		assertEquals(tempSet, foundJohn3Set);

		List<Friendship> foundMario = friendshipRepository
				.getFriendshipsForUserAndStatus(mario.getId(),
						FriendshipStatus.PENDING, false);
		Set<Friendship> foundMarioSet = new HashSet<Friendship>(foundMario);
		tempSet.clear();
		tempSet.add(friendshipJohn2Mario);
		assertEquals(tempSet, foundMarioSet);
	}

	@Test
	public void testGetFriendshipForSenderAndReceiver() {
		Friendship found = friendshipRepository
				.getFriendshipForSenderAndReceiver(john2.getId(), john3.getId());
		assertEquals(friendshipJohn2John3, found);
	}

}
