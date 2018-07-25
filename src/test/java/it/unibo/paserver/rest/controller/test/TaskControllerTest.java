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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskFlatList;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.flat.ActionFlat;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.flat.request.ActionFlatRequest;
import it.unibo.paserver.domain.flat.request.TaskFlatRequest;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.TaskReportBuilder;
import it.unibo.paserver.domain.support.TaskUserBuilder;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.rest.controller.TaskController;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.task.StrategyHolder;
import it.unibo.paserver.web.controller.task.TaskAddController;
import it.unibo.tper.TPerProxyImpl;
import it.unibo.tper.configuration.TPerConfiguration;
import it.unibo.tper.ws.domain.OpenDataFermateResponse;
import it.unibo.tper.ws.domain.OpenDataFermateResponse.OpenDataFermateResult;
import it.unibo.tper.ws.domain.OpenDataLineeFermate;
import it.unibo.tper.ws.domain.OpenDataLineeFermateResponse;
import it.unibo.tper.ws.domain.OpenDataLineeFermateResponse.OpenDataLineeFermateResult;
import it.unibo.tper.ws.domain.extensions.FermateResponse;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		WebComponentsConfig.class, TestDataContextConfiguration.class,TPerConfiguration.class })
@Transactional
public class TaskControllerTest {

	
	
	@Autowired
	TaskController taskController;
	

	@Autowired
	TaskAddController taskAddController;

	@Autowired
	ActionService actionService;

	@Autowired
	UserService userService;

	@Autowired
	AccountService accountService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskUserService taskUserService;

	@Autowired
	TaskReportService taskReportService;
	
	@PersistenceContext
	private EntityManager entityManager;

	private Task task;
	private Task pasttask;
	private Task futuretask;
	private TaskUser taskUser;

	private User user;
	private User user2;

	@Before
	public void setup() {
		
		EntityBuilderManager.setEntityManager(entityManager);
		
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

		futuretask = new Task();
		futuretask.setName("TestTaskTaskControllerTest");
		futuretask.setDescription("Task scheduled");
		start = new DateTime().plusMonths(2);
		end = start.plusDays(20);
		futuretask.setStart(start);
		futuretask.setDeadline(end);
		futuretask.setDuration(10L);
		futuretask.setSensingDuration(2L);
		actions = new LinkedHashSet<Action>();
		futuretask.setActions(actions);





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

		user2 =  new UserBuilder()
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
		.setHasSIM(false).setSimStatus(SimStatus.NO).build(true);

		userService.save(user);
		userService.save(user2);

		user = userService.getUser("john2@studio.unibo.it");
		user2 = userService.getUser("openmario2@studio.unibo.it");
		
		
		Task toTaskUser = new Task();
		toTaskUser.setName("TestTaskUserTaskControllerTest");
		toTaskUser.setDescription("Task scheduled");
		start = new DateTime();
		end = start.plusDays(20);
		toTaskUser.setStart(start);
		toTaskUser.setDeadline(end);
		toTaskUser.setDuration(10L);
		toTaskUser.setSensingDuration(2L);
		actions = new LinkedHashSet<Action>();
		actions.add(accelerometerAction);
		toTaskUser.setActions(actions);

		taskUser = new TaskUserBuilder()
		.setTask(toTaskUser)
		.setOwner(user2).build();
		
		
		
		

	}

	@Test
	public void searchAvailableTasks() {
		ArrayList<String> users = new ArrayList<String>();
		users.add(user.getOfficialEmail());

		StrategyHolder strategyHolder = new StrategyHolder();
		strategyHolder.setId(1);
		strategyHolder.setName("By Average Level");
		taskAddController.createAndAssignTask(task, users,strategyHolder);
		taskAddController.createAndAssignTask(pasttask, users,strategyHolder);
		taskAddController.createAndAssignTask(futuretask, users,strategyHolder);

		

		Principal p = new Principal() {

			@Override
			public String getName() {
				return "john2@studio.unibo.it";
			}
		};

		TaskFlatList taskFlatList = taskController.getTaskFlat(null, p,
				TaskState.AVAILABLE.toString(),null);

		assertNotNull(taskFlatList);
		assertNotNull(taskFlatList.getList());
		assertEquals(1, taskFlatList.getList().size());

		Set<ActionFlat> actionFlats = taskFlatList.getList().get(0)
				.getActions();
		assertEquals(1, actionFlats.size());
		ActionFlat f = actionFlats.iterator().next();
		assertEquals((Integer) Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt(),
				(Integer) f.getInput_type());

		Task result = taskService.findById(taskFlatList.getList().get(0)
				.getId());
		Set<Action> actions = result.getActions();
		assertEquals(1, actions.size());
		Action action = actions.iterator().next();
		assertTrue(action instanceof ActionSensing);
		ActionSensing actionSensing = (ActionSensing) action;
		assertEquals((Integer) Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt(),
				(Integer) actionSensing.getInput_type());

		
		//task assigned to me created by another user
		taskUserService.valutateTaskUser(taskUser, true);
		taskAddController.createAndAssignTask(taskUser.getTask(), users,strategyHolder);
		//should be reported as available

		taskFlatList = taskController.getTaskFlat(null, p,
				TaskState.AVAILABLE.toString(),null);
		assertNotNull(taskFlatList);
		assertNotNull(taskFlatList.getList());
		assertEquals(2, taskFlatList.getList().size());
		
		


	}

	// get task created by principal
	@Test
	public void getCreatedTask()
	{
		//taskUserService.save(taskUser);	

		
		Principal p = new Principal() {

			@Override
			public String getName() {
				return "openmario2@studio.unibo.it";
			}
		};
		
		
		
		TaskFlatList taskFlatList = taskController.getCreatedTaskFlat(null, p, TaskValutation.PENDING.toString());
		assertNotNull(taskFlatList);
		assertNotNull(taskFlatList.getList());
		assertEquals(1, taskFlatList.getList().size());
		
		taskUserService.valutateTaskUser(taskUser, true);
		taskFlatList = taskController.getCreatedTaskFlat(null, p, TaskValutation.APPROVED.toString());
		assertNotNull(taskFlatList);
		assertNotNull(taskFlatList.getList());
		assertEquals(1, taskFlatList.getList().size());
		
		taskUserService.valutateTaskUser(taskUser, false);
		taskFlatList = taskController.getCreatedTaskFlat(null, p, TaskValutation.REFUSED.toString());
		assertNotNull(taskFlatList);
		assertNotNull(taskFlatList.getList());
		assertEquals(1, taskFlatList.getList().size());	
		
		

	}
	
	
	
	
	
	@Test
	public void createTask()
	{
		Principal p = new Principal() {

			@Override
			public String getName() {
				return "openmario2@studio.unibo.it";
			}
		};
		
		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction
		.setInput_type(Pipeline.Type.ACCELEROMETER_CLASSIFIER.toInt());
		accelerometerAction.setName("Accelerometer");
		
		ActionFlatRequest actionFlatRequest = new ActionFlatRequest();
		actionFlatRequest.setDuration_threshold(accelerometerAction.getDuration_threshold());
		actionFlatRequest.setInput_type(accelerometerAction.getInput_type());
		actionFlatRequest.setName(accelerometerAction.getName());
		actionFlatRequest.setNumeric_threshold(accelerometerAction.getNumeric_threshold());
		actionFlatRequest.setTitle(actionFlatRequest.getTitle());
		actionFlatRequest.setType(ActionType.SENSING_MOST);
		
		Set<ActionFlatRequest> actionFlatRequests = new HashSet<ActionFlatRequest>();
		actionFlatRequests.add(actionFlatRequest);
		
		
		
		
		TaskFlatRequest flatRequest = new TaskFlatRequest();
		flatRequest.setActions(actionFlatRequests);
		flatRequest.setCanBeRefused(true);
		flatRequest.setDeadline(new DateTime().plus(20));
		flatRequest.setDescription("Accelerometer sensing most");
		flatRequest.setDuration(10L);
		flatRequest.setName("TestCreateTaskControllerTest");
		//flatRequest.setOwner("openmario2@studio.unibo.it");
		flatRequest.setSensingDuration(2L);
		flatRequest.setStart(new DateTime());
		flatRequest.setType(Task.class.getSimpleName());
		
		
		TaskFlat taskFlat =  taskController.createTask(flatRequest, p);		
		assertNotNull(taskFlat);
		
		Task t = taskService.findById(taskFlat.getId());
		assertNotNull(t);
		assertEquals(t.getTaskReport().size(), 0);
		
		TaskUser tu = taskUserService.getTaskUserByTaskId(t.getId());
		assertNotNull(tu);
		assertEquals(tu.getValutation(), TaskValutation.PENDING);
		
		
	}
	
	
	

}
