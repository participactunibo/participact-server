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
package it.unibo.paserver.rest.controller;

import it.unibo.participact.domain.proto.DataAccelerometerClassifierProtos.DataAccelerometerClassifierProto;
import it.unibo.participact.domain.proto.DataAccelerometerClassifierProtos.DataAccelerometerClassifierProtoList;
import it.unibo.participact.domain.proto.DataAccelerometerProtos.DataAccelerometerProto;
import it.unibo.participact.domain.proto.DataAccelerometerProtos.DataAccelerometerProtoList;
import it.unibo.participact.domain.proto.DataActivityRecognitionCompareProtos.DataActivityRecognitionCompareProto;
import it.unibo.participact.domain.proto.DataActivityRecognitionCompareProtos.DataActivityRecognitionCompareProtoList;
import it.unibo.participact.domain.proto.DataAppOnScreenProtos.DataAppOnScreenProto;
import it.unibo.participact.domain.proto.DataAppOnScreenProtos.DataAppOnScreenProtoList;
import it.unibo.participact.domain.proto.DataBatteryProtos.DataBatteryProto;
import it.unibo.participact.domain.proto.DataBatteryProtos.DataBatteryProtoList;
import it.unibo.participact.domain.proto.DataBluetoothProtos.DataBluetoothProto;
import it.unibo.participact.domain.proto.DataBluetoothProtos.DataBluetoothProtoList;
import it.unibo.participact.domain.proto.DataCellProtos.DataCellProto;
import it.unibo.participact.domain.proto.DataCellProtos.DataCellProtoList;
import it.unibo.participact.domain.proto.DataConnectionTypeProtos.DataConnectionTypeProto;
import it.unibo.participact.domain.proto.DataConnectionTypeProtos.DataConnectionTypeProtoList;
import it.unibo.participact.domain.proto.DataDRProtos.DataDRProto;
import it.unibo.participact.domain.proto.DataDRProtos.DataDRProtoList;
import it.unibo.participact.domain.proto.DataGoogleActivityRecognitionProtos.DataGoogleActivityRecognitionProto;
import it.unibo.participact.domain.proto.DataGoogleActivityRecognitionProtos.DataGoogleActivityRecognitionProtoList;
import it.unibo.participact.domain.proto.DataGyroscopeProtos.DataGyroscopeProto;
import it.unibo.participact.domain.proto.DataGyroscopeProtos.DataGyroscopeProtoList;
import it.unibo.participact.domain.proto.DataInstalledAppsProtos.DataInstalledAppsProto;
import it.unibo.participact.domain.proto.DataInstalledAppsProtos.DataInstalledAppsProtoList;
import it.unibo.participact.domain.proto.DataLightProtos.DataLightProto;
import it.unibo.participact.domain.proto.DataLightProtos.DataLightProtoList;
import it.unibo.participact.domain.proto.DataLocationProtos.DataLocationProto;
import it.unibo.participact.domain.proto.DataLocationProtos.DataLocationProtoList;
import it.unibo.participact.domain.proto.DataMagneticFieldProtos.DataMagneticFieldProto;
import it.unibo.participact.domain.proto.DataMagneticFieldProtos.DataMagneticFieldProtoList;
import it.unibo.participact.domain.proto.DataNetTrafficProtos.DataNetTrafficProto;
import it.unibo.participact.domain.proto.DataNetTrafficProtos.DataNetTrafficProtoList;
import it.unibo.participact.domain.proto.DataPhoneCallDurationProtos.DataPhoneCallDurationProto;
import it.unibo.participact.domain.proto.DataPhoneCallDurationProtos.DataPhoneCallDurationProtoList;
import it.unibo.participact.domain.proto.DataPhoneCallEventProtos.DataPhoneCallEventProto;
import it.unibo.participact.domain.proto.DataPhoneCallEventProtos.DataPhoneCallEventProtoList;
import it.unibo.participact.domain.proto.DataPhotoProtos.DataPhotoProto;
import it.unibo.participact.domain.proto.DataPhotoProtos.DataPhotoProtoList;
import it.unibo.participact.domain.proto.DataQuestionnaireFlatProtos.DataQuestionnaireFlatProto;
import it.unibo.participact.domain.proto.DataQuestionnaireFlatProtos.DataQuestionnaireFlatProtoList;
import it.unibo.participact.domain.proto.DataSystemStatsProtos.DataSystemStatsProto;
import it.unibo.participact.domain.proto.DataSystemStatsProtos.DataSystemStatsProtoList;
import it.unibo.participact.domain.proto.DataWifiScanProtos.DataWifiScanProto;
import it.unibo.participact.domain.proto.DataWifiScanProtos.DataWifiScanProtoList;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionQuestionaire;
import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.ClosedAnswer;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataAccelerometer;
import it.unibo.paserver.domain.DataAccelerometerClassifier;
import it.unibo.paserver.domain.DataActivityRecognitionCompare;
import it.unibo.paserver.domain.DataAppNetTraffic;
import it.unibo.paserver.domain.DataAppOnScreen;
import it.unibo.paserver.domain.DataBattery;
import it.unibo.paserver.domain.DataBluetooth;
import it.unibo.paserver.domain.DataCell;
import it.unibo.paserver.domain.DataConnectionType;
import it.unibo.paserver.domain.DataDR;
import it.unibo.paserver.domain.DataDeviceNetTraffic;
import it.unibo.paserver.domain.DataGoogleActivityRecognition;
import it.unibo.paserver.domain.DataGyroscope;
import it.unibo.paserver.domain.DataInstalledApps;
import it.unibo.paserver.domain.DataLight;
import it.unibo.paserver.domain.DataLocation;
import it.unibo.paserver.domain.DataMagneticField;
import it.unibo.paserver.domain.DataPhoneCallDuration;
import it.unibo.paserver.domain.DataPhoneCallEvent;
import it.unibo.paserver.domain.DataPhoto;
import it.unibo.paserver.domain.DataQuestionaireClosedAnswer;
import it.unibo.paserver.domain.DataQuestionaireOpenAnswer;
import it.unibo.paserver.domain.DataSystemStats;
import it.unibo.paserver.domain.DataWifiScan;
import it.unibo.paserver.domain.GeobadgeCollected;
import it.unibo.paserver.domain.InterestPoint;
import it.unibo.paserver.domain.Question;
import it.unibo.paserver.domain.ResponseMessage;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.service.ActionService;
import it.unibo.paserver.service.DataService;
import it.unibo.paserver.service.GeobadgeCollectedService;
import it.unibo.paserver.service.InterestPointService;
import it.unibo.paserver.service.TaskResultService;
import it.unibo.paserver.service.TaskService;
import it.unibo.paserver.service.UserService;

import java.net.SocketTimeoutException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResultDataController {

	@Autowired
	UserService userService;

	@Autowired
	DataService dataService;

	@Autowired
	ActionService actionService;

	@Autowired
	TaskService taskService;

	@Autowired
	TaskResultService taskResultService;
	
	@Autowired
	GeobadgeCollectedService geobadgeCollectedService;
	
	@Autowired
	InterestPointService interestPointService;

	private static final Logger logger = LoggerFactory
			.getLogger(ResultDataController.class);

	public static final String HEADER_KEY = "Request_key";

	@RequestMapping(value = "/rest/upload/accelerometer", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataAccelerometer(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataAccelerometerProtoList list = DataAccelerometerProtoList
						.parseFrom(request.getInputStream());
				List<DataAccelerometerProto> dataList = list.getListList();
				logger.info(
						"Received {} accelerometer data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataAccelerometerProto dataAccelerometerProto : dataList) {
					DataAccelerometer data = new DataAccelerometer();
					data.setSampleTimestamp(new DateTime(dataAccelerometerProto
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setX(dataAccelerometerProto.getX());
					data.setY(dataAccelerometerProto.getY());
					data.setZ(dataAccelerometerProto.getZ());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAccelerometer",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/accelerometerClassifier", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataAccelerometerClassifier(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataAccelerometerClassifierProtoList list = DataAccelerometerClassifierProtoList
						.parseFrom(request.getInputStream());
				List<DataAccelerometerClassifierProto> dataList = list
						.getListList();

				logger.info(
						"Received {} accelerometerClassifier data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataAccelerometerClassifierProto netData : dataList) {
					DataAccelerometerClassifier data = new DataAccelerometerClassifier();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setValue(netData.getValue());
					data.setDataReceivedTimestamp(now);
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAccelerometerClassifier",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}
	
	@RequestMapping(value = "/rest/upload/dr", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataDR(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataDRProtoList list = DataDRProtoList
						.parseFrom(request.getInputStream());
				List<DataDRProto> dataList = list
						.getListList();

				logger.info(
						"Received {} dr data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataDRProto netData : dataList) {

					DataDR data = new DataDR();
					data.setSampleTimestamp(new DateTime(netData.getTimestamp()));
					data.setDataReceivedTimestamp(now);
					data.setUser(user);
					data.setDataReceivedTimestamp(new DateTime());
					
					data.setAccuracy(netData.getAccuracy());
					data.setLatitude(netData.getLatitude());
					data.setLongitude(netData.getLongitude());
					data.setPole(netData.getPole());
					data.setState(netData.getState());
					data.setStatus(netData.getStatus());

					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataDR",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/activityrecognitioncompare", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataActivityRecognitionCompare(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataActivityRecognitionCompareProtoList list = DataActivityRecognitionCompareProtoList
						.parseFrom(request.getInputStream());
				List<DataActivityRecognitionCompareProto> dataList = list
						.getListList();

				logger.info(
						"Received {} activityRecognitionCompare data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataActivityRecognitionCompareProto netData : dataList) {

					DataActivityRecognitionCompare data = new DataActivityRecognitionCompare();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setUser(user);
					data.setDataReceivedTimestamp(new DateTime());
					data.setGoogleArConfidence(netData.getGoogleArConfidence());
					data.setGoogleArTimestamp(new DateTime(netData
							.getGoogleArTimestamp()));
					data.setGoogleArValue(netData.getGoogleArValue());
					data.setMostArTimestamp(new DateTime(netData
							.getMostArTimestamp()));
					data.setMostArValue(netData.getMostArValue());
					data.setUserActivity(netData.getUserActivity());
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAccelerometerClassifier",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/apponscreen", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataAppOnScreen(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataAppOnScreenProtoList list = DataAppOnScreenProtoList
						.parseFrom(request.getInputStream());
				List<DataAppOnScreenProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} AppOnScreen items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataAppOnScreenProto netData : dataList) {

					DataAppOnScreen data = new DataAppOnScreen();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setAppName(netData.getAppName());
					data.setEndTime(new DateTime(netData.getEndTime()));
					data.setStartTime(new DateTime(netData.getStartTime()));
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAppOnScreen",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/appsnettraffic", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataAppsNetTraffic(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataNetTrafficProtoList list = DataNetTrafficProtoList
						.parseFrom(request.getInputStream());
				List<DataNetTrafficProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} DataNetTraffic (for apps) items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataNetTrafficProto netData : dataList) {

					DataAppNetTraffic data = new DataAppNetTraffic();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setName(netData.getAppName());
					data.setRxBytes(netData.getRxBytes());
					data.setTxBytes(netData.getTxBytes());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAppOnScreen",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/battery", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataBattery(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataBatteryProtoList list = DataBatteryProtoList
						.parseFrom(request.getInputStream());
				List<DataBatteryProto> dataList = list.getListList();

				DateTime now = new DateTime();
				logger.info(
						"Received {} battery data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				List<Data> resultData = new ArrayList<Data>();
				for (DataBatteryProto netData : dataList) {

					DataBattery data = new DataBattery();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setHealth(netData.getHealth());
					data.setLevel(netData.getLevel());
					data.setPlugged(netData.getPlugged());
					data.setScale(netData.getScale());
					data.setStatus(netData.getStatus());
					data.setTemperature(netData.getTemperature());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error("Error while receiving data in submitDataBattery",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/bluetooth", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataBluetooth(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataBluetoothProtoList list = DataBluetoothProtoList
						.parseFrom(request.getInputStream());
				List<DataBluetoothProto> dataList = list.getListList();

				DateTime now = new DateTime();
				logger.info(
						"Received {} bluetooth data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				List<Data> resultData = new ArrayList<Data>();
				for (DataBluetoothProto netData : dataList) {

					DataBluetooth data = new DataBluetooth();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setDeviceClass(netData.getDeviceClass());
					data.setFriendlyName(netData.getFriendlyName());
					data.setMac(netData.getMac());
					data.setMajorClass(netData.getMajorClass());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataBluetooth", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/connectiontype", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataConnectionType(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataConnectionTypeProtoList list = DataConnectionTypeProtoList
						.parseFrom(request.getInputStream());
				List<DataConnectionTypeProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} DataConnectionType items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataConnectionTypeProto netData : dataList) {

					DataConnectionType data = new DataConnectionType();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setConnectionType(netData.getConnectionType());
					data.setMobileNetworkType(netData.getMobileNetworkType());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAppOnScreen",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/cell", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataCell(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataCellProtoList list = DataCellProtoList.parseFrom(request
						.getInputStream());
				List<DataCellProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} cell data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataCellProto netData : dataList) {

					DataCell data = new DataCell();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setBaseNetworkId(netData.getBaseNetworkId());
					data.setBaseStationId(netData.getBaseStationId());
					data.setBaseStationLatitude(netData
							.getBaseStationLatitude());
					data.setBaseStationLongitude(netData
							.getBaseStationLongitude());
					data.setBaseSystemId(netData.getBaseSystemId());
					data.setGsmCellId(netData.getGsmCellId());
					data.setGsmLac(netData.getGsmLac());
					data.setPhoneType(netData.getPhoneType());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error("Error while receiving data in submitDataCell", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/devicenettraffic", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataDeviceNetTraffic(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataNetTrafficProtoList list = DataNetTrafficProtoList
						.parseFrom(request.getInputStream());
				List<DataNetTrafficProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} DataNetTraffic (for device) items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataNetTrafficProto netData : dataList) {

					DataDeviceNetTraffic data = new DataDeviceNetTraffic();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setRxBytes(netData.getRxBytes());
					data.setTxBytes(netData.getTxBytes());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataAppOnScreen",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/googleactivityrecognition", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataGoogleActivityRecognition(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataGoogleActivityRecognitionProtoList list = DataGoogleActivityRecognitionProtoList
						.parseFrom(request.getInputStream());
				List<DataGoogleActivityRecognitionProto> dataList = list
						.getListList();
				logger.info(
						"Received {} GoogleActivityRecognition data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataGoogleActivityRecognitionProto netData : dataList) {
					DataGoogleActivityRecognition data = new DataGoogleActivityRecognition();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setUser(user);
					data.setConfidence(netData.getConfidence());
					data.setClassifier_value(netData.getRecognizedActivity());

					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataGoogleActivityRecognition",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/gyroscope", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataGyroscope(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataGyroscopeProtoList list = DataGyroscopeProtoList
						.parseFrom(request.getInputStream());
				List<DataGyroscopeProto> dataList = list.getListList();
				logger.info(
						"Received {} gyroscope data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataGyroscopeProto netData : dataList) {
					DataGyroscope data = new DataGyroscope();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setRotationX(netData.getRotationX());
					data.setRotationY(netData.getRotationY());
					data.setRotationZ(netData.getRotationZ());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataGyroscope", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/installedapps", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataInstalledApps(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataInstalledAppsProtoList list = DataInstalledAppsProtoList
						.parseFrom(request.getInputStream());
				List<DataInstalledAppsProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} dataInstalledApps items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataInstalledAppsProto netData : dataList) {

					DataInstalledApps data = new DataInstalledApps();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setPkgName(netData.getPkgName());
					data.setRequestedPermissions(netData
							.getRequestedPermissions());
					data.setVersionCode(netData.getVersionCode());
					data.setVersionName(netData.getVersionName());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataInstalledApps",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/light", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataLight(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataLightProtoList list = DataLightProtoList.parseFrom(request
						.getInputStream());
				List<DataLightProto> dataList = list.getListList();

				logger.info(
						"Received {} light data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataLightProto netData : dataList) {

					DataLight data = new DataLight();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setValue(netData.getValue());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error("Error while receiving data in submitDataLight", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/location", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataLocation(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataLocationProtoList list = DataLocationProtoList
						.parseFrom(request.getInputStream());
				List<DataLocationProto> dataList = list.getListList();

				logger.info(
						"Received {} dataLocation items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataLocationProto netData : dataList) {

					DataLocation data = new DataLocation();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setAccuracy(netData.getAccuracy());
					data.setLatitude(netData.getLatitude());
					data.setLongitude(netData.getLongitude());
					data.setProvider(netData.getProvider());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataLocation", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/magneticfield", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataMagneticField(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataMagneticFieldProtoList list = DataMagneticFieldProtoList
						.parseFrom(request.getInputStream());
				List<DataMagneticFieldProto> dataList = list.getListList();

				logger.info(
						"Received {} dataMagneticField items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataMagneticFieldProto netData : dataList) {

					DataMagneticField data = new DataMagneticField();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setMagneticFieldX(netData.getMagneticFieldX());
					data.setMagneticFieldY(netData.getMagneticFieldY());
					data.setMagneticFieldZ(netData.getMagneticFieldZ());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataMagneticField",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/phonecallduration", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataPhoneCallDuration(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataPhoneCallDurationProtoList list = DataPhoneCallDurationProtoList
						.parseFrom(request.getInputStream());
				List<DataPhoneCallDurationProto> dataList = list.getListList();

				logger.info(
						"Received {} dataPhoneCallDuration items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataPhoneCallDurationProto netData : dataList) {

					DataPhoneCallDuration data = new DataPhoneCallDuration();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setCallEnd(new DateTime(netData.getCallEnd()));
					data.setCallStart(new DateTime(netData.getCallStart()));
					data.setIsIncoming(netData.getIsIncoming());
					data.setPhoneNumber(netData.getPhoneNumber());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataPhoneCallDuration",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/phonecallevent", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataPhoneCallEvent(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataPhoneCallEventProtoList list = DataPhoneCallEventProtoList
						.parseFrom(request.getInputStream());
				List<DataPhoneCallEventProto> dataList = list.getListList();

				logger.info(
						"Received {} dataPhoneCallEvent items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				for (DataPhoneCallEventProto netData : dataList) {

					DataPhoneCallEvent data = new DataPhoneCallEvent();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);
					data.setIsIncomingCall(netData.getIsIncoming());
					data.setIsStart(netData.getIsStart());
					data.setPhoneNumber(netData.getPhoneNumber());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataPhoneCallEvent",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/systemstats", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataSystemStats(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataSystemStatsProtoList list = DataSystemStatsProtoList
						.parseFrom(request.getInputStream());
				List<DataSystemStatsProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} dataSystemStats items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataSystemStatsProto netData : dataList) {

					DataSystemStats data = new DataSystemStats();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setBOOT_TIME(netData.getBOOTTIME());
					data.setCONTEXT_SWITCHES(netData.getCONTEXTSWITCHES());
					data.setCPU_FREQUENCY(netData.getCPUFREQUENCY());
					data.setCPU_HARDIRQ(netData.getCPUHARDIRQ());
					data.setCPU_IDLE(netData.getCPUIDLE());
					data.setCPU_IOWAIT(netData.getCPUIOWAIT());
					data.setCPU_NICED(netData.getCPUNICED());
					data.setCPU_SOFTIRQ(netData.getCPUSOFTIRQ());
					data.setCPU_SYSTEM(netData.getCPUSYSTEM());
					data.setCPU_USER(netData.getCPUUSER());
					data.setMEM_ACTIVE(netData.getMEMACTIVE());
					data.setMEM_FREE(netData.getMEMFREE());
					data.setMEM_INACTIVE(netData.getMEMINACTIVE());
					data.setMEM_TOTAL(netData.getMEMTOTAL());
					data.setPROCESSES(netData.getPROCESSES());

					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataSystemStats",
						e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/wifiscan", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataWifiScan(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataWifiScanProtoList list = DataWifiScanProtoList
						.parseFrom(request.getInputStream());
				List<DataWifiScanProto> dataList = list.getListList();

				DateTime now = new DateTime();
				List<Data> resultData = new ArrayList<Data>();
				logger.info(
						"Received {} dataWifiScan data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);
				for (DataWifiScanProto netData : dataList) {

					DataWifiScan data = new DataWifiScan();
					data.setSampleTimestamp(new DateTime(netData
							.getSampleTime()));
					data.setDataReceivedTimestamp(now);

					data.setBssid(netData.getBssid());
					data.setCapabilities(netData.getCapabilities());
					data.setFrequency(netData.getFrequency());
					data.setLevel(netData.getLevel());
					data.setSsid(netData.getSsid());
					data.setUser(user);
					resultData.add(data);
				}
				dataService.save(resultData);

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error(
						"Error while receiving data in submitDataWifiScan", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/photo", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataPhoto(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataPhotoProtoList photoProtoList = DataPhotoProtoList
						.parseFrom(request.getInputStream());

				logger.info(
						"Received {} dataPhoto data items from user {} (KEY: {})",
						photoProtoList.getListCount(), user.getOfficialEmail(),
						key);

				for (DataPhotoProto photoProto : photoProtoList.getListList()) {
					try {
						DateTime now = new DateTime();
						Task task = taskService
								.findById(photoProto.getTaskId());
						Action action = actionService.findById(photoProto
								.getActionId());

						if (task == null || action == null) {
							response.setResultCode(ResponseMessage.DATA_NOT_REQUIRED);
							logger.warn(
									"Received photo for task {}, action {}, which are missing. Refusing the upload and returning {}.",
									photoProto.getTaskId(),
									photoProto.getActionId(), response);
							return response;
						}

						DataPhoto data = new DataPhoto();
						data.setSampleTimestamp(new DateTime(photoProto
								.getSampleTime()));
						data.setDataReceivedTimestamp(now);

						data.setActionId(photoProto.getActionId());
						data.setTaskId(photoProto.getTaskId());
						data.setHeight(photoProto.getHeight());
						data.setWidth(photoProto.getWidth());
						data.setUser(user);

						BinaryDocument bd = new BinaryDocument();
						bd.setContent(photoProto.getImage().toByteArray());
						bd.setContentType("image");
						bd.setContentSubtype("jpg");
						bd.setCreated(now);
						String filename = String.format(
								"task_%09d_action_%09d_%s.jpg",
								data.getTaskId(), data.getActionId(),
								user.getOfficialEmail());
						bd.setFilename(filename);
						bd.setOwner(user);

						data.setFile(bd);

						logger.info(
								"Received photo {} from {} ({}x{}px), {} bytes",
								filename, user.getOfficialEmail(), data
										.getWidth(), data.getHeight(), data
										.getFile().getContent().length);

						data = dataService.merge(data);

						taskResultService.addData(photoProto.getTaskId(),
								user.getId(), data);
					} catch (NoResultException e) {
						response.setResultCode(ResponseMessage.DATA_NOT_REQUIRED);
						logger.warn(
								"Received photo for task {}, action {}, which are missing. Refusing the upload and returning {}.",
								photoProto.getTaskId(),
								photoProto.getActionId(), response);
						return response;
					}
				}

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error("Error while receiving data in submitDataPhoto", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}

	@RequestMapping(value = "/rest/upload/question", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage submitDataQuestion(
			HttpServletRequest request, Principal principal) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
				String key = request.getHeader(HEADER_KEY);
				response.setKey(key);
				DataQuestionnaireFlatProtoList list = DataQuestionnaireFlatProtoList
						.parseFrom(request.getInputStream());
				List<DataQuestionnaireFlatProto> dataList = list.getListList();

				DateTime now = new DateTime();
				logger.info(
						"Received {} dataQuestionnaire data items from user {} (KEY: {})",
						list.getListCount(), user.getOfficialEmail(), key);

				Map<Long, List<Data>> taskIdToDataToAdd = new TreeMap<Long, List<Data>>();

				for (DataQuestionnaireFlatProto networkAnswer : dataList) {

					ActionQuestionaire questionnaire = (ActionQuestionaire) actionService
							.findById(networkAnswer.getActionId());

					if (networkAnswer.getType() == 0) {
						// answer to open question
						DataQuestionaireOpenAnswer data = new DataQuestionaireOpenAnswer();
						data.setSampleTimestamp(new DateTime(networkAnswer
								.getSampleTime()));
						data.setDataReceivedTimestamp(now);

						data.setOpenAnswerValue(networkAnswer
								.getOpenAnswerValue());
						Question q = getQuestionById(questionnaire,
								networkAnswer.getQuestionId());
						data.setQuestion(q);
						data.setUser(user);

						Data savedData = dataService.merge(data);
						addData(taskIdToDataToAdd, networkAnswer.getTaskId(),
								savedData);
					} else if (networkAnswer.getType() == 1) {
						// answer to closed question
						DataQuestionaireClosedAnswer data = new DataQuestionaireClosedAnswer();
						data.setSampleTimestamp(new DateTime(networkAnswer
								.getSampleTime()));
						data.setDataReceivedTimestamp(now);

						Question q = getQuestionById(questionnaire,
								networkAnswer.getQuestionId());
						ClosedAnswer ca = getClosedAnswerById(q,
								networkAnswer.getAnswerId());
						data.setClosedAnswer(ca);
						data.setClosedAnswerValue(networkAnswer
								.getClosedAnswerValue());
						data.setUser(user);

						Data savedData = dataService.merge(data);
						addData(taskIdToDataToAdd, networkAnswer.getTaskId(),
								savedData);
					} else {
						logger.warn("Received malformed questionnaire answer, ignoring");
					}

				}

				for (Long taskId : taskIdToDataToAdd.keySet()) {
					taskResultService.addData(taskId, user.getId(),
							taskIdToDataToAdd.get(taskId));
				}

				response.setResultCode(HttpStatus.OK.value());

			} catch (SocketTimeoutException e) {
				logger.warn("Timed out while reading data from socket");
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			} catch (Exception e) {
				logger.error("Error while receiving dataQuestionnaire", e);
				response.setResultCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
		} else {
			response.setResultCode(HttpStatus.UNAUTHORIZED.value());
		}
		logger.debug("Returning {}", response);
		return response;
	}
	
	
	
	@RequestMapping(value = "/rest/geobadgeCollected", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage submitBadgeCollected(
			ModelAndView modelAndView, Principal principal,
			@RequestParam("taskId") Long taskId,
			@RequestParam("actionId") Long actionId,
			@RequestParam("timestamp") Long timeStamp,
			@RequestParam("description") String description) {
		ResponseMessage response = new ResponseMessage();
		User user = userService.getUser(principal.getName());
		if (user != null) {
			try {
//				TaskReport taskReport = taskReportService.findByUserAndTask(
//						user.getId(), taskId);
				logger.info("User {} collected badge {}", user.getOfficialEmail(),
						description);
				GeobadgeCollected geobadgeCollected = new GeobadgeCollected();
				geobadgeCollected.setDescription(description);
				geobadgeCollected.setIdTask(taskId);
				geobadgeCollected.setIdAction(actionId);
				geobadgeCollected.setTimestamp(new DateTime(timeStamp));
//				InterestPoint i = interestPointService.findByDescription(description);
//				geobadgeCollected.setIdInterestPoint(i.getId());
				geobadgeCollected.setIdUser(user.getId());
				geobadgeCollectedService.save(geobadgeCollected);
				logger.info("Geobadge {} save with succes",
						description);
				response.setResultCode(200);
				return response;
			} catch (NoResultException e) {
				logger.error(
						"User {} claims to have failed task {} which is unknown",
						user.getOfficialEmail(), taskId);
				response.setResultCode(200);
				return response;
			}
		}
		response.setResultCode(500);
				return response;
		
		
	}
	
	

	private static void addData(Map<Long, List<Data>> taskIdToDataToAdd,
			Long taskId, Data data) {
		if (taskId == null || data == null) {
			throw new NullPointerException();
		}
		if (!taskIdToDataToAdd.containsKey(taskId)) {
			taskIdToDataToAdd.put(taskId, new ArrayList<Data>());
		}
		taskIdToDataToAdd.get(taskId).add(data);
	}

	private Question getQuestionById(ActionQuestionaire questionnaire, long id) {
		for (Question question : questionnaire.getQuestions()) {
			if (question.getId() == id) {
				return question;
			}
		}
		return null;
	}

	private ClosedAnswer getClosedAnswerById(Question question, long id) {
		for (ClosedAnswer answer : question.getClosed_answers()) {
			if (answer.getId() == id) {
				return answer;
			}
		}
		return null;
	}
}
