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
package it.unibo.tper.service.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.domain.support.TPerBusStopBuilder;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.tper.domain.BusStop;
import it.unibo.tper.service.BusStopService;

import org.joda.time.DateTime;
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
public class BusStopServiceTest {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	private BusStopService busStopService;
	
	private BusStop busStop1;
	private BusStop busStop2;
	private BusStop oldBusStop3;
	
	
	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);
		
		ArrayList<String> lines = new ArrayList<String>();
		lines.add("20");
		lines.add("22");
		
		busStop1 = new TPerBusStopBuilder()
		.setCode(1)
		.setCoordinateX(50)
		.setCoordinateY(50)
		.setCreationTime(new DateTime())
		.setLatitude(55.55)
		.setLongitude(50.55)
		.setMunicipality("BOLOGNA")
		.setName("FRASSINAGO")
		.setSite("SARAGOZZA")
		.setZoneCode(21)
		.setLines(lines).build();
		
		busStop2 = new TPerBusStopBuilder()
		.setCode(2)
		.setCoordinateX(100)
		.setCoordinateY(100)
		.setCreationTime(new DateTime())
		.setLatitude(65.55)
		.setLongitude(60.55)
		.setMunicipality("BOLOGNA")
		.setName("SAN VITALE")
		.setSite("SAN VITALE")
		.setZoneCode(5)
		.setLines(lines).build();

		
		oldBusStop3 = new TPerBusStopBuilder()
		.setCode(3)
		.setCoordinateX(20)
		.setCoordinateY(20)
		.setCreationTime(new DateTime().minusDays(23))
		.setLatitude(75.55)
		.setLongitude(70.55)
		.setMunicipality("BOLOGNA")
		.setName("SANTO STEFANO")
		.setSite("SANTO STEFANO")
		.setZoneCode(10)
		.setLines(lines).build();
	}
	
	@Test
	public void updateBusStopData()
	{
		busStop1.setName("NUOVO NOME");
		busStop2.setName("NUOVO NOME");

		
		List<BusStop> stops = new ArrayList<BusStop>();
		stops.add(busStop1);
		stops.add(busStop2);
		stops.add(oldBusStop3);
		
		busStopService.updateBusStopData(stops, new DateTime().minusDays(1));
		assertEquals("NUOVO NOME",busStopService.findById(busStop1.getId()).getName());
		assertEquals("NUOVO NOME",busStopService.findById(busStop2.getId()).getName());
		assertEquals(2,(long)busStopService.getBusStopCount());

		
		
	}
	

}
