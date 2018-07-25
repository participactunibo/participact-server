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
package it.unibo.paserver.web.controller.task.test;

import static org.junit.Assert.assertNotNull;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.rest.controller.TaskController;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

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
public class AddUserToTaskTest {

	@Autowired
	TaskController taskController;

	@Autowired
	UserService userService;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskReportService taskReportService;

	private Task task;
	private Task pasttask;

	private User user;

	@Before
	public void setup() {
		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction
				.setInput_type(Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt());
		accelerometerAction.setName("Accelerometer");

		ActionSensing accelerometerAction2 = new ActionSensing();
		accelerometerAction2
				.setInput_type(Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt());
		accelerometerAction2.setName("Accelerometer");

		task = new Task();
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
		task = taskService.save(task);

		pasttask = new Task();
		pasttask.setName("TestTaskTaskControllerTest");
		pasttask.setDescription("Task scheduled");
		start = new DateTime().minusMonths(2);
		end = start.plusDays(20);
		pasttask.setStart(start);
		pasttask.setDeadline(end);
		pasttask.setDuration(10L);
		pasttask.setSensingDuration(2L);
		actions = new LinkedHashSet<Action>();
		actions.add(accelerometerAction2);
		pasttask.setActions(actions);
		pasttask = taskService.save(pasttask);

		user = new UserBuilder()
				.setCredentials("john2@studio.unibo.it", "secret")
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
				.setSimStatus(SimStatus.NEW_SIM).build(true);

		userService.save(user);

		user = userService.getUser("john2@studio.unibo.it");
	}

	@Test
	public void addExistingUserToAvailableTask() {
		Collection<String> newUsers = new ArrayList<String>();
		newUsers.add("john2@studio.unibo.it");
		taskService.addUsersToTask(task.getId(), newUsers);
		TaskReport tr = taskReportService.findByUserAndTask(user.getId(),
				task.getId());
		assertNotNull(tr);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addUnknownUserToAvailableTask() {
		Collection<String> newUsers = new ArrayList<String>();
		newUsers.add("unknown@studio.unibo.it");
		taskService.addUsersToTask(task.getId(), newUsers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addUnknownUserToNullTask() {
		Collection<String> newUsers = new ArrayList<String>();
		newUsers.add("unknown@studio.unibo.it");
		taskService.addUsersToTask(-1, newUsers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addNullUserToAvailableTask() {
		Collection<String> newUsers = new ArrayList<String>();
		newUsers.add("john2@studio.unibo.it");
		newUsers.add(null);
		taskService.addUsersToTask(task.getId(), newUsers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void addExistingUserToUnAvailableTask() {
		Collection<String> newUsers = new ArrayList<String>();
		newUsers.add("john2@studio.unibo.it");
		taskService.addUsersToTask(pasttask.getId(), newUsers);
		TaskReport tr = taskReportService.findByUserAndTask(user.getId(),
				task.getId());
		assertNotNull(tr);
	}
}
