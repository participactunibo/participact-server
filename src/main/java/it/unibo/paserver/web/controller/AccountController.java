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
package it.unibo.paserver.web.controller;

import it.unibo.paserver.domain.Account;
import it.unibo.paserver.domain.Role;
import it.unibo.paserver.domain.support.AccountBuilder;
import it.unibo.paserver.service.AccountService;
import it.unibo.paserver.web.validator.AddAccountFormValidator;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	private static final Logger logger = LoggerFactory
			.getLogger(AccountController.class);

	@ModelAttribute("addAccountForm")
	public AddAccountForm getAddAccountForm() {
		return new AddAccountForm();
	}

	@InitBinder("addAccountForm")
	public void initBinder(WebDataBinder binder) {
		binder.setRequiredFields("username");
		binder.setValidator(new AddAccountFormValidator());
	}

	@RequestMapping(value = "/protected/account", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView account(ModelAndView modelAndView) {
		modelAndView.setViewName("protected/account");
		modelAndView.addObject("totalAccounts",
				accountService.getAccountsCount());
		List<Account> accounts = accountService.getAccounts();
		Collections.sort(accounts, new Comparator<Account>() {

			@Override
			public int compare(Account o1, Account o2) {
				return o1.getUsername().compareTo(o2.getUsername());
			}

		});
		modelAndView.addObject("accounts", accounts);
		return modelAndView;
	}

	@RequestMapping(value = "/protected/account/add", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView accountForm(ModelAndView modelAndView) {
		modelAndView.setViewName("protected/account/add");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/account/addAccount", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView accountAdd(
			@ModelAttribute @Validated AddAccountForm addAccountForm,
			BindingResult bindingResult, ModelAndView modelAndView,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("protected/account/add");
			return modelAndView;
		}

		Account account = accountService.getAccount(addAccountForm
				.getUsername());
		if (account != null) {
			bindingResult.rejectValue("username", "alreadyexists",
					new String[] { addAccountForm.getUsername() },
					"alreadyexists");
			modelAndView.setViewName("protected/account/add");
		} else {
			AccountBuilder ab = new AccountBuilder().credentials(
					addAccountForm.getUsername(), addAccountForm.getPassword());
			if (Boolean.TRUE.equals(addAccountForm.getRoleAdmin())) {
				ab.addRole(Role.ROLE_ADMIN);
			}
			if (Boolean.TRUE.equals(addAccountForm.getRoleView())) {
				ab.addRole(Role.ROLE_VIEW);
			}
			ab.creationDate(new DateTime());
			Account newAccount = ab.build(true);
			logger.info("Saving new account: {}", newAccount.toString());
			accountService.save(newAccount);
			redirectAttributes.addFlashAttribute("successmessage", String
					.format("Account \"%s\" successfully created",
							newAccount.getUsername()));
			modelAndView.setViewName("redirect:/protected/account");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/validUsername/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody Map<String, Object> validUsername(
			@PathVariable String username) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (username == null || username.length() == 0
				|| !username.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*$")) {
			result.put("valid", false);
			result.put("cause", "Invalid username");
			return result;
		}
		Account existingAccount = accountService.getAccount(username);
		if (existingAccount == null) {
			result.put("valid", true);
		} else {
			result.put("valid", false);
			result.put("cause", "Already registered");
		}
		return result;
	}

	@RequestMapping(value = "/protected/account/delete", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView accountForm(@RequestParam Integer id,
			ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
		logger.trace("Received request to delete account {}", id);
		if (accountService.deleteAccount(id)) {
			redirectAttributes.addFlashAttribute("successmessage",
					String.format("Account #\"%d\" successfully deleted", id));
		} else {
			redirectAttributes
					.addFlashAttribute(
							"errormessage",
							String.format(
									"Unabe to delete account #\"%d\", please consult logs for further information",
									id));
		}
		modelAndView.setViewName("redirect:/protected/account");
		return modelAndView;
	}
}
