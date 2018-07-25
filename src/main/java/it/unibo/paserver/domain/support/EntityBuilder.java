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

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.ArrayUtils;

public abstract class EntityBuilder<T extends Serializable> {

	protected T entity;

	{
		initEntity();
	}

	abstract void initEntity();

	abstract T assembleEntity();

	public T build(Boolean... doNotPersist) {
		T entity = assembleEntity();
		if (ArrayUtils.isEmpty(doNotPersist)
				|| (ArrayUtils.isNotEmpty(doNotPersist) && doNotPersist[0] == Boolean.FALSE)) {
			EntityBuilderManager.getEntityManager().persist(entity);
		}
		T temp = entity;
		initEntity();
		return temp;
	}

	public T getEntity() {
		return entity;
	}

	public static class EntityBuilderManager {
		private static ThreadLocal<EntityManager> entityManagerHolder = new ThreadLocal<EntityManager>();

		public static void setEntityManager(EntityManager entityManger) {
			entityManagerHolder.set(entityManger);
		}

		public static void clearEntityManager() {
			entityManagerHolder.remove();
		}

		public static EntityManager getEntityManager() {
			return entityManagerHolder.get();
		}
	}
}
