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
package it.unibo.tper.mantainance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import it.unibo.paserver.web.ResourceNotFoundException;
import it.unibo.tper.TPerProxy;
import it.unibo.tper.domain.TPerBusStop;
import it.unibo.tper.service.BusStopService;
import it.unibo.tper.ws.domain.extensions.FermateResponse;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TPerBusStopMantainance {

	@Autowired
	BusStopService busStopService;
	@Autowired
	TPerProxy proxy;
	
	private static final Logger logger = LoggerFactory
			.getLogger(TPerBusStopMantainance.class);

	@Scheduled(cron = "0 0 1 1 * *")
	public void updateTPerBusSTop() {
		try
		{
			FermateResponse fermateResponse = proxy.getOpenDataLineeFermateResponse();
			if(fermateResponse!=null)
			{
				Map<Integer,TPerBusStop> map = new HashMap<Integer,TPerBusStop>();
				for(FermateResponse.Table t : fermateResponse.getTable())
				{				

					TPerBusStop current =  map.get(t.getCodiceFermata());
					if(current != null)
						current.getLines().add(t.getCodiceLinea());
					else
					{
						current = new TPerBusStop(t);
						current.getLines().add(t.getCodiceLinea());
						map.put(current.getCode(), current);
					}
				}

				busStopService.updateBusStopData(new ArrayList<TPerBusStop>(map.values()), new DateTime().minusDays(1));

			}
			else
				throw new ResourceNotFoundException();	

		}
		catch(Exception e)
		{
			logger.error("Error while retrieving BusStops", e);
		}
	}
	


}
