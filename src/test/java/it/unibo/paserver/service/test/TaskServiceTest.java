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
package it.unibo.paserver.service.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.TaskBuilder;
import it.unibo.paserver.domain.support.TaskReportBuilder;
import it.unibo.paserver.domain.support.TaskUserBuilder;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.service.TaskService;

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
public class TaskServiceTest {

	@Autowired
	TaskService taskService;

	@PersistenceContext
	EntityManager manager;

	private User assignedToUser;

	private User creatorUser;

	private TaskUser taskUser;

	private ActionSensing actionLocationTask2;

	private ActionSensing actionLocationTask1;

	private Task task;

	private User tempUser;


	@Before
	public  void setupData() {
		EntityBuilderManager.setEntityManager(manager);

		//create User
		assignedToUser = new UserBuilder().setCredentials("john2@sample.com", "secret")
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
				.setSimStatus(SimStatus.NEW_SIM).build();


		creatorUser = new UserBuilder()
		.setCredentials("openmario2@studio.unibo.it", "secret")
		.setCurrentAddress("via Indipendenza 9")
		.setBirthdate(new LocalDate(1980, 7, 12))
		.setCurrentCity("Bologna").setName("Open")
		.setCurrentProvince("BO").setCf("MRARSS85B14L401w")
		.setRegistrationDateTime(new DateTime())
		.setSurname("Coso").setCurrentZipCode("40136")
		.setGender(Gender.MALE)
		.setDocumentIdType(DocumentIdType.NATIONAL_ID)
		.setDocumentId("AJ234634")
		.setContactPhoneNumber("+39051123456")
		.setOfficialEmail("openmario2@studio.unibo.it")
		.setProjectEmail("mariolino2@gmail.com")
		.setProjectPhoneNumber("+3933975465")
		.setUniCity(UniCity.BOLOGNA)
		.setUniCourse(UniCourse.MAGISTRALE).setUniYear(1)
		.setUniDegree("Oftalmologia").setUniDepartment("COSE")
		.setUniSchool(UniSchool.MEDICINA_E_CHIRURGIA)
		.setUniIsSupplementaryYear(false).setHasPhone(false)
		.setWantsPhone(false).setIsActive(true)
		.setHasSIM(false).setSimStatus(SimStatus.NO).build();



		Set<Action> actionsg = new LinkedHashSet<Action>();

		actionLocationTask1 = new ActionSensing();
		actionLocationTask1.setDuration_threshold(20);
		actionLocationTask1.setNumeric_threshold(0);
		actionLocationTask1.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocationTask1.setName("Geolocation");

		actionLocationTask2 = new ActionSensing();
		actionLocationTask2.setDuration_threshold(20);
		actionLocationTask2.setNumeric_threshold(0);
		actionLocationTask2.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocationTask2.setName("Geolocation2");

		actionsg.add(actionLocationTask2);
		actionsg.add(actionLocationTask1);



		Task toTaskUser = new Task();
		toTaskUser.setName("Task User Task");
		toTaskUser.setDescription("Prova");
		toTaskUser.setStart(new DateTime());
		toTaskUser.setDeadline(new DateTime().plusDays(30));
		toTaskUser.setDuration(10L);
		toTaskUser.setSensingDuration(2L);
		toTaskUser.setActions(actionsg);


		taskUser = new TaskUserBuilder()
		.setTask(toTaskUser)		
		.setOwner(creatorUser).build();

		actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionLocationTask1);

		task = new TaskBuilder()
		.setName("Geolocation")
		.setDescription("Geolocation task")
		.setStart(new DateTime())
		.setDeadline(new DateTime().plusDays(30))
		.setDuration(10L)
		.setSensingDuration(2L)
		.setActions(actionsg).build();	

		TaskReport taskReport = new TaskReportBuilder()
		.setUser(assignedToUser)
		.setTask(taskUser.getTask())
		.setCurrentState(TaskState.AVAILABLE)
		.setAcceptedDateTime(new DateTime())
		.setExpirationDateTime(new DateTime()
		.plusMinutes(10)).build();

		taskReport = new TaskReportBuilder()
		.setUser(creatorUser)
		.setTask(task)
		.setCurrentState(TaskState.ACCEPTED)
		.setAcceptedDateTime(new DateTime())
		.setExpirationDateTime(new DateTime()
		.plusMinutes(10)).build();


		taskReport = new TaskReportBuilder()
		.setUser(assignedToUser)
		.setTask(task)
		.setCurrentState(TaskState.ACCEPTED)
		.setAcceptedDateTime(new DateTime())
		.setExpirationDateTime(new DateTime()
		.plusMinutes(10)).build();


	}


	@Test
	public void findById()
	{
		task = taskService.findById(this.task.getId());
		assertEquals(task, this.task);
	}

	@Test
	public void getTasksCount()
	{
		long count = taskService.getTasksCount();
		assertEquals(2, count);
	}

	@Test
	public void getTasksCountByUser()
	{
		long count = taskService.getTasksCount(assignedToUser.getId());
		assertEquals(2, count);

		count = taskService.getTasksCount(creatorUser.getId());
		assertEquals(1, count);

	}

	@Test
	public void getTasks()
	{
		List<Task> tasks = taskService.getTasks();
		int number = tasks.size();
		assertEquals(2,number);

	}

	@Test
	public void getTasksByUser()
	{
		List<Task> t = taskService.getTasksByUser(assignedToUser.getId());
		assertEquals(2, t.size());

		t = taskService.getTasksByUser(creatorUser.getId());
		assertEquals(1, t.size());
	}

	@Test
	public void getTasksByUserAndCurrentState()
	{

		List<Task> t = taskService.getTasksByUser(assignedToUser.getId(), TaskState.AVAILABLE);
		assertEquals(1, t.size());

		t = taskService.getTasksByUser(creatorUser.getId(), TaskState.ACCEPTED);
		assertEquals(1, t.size());

		t = taskService.getTasksByUser(assignedToUser.getId(), TaskState.ACCEPTED);
		assertEquals(1, t.size());
	}


	@Test
	public void getTasksByAction()
	{
		List<Task> tasks = taskService.getTasksByAction(actionLocationTask1.getId());
		assertEquals(2, tasks.size());

		tasks = taskService.getTasksByAction(actionLocationTask2.getId());
		assertEquals(1, tasks.size());
	}


	@Test
	public void assignTaskToUsers()
	{
		Task notAssignedTask = new TaskBuilder()
		.setName("Not Assigned Normal Task")
		.setDescription("Try task")
		.setStart(new DateTime())
		.setDeadline(new DateTime().plusDays(30))
		.setDuration(10L)
		.setSensingDuration(2L).build();

		Task toTaskUser = new Task();
		toTaskUser.setName("Not Assigned Task User");
		toTaskUser.setDescription("Try task");
		toTaskUser.setStart(new DateTime());
		toTaskUser.setDeadline(new DateTime().plusDays(30));
		toTaskUser.setDuration(10L);
		toTaskUser.setSensingDuration(2L);


		TaskUser notAssignedTaskUser = new TaskUserBuilder()
		.setTask(toTaskUser)
		.setOwner(creatorUser)
		.build();

		List<String> users = new ArrayList<String>();
		users.add(creatorUser.getOfficialEmail());

		Task assignedTask = taskService.assignTaskToUsers(notAssignedTask, users);
		Set<TaskReport> taskReports = assignedTask.getTaskReport();
		assertEquals(1, taskReports.size());
		TaskReport taskReport = (TaskReport) taskReports.toArray()[0];
		assertEquals(creatorUser, taskReport.getUser());

		users = new ArrayList<String>();
		users.add(assignedToUser.getOfficialEmail());
		notAssignedTaskUser.setValutation(TaskValutation.APPROVED);
		assignedTask = taskService.assignTaskToUsers(notAssignedTaskUser.getTask(), users);
		taskReports = assignedTask.getTaskReport();
		assertEquals(1, taskReports.size());
		taskReport = (TaskReport) taskReports.toArray()[0];
		assertEquals(assignedToUser, taskReport.getUser());


	}

	
	




	/*
	Task findById(long id);

	Task save(Task task);

	Long getTasksCount();

	Long getTasksCount(long userId); 

	List<Task> getTasks();



	List<Task> getTasksByUser(long userId);

	List<Task> getTasksByUser(long userId, TaskState taskCurrentState);

	List<Task> getAvailableTasksByUser(long userId, TaskState taskCurrentState, DateTime dateTime);

	List<Task> getTasksByAction(long actionId);

	Task assignTaskToUsers(Task task, Collection<String> users);

	Task addUsersToTask(long taskId, Collection<String> users);

	boolean deleteTask(long id);

	List<TaskUser> getTaskUser();

	List<TaskUser> getTaskbyOwner(long userId);

	List<TaskUser> getTaskbyOwner(long userId, TaskState taskCurrentState);	

	Task valutateTaskUser(TaskUser task, boolean decision);
	 */

}
