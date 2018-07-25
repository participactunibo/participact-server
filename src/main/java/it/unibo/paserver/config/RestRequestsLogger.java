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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RestRequestsLogger extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory
			.getLogger(RestRequestsLogger.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (uri.indexOf("/rest/") < 0) {
			return super.preHandle(request, response, handler);
		}
		String method = request.getMethod();
		String queryString = request.getQueryString();
		String remoteAddr = request.getRemoteAddr();
		String remoteUser = request.getRemoteUser();
		StringBuilder sb = new StringBuilder();
		if (remoteUser != null) {
			sb.append(remoteUser).append(" ");
		}
		sb.append(String.format("%s %s %s", remoteAddr, method, uri));
		if (queryString != null) {
			sb.append("?");
			sb.append(queryString);
		}
		logger.debug("Request {}", sb.toString());
		return super.preHandle(request, response, handler);
	}
}
