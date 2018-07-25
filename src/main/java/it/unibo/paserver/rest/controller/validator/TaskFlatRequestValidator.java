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
import java.util.Set;

import it.unibo.paserver.domain.ActionType;
import it.unibo.paserver.domain.Friendship;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.domain.Friendship.FriendshipStatus;
import it.unibo.paserver.domain.flat.request.ActionFlatRequest;
import it.unibo.paserver.domain.flat.request.TaskFlatRequest;
import it.unibo.paserver.service.FriendshipService;
import it.unibo.paserver.web.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TaskFlatRequestValidator implements Validator{
	

	private final ActionFlatRequestValidator actionFlatRequestValidator;

	
	@Autowired
	public TaskFlatRequestValidator(ActionFlatRequestValidator actionFlatRequestValidator) {
		if (actionFlatRequestValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " +
					"required and must not be null.");
		}
		
		
		if (!actionFlatRequestValidator.supports(ActionFlatRequest.class)) {
			throw new IllegalArgumentException("The supplied [Validator] must " +
					"support the validation of [ActionFlatRequest] instances.");
		}
		
		this.actionFlatRequestValidator = actionFlatRequestValidator;
	}
	
	

	@Override
	public boolean supports(Class<?> clazz) {
		return TaskFlatRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//validation of TaskFlatRequest

		ValidationUtils.rejectIfEmpty(errors, "name", "required.task");
		ValidationUtils.rejectIfEmpty(errors, "description", "required.task");

		TaskFlatRequest request = (TaskFlatRequest) target;
		if(request.getStart() == null)
			errors.rejectValue("start","required.task");
		if(request.getDeadline() == null)
			errors.rejectValue("deadline","required.task");

		if(request.getStart() != null)
			if(request.getStart().isAfter(request.getDeadline()))
				errors.rejectValue("start","invalid.task.start");
		if(request.getDeadline() != null)
			if(request.getDeadline().isBeforeNow())
				errors.rejectValue("deadline","invalid.task.deadline");

		if(request.getDuration() == null)
			errors.rejectValue("duration","required.task");
		else if(request.getDuration() <= 0)
				errors.rejectValue("duration","negative.task.duration");

		if(request.getActions() == null || request.getActions().size() == 0)
			errors.rejectValue("actions","required.task");
		else
		{
			int index = 0;
			for(ActionFlatRequest a : request.getActions())
			{
				errors.pushNestedPath("actions["+index+"]");
				ValidationUtils.invokeValidator(actionFlatRequestValidator, a, errors);
				errors.popNestedPath();
				index++;
				if(a.getType() != null)
				{
					if(a.getType().equals(ActionType.SENSING_MOST))
					{
						if(request.getSensingDuration() == null)
							errors.rejectValue("sensingDuration","required.task");
						else if(request.getDuration() != null)
							if(request.getSensingDuration() > request.getDuration())
								errors.rejectValue("sensingDuration","gratherthanduration.task.sensingDuration");

					}
				}
			}
			
		}
		
		





	}

}
