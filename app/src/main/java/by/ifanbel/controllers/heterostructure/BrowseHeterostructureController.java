package by.ifanbel.controllers.heterostructure;

import by.ifanbel.data.database.services.HeterostructureService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * The controller handles the requests from Browse Heterostructure page.
 */
@Controller
@RequestMapping("/browseHeterostructure/")
public class BrowseHeterostructureController {

	private Counter browseCounter;

	@Autowired
	private PrometheusMeterRegistry meterRegistry;

	@Autowired
	private HeterostructureService heterostructureService;

	@RequestMapping(value = {""}, method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showListOfAllHeterostructures() {
		List<Object[]> listOfAllHeterostructures = heterostructureService.getTable();
		ModelAndView model = new ModelAndView("browseHeterostructure");
		model.addObject("listOfAllHeterostructures", listOfAllHeterostructures);
		return model;
	}

	@RequestMapping(value = {"/createNewHeterostructure"}, method = RequestMethod.GET)
	public String newHeterostructure() {
		return "redirect:/createNewHeterostructure";
	}

	@RequestMapping(value = {"/viewHeterostructure/{sampleName}.{type}"}, method = RequestMethod.GET)
	public String viewHeterostructure() {
		return "redirect:/viewHeterostructure/{sampleName}.{type}";
	}


}
