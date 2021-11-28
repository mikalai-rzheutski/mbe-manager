package by.ifanbel.controllers.heterostructure;

import by.ifanbel.data.cache.Cache;
import by.ifanbel.data.database.dto.JspBeanHeterostructure;
import by.ifanbel.data.database.entities.Heterostructure;
import by.ifanbel.data.database.services.HeterostructureService;
import by.ifanbel.data.database.services.exceptions.NoSuchHeterostructureException;
import by.ifanbel.graphic.Design;
import by.ifanbel.graphic.MSWordDocument;
import io.micrometer.core.annotation.Timed;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller handles the requests from View Heterostructure page.
 */

@Controller
@RequestMapping("/viewHeterostructure/")
public class ViewHeterostructureController {

	@Autowired
	private HeterostructureService heterostructureService;

	// A presence of "-" in a sample's name means that the condition section should not be drawn
	private String getNormalSampleName(String sampleName) {
		return sampleName.replace("-", "");
	}

	private String getConditionsChar(String sampleName) {
		return drawConditions(sampleName)? "" : "-";
	}

	private boolean drawConditions(String sampleName) {
		return !sampleName.contains("-");
	}

	@GetMapping("{sampleName}.{type}")
	public ModelAndView openHeterostructure(@PathVariable String sampleName, @PathVariable @Nullable String type) {
	//	String showConditionsChar = "-";
	//	if (!sampleName.contains(showConditionsChar)) showConditionsChar = "";
		ModelAndView m = new ModelAndView("viewHeterostructure");
		JspBeanHeterostructure jspBeanHeterostructure = null;
		try {
			jspBeanHeterostructure = heterostructureService.getJspHeterostructureBySampleNumber(getNormalSampleName(sampleName));
		} catch (NoSuchHeterostructureException e) {
			e.printStackTrace();
		}
		String extension = (type == null) ? Design.SVG : type;
		m.addObject("jspBeanHeterostructure", jspBeanHeterostructure);
		m.addObject("image_type", extension);
		m.addObject("showConditionsChar", getConditionsChar(sampleName));
		return m;
	}

	@GetMapping("get_image/{sampleName}.{type}")
	@ResponseBody
	@Timed("get.picture.response")
	public byte[] getImageWithMediaType(@PathVariable String sampleName, @PathVariable String type) throws IOException {
		Heterostructure h = null;
		try {
			h = heterostructureService.getHeterostructureBySampleNumber(getNormalSampleName(sampleName));
		} catch (NoSuchHeterostructureException e) {
			e.printStackTrace();
		}
		byte[] result = Cache.getBytes(h, type, drawConditions(sampleName));
		return result;
	}

	@GetMapping("openPrevious")
	public ModelAndView getPrevious(@RequestParam String sampleName) {
		String newSampleName = null;
		try {
			newSampleName = getNewSampleName(getNormalSampleName(sampleName), 1);
		} catch (NoSuchHeterostructureException e) {
			e.printStackTrace();
			return new ModelAndView("exceptionMessage").addObject("message", e.toString());
		}
		return new ModelAndView("redirect:/viewHeterostructure/".concat(newSampleName).concat(getConditionsChar(sampleName))
				.concat(".svg"));
	}

	@GetMapping("openCurrent")
	public ModelAndView getCurrent(@RequestParam String sampleName) {
		String newSampleName = null;
		try {
			newSampleName = getNewSampleName(getNormalSampleName(sampleName), 0);
		} catch (NoSuchHeterostructureException e) {
			e.printStackTrace();
			return new ModelAndView("exceptionMessage").addObject("message", e.toString());
		}
		return new ModelAndView("redirect:/viewHeterostructure/".concat(newSampleName).concat(getConditionsChar(sampleName))
				.concat(".svg"));
	}

	@GetMapping("openNext")
	public ModelAndView getNext(@RequestParam String sampleName) {
		String newSampleName = null;
		try {
			newSampleName = getNewSampleName(getNormalSampleName(sampleName), -1);
		} catch (NoSuchHeterostructureException e) {
			e.printStackTrace();
			return new ModelAndView("exceptionMessage").addObject("message", e.toString());
		}
		return new ModelAndView("redirect:/viewHeterostructure/".concat(newSampleName).concat(getConditionsChar(sampleName))
				.concat(".svg"));
	}

	private String getNewSampleName(String sampleName, int shift) throws NoSuchHeterostructureException {
		List<Object[]> TableOfHeterostructures = Cache.getTableOfHeterostructures(heterostructureService);
		List<String> listOfSampleNames = TableOfHeterostructures.stream()
				.map((Object[] objects) -> (String) objects[1])
				.collect(Collectors.toList());
		List<String> searchListOfSampleNames = TableOfHeterostructures.stream()
				.map((Object[] objects) -> ((String) objects[1]).toLowerCase())
				.collect(Collectors.toList());
		int index = searchListOfSampleNames.indexOf(sampleName.toLowerCase()
				.trim());
		if (index == -1) throw new NoSuchHeterostructureException(sampleName);
		int newIndex = index + shift;
		int size = listOfSampleNames.size();
		if (newIndex < 0) newIndex = 0;
		if (newIndex >= size) newIndex = size - 1;
		return listOfSampleNames.get(newIndex);
	}

	@RequestMapping(value = {"editHeterostructure"}, method = RequestMethod.GET)
	public String editHeterostructure(String sampleName, Model model) {
		String uri = UriComponentsBuilder.fromUriString("redirect:/editHeterostructure/")
				.queryParam("sampleName", sampleName)
				.build()
				.toUriString();
		return uri;
	}

	@RequestMapping(value = {"createNewHeterostructure"}, method = RequestMethod.GET)
	public String newHeterostructure() {
		return "redirect:/createNewHeterostructure";
	}

	@RequestMapping("exportToMSWord")
	public void exportToMSWord(String sampleName, HttpServletResponse response) throws IOException, NoSuchHeterostructureException, InvalidFormatException {
		response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		response.setHeader("Content-Disposition", "inline;filename=" + sampleName + ".docx");
		BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
		MSWordDocument wordDocument = new MSWordDocument(heterostructureService.getHeterostructureBySampleNumber(sampleName), false);
		wordDocument.getDocument(outStream);
		outStream.flush();
	}

}
