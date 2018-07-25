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

import it.unibo.paserver.domain.flat.request.ClosedAnswerRequest;
import it.unibo.paserver.domain.flat.request.QuestionRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class QuestionRequestValidator implements Validator{

	private final ClosedAnswerRequestValidator closedAnswerRequestValidator;


	@Autowired
	public QuestionRequestValidator(ClosedAnswerRequestValidator closedAnswerRequestValidator) {
		if (closedAnswerRequestValidator == null) {
			throw new IllegalArgumentException("The supplied [Validator] is " +
					"required and must not be null.");
		}
		if (!closedAnswerRequestValidator.supports(ClosedAnswerRequest.class)) {
			throw new IllegalArgumentException("The supplied [Validator] must " +
					"support the validation of [ClosedAnswerRequest] instances.");
		}
		this.closedAnswerRequestValidator = closedAnswerRequestValidator;

	}


	@Override
	public boolean supports(Class<?> clazz) {
		return QuestionRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "question", "required.task");
		QuestionRequest request = (QuestionRequest) target;

		if(request.getQuestion_order() == null)
			errors.rejectValue("question_order", "required.task");

		else if(request.getQuestion_order() < 0)
			errors.rejectValue("answerOrder", "greatherthanzero.question.questionorder");



		if(request.getIsClosedAnswers() == null)
			errors.rejectValue("isClosedAnswers", "required.task");
		else if (request.getIsClosedAnswers())
			validateClosedQuestion(request,errors);
		else
			validateOpenQuestion(request,errors);		

	}

	private void validateOpenQuestion(QuestionRequest request, Errors errors) {
		if(request.getIsMultipleAnswers() == null)
			errors.rejectValue("isMultipleAnswers", "required.task");

		else	if(request.getIsMultipleAnswers())
			errors.rejectValue("isMultipleAnswers","mustbefalse.openquestion.ismultipleanswer");



	}

	private void validateClosedQuestion(QuestionRequest request, Errors errors) {

		if(request.getIsMultipleAnswers() == null)
			errors.rejectValue("isMultipleAnswers", "required.task");		
		int index = 0;
		if(request.getClosed_answers() == null)
			errors.rejectValue("closed_answers", "required.task");		

		else if(request.getClosed_answers().size() < 2)
			errors.rejectValue("closed_answers", "greatherthenone.closed_answer.size");		
		else
		{
			for(ClosedAnswerRequest c : request.getClosed_answers())
			{
				errors.pushNestedPath("closed_answers["+index+"]");
				ValidationUtils.invokeValidator(closedAnswerRequestValidator, c, errors);
				errors.popNestedPath();
				index++;
			}
		}

	}

}
