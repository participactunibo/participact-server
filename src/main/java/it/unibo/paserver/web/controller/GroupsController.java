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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import it.unibo.paserver.domain.DataGroups;
import it.unibo.paserver.domain.DataLocationAvg;
import it.unibo.paserver.domain.GroupsTask;
import it.unibo.paserver.domain.User;
import it.unibo.paserver.groups.DatabaseGroupUser;
import it.unibo.paserver.groups.GroupCalculate;
import it.unibo.paserver.groups.GroupPoint;
import it.unibo.paserver.groups.GroupUser;
import it.unibo.paserver.groups.GroupUserElementDataManager;
import it.unibo.paserver.groups.GroupUtils;
import it.unibo.paserver.service.DataLocationService;
import it.unibo.paserver.service.GroupsContactsService;
import it.unibo.paserver.service.GroupsService;
import it.unibo.paserver.service.GroupsTaskService;
import it.unibo.paserver.service.UserService;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GroupsController {

	@Autowired
	private UserService userService;

	@Autowired
	private GroupsService groupService;

	@Autowired
	private GroupsTaskService groupsTaskService;

	@Autowired
	private GroupsContactsService groupsContactsService;

	@Autowired
	@Qualifier("dataLocationService")
	private DataLocationService dataLocationService;

	@RequestMapping(value = "/protected/groups", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView index(ModelAndView modelAndView) {
		modelAndView.setViewName("protected/groups/main");
		modelAndView.addObject("esito", "The last update was successful");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/groups", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView indexPost(ModelAndView modelAndView,
			@ModelAttribute GroupsForm groupsForm) {

		DateTime startDate = GroupUtils.convertDate(groupsForm.getStart());
		DateTime endDate = GroupUtils.convertDate(groupsForm.getEnd());

		if (startDate == null || endDate == null) {
			modelAndView.addObject("esito",
					"Input not valid, the format is: AAAA-MM-GG");
		} else {

			groupsTaskService.deleteAllGroups();
			groupsContactsService.deleteAll();

			List<User> allUsers = userService.getUsers();
			ArrayList<GroupUser> allGroupUsers = new ArrayList<GroupUser>();
			ArrayList<Long> allId = new ArrayList<Long>();

			for (User u : allUsers) {
				allGroupUsers.add(new GroupUser(u.getId()));
				allId.add(u.getId());
			}

			allUsers = null;

			GroupCalculate groupCalculate = new GroupCalculate(allGroupUsers);

			GroupUserElementDataManager guedm = new GroupUserElementDataManager();
			while (startDate.getMillis() < endDate.getMillis()) {
				List<DataLocationAvg> lista = dataLocationService
						.getDataLocationAvg(startDate, endDate);

				ArrayList<GroupUser> users = new ArrayList<GroupUser>();

				for (DataLocationAvg d : lista) {
					GroupUser tmp = new GroupUser(d.getId());
					tmp.setPoint(new GroupPoint(d.getLatitude(), d
							.getLongitude()));
					users.add(tmp);
				}

				groupCalculate.calculateGroups(users, guedm,
						groupsContactsService, startDate);
				startDate = GroupUtils.addOneHour(startDate);
			}

			/*************************************************************/

			// DELETING OLD GROUPS
			boolean delete = true;
			List<Long> toDelete = groupService.getAllId();
			for (long l : toDelete) {
				if (!groupService.deleteGroups(l)) {
					delete = false;
					break;
				}
			}

			/*************************************************************/

			List<GroupUser> calculatedGroupUsers = groupCalculate.getUsers();

			for (GroupUser u : calculatedGroupUsers) {
				List<Long> friends = groupCalculate.getFriends(u.getId());
				for (long l : friends) {
					DataGroups group = new DataGroups();
					group.setUser_id(u.getId());
					group.setId_user_group(l);
					group.setType(GroupUtils.FRIENDS);
					groupService.save(group);
				}

				List<Long> community = groupCalculate.getCommunity(u.getId());
				for (Long l : community) {
					DataGroups group = new DataGroups();
					group.setUser_id(u.getId());
					group.setId_user_group(l);
					group.setType(GroupUtils.COMMUNITY);
					groupService.save(group);
				}

				List<Long> familiarStrangers = groupCalculate.getStrangers(u
						.getId());

				for (Long l : familiarStrangers) {
					DataGroups group = new DataGroups();
					group.setUser_id(u.getId());
					group.setId_user_group(l);
					group.setType(GroupUtils.FAMILIARSTRANGERS);
					groupService.save(group);
				}

				ArrayList<Long> inGroup = new ArrayList<Long>();
				inGroup.addAll(friends);
				inGroup.addAll(community);
				inGroup.addAll(familiarStrangers);

				List<Long> strangers = GroupUtils.initRandomGroups(inGroup,
						allId);
				for (Long l : strangers) {
					DataGroups group = new DataGroups();
					group.setUser_id(u.getId());
					group.setId_user_group(l);
					group.setType(GroupUtils.STRANGERS);
					groupService.save(group);
				}
			}

			// Creazione GroupsTask
			long groupId = 0;
			for (long userid : groupService.getAllGroupsId()) {
				List<DataGroups> groups = groupService
						.getGroupsByUserId(userid);
				HashSet<GroupsTask> groupForUser = new HashSet<GroupsTask>();

				if (!groupsTaskService.isUserPresentInGroup(userid)) {
					GroupsTask gt = new GroupsTask();
					gt.setGroupId(groupId);
					gt.setComponentOfGroupId(userid);
					groupForUser.add(gt);

					for (DataGroups dg : groups) {
						if (!groupsTaskService.isUserPresentInGroup(dg
								.getId_user_group())) {
							GroupsTask g = new GroupsTask();
							g.setGroupId(groupId);
							g.setComponentOfGroupId(dg.getId_user_group());
							groupForUser.add(g);
						}
					}
				}

				if (groupForUser.size() > 1)
					for (GroupsTask g : groupForUser) {
						groupsTaskService.save(g);
					}
				groupId++;
			}

			if (delete) {
				modelAndView.addObject("esito", "Groups successfully updated");
			} else {
				modelAndView.addObject("esito", "Groups not updated");
			}
		}
		modelAndView.setViewName("protected/groups/main");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/groups/listofuser", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listOfUser(ModelAndView modelAndView) {
		List<User> users = userService.getUsers();
		
		Collections.sort(users);

		modelAndView.addObject("users", users);

		modelAndView.setViewName("protected/groups/listOfUser");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/groups/listofuser/info/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listOfGroups(ModelAndView modelAndView,
			@PathVariable long id, RedirectAttributes redirectAttributes) {

		List<GroupsTask> groupOfTask = groupsTaskService
				.getGroupsTaskByUserId(id);

		modelAndView.addObject("groupOfTask", groupOfTask);

		DatabaseGroupUser user = new DatabaseGroupUser();
		user.setId(id);

		User currentUser = userService.getUser(user.getId());

		modelAndView.addObject("currentUser", currentUser.getName() + " "
				+ currentUser.getSurname());

		modelAndView.setViewName("/protected/groups/listOfGroups");
		return modelAndView;
	}

	@RequestMapping(value = "/protected/groups/listOfGroups", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listOfGroups(ModelAndView modelAndView) {

		modelAndView.setViewName("protected/groups/listOfGroups");
		return modelAndView;
	}

	@ModelAttribute("groupsForm")
	public GroupsForm getMonthlyTargetScore() {
		return new GroupsForm();
	}
}
