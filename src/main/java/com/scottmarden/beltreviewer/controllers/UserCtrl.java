package com.scottmarden.beltreviewer.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scottmarden.beltreviewer.models.Role;
import com.scottmarden.beltreviewer.models.User;
import com.scottmarden.beltreviewer.services.UserService;
import com.scottmarden.beltreviewer.validators.UserValidator;

@Controller
public class UserCtrl {

	private UserService userService;
	
	private UserValidator userValidator;
	
	public UserCtrl(UserService userService, UserValidator userValidator) {
		this.userService = userService;
		this.userValidator = userValidator;
	}
	
//	@RequestMapping(value= "/")
//	public String landingPage(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, @Valid @ModelAttribute("user") User user, Model model) {
//		return "landingPage.jsp";
//	}
	
	@RequestMapping(value="/home")
	public String redirectDash() {
		return "redirect:/events";
	}
	
	@RequestMapping(value="/registration")
	public String redirectLanding() {
		return "redirect:/";
	}
	
	@PostMapping(value="/registration")
	public String registration(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
		userValidator.validate(user, result);
		if(result.hasErrors()) {
			return "landingPage.jsp";
		}
		List<Role> adminRole = userService.findRoleByName("ROLE_ADMIN");
		if(adminRole.get(0).getUsers().size() == 0 ) {
			userService.saveWithAdminRole(user);
			return "redirect:/home";
		}else {
			userService.saveWithUserRole(user);
			return "redirect:/home";
		}

	}
	
	@RequestMapping(value="/login")
	public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, @Valid @ModelAttribute("user") User user) {
		if(error != null) {
			model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
		}
		if(logout != null) {
			model.addAttribute("logoutMessage", "Logout Successful!");
		}
		model.addAttribute("states", userService.stateList());
		
		return "landingPage.jsp";
	}
	
	@RequestMapping(value= {"/", "/home"})
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		User currentUser = userService.findByEmail(username);
		if(currentUser.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
			model.addAttribute("adminFlag", "admin");
		}
		model.addAttribute("currentUser", currentUser);
		return "homePage.jsp";
	}
	
	@RequestMapping(value="/admin")
	public String adminPage(Principal principal, Model model) {
		String email = principal.getName();
		model.addAttribute("currentUser", userService.findByEmail(email));
		List<User> users = userService.findRoleByName("ROLE_USER").get(0).getUsers();
		List<User> admins = userService.findRoleByName("ROLE_ADMIN").get(0).getUsers();
		model.addAttribute("users", users);
		model.addAttribute("admins", admins);
		return "adminPage.jsp";
	}
	
	@RequestMapping(value="/admin/delete/{user_id}")
	public String destroyUser(@PathVariable("user_id") Long user_id) {
		userService.destroyUser(user_id);
		return "redirect:/admin";
	}
	
	@RequestMapping(value="/admin/create_admin/{user_id}")
	public String createAdmin(@PathVariable("user_id") Long user_id) {
		userService.createAdmin(user_id);
		return "redirect:/admin";
	}
	
}
