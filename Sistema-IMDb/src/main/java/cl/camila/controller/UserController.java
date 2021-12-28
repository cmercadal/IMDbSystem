package cl.camila.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cl.camila.model.User;
import cl.camila.service.UserService;
import cl.camila.validator.UserValidator;

@Controller
public class UserController {
	
	@Autowired
	private UserValidator userValidator;
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model,
			@Valid @ModelAttribute("user") User user, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");

		if (error != null) {
			modelAndView.addObject("errorMessage", "Invalid credentials, please try again.");
		}
		if (logout != null) {
			modelAndView.addObject("logoutMessage", "Logout Successful!");
		}
		return modelAndView;
	}

	@GetMapping("/registration")
	public ModelAndView registerForm(@Valid @ModelAttribute("user") User user) {
		return new ModelAndView("registration");
	}

	@PostMapping("/registration")
	public RedirectView registration(@Valid @ModelAttribute("user") User user, BindingResult result) {
		userValidator.validate(user, result);
		if (result.hasErrors()) {
			return new RedirectView("registration");
		} else {
			userService.saveWithUserRole(user);
			// userService.saveUserWithAdminRole(user);
			return new RedirectView("/login");
		}
	}

	@RequestMapping(value = { "/", "/home" })
	public RedirectView home(Principal principal) {
		return new RedirectView("/shows");
	}
}

