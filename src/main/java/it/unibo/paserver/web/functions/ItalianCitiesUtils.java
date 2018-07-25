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
package it.unibo.paserver.web.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class ItalianCitiesUtils {

	private static final Logger logger = LoggerFactory
			.getLogger(ItalianCitiesUtils.class);
	private Map<String, String> codToCity;
	private Map<String, String> codToProv;

	private void init() {
		codToCity = new TreeMap<String, String>();
		codToProv = new TreeMap<String, String>();
		BufferedReader reader = null;
		try {
			DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
			InputStream source = defaultResourceLoader.getResource(
					"META-INF/ita_cities_codes.txt").getInputStream();
			reader = new BufferedReader(new InputStreamReader(source));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(";");
				codToCity.put(data[0], data[1]);
				codToProv.put(data[0], data[2]);
			}
		} catch (IOException e) {
			logger.error("Error while reading cities codes", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("Error while closing cities codes file", e);
				}
			}
		}
	}

	private void checkInit() {
		if (codToCity == null) {
			init();
		}
	}

	public String getBornCity(String cf) {
		if (cf == null || cf.length() != 16) {
			return "_________________________";
		}
		checkInit();
		String code = cf.substring(11, 15).toUpperCase();
		String cityName = codToCity.get(code);
		return cityName != null ? cityName : "_________________________";
	}

	public String getBornCityProv(String cf) {
		if (cf == null || cf.length() != 16) {
			return "__________";
		}
		checkInit();
		String code = cf.substring(11, 15).toUpperCase();
		String cityName = codToProv.get(code);
		return cityName != null ? cityName : "__________";
	}
}
