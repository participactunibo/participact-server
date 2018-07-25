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

import static org.junit.Assert.*;

import java.util.LinkedHashSet;
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
import it.unibo.paserver.repository.TaskReportRepository;
import it.unibo.paserver.repository.TaskRepository;
import it.unibo.paserver.repository.UserRepository;

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
public class JpaTaskReportRepositoryTest {
	
	
	@Autowired
	private TaskRepository taskRepository;
	
	
	@Autowired
	private TaskReportRepository taskReportRepository;
	
	@PersistenceContext
	private EntityManager manager;
	
	private User user,owner;
	private ActionSensing actionLocationTask1;
	private ActionSensing actionLocationTask2;
	private Task taskg;
	private TaskUser taskUser;
	private TaskReport taskReportg;
	
	
	@Before
	public void setupData()
	{
		EntityBuilderManager.setEntityManager(manager);
		//creation of users
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
				.setSimStatus(SimStatus.NEW_SIM).build();
		
		
		owner = new UserBuilder()
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
		
		
		//creation of ActionSensing
		actionLocationTask1 = new ActionSensing();
		actionLocationTask1.setDuration_threshold(20);
		actionLocationTask1.setNumeric_threshold(0);
		actionLocationTask1.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocationTask1.setName("Geolocation");
		//creation of simpleTask
		Set<Action> actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionLocationTask1);
		
		
		
		
		TaskBuilder taskBuilder = new TaskBuilder();
		
		taskg = taskBuilder
		.setName("Geolocation")
		.setDescription("Geolocation task")
		.setStart(new DateTime())
		.setDeadline(new DateTime().plusDays(30))
		.setDuration(10L)
		.setSensingDuration(2L)
		.setActions(actionsg).build();		
		
		
		
		 taskReportg = new TaskReportBuilder()
		.setUser(user)
		.setTask(taskg)
		.setCurrentState(TaskState.AVAILABLE)
		.setAcceptedDateTime(new DateTime())
		.setExpirationDateTime(new DateTime()
				.plusMinutes(10)).build();
				
		
		

		
		
		//creation of userTask	
		actionLocationTask2 = new ActionSensing();
		actionLocationTask2.setDuration_threshold(20);
		actionLocationTask2.setNumeric_threshold(0);
		actionLocationTask2.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocationTask2.setName("Geolocation2");
		actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionLocationTask2);
		actionsg.add(actionLocationTask1);
		
		Task toTaskUser = new Task();
		toTaskUser.setName("Geolocation2");
		toTaskUser.setDescription("Geolocation2 task");
		toTaskUser.setStart(new DateTime());
		toTaskUser.setDeadline(new DateTime().plusDays(30));
		toTaskUser.setDuration(10L);
		toTaskUser.setSensingDuration(2L);
		toTaskUser.setActions(actionsg);
		
		taskUser = new TaskUserBuilder()
		.setTask(toTaskUser)		
		.setOwner(owner).build();
		
		
		taskReportg = new TaskReportBuilder()
		.setUser(user)
		.setTask(toTaskUser)
		.setCurrentState(TaskState.ACCEPTED)
		.setAcceptedDateTime(new DateTime())
		.setExpirationDateTime(new DateTime()
				.plusMinutes(10)).build();
	}
	
	
	
	@Test
	public void getTeskReportByTaskAndUserAndOwner()
	{		
		TaskReport report = taskReportRepository.findByUserAndTaskAndOwner(user.getId(), taskUser.getTask().getId(), owner.getId());
		assertEquals(taskReportg,report);
	
	}

}
