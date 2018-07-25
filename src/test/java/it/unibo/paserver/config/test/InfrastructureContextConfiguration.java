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
package it.unibo.paserver.config.test;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "it.unibo.paserver.service",
		"it.unibo.paserver.repository", "it.unibo.paserver.domain.support",
		"it.unibo.paserver.gamificationlogic","it.unibo.tper.repository","it.unibo.tper.service" })
public class InfrastructureContextConfiguration {

	private static final Logger logger = LoggerFactory
			.getLogger(InfrastructureContextConfiguration.class);

	static {
		try {
			Class.forName("org.h2.Driver");
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
		logger.info("Initing EntityManagerFactory");
		LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
		Properties properties = new Properties();
		properties.put("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		properties.put("hibernate.cache.use_second_level_cache", Boolean.TRUE);
		properties.put("hibernate.cache.use_query_cache", Boolean.TRUE);
		properties.put("hibernate.jdbc.batch_size", 100);
		properties.put("hibernate.hbm2ddl.auto", "create");
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

		emfb.setDataSource(dataSource);
		emfb.setJpaProperties(properties);
		emfb.setJpaVendorAdapter(jpaVendorAdapter());
		emfb.setPackagesToScan("it.unibo.paserver.domain","it.unibo.tper.domain");
		return emfb;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		logger.info("Initing TransactionManager");
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setDataSource(dataSource);
		return txManager;
	}

	@Bean
	public DataSource dataSource() {
		logger.info("Initing DataSource");
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		builder.setType(EmbeddedDatabaseType.H2);
		return builder.build();
	}

}
