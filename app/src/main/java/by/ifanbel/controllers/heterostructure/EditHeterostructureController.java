package by.ifanbel.controllers.heterostructure;

import by.ifanbel.data.database.dto.JspBeanHeterostructure;
import by.ifanbel.data.database.entities.Material;
import by.ifanbel.data.database.services.HeterostructureService;
import by.ifanbel.data.database.services.exceptions.NoSuchHeterostructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * The controller handles the requests from Edit Heterostructure page.
 */

@Controller
@RequestMapping("/editHeterostructure/")
public class EditHeterostructureController {

	@Autowired
	private HeterostructureService heterostructureService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView editHeterostructure(@RequestParam (required = false, defaultValue = "") String sampleName) {
		JspBeanHeterostructure jspBeanHeterostructure;
		if ((sampleName == null) || (sampleName.isEmpty())) jspBeanHeterostructure = new JspBeanHeterostructure(1);
		else {
			try {
				jspBeanHeterostructure = heterostructureService.getHeterostructureBySampleNumber(sampleName)
						.getJspBean();
			} catch (NoSuchHeterostructureException e) {
				e.printStackTrace();
				return new ModelAndView("exceptionMessage").addObject("message", e.toString());
			}
		}
		return new ModelAndView("editHeterostructure").addObject("jspBeanHeterostructure", jspBeanHeterostructure);
	}

	private void checkAndSaveHeterestructure(JspBeanHeterostructure jspBeanHeterostructure, BindingResult bindingResult) {
		StringBuilder errMsg = new StringBuilder();
		for (ObjectError error : bindingResult.getAllErrors()) {
			List<String> errorList = Arrays.asList(error.getDefaultMessage()
					.split(System.lineSeparator()));
			jspBeanHeterostructure.getErrorMessages()
					.addAll(errorList);
		}
		if ( !bindingResult.hasErrors() ) heterostructureService.createOrUpdate(jspBeanHeterostructure);
	}

	@RequestMapping(value = {"/saveHeterostructure"}, method = RequestMethod.POST)
	public String saveHeterostructure(@Valid JspBeanHeterostructure jspBeanHeterostructure, BindingResult bindingResult) {
		checkAndSaveHeterestructure(jspBeanHeterostructure, bindingResult);
		new ModelAndView().addObject(jspBeanHeterostructure);
		return "editHeterostructure";
	}

	@RequestMapping(value = {"/openHeterostructure"}, method = RequestMethod.POST)
	public String openHeterostructure(@Valid JspBeanHeterostructure jspBeanHeterostructure, BindingResult bindingResult) {
		checkAndSaveHeterestructure(jspBeanHeterostructure, bindingResult);
		new ModelAndView().addObject(jspBeanHeterostructure);
		if ( bindingResult.hasErrors() ) return "editHeterostructure";
		return "redirect:/viewHeterostructure/".concat(jspBeanHeterostructure.getSampleNumber())
				.concat(".svg");
	}

	@RequestMapping(value = {"/deleteHeterostructure"}, method = RequestMethod.POST)
	public String deleteHeterostructure(@Valid JspBeanHeterostructure jspBeanHeterostructure) {
		heterostructureService.delete(jspBeanHeterostructure.getSampleNumber());
		return "redirect:/browseHeterostructure/";
	}

}
