package by.ifanbel.data.database.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The class describes layer which is a part of Heterostructure and stored in the database.
 */
@Entity
@Table(name = "layer")
public class Layer {

	@EmbeddedId
	LayerKey layerKey = new LayerKey();

	@ManyToOne
	@MapsId("heterostructure_id")
	@JoinColumn(name = "heterostructure_id", nullable = false)
	private Heterostructure heterostructure;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "start_material_id")
	private Material startMaterial;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "stop_material_id")
	private Material stopMaterial;

	private double thickness;

	private String comment;

	public Layer() {
	}

	public Layer(Heterostructure heterostructure, int layerNumber, Material startMaterial, Material stopMaterial, double thickness, String comment) {
		this.heterostructure = heterostructure;
		this.layerKey.layerNumber = layerNumber;
		this.startMaterial = startMaterial;
		this.stopMaterial = stopMaterial;
		this.thickness = thickness;
		this.comment = comment.trim();

	}

	public Layer(Heterostructure heterostructure, int layerNumber, Material material, double thickness, String comment) {
		this.heterostructure = heterostructure;
		this.layerKey.layerNumber = layerNumber;
		this.startMaterial = material;
		this.stopMaterial = material;
		this.thickness = thickness;
		this.comment = comment.trim();
	}

	public boolean isGradientContent() {
		return ((startMaterial.getxAl() != stopMaterial.getxAl()) || (startMaterial.getyIn() != stopMaterial.getyIn()));
	}

	public Material getStartMaterial() {
		return startMaterial;
	}

	public Material getStopMaterial() {
		return stopMaterial;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public String getComment() {
		return comment;
	}

	public int getLayerNumber() {
		return layerKey.layerNumber;
	}

	public boolean isSameFlowAndTemperature(Layer layer) {
		boolean isSameNitrogenType = (this.getStartMaterial()
				.getNitrogenType() == this.getStopMaterial()
				.getNitrogenType()) &&
				(layer.getStartMaterial()
						.getNitrogenType() == layer.getStopMaterial()
						.getNitrogenType()) &&
				(this.getStartMaterial()
						.getNitrogenType() == layer.getStartMaterial()
						.getNitrogenType());
		boolean isSameNitrogenFlow = (this.getStartMaterial()
				.getNitrogenFlow() == this.getStopMaterial()
				.getNitrogenFlow()) &&
				(layer.getStartMaterial()
						.getNitrogenFlow() == layer.getStopMaterial()
						.getNitrogenFlow()) &&
				(this.getStartMaterial()
						.getNitrogenFlow() == layer.getStartMaterial()
						.getNitrogenFlow());
		boolean isSameTemperature = (this.getStartMaterial()
				.getTemperature() == this.getStopMaterial()
				.getTemperature()) &&
				(layer.getStartMaterial()
						.getTemperature() == layer.getStopMaterial()
						.getTemperature()) &&
				(this.getStartMaterial()
						.getTemperature() == layer.getStartMaterial()
						.getTemperature());
		boolean isSameSubstrateHeat = (this.getStartMaterial()
				.getSubstrateHeat() == this.getStopMaterial()
				.getSubstrateHeat()) &&
				(layer.getStartMaterial()
						.getSubstrateHeat() == layer.getStopMaterial()
						.getSubstrateHeat()) &&
				(this.getStartMaterial()
						.getSubstrateHeat() == layer.getStartMaterial()
						.getSubstrateHeat());
		return (isSameNitrogenType && isSameNitrogenFlow && isSameTemperature && isSameSubstrateHeat);
	}

	@Embeddable
	public static class LayerKey implements Serializable {

		String heterostructure_id;

		int layerNumber;

		public LayerKey(String heterostructure_id, int layerNumber) {
			this.heterostructure_id = heterostructure_id;
			this.layerNumber = layerNumber;
		}

		public LayerKey() {
		}

		public String getHeterostructure_id() {
			return heterostructure_id;
		}

		public void setHeterostructure_id(String heterostructure_id) {
			this.heterostructure_id = heterostructure_id;
		}

		public int getLayerNumber() {
			return layerNumber;
		}

		public void setLayerNumber(int layerNumber) {
			this.layerNumber = layerNumber;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof LayerKey)) return false;
			LayerKey layerKey = (LayerKey) o;
			return getLayerNumber() == layerKey.getLayerNumber() &&
					getHeterostructure_id().equals(layerKey.getHeterostructure_id());
		}

		@Override
		public int hashCode() {
			return Objects.hash(getHeterostructure_id(), getLayerNumber());
		}
	}

}
