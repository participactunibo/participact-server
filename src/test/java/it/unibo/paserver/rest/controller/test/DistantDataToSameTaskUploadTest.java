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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unibo.participact.domain.proto.DataAccelerometerProtos.DataAccelerometerProto;
import it.unibo.participact.domain.proto.DataAccelerometerProtos.DataAccelerometerProtoList;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.config.test.WebComponentsConfig;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskFlatList;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskResult;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.flat.TaskFlat;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.UserBuilder;
import it.unibo.paserver.rest.controller.ResultDataController;
import it.unibo.paserver.rest.controller.TaskController;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.UserService;
import it.unibo.paserver.web.controller.task.StrategyHolder;
import it.unibo.paserver.web.controller.task.TaskAddController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

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
public class DistantDataToSameTaskUploadTest {

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
	TaskReportService taskReportService;

	@Autowired
	TaskResultService taskResultService;

	@Autowired
	ResultDataController resultDataController;

	private Task task;

	private User user;

	@Before
	public void setup() {
		ActionSensing accelerometerAction = new ActionSensing();
		accelerometerAction.setInput_type(Pipeline.Type.ACCELEROMETER.toInt());
		accelerometerAction.setName("Accelerometer");

		task = new Task();
		task.setName("TestTaskTaskControllerTest");
		task.setDescription("Task scheduled");
		DateTime start = new DateTime().minusDays(5);
		DateTime end = start.plusDays(20);
		task.setStart(start);
		task.setDeadline(end);
		task.setDuration(60L);
		task.setSensingDuration(2L);
		Set<Action> actions = new LinkedHashSet<Action>();
		actions.add(accelerometerAction);
		task.setActions(actions);

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
	public void acceptTasks() throws IOException {
		long numReports = taskReportService.getTaskReportsCountByUser(user
				.getId());
		assertEquals(0, numReports);
		ArrayList<String> users = new ArrayList<String>();
		users.add(user.getOfficialEmail());
		StrategyHolder strategyHolder = new StrategyHolder();
		strategyHolder.setId(1);
		strategyHolder.setName("By Average Level");
		taskAddController.createAndAssignTask(task, users,strategyHolder);
		Principal p = new Principal() {

			@Override
			public String getName() {
				return "john2@studio.unibo.it";
			}
		};

		TaskFlatList taskFlatList = taskController.getTaskFlat(null, p,
				TaskState.AVAILABLE.toString(),null);

		numReports = taskReportService.getTaskReportsCountByUser(user.getId());
		assertEquals(1, numReports);

		TaskFlat tf = taskFlatList.getList().get(0);

		assertNotNull(tf);
		assertEquals(task.getDescription(), tf.getDescription());

		taskController.acceptTask(null, p, tf.getId());

		List<TaskReport> taskReports = taskReportService
				.getTaskReportsByUser(user.getId());
		assertEquals(1, taskReports.size());

		TaskReport tr = taskReports.get(0);

		assertEquals(TaskState.RUNNING, tr.getCurrentState());

		List<DataAccelerometerProto> accData = new ArrayList<DataAccelerometerProto>();
		accData.add(DataAccelerometerProto.newBuilder()
				.setSampleTime(new DateTime().getMillis()).setX(0).setY(42)
				.setZ(0).build());
		accData.add(DataAccelerometerProto.newBuilder()
				.setSampleTime(new DateTime().plusMinutes(32).getMillis())
				.setX(0).setY(84).setZ(0).build());
		DataAccelerometerProtoList protoAccData = DataAccelerometerProtoList
				.newBuilder().addAllList(accData).build();
		HttpServletRequest hr = mock(HttpServletRequest.class);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		protoAccData.writeTo(baos);
		baos.close();
		ServletInputStream msis = new MockServletInputStream(baos.toByteArray());
		when(hr.getHeader(ResultDataController.HEADER_KEY)).thenReturn(
				"MOCK_KEY");
		when(hr.getInputStream()).thenReturn(msis);

		resultDataController.submitDataAccelerometer(hr, p);

		tr = taskReportService.findByUserAndTask(user.getId(), task.getId());
		assertNotNull(tr);
		assertEquals(TaskState.RUNNING, tr.getCurrentState());
		assertEquals(0, tr.getTaskResult().getData().size());

		TaskResult taskResult = taskResultService.getTaskResultByTaskReport(tr
				.getId());
		assertEquals(2, taskResult.getData().size());

		tr = taskReportService.findByUserAndTask(user.getId(), task.getId());
		assertEquals(2, tr.getTaskResult().getData().size());
	}

	private class MockServletInputStream extends ServletInputStream {

		private InputStream is;

		public MockServletInputStream(byte[] data) {
			is = new ByteArrayInputStream(data);
		}

		@Override
		public int read() throws IOException {
			return is.read();
		}

	}
}
