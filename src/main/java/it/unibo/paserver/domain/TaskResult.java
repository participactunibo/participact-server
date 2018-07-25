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
package it.unibo.paserver.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class TaskResult implements Serializable {

	private static final long serialVersionUID = -8735101887225128L;

	@Id
	@Column(name = "task_result_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "task_report_id")
	private TaskReport taskReport;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "TaskResult_Data", joinColumns = { @JoinColumn(name = "task_result_id") }, inverseJoinColumns = { @JoinColumn(name = "data_id") })
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Data> data = new HashSet<Data>();

	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime lastDataUpdate;

	@Transient
	final private Map<Class<? extends Data>, List<? extends Data>> dataCache;

	public TaskResult() {
		dataCache = new HashMap<Class<? extends Data>, List<? extends Data>>();
	}

	public DateTime getLastDataUpdate() {
		return lastDataUpdate;
	}

	public void setLastDataUpdate(DateTime lastDataUpdate) {
		this.lastDataUpdate = lastDataUpdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TaskReport getTaskReport() {
		return taskReport;
	}

	public void setTaskReport(TaskReport taskReport) {
		this.taskReport = taskReport;
	}

	public Set<Data> getData() {
		return data;
	}

	public void setData(Set<Data> data) {
		this.data = data;
	}

	/**
	 * Returns the requested data, sorted by sample timestamp. If the task did
	 * not require the specified data type it returns an empty list.
	 * 
	 * @param clazz
	 * @return The data requested.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Data> List<T> getData(Class<T> clazz) {
		// cache data lists for faster access
		if (dataCache.containsKey(clazz)) {
			return (List<T>) dataCache.get(clazz);
		}
		List<T> result = new ArrayList<T>();
		for (Data d : getData()) {
			if (clazz.isAssignableFrom(d.getClass())) {
				result.add(clazz.cast(d));
			}
		}
		Collections.sort(result, new DataComparator());
		dataCache.put(clazz, result);
		return result;
	}

	private static class DataComparator implements Comparator<Data> {

		@Override
		public int compare(Data o1, Data o2) {
			if (o1 == null) {
				return 1;
			}
			if (o2 == null) {
				return -1;
			}
			return o1.getSampleTimestamp().compareTo(o2.getSampleTimestamp());
		}

	}
}
