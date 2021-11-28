package by.ifanbel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Locale;

@Controller
public class RootController {

	@Autowired
	MessageSource messageSource;

	@Autowired
	LocaleResolver localeResolver;

	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session, HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false, defaultValue = "false") String error) {
		Locale locale = LocaleContextHolder.getLocale();
		session.invalidate();
		localeResolver.setLocale(request, response, locale);
		String message = new Boolean(error)? messageSource.getMessage("login.failure", null, locale): " ";
		ModelAndView model = new ModelAndView("login");
		model.addObject("message", message);
		return model;
	}

	@RequestMapping(value = {"/about"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String aboutPage(HttpSession session) {
		return "about";
	}
}
