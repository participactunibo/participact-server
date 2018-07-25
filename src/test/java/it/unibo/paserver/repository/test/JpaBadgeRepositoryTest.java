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
import it.unibo.paserver.domain.BadgeActions;
import it.unibo.paserver.domain.BadgeTask;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.BadgeTaskBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.repository.BadgeRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;
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
public class JpaBadgeRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	BadgeRepository badgeRepository;

	private Badge badgeActions;
	private Badge badgeTask;
	private Badge badgeOne;
	private Badge badgeTwo;
	private Badge badgeThree;
	private Set<Badge> badgesForUser;
	private User user;
	private Action actionOne;
	private Integer quantityOne;
	private Integer quantityThree;
	private Action actionTwo;
	private Task taskOne;
	private Task taskTwo;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		quantityOne = 1;
		quantityThree = 3;
		actionOne = new ActionSensing();
		actionOne.setDuration_threshold(20);
		actionOne.setNumeric_threshold(0);
		((ActionSensing) actionOne).setInput_type(Pipeline.Type.LOCATION
				.toInt());
		actionOne.setName("Geolocation");
		actionTwo = new ActionSensing();
		actionTwo.setName("Accelerometer");
		actionTwo.setDuration_threshold(2);
		actionTwo.setNumeric_threshold(0);
		((ActionSensing) actionTwo).setInput_type(Pipeline.Type.ACCELEROMETER
				.toInt());

		taskOne = new Task();
		taskOne.setName("Geolocation");
		taskOne.setDescription("Geolocation task");
		taskOne.setStart(new DateTime());
		taskOne.setDeadline(new DateTime().plusDays(30));
		taskOne.setDuration(10L);
		taskOne.setSensingDuration(2L);
		Set<Action> actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionOne);
		taskOne.setActions(actionsg);

		taskTwo = new Task();
		taskTwo.setName("Geolocation2");
		taskTwo.setDescription("Geolocation task2");
		taskTwo.setStart(new DateTime());
		taskTwo.setDeadline(new DateTime().plusDays(30));
		taskTwo.setDuration(10L);
		taskTwo.setSensingDuration(2L);
		Set<Action> actionsg2 = new LinkedHashSet<Action>();
		actionsg2.add(actionTwo);
		taskTwo.setActions(actionsg2);

		badgeActions = new BadgeActionBuilder()
				.setDescription("Prima Descrizione").setTitle("Primo Titolo")
				.setActionType(actionOne.getType())
				.setQuantity(quantityOne + 1).build();

		badgeTask = new BadgeTaskBuilder()
				.setDescription("Seconda Descrizione")
				.setTitle("Secondo Titolo").setTask(taskOne).build();

		badgeOne = new BadgeActionBuilder()
				.setDescription("Prima Descrizione Badge Utente")
				.setTitle("Primo Titolo Badge Utente")
				.setActionType(actionOne.getType()).setQuantity(quantityOne)
				.build();
		badgeTwo = new BadgeTaskBuilder()
				.setDescription("Seconda Descrizione Badge Utente")
				.setTitle("Secondo Titolo Badge Utente").setTask(taskTwo)
				.build();
		
		badgeThree = new BadgeActionBuilder()
		.setDescription("Terza Descrizione Badge")
		.setTitle("Terzo Titolo Badge")
		.setActionType(actionOne.getType()).setQuantity(quantityThree)
		.build();
		
		badgesForUser = new HashSet<Badge>(2);
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

	}

	@Test
	public void testFindBadgeById() {
		Badge found1 = badgeRepository.findById(badgeActions.getId());
		assertEquals(badgeActions.getTitle(), found1.getTitle());
		assertEquals(badgeActions.getDescription(), found1.getDescription());

		Badge found2 = badgeRepository.findById(badgeTask.getId());
		assertEquals(badgeTask.getTitle(), found2.getTitle());
		assertEquals(badgeTask.getDescription(), found2.getDescription());
	}

	@Test
	public void testGetBadgesForUser() {
		@SuppressWarnings("unchecked")
		Set<Badge> foundSet = (Set<Badge>) badgeRepository
				.getBadgesForUser(user.getId());
		assertEquals(badgesForUser, foundSet);
	}

	@Test
	public void testGetBadgesForActionType() {
		List<BadgeActions> foundList = badgeRepository
				.getBadgesForActionType(actionOne.getType());
		Set<Badge> foundSet = new HashSet<Badge>(foundList);
		Set<Badge> tempSet = new HashSet<Badge>();
		tempSet.add(badgeActions);
		tempSet.add(badgeOne);
		tempSet.add(badgeThree);
		assertEquals(tempSet, foundSet);
	}

	@Test
	public void testGetBadgesForActionAndQuantity() {
		BadgeActions found = badgeRepository.getBadgeForActionTypeAndQuantity(
				actionOne.getType(), quantityOne);
		assertEquals(badgeOne, found);

	}
	
	@Test
	public void testGetBadgesForActionAndMaxQuantity() {
		List<BadgeActions> foundList = badgeRepository.getBadgeForActionTypeAndMaxQuantity(actionOne.getType(), quantityThree);
		Set<BadgeActions> foundSet = new HashSet<BadgeActions>(foundList);
		Set<BadgeActions> expectedSet = new HashSet<BadgeActions>();
		expectedSet.add((BadgeActions) badgeActions);
		expectedSet.add((BadgeActions) badgeOne);
		expectedSet.add((BadgeActions) badgeThree);
		assertEquals(expectedSet, foundSet);
	

	}

	@Test
	public void testGetBadgeForTask() {
		BadgeTask found = badgeRepository.getBadgeForTask(taskOne.getId());
		assertEquals(badgeTask, found);
	}

}
