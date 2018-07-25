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
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.repository.AccountRepository;
import it.unibo.paserver.repository.UserRepository;

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
		TestDataContextConfiguration.class })
@Transactional
public class JpaUserRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	UserRepository userRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private User user;
	private Set<Badge> badges;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		Action action = new ActionSensing();
		action.setDuration_threshold(20);
		action.setNumeric_threshold(0);
		((ActionSensing) action).setInput_type(Pipeline.Type.LOCATION.toInt());
		action.setName("Geolocation");
		Badge badgeOne = new BadgeActionBuilder()
				.setDescription("Prima Descrizione").setTitle("Primo Titolo")
				.setActionType(action.getType()).setQuantity(1).build();
		Badge badgeTwo = new BadgeActionBuilder()
				.setDescription("Seconda Descrizione")
				.setTitle("Secondo Titolo").setActionType(action.getType())
				.setQuantity(1).build();
		badges = new HashSet<Badge>(2);
		badges.add(badgeOne);
		badges.add(badgeTwo);
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
				.setSimStatus(SimStatus.NEW_SIM).setBadges(badges).build();
	}

	@Test
	public void testFindUserById() {
		User found = userRepository.findById(user.getId());
		assertEquals(user.getOfficialEmail(), found.getOfficialEmail());
		assertEquals(user.getBadges(), found.getBadges());
	}

	@Test
	public void testFindUserByEmail() {
		User found = userRepository.findByEmail(user.getOfficialEmail());
		assertEquals(user.getOfficialEmail(), found.getOfficialEmail());
	}

	@Test
	public void createdBy() {
		User user2 = new UserBuilder()
				.setCredentials("john3@sample.com", "secret")
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
				.setSimStatus(SimStatus.NEW_SIM).build(true);
		userRepository.save(user2);
		User found = userRepository.findByEmail(user2.getOfficialEmail());
		assertEquals(user2.getOfficialEmail(), found.getOfficialEmail());
	}
}
