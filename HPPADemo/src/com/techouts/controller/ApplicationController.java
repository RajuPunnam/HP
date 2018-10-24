package com.techouts.controller;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationController {

	public static final Logger log = Logger
			.getLogger(ApplicationController.class);

	@RequestMapping(value = "/login")
	public String returnLoginPage(HttpSession session, Model model,
			HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		// String path = req.getContextPath();
		if (req.getHeader("User-Agent").indexOf("Mobile") != -1) {
			log.info("Mobile user "
					+ req.getHeader("User-Agent").indexOf("Mobile"));
			return "UserLogin";
		} else {
			log.info("Desktop user");
			// response.sendRedirect(path + "/index.html");
			return "index";
		}
	}

	@RequestMapping(value = "/loginsucess", method = RequestMethod.GET)
	public String homePage(Principal principal, Model model,
			Authentication authentication, HttpSession session) {
		authentication.getPrincipal();
		log.info("User login sucessfully");
		return "home";
	}

	@RequestMapping(value = "/loginerror")
	public String returnDetailsPage(ModelMap model) {
		model.addAttribute("message", "you entered wrong credentials");
		return "error";

	}
}