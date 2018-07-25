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

import static org.junit.Assert.assertEquals;
import it.unibo.paserver.config.TestDataContextConfiguration;
import it.unibo.paserver.config.test.InfrastructureContextConfiguration;
import it.unibo.paserver.domain.Action;
import it.unibo.paserver.domain.ActionSensing;
import it.unibo.paserver.domain.PointsStrategyForTask;
import it.unibo.paserver.domain.Task;
import it.unibo.paserver.domain.support.EntityBuilder.EntityBuilderManager;
import it.unibo.paserver.domain.support.Pipeline;
import it.unibo.paserver.domain.support.PointsStrategyForTaskBuilder;
import it.unibo.paserver.gamificationlogic.PointsStrategy;
import it.unibo.paserver.repository.PointsStrategyForTaskRepository;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { InfrastructureContextConfiguration.class,
		TestDataContextConfiguration.class })
@Transactional
public class JpaPointsStrategyForTaskRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PointsStrategyForTaskRepository pointsStrategyForTaskRepository;

	@Autowired
	@Qualifier("PointsStrategyByAverageReputation")
	PointsStrategy averageReputationStrategy;

	@Autowired
	@Qualifier("PointsStrategyByAverageLevel")
	PointsStrategy averageLevelStrategy;
	
	@Autowired
	@Qualifier("PointsStrategyBySumLevel")
	PointsStrategy sumLevelStrategy;
	
	@Autowired
	@Qualifier("PointsStrategyBySumReputation")
	PointsStrategy sumReputationStrategy;

	private Task taskOne;
	private Task taskTwo;
	private PointsStrategyForTask pointsStrategyForTaskOne;
	private PointsStrategyForTask pointsStrategyForTaskTwo;

	@Before
	public void setupData() {
		EntityBuilderManager.setEntityManager(entityManager);

		taskOne = new Task();
		taskOne.setName("Geolocation");
		taskOne.setDescription("Geolocation task");
		taskOne.setStart(new DateTime());
		taskOne.setDeadline(new DateTime().plusDays(30));
		taskOne.setDuration(10L);
		taskOne.setSensingDuration(2L);
		ActionSensing actionLocation = new ActionSensing();
		actionLocation.setDuration_threshold(20);
		actionLocation.setNumeric_threshold(0);
		actionLocation.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocation.setName("Geolocation");
		Set<Action> actionsg = new LinkedHashSet<Action>();
		actionsg.add(actionLocation);
		taskOne.setActions(actionsg);

		taskTwo = new Task();
		taskTwo.setName("Geolocation2");
		taskTwo.setDescription("Geolocation task2");
		taskTwo.setStart(new DateTime());
		taskTwo.setDeadline(new DateTime().plusDays(30));
		taskTwo.setDuration(10L);
		taskTwo.setSensingDuration(2L);
		ActionSensing actionLocationTwo = new ActionSensing();
		actionLocationTwo.setDuration_threshold(20);
		actionLocationTwo.setNumeric_threshold(0);
		actionLocationTwo.setInput_type(Pipeline.Type.LOCATION.toInt());
		actionLocationTwo.setName("Geolocation");
		Set<Action> actionsgTwo = new LinkedHashSet<Action>();
		actionsgTwo.add(actionLocation);
		taskTwo.setActions(actionsgTwo);

		entityManager.persist(taskOne);
		entityManager.persist(taskTwo);

		pointsStrategyForTaskOne = new PointsStrategyForTaskBuilder()
				.setStrategyId(averageReputationStrategy.getId()).setTask(taskOne)
				.build();
		pointsStrategyForTaskTwo = new PointsStrategyForTaskBuilder()
				.setStrategyId(averageLevelStrategy.getId()).setTask(taskTwo).build();

	}

	@Test
	public void getPointsStrategyForTaskByTask() {
		PointsStrategyForTask foundOne = pointsStrategyForTaskRepository
				.getPointsStrategyForTaskByTask(taskOne.getId());
		PointsStrategyForTask foundTwo = pointsStrategyForTaskRepository
				.getPointsStrategyForTaskByTask(taskTwo.getId());
		assertEquals(pointsStrategyForTaskOne, foundOne);
		assertEquals(pointsStrategyForTaskTwo, foundTwo);
	}

	@Test
	public void getAll() {
		List<PointsStrategyForTask> foundlList = pointsStrategyForTaskRepository
				.getPointsStrategyForTasks();
		Set<PointsStrategyForTask> foundSet = new HashSet<PointsStrategyForTask>(
				foundlList);
		Set<PointsStrategyForTask> expectedSet = new HashSet<PointsStrategyForTask>();
		expectedSet.add(pointsStrategyForTaskOne);
		expectedSet.add(pointsStrategyForTaskTwo);
		assertEquals(expectedSet, foundSet);
	}

}
