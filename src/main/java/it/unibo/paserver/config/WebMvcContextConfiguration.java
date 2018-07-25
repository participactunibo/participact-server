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

import it.unibo.paserver.web.formatter.PADateTimeFormatter;
import it.unibo.paserver.web.formatter.PALocalDateFormatter;

import javax.servlet.ServletContext;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "it.unibo.paserver.web",
		"it.unibo.paserver.rest","it.unibo.tper.web","it.unibo.tper.opendata" })
@ImportResource("classpath:/spring/spring-security.xml")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				"/resources/**");
	}

	/**
	 * To resolve message codes to actual messages we need a
	 * {@link MessageSource} implementation. The default implementations use a
	 * {@link java.util.ResourceBundle} to parse the property files with the
	 * messages in it.
	 * 
	 * @return the {@link MessageSource}
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames(new String[] { "messages",
				"org.springframework.security.messages" });
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
		internalResourceViewResolver.setOrder(1);
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(JstlView.class);
		return internalResourceViewResolver;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new PALocalDateFormatter());
		registry.addFormatter(new PADateTimeFormatter());
	}

	@Bean
	public CommonsMultipartResolver multipartResolver(
			ServletContext servletContext) {
		CommonsMultipartResolver cmr = new CommonsMultipartResolver(
				servletContext);
		cmr.setMaxUploadSize(30000000);
		return cmr;
	}

	@Bean
	public RestRequestsLogger restRequestsLogger() {
		return new RestRequestsLogger();
	}

	// @Override
	// public void addFormatters(FormatterRegistry registry) {
	// registry.addConverter(bookConverter());
	// registry.addConverter(categoryConverter());
	// }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(restRequestsLogger());
	}
}
