package by.ifanbel.data.database.dto;

import by.ifanbel.view.validation.PatternForListOfString;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;


/**
 * The class is data transfer object to display Heterostructure object in jsp.
 */
public class JspBeanHeterostructure {

	@Pattern(regexp = "\\w+", message = "{validation.incorrectSampleNumber}")
	private String sampleNumber;

	private String description;

	private String date;

	private String growers;

	private String waferNumber;

	private String waferSize;

	private String substrate;

	private String comments;

	private List<String> errorMessages = new ArrayList<>();

	private List<Integer> layerId = new ArrayList<Integer>();

	private List<String> growthMode = new ArrayList<String>();

	private List<Double> thickness = new ArrayList<Double>();

	@PatternForListOfString(regexp = "(((1|0)(\\.0+)?)|(0\\.\\d*))(-(((1|0)(\\.0+)?)|(0\\.\\d*)))?", message = "{validation.incorrectParameter}" + "\"x\"")
	private List<String> x = new ArrayList<String>();

	@PatternForListOfString(regexp = "(((1|0)(\\.0+)?)|(0\\.\\d*))(-(((1|0)(\\.0+)?)|(0\\.\\d*)))?", message = "{validation.incorrectParameter}" + "\"y\"")
	private List<String> y = new ArrayList<String>();

	@PatternForListOfString(regexp = "([0-9]+[.,]?[0-9]+)(-[0-9]+[.,]?[0-9]+)?", message = "{validation.incorrectParameter}" + "{validation.temperature}")
	private List<String> temperature = new ArrayList<String>();

	@PatternForListOfString(regexp = "((100([.,]0*)?)|([0-9]{1,2}([.,][0-9]+)?))(-((100([.,]0*)?)|([0-9]{1,2}([.,][0-9]+)?)))?", message = "{validation.incorrectParameter}" + "{validation.heat}")
	private List<String> heat = new ArrayList<String>();

	@PatternForListOfString(regexp = "([0-9]+([.,][0-9]+)?)(-[0-9]+([.,][0-9]+)?)?", message = "{validation.incorrectParameter}" + "{validation.nFlow}")
	private List<String> nflow = new ArrayList<String>();

	private List<String> dopant = new ArrayList<String>();

	private List<String> layerComment = new ArrayList<String>();

	public JspBeanHeterostructure(int numberOfEmptyLayers) {
		for (int i = 0; i < numberOfEmptyLayers; i++) {
			layerId.add(1);
			growthMode.add("1");
			thickness.add(0.0);
			x.add("");
			y.add("");
			temperature.add("");
			heat.add("");
			nflow.add("");
			dopant.add("1");
			layerComment.add("");
		}
	}

	public JspBeanHeterostructure() {
	}

	public String getSampleNumber() {
		return sampleNumber;
	}

	public void setSampleNumber(String sampleNumber) {
		this.sampleNumber = sampleNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getGrowers() {
		return growers;
	}

	public void setGrowers(String growers) {
		this.growers = growers;
	}

	public String getWaferNumber() {
		return waferNumber;
	}

	public void setWaferNumber(String waferNumber) {
		this.waferNumber = waferNumber;
	}

	public String getWaferSize() {
		return waferSize;
	}

	public void setWaferSize(String waferSize) {
		this.waferSize = waferSize;
	}

	public String getSubstrate() {
		return substrate;
	}

	public void setSubstrate(String substrate) {
		this.substrate = substrate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<Integer> getLayerId() {
		return layerId;
	}

	public void setLayerId(List<Integer> layerId) {
		this.layerId = layerId;
	}

	public List<String> getGrowthMode() {
		return growthMode;
	}

	public void setGrowthMode(List<String> growthMode) {
		this.growthMode = growthMode;
	}

	public List<Double> getThickness() {
		return thickness;
	}

	public void setThickness(List<Double> thickness) {
		this.thickness = thickness;
	}

	public List<String> getX() {
		return x;
	}

	public void setX(List<String> x) {
		this.x = x;
	}

	public List<String> getY() {
		return y;
	}

	public void setY(List<String> y) {
		this.y = y;
	}

	public List<String> getTemperature() {
		return temperature;
	}

	public void setTemperature(List<String> temperature) {
		this.temperature = temperature;
	}

	public List<String> getHeat() {
		return heat;
	}

	public void setHeat(List<String> heat) {
		this.heat = heat;
	}

	public List<String> getNflow() {
		return nflow;
	}

	public void setNflow(List<String> nflow) {
		this.nflow = nflow;
	}

	public List<String> getDopant() {
		return dopant;
	}

	public void setDopant(List<String> dopant) {
		this.dopant = dopant;
	}

	public List<String> getLayerComment() {
		return layerComment;
	}

	public void setLayerComment(List<String> layerComment) {
		this.layerComment = layerComment;
	}


}
