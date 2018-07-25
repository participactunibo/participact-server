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
package it.unibo.paserver.rest.controller.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	
	@Autowired 
	TaskFlatRequestValidator taskFlatRequestValidator;
	
	@Autowired
    private MessageSource messageSource;
	
	
	@InitBinder("taskFlatRequest")
	 private void initBinder(WebDataBinder binder) {
       binder.setValidator(taskFlatRequestValidator);
   }
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public  ErrorInfo handleValidationException(HttpServletRequest req ,MethodArgumentNotValidException pe) {
	
		String url = req.getRequestURL().toString();
		String errorMessage = getErrorMessage("error.bad.arguments");
		
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setUrl(url);
		errorInfo.setMessage(errorMessage);
		
		BindingResult result = pe.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		
		errorInfo.getFieldErrors().addAll(populateErrorInfo(fieldErrors));		
		
		return errorInfo;
		
	}


	private List<it.unibo.paserver.rest.controller.validator.FieldError> populateErrorInfo(List<FieldError> fieldErrors) {
		List<it.unibo.paserver.rest.controller.validator.FieldError> result = new ArrayList<it.unibo.paserver.rest.controller.validator.FieldError>();
		
		
		for(FieldError e : fieldErrors)
		{
			String messageError = getErrorMessage(e.getCode());
			it.unibo.paserver.rest.controller.validator.FieldError fe = new it.unibo.paserver.rest.controller.validator.FieldError();
			fe.setFieldError(messageError);
			fe.setFieldName(e.getField());
			result.add(fe);
		}		
		
		return result;
	}

	

	private String getErrorMessage(String string) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage =  messageSource.getMessage(string,null,locale);
		return errorMessage;
	}



}
