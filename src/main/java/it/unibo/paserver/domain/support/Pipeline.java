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
import it.unibo.paserver.domain.DataSystemStats;
import it.unibo.paserver.domain.DataWifiScan;

public abstract class Pipeline {

	public static enum Type {
		DUMMY, AUDIO_CLASSIFIER, ACCELEROMETER, ACCELEROMETER_CLASSIFIER, RAW_AUDIO, AVERAGE_ACCELEROMETER, APP_ON_SCREEN, APPS_NET_TRAFFIC, BATTERY, BLUETOOTH, CELL, CONNECTION_TYPE, DEVICE_NET_TRAFFIC, GOOGLE_ACTIVITY_RECOGNITION, GYROSCOPE, INSTALLED_APPS, LIGHT, LOCATION, MAGNETIC_FIELD, PHONE_CALL_DURATION, PHONE_CALL_EVENT, SYSTEM_STATS, WIFI_SCAN, ACTIVITY_RECOGNITION_COMPARE, DR, TRAINING, TEST;

		/**
		 * Converts an integer to a valid Pipeline type.
		 * 
		 * @param value
		 *            The integer to convert
		 * @return The Type represented by <code>value</code>. If the conversion
		 *         fails, returns DUMMY.
		 */
		public static Type fromInt(int value) {
			switch (value) {
			case 1:
				return ACCELEROMETER;
			case 2:
				return RAW_AUDIO;
			case 3:
				return AVERAGE_ACCELEROMETER;
			case 4:
				return APP_ON_SCREEN;
			case 5:
				return BATTERY;
			case 6:
				return BLUETOOTH;
			case 7:
				return CELL;
			case 8:
				return GYROSCOPE;
			case 9:
				return INSTALLED_APPS;
			case 10:
				return LIGHT;
			case 11:
				return LOCATION;
			case 12:
				return MAGNETIC_FIELD;
			case 13:
				return PHONE_CALL_DURATION;
			case 14:
				return PHONE_CALL_EVENT;
			case 15:
				return ACCELEROMETER_CLASSIFIER;
			case 16:
				return SYSTEM_STATS;
			case 17:
				return WIFI_SCAN;
			case 18:
				return AUDIO_CLASSIFIER;
			case 19:
				return DEVICE_NET_TRAFFIC;
			case 20:
				return APPS_NET_TRAFFIC;
			case 21:
				return CONNECTION_TYPE;
			case 22:
				return GOOGLE_ACTIVITY_RECOGNITION;
            case 23:
                return ACTIVITY_RECOGNITION_COMPARE;
			case 24:
				return DR;
			case 25:
				return TRAINING;
			case 99:
				return TEST;
			default:
				return DUMMY;
			}
		}

		/**
		 * Converts the Pipeline type to an integer.
		 * 
		 * @return
		 */
		public int toInt() {
			switch (this) {
			case ACCELEROMETER:
				return 1;
			case RAW_AUDIO:
				return 2;
			case AVERAGE_ACCELEROMETER:
				return 3;
			case APP_ON_SCREEN:
				return 4;
			case BATTERY:
				return 5;
			case BLUETOOTH:
				return 6;
			case CELL:
				return 7;
			case GYROSCOPE:
				return 8;
			case INSTALLED_APPS:
				return 9;
			case LIGHT:
				return 10;
			case LOCATION:
				return 11;
			case MAGNETIC_FIELD:
				return 12;
			case PHONE_CALL_DURATION:
				return 13;
			case PHONE_CALL_EVENT:
				return 14;
			case ACCELEROMETER_CLASSIFIER:
				return 15;
			case SYSTEM_STATS:
				return 16;
			case WIFI_SCAN:
				return 17;
			case AUDIO_CLASSIFIER:
				return 18;
			case DEVICE_NET_TRAFFIC:
				return 19;
			case APPS_NET_TRAFFIC:
				return 20;
			case CONNECTION_TYPE:
				return 21;
			case GOOGLE_ACTIVITY_RECOGNITION:
				return 22;
            case ACTIVITY_RECOGNITION_COMPARE:
                return 23;
			case DR:
				return 24;
			case TRAINING:
				return 25;
			case TEST:
				return 99;
			default:
				return 0;
			}
		}

		public Class<? extends Data> getDataClass() {
			switch (this) {
			case ACCELEROMETER:
				return DataAccelerometer.class;
			case ACCELEROMETER_CLASSIFIER:
				return DataAccelerometerClassifier.class;
			case ACTIVITY_RECOGNITION_COMPARE:
				return DataActivityRecognitionCompare.class;
			case APP_ON_SCREEN:
				return DataAppOnScreen.class;
			case APPS_NET_TRAFFIC:
				return DataAppNetTraffic.class;
			case BATTERY:
				return DataBattery.class;
			case BLUETOOTH:
				return DataBluetooth.class;
			case CELL:
				return DataCell.class;
			case CONNECTION_TYPE:
				return DataConnectionType.class;
			case DEVICE_NET_TRAFFIC:
				return DataDeviceNetTraffic.class;
			case GOOGLE_ACTIVITY_RECOGNITION:
				return DataGoogleActivityRecognition.class;
			case GYROSCOPE:
				return DataGyroscope.class;
			case INSTALLED_APPS:
				return DataInstalledApps.class;
			case LIGHT:
				return DataLight.class;
			case LOCATION:
				return DataLocation.class;
			case MAGNETIC_FIELD:
				return DataMagneticField.class;
			case PHONE_CALL_DURATION:
				return DataPhoneCallDuration.class;
			case PHONE_CALL_EVENT:
				return DataPhoneCallEvent.class;
			case SYSTEM_STATS:
				return DataSystemStats.class;
			case WIFI_SCAN:
				return DataWifiScan.class;
			case DR:
				return DataDR.class;
			default:
				return null;
			}
		}
	}
}
