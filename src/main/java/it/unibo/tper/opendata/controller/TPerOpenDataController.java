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
package it.unibo.tper.opendata.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import it.unibo.paserver.domain.TaskFlatList;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.rest.controller.TaskController;
import it.unibo.paserver.service.UserService;
import it.unibo.tper.domain.BusStop;
import it.unibo.tper.opendata.domain.response.BusStopResponse;
import it.unibo.tper.opendata.domain.response.BusStopResponseList;
import it.unibo.tper.service.BusStopService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TPerOpenDataController {

	@Autowired BusStopService busStopService;
	@Autowired UserService userService;


	private static final Logger logger = LoggerFactory
			.getLogger(TPerOpenDataController.class);


	@RequestMapping(value = "/opendata/tper/busstop", method = RequestMethod.GET)
	public @ResponseBody BusStopResponseList getBusStopByPosition(@RequestParam(value="latitude") Double latitude,@RequestParam(value = "longitude") Double longitude, @RequestParam(value = "radius") Double radius) {

		try {


			List<? extends BusStop> busStops = null;
			List<BusStopResponse> response = new ArrayList<BusStopResponse>();
			BusStopResponseList result = new BusStopResponseList();
			if(latitude != null && longitude != null && radius !=null)
			{
				busStops = busStopService.getBusStopsByRadius(latitude, longitude, radius);
				for(BusStop b : busStops)
					response.add(b.converToBusStopResponse());	
				result.setBusStopsList(response);
				return result;
			}



		}
		catch(Exception e)
		{
			logger.error("Error while retrieving BusStops", e);
			return new BusStopResponseList();
		}
		return new BusStopResponseList();
	}

}
