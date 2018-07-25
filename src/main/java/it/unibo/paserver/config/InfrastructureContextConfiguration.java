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

import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "it.unibo.paserver.service",
		"it.unibo.paserver.repository", "it.unibo.paserver.domain.support",
		"it.unibo.paserver.manteinance", "it.unibo.paserver.gamificationlogic",
		"it.unibo.tper.repository", "it.unibo.tper.service",
		"it.unibo.tper.mantainance"})
public class InfrastructureContextConfiguration {

	private static final Logger logger = LoggerFactory
			.getLogger(InfrastructureContextConfiguration.class);

	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Critical error", e);
		}
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		return new HibernateJpaVendorAdapter();
	}

	@Bean
	public FactoryBean<EntityManagerFactory> entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		emfb.setDataSource(dataSource);
		emfb.setJpaVendorAdapter(jpaVendorAdapter());
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setDataSource(dataSource);
		return txManager;
	}

	@Bean
	public DataSource dataSource() {
		BoneCPDataSource ds = new BoneCPDataSource();
		ds.setIdleConnectionTestPeriod(60, TimeUnit.SECONDS);
		ds.setMaxConnectionAge(240, TimeUnit.SECONDS);
		ds.setPartitionCount(3);
		ds.setMaxConnectionsPerPartition(20);
		ds.setMinConnectionsPerPartition(1);
		ds.setAcquireIncrement(1);
		ds.setStatementsCacheSize(100);
		ds.setReleaseHelperThreads(3);

		//ds.setJdbcUrl("jdbc:postgresql://137.204.57.x/participact?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory");
		ds.setJdbcUrl("jdbc:postgresql://127.0.0.1/pa_test");
		ds.setUsername("postgres");
		ds.setPassword("postgres");
		return ds;
	}

}
