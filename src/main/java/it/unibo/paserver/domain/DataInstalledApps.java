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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

@Entity
@Table(appliesTo = "DataInstalledApps", indexes = {
		@Index(name = "instapps_user_ts", columnNames = { "user_id",
				"sampletimestamp" }),
		@Index(name = "instapps_ts", columnNames = { "sampletimestamp" }) })
public class DataInstalledApps extends Data {

	private static final long serialVersionUID = 6986458996220319344L;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String pkgName;

	@NotNull
	private float versionCode;

	@NotNull
	private String versionName;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String requestedPermissions;

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public float getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(float versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getRequestedPermissions() {
		return requestedPermissions;
	}

	public void setRequestedPermissions(String requestedPermissions) {
		this.requestedPermissions = requestedPermissions;
	}

}
