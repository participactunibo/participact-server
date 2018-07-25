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
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.BadgeActionBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.PointsBuilder;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.repository.PointsRepository;

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
public class JpaPointsRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PointsRepository pointsRepository;

	private User user;
	private Task task;
	private DateTime dayOne;
	private DateTime dayThree;
	private Points pointsOne;
	private Points pointsThree;
	private Integer valueOne;
	private Integer valueThree;
	private Action action;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		action = new ActionSensing();
		action.setDuration_threshold(20);
		action.setNumeric_threshold(0);
		((ActionSensing) action).setInput_type(Pipeline.Type.LOCATION.toInt());
		action.setName("Geolocation");

		task = new Task();
		task.setName("Geolocation");
		task.setDescription("Geolocation task");
		task.setStart(new DateTime());
		task.setDeadline(new DateTime().plusDays(30));
		task.setDuration(10L);
		task.setSensingDuration(2L);
		ActionSensing actionLocation = new ActionSensing();
		actionLocation.setDuration_threshold(20);
		actionLocation.setNumeric_threshold(0);
		actionLocation.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocation.setName("Geolocation");
		Set<Action> actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionLocation);
		task.setActions(actionsg);

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

		dayOne = new DateTime().plusDays(1);
		dayThree = new DateTime().plusDays(3);

		valueOne = 1;
		valueThree = 3;

		pointsOne = new PointsBuilder().setDate(dayOne).setTask(task)
				.setUser(user).setValue(valueOne).setType(PointsType.TASK_COMPLETED_WITH_SUCCESS).build();
		pointsThree = new PointsBuilder().setDate(dayThree).setType(PointsType.USER_TASK_APPROVED).setTask(task)
				.setUser(user).setValue(valueThree).build();

	}

	@Test
	public void testGetPointsByUserAndDates() {
		List<Points> foundPointsOne = pointsRepository.getPointsByUserAndDates(
				user.getId(), new DateTime(), new DateTime().plusDays(1));
		Set<Points> foundPointsOneSet = new HashSet<Points>(foundPointsOne);
		Set<Points> pointsOneSet = new HashSet<Points>();
		pointsOneSet.add(pointsOne);
		assertEquals(pointsOneSet, foundPointsOneSet);

		List<Points> foundPointsThree = pointsRepository
				.getPointsByUserAndDates(user.getId(),
						new DateTime().plusDays(2), new DateTime().plusDays(4));
		Set<Points> foundPointsThreeSet = new HashSet<Points>(foundPointsThree);
		Set<Points> pointsThreeSet = new HashSet<Points>();
		pointsThreeSet.add(pointsThree);
		assertEquals(pointsThreeSet, foundPointsThreeSet);

		List<Points> foundPointsAll = pointsRepository.getPointsByUserAndDates(
				user.getId(), new DateTime(), new DateTime().plusDays(4));
		Set<Points> foundPointsAllSet = new HashSet<Points>(foundPointsAll);
		Set<Points> pointsAllSet = new HashSet<Points>();
		pointsAllSet.add(pointsOne);
		pointsAllSet.add(pointsThree);
		assertEquals(pointsAllSet, foundPointsAllSet);

	}
	
	@Test
	public void getPointsByUserAndTask() {
		List<Points> foundList = pointsRepository.getPointsByUserAndTask(user.getId(), task.getId());
		Set<Points> foundSet = new HashSet<Points>(foundList);
		Set<Points> expectedSet = new HashSet<Points>();
		expectedSet.add(pointsOne);
		expectedSet.add(pointsThree);
		assertEquals(expectedSet, foundSet);
	}
	
	@Test
	public void getPointsByUserAndTaskAndType() {
		Points foundOne = pointsRepository.getPointsByUserAndTaskAndType(user.getId(), task.getId(), PointsType.TASK_COMPLETED_WITH_SUCCESS);
		Points foundThree = pointsRepository.getPointsByUserAndTaskAndType(user.getId(), task.getId(), PointsType.USER_TASK_APPROVED);
		assertEquals(pointsOne, foundOne);
		assertEquals(pointsThree, foundThree);
	}
	
	@Test
	public void getPointsByUserAndType() {
		List<Points> foundOneList = pointsRepository.getPointsByUserAndType(user.getId(), PointsType.TASK_COMPLETED_WITH_SUCCESS);
		List<Points> foundThreeList = pointsRepository.getPointsByUserAndType(user.getId(), PointsType.USER_TASK_APPROVED);
		Set<Points> foundOneSet = new HashSet<Points>(foundOneList);
		Set<Points> foundThreeSet = new HashSet<Points>(foundThreeList);
		Set<Points> expectedOne = new HashSet<Points>();
		expectedOne.add(pointsOne);
		Set<Points> expectedThree = new HashSet<Points>();
		expectedThree.add(pointsThree);
		assertEquals(expectedOne, foundOneSet);
		assertEquals(expectedThree, foundThreeSet);
	}
	
	@Test
	public void getPointsByTaskAndType() {
		List<Points> foundOneList = pointsRepository.getPointsByTaskAndType(task.getId(), PointsType.TASK_COMPLETED_WITH_SUCCESS);
		List<Points> foundThreeList = pointsRepository.getPointsByTaskAndType(task.getId(), PointsType.USER_TASK_APPROVED);
		Set<Points> foundOneSet = new HashSet<Points>(foundOneList);
		Set<Points> foundThreeSet = new HashSet<Points>(foundThreeList);
		Set<Points> expectedOne = new HashSet<Points>();
		expectedOne.add(pointsOne);
		Set<Points> expectedThree = new HashSet<Points>();
		expectedThree.add(pointsThree);
		assertEquals(expectedOne, foundOneSet);
		assertEquals(expectedThree, foundThreeSet);
	}

}
