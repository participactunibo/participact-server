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
package it.unibo.paserver.domain.support;

import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionPhoto;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Badge;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.DocumentIdType;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.Gender;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.SimStatus;
import it.unibo.paserver.domain.Points.PointsType;
import it.unibo.paserver.domain.SocialPresence.SocialPresenceType;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.TaskHistory;
import it.unibo.paserver.domain.TaskReport;
import it.unibo.paserver.domain.TaskState;
import it.unibo.paserver.domain.TaskUser;
import it.unibo.paserver.domain.TaskValutation;
import it.unibo.paserver.domain.UniCity;
import it.unibo.paserver.domain.UniCourse;
import it.unibo.paserver.domain.UniSchool;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.TaskHistoryService;
import it.unibo.paserver.service.TaskReportService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.TaskUserService;
import it.unibo.paserver.service.UserLogFileService;
import it.unibo.paserver.service.UserService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class InitialDataSetup {

	private TransactionTemplate transactionTemplate;

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ActionService actionService;

	@Autowired
	TaskService taskService;
	
	@Autowired
	TaskUserService taskUserService;

	@Autowired
	UserService userService;

	@Autowired
	TaskReportService taskReportService;

	@Autowired
	TaskHistoryService taskHistoryService;

	@Autowired
	DataService dataService;

	@Autowired
	UserLogFileService userLogFileService;

	@Autowired
	TaskResultService taskResultService;

	public InitialDataSetup(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}

	public void initialize() {
		EntityBuilderManager.setEntityManager(entityManager);

		transactionTemplate.execute(new TransactionCallback<Void>() {

			@Override
			public Void doInTransaction(TransactionStatus transactionStatus) {
				if (isDataAlreadyPresent()) {
					return null;
				}
				boolean isTesting = false;
				if (isTesting)
					return null;
				new AccountBuilder().credentials("test", "secret")
						.creationDate(new DateTime()).addRole(Role.ROLE_VIEW)
						.build();
				new AccountBuilder().credentials("root", "secret")
						.creationDate(new DateTime()).addRole(Role.ROLE_ADMIN)
						.addRole(Role.ROLE_VIEW).build();

				new AccountBuilder().credentials("prova", "test")
						.creationDate(new DateTime()).build();

				User userOne = new UserBuilder()
						.setCredentials("john@studio.unibo.it", "secret")
						.setCurrentAddress("v.le Risorgimento 2")
						.setBirthdate(new LocalDate(1985, 2, 14))
						.setCurrentCity("Bologna").setImei("6859430911")
						.setName("Mario").setCurrentProvince("BO")
						.setCf("MRARSS85B14M109K")
						.setRegistrationDateTime(new DateTime())
						.setSurname("Rossi").setCurrentZipCode("40135")
						.setGender(Gender.MALE)
						.setDocumentIdType(DocumentIdType.CF)
						.setDocumentId("MRARSS85B14M109K")
						.setContactPhoneNumber("+39 051 123456")
						.setOfficialEmail("john@studio.unibo.it")
						.setProjectEmail("john@gmail.com")
						.setProjectPhoneNumber("+39333012345")
						.setUniCity(UniCity.BOLOGNA)
						.setUniCourse(UniCourse.TRIENNALE).setUniYear(2)
						.setUniDegree("Ingegneria Informatica")
						.setUniDepartment("DISI")
						.setUniSchool(UniSchool.INGEGNERIA_E_ARCHITETTURA)
						.setUniIsSupplementaryYear(false).setHasPhone(false)
						.setWantsPhone(false).setIsActive(true)
						.setHasSIM(false).setSimStatus(SimStatus.NEW_SIM)
						.setBadges(new HashSet<Badge>()).build();

				User userTwo = new UserBuilder()
						.setCredentials("openmario@studio.unibo.it", "secret")
						.setCurrentAddress("via Indipendenza 9")
						.setBirthdate(new LocalDate(1980, 7, 12))
						.setCurrentCity("Bologna").setName("Open")
						.setCurrentProvince("BO").setCf("MRARSS85B14L401K")
						.setRegistrationDateTime(new DateTime())
						.setSurname("Coso").setCurrentZipCode("40136")
						.setGender(Gender.MALE)
						.setDocumentIdType(DocumentIdType.NATIONAL_ID)
						.setDocumentId("AJ234234")
						.setContactPhoneNumber("+39051123456")
						.setOfficialEmail("openmario@studio.unibo.it")
						.setProjectEmail("mariolino@gmail.com")
						.setProjectPhoneNumber("+3933975465")
						.setUniCity(UniCity.BOLOGNA)
						.setUniCourse(UniCourse.MAGISTRALE).setUniYear(1)
						.setUniDegree("Oftalmologia").setUniDepartment("COSE")
						.setUniSchool(UniSchool.MEDICINA_E_CHIRURGIA)
						.setUniIsSupplementaryYear(false).setHasPhone(false)
						.setWantsPhone(false).setIsActive(true)
						.setHasSIM(false).setSimStatus(SimStatus.NO)
						.setBadges(new HashSet<Badge>()).build();

				User user = userService.getUser("openmario@studio.unibo.it");

				User userThree = new UserBuilder()
						.setCredentials("openluca@studio.unibo.it", "secret")
						.setCurrentAddress("via Indipendenza 9")
						.setBirthdate(new LocalDate(1980, 7, 12))
						.setCurrentCity("Bologna").setName("Open")
						.setCurrentProvince("BO").setCf("MRARTT85B14L401K")
						.setRegistrationDateTime(new DateTime())
						.setSurname("Verdi").setCurrentZipCode("40136")
						.setGender(Gender.MALE)
						.setDocumentIdType(DocumentIdType.NATIONAL_ID)
						.setDocumentId("AJ231284")
						.setContactPhoneNumber("+39051125656")
						.setOfficialEmail("openluca@studio.unibo.it")
						.setProjectEmail("luchetto@gmail.com")
						.setProjectPhoneNumber("+3933925465")
						.setUniCity(UniCity.BOLOGNA)
						.setUniCourse(UniCourse.MAGISTRALE).setUniYear(1)
						.setUniDegree("Oftalmologia").setUniDepartment("COSE")
						.setUniSchool(UniSchool.MEDICINA_E_CHIRURGIA)
						.setUniIsSupplementaryYear(false).setHasPhone(false)
						.setWantsPhone(false).setIsActive(true)
						.setHasSIM(false).setSimStatus(SimStatus.NO)
						.setBadges(new HashSet<Badge>()).build();

				User user2 = userService.getUser("john@studio.unibo.it");

				new FriendshipBuilder().setSender(userOne).setReceiver(userTwo)
						.setStatus(FriendshipStatus.ACCEPTED).build();
				new FriendshipBuilder().setSender(userOne)
						.setReceiver(userThree)
						.setStatus(FriendshipStatus.PENDING).build();

				ActionPhoto actionPhoto = new ActionPhoto();
				actionPhoto.setDuration_threshold(20);
				actionPhoto.setNumeric_threshold(0);
				actionPhoto.setName("actionPhoto");
				;

				ActionSensing actionLocation = new ActionSensing();
				actionLocation.setDuration_threshold(20);
				actionLocation.setNumeric_threshold(0);
				actionLocation.setInput_type(Pipeline.Type.LOCATION.toInt());
				actionLocation.setName("Geolocation");
				Task taskg = new Task();
				taskg.setName("Geolocation");
				taskg.setDescription("Geolocation task");
				taskg.setStart(new DateTime());
				taskg.setDeadline(new DateTime().plusDays(30));
				taskg.setDuration(10L);
				taskg.setSensingDuration(2L);
				Set<Action> actionsg = new LinkedHashSet<Action>();
				actionsg.add(actionLocation);
				taskg.setActions(actionsg);
				User userg = userService.getUser("john@studio.unibo.it");
				TaskReport taskReportg = new TaskReport();
				taskg.getTaskReport().add(taskReportg);
				taskReportg.setUser(userg);
				taskReportg.setTask(taskg);
				taskReportg.setAcceptedDateTime(new DateTime());
				taskReportg.setExpirationDateTime(new DateTime()
						.plusMinutes(10));
				TaskHistory status = new TaskHistory();
				status.setState(TaskState.AVAILABLE);
				status.setTaskReport(taskReportg);
				status.setTimestamp(new DateTime());
				taskReportg.addHistory(status);
				status = new TaskHistory();
				status.setState(TaskState.RUNNING);
				status.setTaskReport(taskReportg);
				status.setTimestamp(new DateTime());
				taskReportg.addHistory(status);
				taskService.save(taskg);
				DataLocation dl = new DataLocation();
				dl.setAccuracy(20);
				dl.setSampleTimestamp(new DateTime());
				dl.setLatitude(44.487191);
				dl.setLongitude(11.328278);
				dl.setProvider("network");
				dl.setDataReceivedTimestamp(new DateTime());
				dl.setUser(userg);
				dataService.save(dl);
				dl = new DataLocation();
				dl.setAccuracy(20);
				dl.setSampleTimestamp(new DateTime());
				dl.setLatitude(44.493562);
				dl.setLongitude(11.343212);
				dl.setProvider("network");
				dl.setDataReceivedTimestamp(new DateTime().plusMinutes(1));
				dl.setUser(userg);
				dataService.save(dl);
				dl = new DataLocation();
				dl.setAccuracy(20);
				dl.setSampleTimestamp(new DateTime());
				dl.setLatitude(44.505255);
				dl.setLongitude(11.341925);
				dl.setProvider("network");
				dl.setDataReceivedTimestamp(new DateTime().plusMinutes(2));
				dl.setUser(userg);
				dataService.save(dl);

				ActionSensing appOnScreen = new ActionSensing();
				appOnScreen.setDuration_threshold(2);
				appOnScreen.setNumeric_threshold(0);
				appOnScreen.setInput_type(Pipeline.Type.APP_ON_SCREEN.toInt());
				appOnScreen.setName("AppOnScreen");
				actionService.save(appOnScreen);

				ActionSensing acc = new ActionSensing();
				acc.setName("Accelerometer");
				acc.setDuration_threshold(2);
				acc.setNumeric_threshold(0);
				acc.setInput_type(Pipeline.Type.ACCELEROMETER.toInt());
				actionService.save(acc);

				ActionSensing geo = new ActionSensing();
				geo.setName("GeoLocation");
				geo.setDuration_threshold(3);
				geo.setNumeric_threshold(0);
				geo.setInput_type(Pipeline.Type.LOCATION.toInt());
				actionService.save(geo);

				Task task = new Task();
				task.setName("AppOnScreen e Accelerometro");
				task.setDescription("Task scheduled");
				task.setDeadline(new DateTime(2013, 7, 15, 0, 0, 0));
				task.setStart(new DateTime(2013, 7, 10, 0, 0, 0));
				task.setDuration(10L);
				task.setSensingDuration(2L);
				Set<Action> actions = new LinkedHashSet<Action>();
				actions.add(appOnScreen);
				actions.add(acc);
				task.setActions(actions);

				Task sTask = new Task();
				sTask.setDeadline(new DateTime(2013, 7, 15, 0, 0, 0));
				sTask.setDescription("Task di sensing");
				sTask.setName("Accelerometro");
				sTask.setDuration(0L);
				sTask.setSensingDuration(0L);
				sTask.setStart(new DateTime(2013, 7, 1, 0, 0, 0));
				Set<Action> sActions = new LinkedHashSet<Action>();
				sActions.add(acc);
				sTask.setActions(sActions);

				Task task2 = new Task();
				task2.setDeadline(new DateTime());
				task2.setDescription("Task di sensing 2");
				task2.setName("App on Screen 2");
				task2.setDescription("Prova descrizione");
				task2.setDuration(5L);
				task2.setSensingDuration(3L);
				task2.setStart(new DateTime());
				Set<Action> task2Actions = new LinkedHashSet<Action>();
				task2Actions.add(appOnScreen);
				task2.setActions(task2Actions);
				
				
				Task toTask3 = new Task();
				toTask3.setDeadline(new DateTime().plusDays(20));
				toTask3.setDescription("Task di sensing 3");
				toTask3.setName("App on Screen 3");
				toTask3.setDescription("Prova");
				toTask3.setDuration(5L);
				toTask3.setSensingDuration(3L);
				toTask3.setStart(new DateTime());
				Set<Action> toTask3Actions = new LinkedHashSet<Action>();
				toTask3Actions.add(appOnScreen);
				toTask3.setActions(toTask3Actions);

				TaskUser task3 = new TaskUser();
				task3.setTask(toTask3);				
				task3.setOwner(user);
				
				Task toTask4 = new Task();
				toTask4.setDeadline(new DateTime().plusDays(28));
				toTask4.setDescription("Task di sensing 4");
				toTask4.setName("App on Screen 4");
				toTask4.setDescription("Prova");
				toTask4.setDuration(5L);
				toTask4.setSensingDuration(3L);
				toTask4.setStart(new DateTime());
				Set<Action> task4Actions = new LinkedHashSet<Action>();
				task4Actions.add(appOnScreen);
				toTask4.setActions(task4Actions);				

				TaskUser task4 = new TaskUser();				
				task4.setOwner(user2);
				task4.setTask(toTask4);
				
				
				Task toTask5 = new Task();
				toTask5.setDeadline(new DateTime().plusDays(28));
				toTask5.setDescription("GeoLocation 2");
				toTask5.setName("GeoLocation 2");
				toTask5.setDescription("Prova");
				toTask5.setDuration(5L);
				toTask5.setSensingDuration(3L);
				toTask5.setStart(new DateTime());				
				Set<Action> task5Actions = new LinkedHashSet<Action>();
				task5Actions.add(geo);
				toTask5.setActions(task5Actions);
				
				TaskUser task5 = new TaskUser();				
				task5.setOwner(user);
				task5.setTask(toTask5);
				
				Task toTask6 = new Task();
				toTask6.setDeadline(new DateTime().plusDays(28));
				toTask6.setDescription("Photo");
				toTask6.setName("Photo");
				toTask6.setDescription("Prova");
				toTask6.setDuration(5L);
				toTask6.setSensingDuration(3L);
				toTask6.setStart(new DateTime());
				Set<Action> task6Actions = new LinkedHashSet<Action>();
				task6Actions.add(actionPhoto);
				toTask6.setActions(task6Actions);
				
				TaskUser task6 = new TaskUser();				
				task6.setValutation(TaskValutation.APPROVED);
				task6.setTask(toTask6);
				task6.setOwner(user);
				
				

				Task task7 = new Task();
				task7.setDeadline(new DateTime().plusDays(28));
				task7.setDescription("Task foto 2");
				task7.setName("foto 2");
				task7.setDescription("Prova");
				task7.setDuration(5L);
				task7.setSensingDuration(3L);
				task7.setStart(new DateTime());
				Set<Action> task7Actions = new LinkedHashSet<Action>();
				task7Actions.add(actionPhoto);
				task7.setActions(task7Actions);

				taskService.save(task);
				taskService.save(sTask);
				taskService.save(task2);
				taskUserService.save(task3);
				taskUserService.save(task4);

				taskUserService.save(task5);
				taskUserService.save(task6);
				taskService.save(task7);

				TaskReport taskReport = new TaskReport();
				taskReport.setUser(user);
				taskReport.setTask(task);

				TaskReport sTaskReport = new TaskReport();
				sTaskReport.setUser(user);
				sTaskReport.setTask(sTask);

				TaskReport taskReport2 = new TaskReport();
				taskReport2.setUser(user);
				taskReport2.setTask(task2);

				TaskReport taskReport3 = new TaskReport();
				taskReport3.setCurrentState(TaskState.RUNNING);
				taskReport3.setUser(user2);
				taskReport3.setTask(task6.getTask());

				TaskReport taskReport4 = new TaskReport();
				taskReport4.setCurrentState(TaskState.RUNNING);
				taskReport4.setUser(user);
				taskReport4.setTask(task7);

				TaskHistory history = new TaskHistory();
				history.setState(TaskState.COMPLETED_WITH_FAILURE);
				history.setTaskReport(taskReport);
				history.setTimestamp(new DateTime());
				taskReport.addHistory(history);
				taskReportService.save(taskReport);

				history = new TaskHistory();
				history.setState(TaskState.REJECTED);
				history.setTaskReport(sTaskReport);
				history.setTimestamp(new DateTime());
				sTaskReport.addHistory(history);
				taskReportService.save(sTaskReport);

				history = new TaskHistory();
				history.setState(TaskState.COMPLETED_WITH_SUCCESS);
				history.setTaskReport(taskReport2);
				history.setTimestamp(new DateTime());
				taskReport2.addHistory(history);
				taskReportService.save(taskReport2);

				history = new TaskHistory();
				history.setState(TaskState.RUNNING);
				history.setTaskReport(taskReport3);
				history.setTimestamp(new DateTime());
				taskReport2.addHistory(history);
				taskReportService.save(taskReport3);

				history = new TaskHistory();
				history.setState(TaskState.RUNNING);
				history.setTaskReport(taskReport4);
				history.setTimestamp(new DateTime());
				taskReport2.addHistory(history);
				taskReportService.save(taskReport4);

				history = new TaskHistory();
				history.setState(TaskState.AVAILABLE);
				history.setTaskReport(taskReport3);
				history.setTimestamp(new DateTime());
				taskReport3.addHistory(history);
				taskReportService.save(taskReport3);
				
				/*DataPhoto data = new DataPhoto();
				data.setSampleTimestamp(new DateTime());
				data.setDataReceivedTimestamp(new DateTime());
				data.setHeight(1920);
				data.setWidth(1200);

				data.setActionId(actionPhoto.getId());
				data.setTaskId(task6.getId());

				data.setUser(user2);

				DataPhoto data2 = new DataPhoto();
				data2.setSampleTimestamp(new DateTime());
				data2.setDataReceivedTimestamp(new DateTime());
				data2.setHeight(500);
				data2.setWidth(700);

				data2.setActionId(actionPhoto.getId());
				data2.setTaskId(task7.getId());

				data2.setUser(user);

				InputStream photoInputStream;
				try {
					photoInputStream = Thread.currentThread()
							.getContextClassLoader()
							.getResourceAsStream("test.jpg");

					byte[] photobites = IOUtils.toByteArray(photoInputStream);
					BinaryDocument binaryDocument = new BinaryDocument();
					binaryDocument.setContent(photobites);
					binaryDocument.setContentType("image");
					binaryDocument.setContentSubtype("jpeg");
					binaryDocument.setCreated(new DateTime());

					String filename = String.format(
							"task_%09d_action_%09d_%s.jpg", data.getTaskId(),
							data.getActionId(), user2.getOfficialEmail());
					binaryDocument.setFilename(filename);
					binaryDocument.setOwner(user2);

					data.setFile(binaryDocument);
					dataService.save(data);
					taskResultService.addData(task6.getId(), user2.getId(),
							data);

					BinaryDocument binaryDocument2 = new BinaryDocument();
					binaryDocument2.setContent(photobites);
					binaryDocument2.setContentType("image");
					binaryDocument2.setContentSubtype("jpeg");
					binaryDocument2.setCreated(new DateTime());

					filename = String.format("task_%09d_action_%09d_%s.jpg",
							data2.getTaskId(), data2.getActionId(),
							user.getOfficialEmail());
					binaryDocument2.setFilename(filename);
					binaryDocument2.setOwner(user);
					data2.setFile(binaryDocument2);
					dataService.save(data2);
					taskResultService.addData(task7.getId(), user.getId(),
							data2);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

				// Task t = taskService.findById(8L);
				// List<TaskReport> result =
				// taskReportService.getTaskReportsByTask(t.getId());
				// for(TaskReport report: result){
				// String historyString = "";
				// // for(TaskHistory hist : report.getHistory()){
				// // historyString += hist.getState() + " ";
				// // }
				// // String print =
				// String.format("Task report associato all'user %s e al task %s, history %s",
				// report.getUser().getName(),report.getTask().toString());
				// // System.out.println(print);
				// }

				// float x = 0.0f;
				// float y = 1.0f;
				// float z = 0.3f;
				//
				//
				// DataAccelerometer data = new DataAccelerometer();
				// data.setUser(user);
				// data.setSampleTimestamp(new DateTime());
				// data.setDataReceivedTimestamp(new DateTime());
				// data.setX(x);
				// data.setY(y);
				// data.setZ(z);
				// dataService.save(data);
				//
				// List<TaskReport> taskReports =
				// taskReportService.getTaskReportsByUser(user.getId());
				//
				// for(TaskReport report : taskReports){
				// Set<Action> reportActions = report.getTask().getActions();
				// for(Action act : reportActions){
				// if(act.getName().equals("Accelerometer")){
				// report.getTaskResult().getData().add(data);
				// taskReportService.save(report);
				// }
				// }
				// }

				Badge photoBadge = new BadgeActionBuilder()
						.setActionType(ActionType.PHOTO)
						.setDescription("Descrizione Badge Photo - 1")
						.setTitle("Titolo Badge Photo - 1").setQuantity(1)
						.build();
				Badge taskBadge = new BadgeTaskBuilder()
						.setDescription("Descrizione Badge Task 1")
						.setTitle("Titolo Badge Task 1").setTask(taskg).build();
				user.getBadges().add(photoBadge);

				new PointsBuilder().setDate(new DateTime().minusDays(1))
						.setTask(taskg).setUser(userOne).setValue(10).setType(PointsType.TASK_COMPLETED_WITH_SUCCESS).build();
				new PointsBuilder().setDate(new DateTime().minusDays(2))
						.setTask(taskg).setUser(userTwo).setValue(13).setType(PointsType.TASK_COMPLETED_WITH_SUCCESS).build();
				new PointsBuilder().setDate(new DateTime().minusDays(3))
						.setTask(taskg).setUser(userThree).setValue(8).setType(PointsType.TASK_COMPLETED_WITH_SUCCESS).build();

				new SocialPresenceBuilder().setSocialId("1111")
						.setSocialNetwork(SocialPresenceType.TWITTER)
						.setUser(userOne).build();
				new SocialPresenceBuilder().setSocialId("2222")
						.setSocialNetwork(SocialPresenceType.TWITTER)
						.setUser(userTwo).build();
				new SocialPresenceBuilder().setSocialId("3333")
						.setSocialNetwork(SocialPresenceType.FACEBOOK)
						.setUser(userThree).build();
				new SocialPresenceBuilder().setSocialId("4444")
						.setSocialNetwork(SocialPresenceType.FACEBOOK)
						.setUser(userTwo).build();
				new SocialPresenceBuilder().setSocialId("5555")
						.setSocialNetwork(SocialPresenceType.GOOGLE)
						.setUser(userThree).build();
				new SocialPresenceBuilder().setSocialId("6666")
						.setSocialNetwork(SocialPresenceType.GOOGLE)
						.setUser(userOne).build();

				new ReputationBuilder().setActionType(ActionType.PHOTO)
						.setUser(userOne).setValue(70).build();
				new ReputationBuilder().setActionType(ActionType.QUESTIONNAIRE)
						.setUser(userTwo).setValue(60).build();

				return null;
			}

			private boolean isDataAlreadyPresent() {
				return entityManager.createQuery(
						"select count(a.id) from Account a", Long.class)
						.getSingleResult() > 0;
			}
		});
	}
}
