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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

@Configuration
@ImportResource("classpath:/spring/webflow-config.xml")
public class WebflowContextConfiguration {

	private static final Logger logger = LoggerFactory
			.getLogger(WebflowContextConfiguration.class);

	@Autowired
	private FlowExecutor flowExecutor;

	@Autowired
	private FlowDefinitionRegistry flowRegistry;

	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
		flowHandlerAdapter.setFlowExecutor(flowExecutor);
		logger.info("Set up Webflow handler adapter");
		return flowHandlerAdapter;
	}

	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
		flowHandlerMapping.setFlowRegistry(flowRegistry);
		logger.info("Set up Webflow handler mapping");
		return flowHandlerMapping;
	}

	@Bean(name = "securityFlowExecutionListener")
	public SecurityFlowExecutionListener getSecurityFlowExecutionListener() {
		return new SecurityFlowExecutionListener();
	}
}
