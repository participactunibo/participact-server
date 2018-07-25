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
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.SocialPresence;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.SocialPresenceBuilder;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.repository.SocialPresenceRepository;

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
public class JpaSocialPresenceRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SocialPresenceRepository socialPresenceRepository;

	private User user;
	private User user2;
	private SocialPresence twitterPresence;
	private SocialPresence twitterPresence2;
	private SocialPresence facebookPresence;
	private SocialPresence googlePresence;
	private Set<SocialPresence> allPresences;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		Action action = new ActionSensing();
		action.setDuration_threshold(20);
		action.setNumeric_threshold(0);
		((ActionSensing) action).setInput_type(Pipeline.Type.LOCATION.toInt());
		action.setName("Geolocation");

		Badge badgeOne = new BadgeActionBuilder()
				.setDescription("Prima Descrizione Badge Utente")
				.setTitle("Primo Titolo Badge Utente")
				.setActionType(action.getType()).setQuantity(1).build();
		Badge badgeTwo = new BadgeActionBuilder()
				.setDescription("Seconda Descrizione Badge Utente")
				.setTitle("Secondo Titolo Badge Utente")
				.setActionType(action.getType()).setQuantity(1).build();
		Set<Badge> badgesForUser = new HashSet<Badge>(2);
		badgesForUser.add(badgeOne);
		badgesForUser.add(badgeTwo);
		user = new UserBuilder().setCredentials("john2@sample.com", "secret")
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
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badgesForUser)
				.build();

		user2 = new UserBuilder().setCredentials("john3@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430914")
				.setName("Mario").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Rossi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F5TRW")
				.setContactPhoneNumber("+39 051 423456")
				.setOfficialEmail("john3@studio.unibo.it")
				.setProjectEmail("john3@gmail.com")
				.setProjectPhoneNumber("+39333412345")
				.setUniCity(UniCity.BOLOGNA).setUniCourse(UniCourse.TRIENNALE)
				.setUniYear(2).setUniDegree("Ingegneria Informatica")
				.setUniDepartment("DISI")
				.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
				.setUniIsSupplementaryYear(false).setHasPhone(false)
				.setWantsPhone(false).setIsActive(true).setHasSIM(false)
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badgesForUser)
				.build();

		twitterPresence = new SocialPresenceBuilder().setSocialId("1111")
				.setSocialNetwork(SocialPresenceType.TWITTER).setUser(user)
				.build();
		twitterPresence2 = new SocialPresenceBuilder().setSocialId("3333")
				.setSocialNetwork(SocialPresenceType.TWITTER).setUser(user2)
				.build();
		facebookPresence = new SocialPresenceBuilder().setSocialId("2222")
				.setSocialNetwork(SocialPresenceType.FACEBOOK).setUser(user)
				.build();
		googlePresence = new SocialPresenceBuilder().setSocialId("4444")
				.setSocialNetwork(SocialPresenceType.GOOGLE).setUser(user)
				.build();

		allPresences = new HashSet<SocialPresence>();
		allPresences.add(twitterPresence);
		allPresences.add(facebookPresence);
		allPresences.add(googlePresence);

	}

	@Test
	public void testGetSocialPresencesForUser() {
		List<SocialPresence> foundList = socialPresenceRepository
				.getSocialPresencesForUser(user.getId());
		Set<SocialPresence> foundSet = new HashSet<SocialPresence>(foundList);
		assertEquals(allPresences, foundSet);
	}

	@Test
	public void testGetSocialPresenceForUserAndSocialNetwork() {
		SocialPresence foundTwitter = socialPresenceRepository
				.getSocialPresenceForUserAndSocialNetwork(user.getId(),
						SocialPresenceType.TWITTER);
		assertEquals(twitterPresence, foundTwitter);

		SocialPresence foundFacebook = socialPresenceRepository
				.getSocialPresenceForUserAndSocialNetwork(user.getId(),
						SocialPresenceType.FACEBOOK);
		assertEquals(facebookPresence, foundFacebook);

		SocialPresence foundGoogle = socialPresenceRepository
				.getSocialPresenceForUserAndSocialNetwork(user.getId(),
						SocialPresenceType.GOOGLE);
		assertEquals(googlePresence, foundGoogle);
	}

	@Test
	public void testGetSocialPresencesForSocialNetwork() {
		List<SocialPresence> foundList = socialPresenceRepository
				.getSocialPresencesForSocialNetwork(SocialPresenceType.TWITTER);
		Set<SocialPresence> foundSet = new HashSet<SocialPresence>(foundList);
		Set<SocialPresence> twitterSet = new HashSet<SocialPresence>();
		twitterSet.add(twitterPresence);
		twitterSet.add(twitterPresence2);
		assertEquals(twitterSet, foundSet);
	}

}
