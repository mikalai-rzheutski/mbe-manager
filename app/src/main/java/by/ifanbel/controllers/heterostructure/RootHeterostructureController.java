package by.ifanbel.controllers.heterostructure;

import by.ifanbel.data.database.services.HeterostructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * The controller handles general requests.
 */

@Controller
public class RootHeterostructureController {

	@Autowired
	private HeterostructureService heterostructureService;

	@Autowired
	MessageSource messageSource;


	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String homePage() {
		return "redirect:/browseHeterostructure/";
	}

	@RequestMapping(value = {"/login/oauth2/code/google"}, method = RequestMethod.GET)
	public String fromGoogle() {
		return "login";
	}

	@RequestMapping(value = {"/noSuchPage"}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView noSuchPage() {
		String message = messageSource.getMessage("exception.noPage", null, LocaleContextHolder.getLocale());
		return new ModelAndView("exceptionMessage").addObject("message", message);
	}

	@RequestMapping(value = {"/accessDenied"}, method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		String message = messageSource.getMessage("exception.noAccess", null, LocaleContextHolder.getLocale());
		return new ModelAndView("exceptionMessage").addObject("message", message);
	}

	@RequestMapping(value = {"/invalidOauth2User"}, method = RequestMethod.GET)
	public ModelAndView invalidOauth2User(HttpServletRequest request) {
		Exception e = (Exception)(request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION));
		String message = e.getMessage();
		return new ModelAndView("exceptionMessage").addObject("message", message);
	}

	@RequestMapping(value = {"/createNewHeterostructure"}, method = RequestMethod.GET)
	public String createHeterostructure(Model model) {
		return "redirect:/editHeterostructure/";
	}

}

