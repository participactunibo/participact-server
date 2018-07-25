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
package it.unibo.paserver.groups;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

public class GroupUtils {

	private static final int inM = 1000;
	private static final double R = 6371;
	private static final double pigreco = Math.PI;
	private static final long MILLISECONDSOFHOUR = 1 * 60 * 60 * 1000;

	public static final boolean SAVECONTACTS = false;
	public static final int NUMBEROFUSERINGROUP = 3;
	public static final String FRIENDS = "friends";
	public static final String COMMUNITY = "community";
	public static final String FAMILIARSTRANGERS = "familiarStrangers";
	public static final String STRANGERS = "strangers";

	public static final int DISTANCE = 300;
	public static final int CONTACTSINDAY = 5;
	public static final double INFAMILY = 0.2;
	public static final double MAXACCURACY = 100;

	/*
	 * This function calculates the distance between two points on the Earth's
	 * surface , given the coordinates in latitude and longitude coordinates
	 * expressed in decimal degrees
	 */

	public static DateTime addOneHour(DateTime date) {
		DateTime result = new DateTime(date.getMillis() + MILLISECONDSOFHOUR);
		return result;
	}

	public static DateTime convertDate(String date) {
		int hour, min, sec, anno, mese, day;
		// 2015-05-11

		StringTokenizer tok = new StringTokenizer(date, " :-");
		try {
			anno = Integer.parseInt(tok.nextToken().trim());
			mese = Integer.parseInt(tok.nextToken().trim());
			day = Integer.parseInt(tok.nextToken().trim());
			hour = 0;
			min = 0;
			sec = 0;
		} catch (Exception e) {
			return null;
		}

		if (mese < 0 || mese > 12 || day < 0 || day > 31)
			return null;

		DateTime result = new DateTime(anno, mese, day, hour, min, sec);
		return result;
	}

	public static double getDistance(double latA, double lonA, double latB,
			double lonB) {

		double lat_alfa, lat_beta;
		double lon_alfa, lon_beta;
		double fi;
		double p, d;

		lat_alfa = pigreco * latA / 180;
		lat_beta = pigreco * latB / 180;
		lon_alfa = pigreco * lonA / 180;
		lon_beta = pigreco * lonB / 180;

		fi = Math.abs(lon_alfa - lon_beta);

		p = Math.acos(Math.sin(lat_beta) * Math.sin(lat_alfa)
				+ Math.cos(lat_beta) * Math.cos(lat_alfa) * Math.cos(fi));

		d = p * R * inM;
		return d;
	}

	public static List<Long> initRandomGroups(List<Long> inGroup,
			List<Long> users) {
		Random random = new Random(System.currentTimeMillis());
		ArrayList<Long> result = new ArrayList<Long>();

		int dim = random.nextInt(15);

		for (int i = 0; i < dim; i++) {
			int toAdd = random.nextInt(users.size());
			while (inGroup.contains(users.get(toAdd)))
				toAdd = random.nextInt(users.size());
			result.add(users.get(toAdd));
		}
		return result;
	}

	public static double getDistance(GroupPoint pointA, GroupPoint pointB) {

		double lat_alfa, lat_beta;
		double lon_alfa, lon_beta;
		double fi;
		double p, d;

		lat_alfa = pigreco * pointA.getLatitude() / 180;
		lat_beta = pigreco * pointB.getLatitude() / 180;
		lon_alfa = pigreco * pointA.getLongitude() / 180;
		lon_beta = pigreco * pointB.getLongitude() / 180;

		fi = Math.abs(lon_alfa - lon_beta);

		p = Math.acos(Math.sin(lat_beta) * Math.sin(lat_alfa)
				+ Math.cos(lat_beta) * Math.cos(lat_alfa) * Math.cos(fi));

		d = p * R * inM;
		return d;
	}
}
