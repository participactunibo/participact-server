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
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Points;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.Score;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.rest.ScoreRestResult;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.FriendshipBuilder;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.rest.controller.LeaderboardsController;
import it.unibo.paserver.service.PointsService;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
public class LeaderboardControllerTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private LeaderboardsController leaderboardsController;
	@Autowired
	private PointsService pointsService;

	private User primo;
	private User secondo;
	private User terzo;
	private Points pointsPrimo;
	private Points pointsSecondo;
	private Points pointsTerzo;
	private Principal principal = new Principal() {

		@Override
		public String getName() {
			return "secondo@studio.unibo.it";
		}
	};

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		primo = new UserBuilder().setCredentials("primo@sample.com", "secret")
				.setCurrentAddress("v.le Risorgimento 2")
				.setBirthdate(new LocalDate(1985, 2, 14))
				.setCurrentCity("Bologna").setImei("6859430917")
				.setName("Primo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Primi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MWWRSS85B14F5TRK")
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
				.setCurrentCity("Bologna").setImei("6859430918")
				.setName("Secondo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Secondi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS85B14F76RV")
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
				.setCurrentCity("Bologna").setImei("6859430919")
				.setName("Terzo").setCurrentProvince("BO")
				.setRegistrationDateTime(new DateTime()).setSurname("Terzi")
				.setCurrentZipCode("40135").setGender(Gender.MALE)
				.setDocumentIdType(DocumentIdType.CF)
				.setDocumentId("MRARSS8T614F5TRP")
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

		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction
				.setInput_type(Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt());
		accelerometerAction.setName("Accelerometer");
		Task task = new Task();
		task.setName("TestTaskTaskControllerTest");
		task.setDescription("Task scheduled");
		DateTime start = new DateTime();
		DateTime end = start.plusDays(20);
		task.setStart(start);
		task.setDeadline(end);
		task.setDuration(10L);
		task.setSensingDuration(2L);
		Set<Action> actions = new LinkedHashSet<Action>();
		actions.add(accelerometerAction);
		task.setActions(actions);
		DateTime now = new DateTime();
		pointsPrimo = pointsService.create(primo, task, now.minusDays(1), 1, PointsType.TASK_COMPLETED_WITH_SUCCESS);
		pointsSecondo = pointsService
				.create(secondo, task, now.minusDays(2), 2, PointsType.TASK_COMPLETED_WITH_SUCCESS);
		pointsTerzo = pointsService.create(terzo, task, now.minusDays(3), 3, PointsType.TASK_COMPLETED_WITH_SUCCESS);

	}

	@Test
	public void testGlobalLeaderboard() {

		ScoreRestResult[] found = leaderboardsController.getLeaderBoard(
				principal, "global");
		Set<ScoreRestResult> foundSet = new HashSet<ScoreRestResult>(
				Arrays.asList(found));
		Set<ScoreRestResult> expectedSet = new HashSet<ScoreRestResult>();
		Score scoreOne = new Score();
		scoreOne.setUser(primo);
		scoreOne.setValue(1);
		Score scoreTwo = new Score();
		scoreTwo.setUser(secondo);
		scoreTwo.setValue(2);
		Score scoreThree = new Score();
		scoreThree.setUser(terzo);
		scoreThree.setValue(3);
		expectedSet.add(new ScoreRestResult(scoreOne));
		expectedSet.add(new ScoreRestResult(scoreTwo));
		expectedSet.add(new ScoreRestResult(scoreThree));

		assertEquals(expectedSet, foundSet);

	}

	@Test
	public void testFriendsLeaderboard() {

		ScoreRestResult[] found = leaderboardsController.getLeaderBoard(
				principal, "social");
		Set<ScoreRestResult> foundSet = new HashSet<ScoreRestResult>(
				Arrays.asList(found));
		Set<ScoreRestResult> expectedSet = new HashSet<ScoreRestResult>();
		Score scoreTwo = new Score();
		scoreTwo.setUser(secondo);
		scoreTwo.setValue(2);
		Score scoreThree = new Score();
		scoreThree.setUser(terzo);
		scoreThree.setValue(3);
		expectedSet.add(new ScoreRestResult(scoreTwo));
		expectedSet.add(new ScoreRestResult(scoreThree));

		assertEquals(expectedSet, foundSet);

	}

}
