package com.pavikumbhar.javaheart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pavikumbhar.javaheart.model.Customer;
import com.pavikumbhar.javaheart.service.CustomerService;

import lombok.AllArgsConstructor;

/**
 * 
 * @author pavi kumbhar Handles requests for the application login page.
 */

@AllArgsConstructor
@Controller
public class LoginController {

	private CustomerService customerService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginForm() {
		return "login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String verifyLogin(@RequestParam String userId, @RequestParam String password, HttpSession session,
			Model model) {

		Customer customer = customerService.loginCustomer(userId, password);
		if (customer == null) {
			model.addAttribute("loginError", "Error logging in. Please try again");
			return "login";
		}
		session.setAttribute("loggedInUser", customer);
		return "redirect:/";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute("loggedInUser");
		return "login";
	}
}
