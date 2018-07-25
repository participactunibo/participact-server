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
package it.unibo.paserver.repository;

import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.TaskResult;

import java.util.Collection;
import java.util.List;

public interface TaskResultRepository {

	TaskResult findById(long id);

	TaskResult findByUserAndTask(long userId, long taskId);

	TaskResult save(TaskResult taskResult);

	Long getTaskResultsCount();

	Long getTaskResultsCountByUser(long userId);

	Long getTaskResultsCountByTask(long taskId);

	List<TaskResult> getTaskResults();

	List<TaskResult> getTaskResultsByTask(long taskId);

	List<TaskResult> getTaskResultsByUser(long userId);

	boolean deleteTaskResult(long id);

	public void flush();

	public void clear();

	TaskResult addData(long taskId, long userId, Data data);

	TaskResult updateTaskResult(TaskResult taskResult, boolean force);

	TaskResult addData(long taskId, long userId, Collection<? extends Data> data);
}
