package by.ifanbel.data.database.entities;

import by.ifanbel.data.database.dto.JspBeanHeterostructure;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The class describes epitaxial heterostructure constructed by user which is stored in the database.
 */
/*@Configurable(preConstruction=true,autowire= Autowire.BY_TYPE)*/
@Entity
@Table(name = "heterostructure")
public class Heterostructure {

	@Id
	@Column(name = "id", nullable = false)
	private String sampleNumber;

	@Type(type="text")
	private String description = "";

	private String waferNumber = "";

	@Type(type = "date")
	@Temporal(TemporalType.DATE)
	private Date date;

	//@Type(type="text")
	@Lob
	private String comments = "";

	private String growersLastNames = "";

	private String substrateMaterial = "";

	private String waferSize = "";

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "heterostructure", cascade = CascadeType.ALL)
	private List<Layer> layers = new ArrayList<Layer>();

	public Heterostructure() {
	}

	public Heterostructure(String sampleNumber, String waferNumber, Date date, String comments, String growersLastNames, String substrateMaterial) {
		this.sampleNumber = sampleNumber;
		this.waferNumber = waferNumber;
		this.date = date;
		this.comments = comments;
		this.growersLastNames = growersLastNames;
		this.substrateMaterial = substrateMaterial;
	}

	public Heterostructure(JspBeanHeterostructure jspBeanHeterostructure) {
		update(jspBeanHeterostructure);
	}

	public void update(JspBeanHeterostructure jspBeanHeterostructure) {
		this.sampleNumber = jspBeanHeterostructure.getSampleNumber();
		this.description = jspBeanHeterostructure.getDescription();
		this.waferNumber = jspBeanHeterostructure.getWaferNumber();
		try {
		//	this.date = new SimpleDateFormat("yyyy-MM-dd").parse(jspBeanHeterostructure.getDate());
		//	this.date = LocalDate.parse(jspBeanHeterostructure.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			this.date= Date.valueOf(jspBeanHeterostructure.getDate());
		} catch (Exception e) {
			this.date = null;
		}
		this.comments = jspBeanHeterostructure.getComments();
		this.growersLastNames = jspBeanHeterostructure.getGrowers();
		this.substrateMaterial = jspBeanHeterostructure.getSubstrate();
		this.waferSize = jspBeanHeterostructure.getWaferSize();
		this.layers = new ArrayList<Layer>();
		for (int i = 0; i < jspBeanHeterostructure.getLayerId()
				.size(); i++) {
			String x = jspBeanHeterostructure.getX()
					.get(i);
			String y = jspBeanHeterostructure.getY()
					.get(i);
			String temperature = jspBeanHeterostructure.getTemperature()
					.get(i);
			String heat = jspBeanHeterostructure.getHeat()
					.get(i);
			String nitrogenFlow = jspBeanHeterostructure.getNflow()
					.get(i);
			String nitrogenType = jspBeanHeterostructure.getGrowthMode()
					.get(i);
			String dopant = jspBeanHeterostructure.getDopant()
					.get(i);
			Double thickness = jspBeanHeterostructure.getThickness()
					.get(i);
			String layerComment = jspBeanHeterostructure.getLayerComment()
					.get(i);
			Material startMaterial = new Material(getStartValue(x), getStartValue(y), getStartValue(temperature), getStartValue(heat), getStartValue(nitrogenFlow),
					Material.NitrogenType.getNitrogenTypeByString(nitrogenType), Material.Dopant.getDopantByString(dopant));
			Material stopMaterial = new Material(getStopValue(x), getStopValue(y), getStopValue(temperature), getStopValue(heat), getStopValue(nitrogenFlow),
					Material.NitrogenType.getNitrogenTypeByString(nitrogenType), Material.Dopant.getDopantByString(dopant));
			layers.add(i, new Layer(this, i, startMaterial, stopMaterial, thickness, layerComment));
		}
	}

	private float getStartValue(String s) {
		String[] values = s.split("-");
		try {
			return Float.parseFloat(values[0].trim()
					.replace(",", "."));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private float getStopValue(String s) {
		String[] values = s.split("-");
		try {
			return Float.parseFloat(values[values.length - 1].trim()
					.replace(",", "."));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	@JsonIgnore
	public JspBeanHeterostructure getJspBean() {
		JspBeanHeterostructure jspBeanHeterostructure = new JspBeanHeterostructure(0);
		jspBeanHeterostructure.setSampleNumber(this.sampleNumber);
		jspBeanHeterostructure.setDescription(this.description);
		jspBeanHeterostructure.setDate(new SimpleDateFormat("yyyy-MM-dd").format(this.date));
		jspBeanHeterostructure.setGrowers(this.growersLastNames);
		jspBeanHeterostructure.setWaferNumber(this.waferNumber);
		jspBeanHeterostructure.setWaferSize(this.waferSize);
		jspBeanHeterostructure.setSubstrate(this.substrateMaterial);
		jspBeanHeterostructure.setComments(this.comments);
		for (int i = 0; i < layers.size(); i++) {
			Layer layer = layers.get(i);
			jspBeanHeterostructure.getLayerId()
					.add(i, layer.getLayerNumber());
			jspBeanHeterostructure.getGrowthMode()
					.add(i, layer.getStartMaterial()
							.getNitrogenType()
							.getDescription());
			jspBeanHeterostructure.getThickness()
					.add(i, layer.getThickness());
			jspBeanHeterostructure.getX()
					.add(i, getHtmlInputText(layer.getStartMaterial()
							.getxAl(), layer.getStopMaterial()
							.getxAl()));
			jspBeanHeterostructure.getY()
					.add(i, getHtmlInputText(layer.getStartMaterial()
							.getyIn(), layer.getStopMaterial()
							.getyIn()));
			jspBeanHeterostructure.getTemperature()
					.add(i, getHtmlInputText(layer.getStartMaterial()
							.getTemperature(), layer.getStopMaterial()
							.getTemperature()));
			jspBeanHeterostructure.getHeat()
					.add(i, getHtmlInputText(layer.getStartMaterial()
							.getSubstrateHeat(), layer.getStopMaterial()
							.getSubstrateHeat()));
			jspBeanHeterostructure.getNflow()
					.add(i, getHtmlInputText(layer.getStartMaterial()
							.getNitrogenFlow(), layer.getStopMaterial()
							.getNitrogenFlow()));
			jspBeanHeterostructure.getDopant()
					.add(i, layer.getStartMaterial()
							.getDopant()
							.getDescription());
			jspBeanHeterostructure.getLayerComment()
					.add(i, layer.getComment());
		}
		return jspBeanHeterostructure;
	}

	private String getHtmlInputText(Number startValue, Number stopValue) {
		if (startValue.equals(stopValue)) return startValue.toString();
		else return startValue.toString() + " " + "-" + " " + stopValue.toString();
	}


	public void addLayer(Layer layer) {
		layers.add(layer);
	}

	public int layersNumber() {
		return layers.size();
	}

	public Layer getLayer(int index) {
		return layers.get(index);
	}

	public String getSampleNumber() {
		return sampleNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getWaferNumber() {
		return waferNumber;
	}

	public String getWaferSize() {
		return waferSize;
	}

	public void setWaferSize(String waferSize) {
		this.waferSize = waferSize;
	}

	public Date getDate() {
		return date;
	}

	public String getComments() {
		return comments;
	}

	public String getGrowersLastNames() {
		return growersLastNames;
	}

	public String getSubstrateMaterial() {
		return substrateMaterial;
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}


	public double getTotalThickness() {
		return layers.stream()
				.map(layer -> layer.getThickness())
				.reduce(0.0, (total, layerThickness) -> total + layerThickness);
	}
}
