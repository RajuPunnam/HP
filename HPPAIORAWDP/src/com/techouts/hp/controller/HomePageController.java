package com.techouts.hp.controller;

import java.net.UnknownHostException;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techouts.hp.pojo.User;
import com.techouts.hp.service.impl.UserService;
import com.techouts.hp.util.OSUtil;

/**
 * HomePageController class for login and registration
 * @author raju.p
 *
 */
@Controller
public class HomePageController {
	private static final Logger log = Logger
			.getLogger(HomePageController.class);
	@Autowired
	private UserService userService;
	@Autowired
	private OSUtil OSUtil;

	@RequestMapping(value = "/login")
	public String returnLoginPage(HttpSession session, Model model) {
		return "UserLogin";
	}

	@RequestMapping(value = "/loginsucess")
	public String homePage(Model model) throws UnknownHostException {
		/*log.info("User login sucessfully");*/
		model.addAttribute("machineip", OSUtil.getIP());
		return "Rawdataprocess";
	}

	@RequestMapping(value = "/loginerror")
	public String returnDetailsPage(ModelMap model) {
		model.addAttribute("message", "you entered wrong credentials");
		return "error";
	}

	@RequestMapping(value = "/registration")
	public String userRegistration() {
		log.info("User has to register");
		return "Register";
	}

	@RequestMapping(value = "/userRegistration", method = RequestMethod.POST)
	public String registration(@ModelAttribute User user, Model model) {
		model.addAttribute("RegStatus", userService.userRegistration(user));
		return "RegistrationSucess";
	}

}
