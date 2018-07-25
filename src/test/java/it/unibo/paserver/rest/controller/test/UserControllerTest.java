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
package it.unibo.paserver.rest.controller.test;

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Level.LevelRank;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.rest.FriendshipRestStatus;
import it.unibo.paserver.domain.rest.SocialPresenceFriendsRequest;
import it.unibo.paserver.domain.rest.SocialPresenceRequest;
import it.unibo.paserver.domain.rest.UserRestResult;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.FriendshipBuilder;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.rest.controller.UserRestController;
import it.unibo.paserver.service.SocialPresenceService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
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
		WebComponentsConfig.class, TestDataContextConfiguration.class })
@Transactional
public class UserControllerTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private UserRestController userRestController;

	@Autowired
	private SocialPresenceService socialPresenceService;

	private User primo;
	private User secondo;
	private User terzo;

	private Principal principalPrimo = new Principal() {

		@Override
		public String getName() {
			return "primo@studio.unibo.it";
		}
	};

	private Principal principalSecondo = new Principal() {

		@Override
		public String getName() {
			return "secondo@studio.unibo.it";
		}
	};

	private Principal principalTerzo = new Principal() {

		@Override
		public String getName() {
			return "terzo@studio.unibo.it";
		}
	};

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		primo = new UserBuilder().setCredentials("primo@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430912")
				.setName("Primo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Primi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRK")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("primo@studio.unibo.it")
				.setProjectEmail("primo@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM)
				.setBadges(new HashSet<Badge>()).build();

		secondo = new UserBuilder()
				.setCredentials("secondo@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430913")
				.setName("Secondo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Secondi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRV")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("secondo@studio.unibo.it")
				.setProjectEmail("secondo@gmail.com")
				.setProjectPhoneNumber("+39333012345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM)
				.setBadges(new HashSet<Badge>()).build(true);

		terzo = new UserBuilder().setCredentials("terzo@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430914")
				.setName("Terzo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Terzi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRP")
				.setContactPhoneNumber("+39 051 123456")
				.setOfficialEmail("terzo@studio.unibo.it")
				.setProjectEmail("terzo@gmail.com")
				.setProjectPhoneNumber("+39333012315")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM)
				.setBadges(new HashSet<Badge>()).build(true);

		Friendship friendshipSecondotoTerzo = new FriendshipBuilder()
				.setSender(secondo).setReceiver(terzo)
				.setStatus(FriendshipStatus.ACCEPTED).build();

		Friendship friendshipPrimotoTerzo = new FriendshipBuilder()
				.setSender(primo).setReceiver(terzo)
				.setStatus(FriendshipStatus.REJECTED).build();

		Friendship friendshipPrimotoSecondo = new FriendshipBuilder()
				.setSender(primo).setReceiver(secondo)
				.setStatus(FriendshipStatus.PENDING).build();
	}

	@Test
	public void getUserInfoTest() {
		UserRestResult found = userRestController.getUserInfo(primo.getId());
		UserRestResult expected = new UserRestResult(primo);
		assertEquals(expected, found);

	}

	@Test
	public void getUserLevelTest() {
		LevelRank found = userRestController.getUserLevelRank(primo.getId(),
				"photo");
		assertEquals(LevelRank.LOW, found);
	}

	@Test
	public void getFriendStatusTest() {
		FriendshipRestStatus foundPrimoTerzo = userRestController
				.getFriendStatus(principalPrimo, terzo.getId());
		assertEquals("REJECTED_SENT", foundPrimoTerzo.getStatus());

		FriendshipRestStatus foundTerzoPrimo = userRestController
				.getFriendStatus(principalTerzo, primo.getId());
		assertEquals("REJECTED_RECEIVED", foundTerzoPrimo.getStatus());

		FriendshipRestStatus foundPrimoSecondo = userRestController
				.getFriendStatus(principalPrimo, secondo.getId());
		assertEquals("PENDING_SENT", foundPrimoSecondo.getStatus());

		FriendshipRestStatus foundSecondoPrimo = userRestController
				.getFriendStatus(principalSecondo, primo.getId());
		assertEquals("PENDING_RECEIVED", foundSecondoPrimo.getStatus());

		FriendshipRestStatus foundTerzoSecondo = userRestController
				.getFriendStatus(principalTerzo, secondo.getId());
		assertEquals("ACCEPTED", foundTerzoSecondo.getStatus());

		FriendshipRestStatus foundSecondoTerzo = userRestController
				.getFriendStatus(principalSecondo, terzo.getId());
		assertEquals("ACCEPTED", foundSecondoTerzo.getStatus());

		FriendshipRestStatus foundError = userRestController.getFriendStatus(
				principalPrimo, primo.getId());
		assertEquals("ERROR", foundError.getStatus());
	}

	@Test
	public void getFriendsTest() {
		UserRestResult[] foundArrayPrimoPending = userRestController
				.getFriends(principalPrimo, "PENDING_SENT");
		Set<UserRestResult> foundSetPrimoPending = new HashSet<UserRestResult>(
				Arrays.asList(foundArrayPrimoPending));
		Set<UserRestResult> expectedSetPrimoPending = new HashSet<UserRestResult>(
				1);
		expectedSetPrimoPending.add(new UserRestResult(secondo));
		assertEquals(expectedSetPrimoPending, foundSetPrimoPending);

		UserRestResult[] foundArraySecondoPending = userRestController
				.getFriends(principalSecondo, "PENDING_RECEIVED");
		Set<UserRestResult> foundSetSecondoPending = new HashSet<UserRestResult>(
				Arrays.asList(foundArraySecondoPending));
		Set<UserRestResult> expectedSetSecondoPending = new HashSet<UserRestResult>(
				1);
		expectedSetSecondoPending.add(new UserRestResult(primo));
		assertEquals(expectedSetSecondoPending, foundSetSecondoPending);

		UserRestResult[] foundArrayPrimoRejected = userRestController
				.getFriends(principalPrimo, "REJECTED_SENT");
		Set<UserRestResult> foundSetPrimoRejected = new HashSet<UserRestResult>(
				Arrays.asList(foundArrayPrimoRejected));
		Set<UserRestResult> expectedSetPrimoRejected = new HashSet<UserRestResult>(
				1);
		expectedSetPrimoRejected.add(new UserRestResult(terzo));
		assertEquals(expectedSetPrimoRejected, foundSetPrimoRejected);

		UserRestResult[] foundArrayTerzoRejected = userRestController
				.getFriends(principalTerzo, "REJECTED_RECEIVED");
		Set<UserRestResult> foundSetTerzoRejected = new HashSet<UserRestResult>(
				Arrays.asList(foundArrayTerzoRejected));
		Set<UserRestResult> expectedSetTerzoRejected = new HashSet<UserRestResult>(
				1);
		expectedSetTerzoRejected.add(new UserRestResult(primo));
		assertEquals(expectedSetTerzoRejected, foundSetTerzoRejected);

		UserRestResult[] foundArraySecondoAccepted = userRestController
				.getFriends(principalSecondo, "accepted");
		Set<UserRestResult> foundSetSecondoAccepted = new HashSet<UserRestResult>(
				Arrays.asList(foundArraySecondoAccepted));
		Set<UserRestResult> expectedSetSecondoAccepted = new HashSet<UserRestResult>(
				1);
		expectedSetSecondoAccepted.add(new UserRestResult(terzo));
		assertEquals(expectedSetSecondoAccepted, foundSetSecondoAccepted);

		UserRestResult[] foundArrayterzoAccepted = userRestController
				.getFriends(principalTerzo, "accepted");
		Set<UserRestResult> foundSetTerzoAccepted = new HashSet<UserRestResult>(
				Arrays.asList(foundArrayterzoAccepted));
		Set<UserRestResult> expectedSetterzoAccepted = new HashSet<UserRestResult>(
				1);
		expectedSetterzoAccepted.add(new UserRestResult(secondo));
		assertEquals(expectedSetterzoAccepted, foundSetTerzoAccepted);
	}

	@Test
	public void addFriendTest() {
		FriendshipRestStatus request = new FriendshipRestStatus();
		request.setStatus("accepted");
		Boolean boolValue = userRestController.addFriend(principalPrimo,
				secondo.getId(), request);
		assertEquals(Boolean.valueOf(true), boolValue);

		UserRestResult[] foundArraySecondoAccepted = userRestController
				.getFriends(principalSecondo, "accepted");
		Set<UserRestResult> foundSetSecondoAccepted = new HashSet<UserRestResult>(
				Arrays.asList(foundArraySecondoAccepted));
		Set<UserRestResult> expectedSetSecondoAccepted = new HashSet<UserRestResult>(
				1);
		expectedSetSecondoAccepted.add(new UserRestResult(primo));
		expectedSetSecondoAccepted.add(new UserRestResult(terzo));
		assertEquals(expectedSetSecondoAccepted, foundSetSecondoAccepted);
	}

	@Test
	public void addSocialPresenceTest() {
		SocialPresenceRequest requestPrimo = new SocialPresenceRequest();
		requestPrimo.setSocialId("444");
		Boolean boolValuePrimo = userRestController.addSocialPresence(
				principalPrimo, "facebook", requestPrimo);
		assertEquals(Boolean.valueOf(true), boolValuePrimo);

		SocialPresence foundSocialPresencePrimo = socialPresenceService
				.getSocialPresenceForUserAndSocialNetwork(primo.getId(),
						SocialPresenceType.FACEBOOK);
		assertEquals(requestPrimo.getSocialId(),
				foundSocialPresencePrimo.getSocialId());

	}

	@Test
	public void getFriendsFromSocialTest() {

		SocialPresenceRequest requestAddSecondo = new SocialPresenceRequest();
		requestAddSecondo.setSocialId("8888");
		Boolean boolValueSecondo = userRestController.addSocialPresence(
				principalSecondo, "facebook", requestAddSecondo);
		assertEquals(Boolean.valueOf(true), boolValueSecondo);

		SocialPresence foundSocialPresenceSecondo = socialPresenceService
				.getSocialPresenceForUserAndSocialNetwork(secondo.getId(),
						SocialPresenceType.FACEBOOK);
		assertEquals(requestAddSecondo.getSocialId(),
				foundSocialPresenceSecondo.getSocialId());

		SocialPresenceFriendsRequest request = new SocialPresenceFriendsRequest();
		Set<String> socialIds = new HashSet<String>(1);
		socialIds.add("8888");
		request.setIds(socialIds);

		UserRestResult[] result = userRestController.getFriendsForSocial(
				principalPrimo, "facebook", request);
		Set<UserRestResult> resultSet = new HashSet<UserRestResult>(
				Arrays.asList(result));
		Set<UserRestResult> expectedSet = new HashSet<UserRestResult>();
		// expectedSet.add(new UserRestResult(secondo));
		assertEquals(expectedSet, resultSet);
	}

}
