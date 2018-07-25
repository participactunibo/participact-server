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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Question implements Serializable {

	private static final long serialVersionUID = 4337352852760317052L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "question_id")
	private Long id;

	@NotNull
	@Column(columnDefinition = "TEXT")
	private String question;

	@NotNull
	private Integer question_order;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "QuestionClosedAnswers", joinColumns = @JoinColumn(name = "question_id"), inverseJoinColumns = @JoinColumn(name = "closed_answer_id"))
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy(value = "answerOrder")
	private List<ClosedAnswer> closed_answers;

	@NotNull
	private Boolean isClosedAnswers;

	@NotNull
	private Boolean isMultipleAnswers;

	public Boolean getIsClosedAnswers() {
		return isClosedAnswers;
	}

	public void setIsClosedAnswers(Boolean isClosedAnswers) {
		this.isClosedAnswers = isClosedAnswers;
	}

	public Boolean getIsMultipleAnswers() {
		return isMultipleAnswers;
	}

	public void setIsMultipleAnswers(Boolean isMultipleAnswers) {
		this.isMultipleAnswers = isMultipleAnswers;
	}

	public Question() {
		closed_answers = new LinkedList<ClosedAnswer>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getQuestionOrder() {
		return question_order;
	}

	public void setQuestionOrder(int question_order) {
		this.question_order = question_order;
	}

	public List<ClosedAnswer> getClosed_answers() {
		return closed_answers;
	}

	public void setClosed_answers(List<ClosedAnswer> closed_answers) {
		this.closed_answers = closed_answers;
	}

	@JsonIgnore
	public int getNextClosedAnswerOrder() {
		if (closed_answers == null) {
			throw new IllegalStateException("closed answers can't be null");
		}
		if (closed_answers.size() == 0) {
			return 0;
		}
		int max = 0;
		for (ClosedAnswer ca : closed_answers) {
			if (ca.getAnswerOrder() > max) {
				max = ca.getAnswerOrder();
			}
		}
		max = max + 1;
		return max;
	}

	@JsonIgnore
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Question ");
		sb.append(id);
		sb.append(" ");
		if (isClosedAnswers) {
			sb.append("[closed]");
			if (isMultipleAnswers) {
				sb.append("[multiple]");
			} else {
				sb.append("[single]");
			}
		} else {
			sb.append("[open]");
		}
		sb.append(": ");
		sb.append(getQuestion());
		return sb.toString();
	}
}
