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

import it.unibo.paserver.domain.BinaryDocument;
import it.unibo.paserver.domain.Data;
import it.unibo.paserver.domain.DataPhoto;

import java.util.List;

public interface DataRepository {

	Data findById(long id);

	<T extends Data> T save(T data);

	Long getDataCount();

	Long getDataCountByUser(long userId);

	Long getDataCountByTask(long taskId);

	List<Data> getData();

	<T extends Data> List<T> getData(Class<T> clazz);

	List<Data> getDataByUser(long userId);

	<T extends Data> List<T> getDataByUser(long userId, Class<T> clazz);

	List<Data> getDataByTask(long taskId);

	<T extends Data> List<T> getDataByTask(long taskId, Class<T> clazz);

	List<Data> getDataByUserAndTask(long userId, long taskId);

	<T extends Data> List<T> getDataByUserAndTask(long userId, long taskId,
			Class<T> clazz);

	List<Data> getDataByTaskReport(long taskReportId);

	<T extends Data> List<T> getDataByTaskReport(long taskReportId,
			Class<T> clazz);

	boolean deleteData(long id);

	BinaryDocument getPhotoContent(Long id);

	public void flush();

	public void clear();

	<T extends Data> T merge(T data);

	List<DataPhoto> getDataPhotoByTaskAction(long taskId, long actionId);


}
