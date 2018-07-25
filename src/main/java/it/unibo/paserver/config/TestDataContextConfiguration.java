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
package it.unibo.paserver.config;

import it.unibo.paserver.domain.support.InitialDataSetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * * <b>Note:</b> After starting the container, you can use the following URL
 * (with your favoriate JDBC client) to connect to the database:
 * <i>jdbc:h2:tcp://localhost/mem:testdb</i>
 * 
 */
@Configuration
public class TestDataContextConfiguration {

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean(initMethod = "initialize")
	public InitialDataSetup setupData() {
		return new InitialDataSetup(new TransactionTemplate(transactionManager));
	}

	// @Bean(initMethod = "start", destroyMethod = "shutdown")
	// @DependsOn("dataSource")
	// public Server dataSourceTcpConnector() {
	// try {
	// return Server.createTcpServer();
	// } catch (SQLException e) {
	// throw new RuntimeException(e);
	// }
	// }
}
