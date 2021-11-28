package by.ifanbel.data.database.entities;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The class describes a material of a Layer which is stored in the database.
 */
@Entity
public class Material {

	public final static String  AMMONIA = "NH3";
	public final static String  N_PLASMA = "N*";

	public final static String  NO_DOPANT = "";
	public final static String  Si_DOPANT = ":Si";
	public final static String  Mg_DOPANT = ":Mg";

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id", nullable = false)
	private long id;


	private float xAl;

	private float yIn;

	private float temperature;

	private float substrateHeat;

	private float nitrogenFlow;

	private NitrogenType nitrogenType;

	private Dopant dopant;

	public Material() {
	}

	;

	public Material(float xAl, float yIn, float temperature, float substrateHeat, float nitrogenFlow, NitrogenType nitrogenType, Dopant dopant) {
		this.xAl = xAl;
		this.yIn = yIn;
		this.temperature = temperature;
		this.substrateHeat = substrateHeat;
		this.nitrogenFlow = nitrogenFlow;
		this.nitrogenType = nitrogenType;
		this.dopant = dopant;
	}

	public float getxAl() {
		return xAl;
	}

	public float getyIn() {
		return yIn;
	}

	public float getTemperature() {
		return temperature;
	}

	public float getSubstrateHeat() {
		return substrateHeat;
	}

	public float getNitrogenFlow() {
		return nitrogenFlow;
	}

	@Enumerated(EnumType.STRING)
	public NitrogenType getNitrogenType() {
		return nitrogenType;
	}

	@Enumerated(EnumType.STRING)
	public Dopant getDopant() {
		return dopant;
	}


	public enum Dopant {
		none(NO_DOPANT), Si(Si_DOPANT), Mg(Mg_DOPANT);

		private String description;

		private Dopant(String description) {
			this.description = description;
		}

		public static Dopant getDopantByString(String stringDopant) {
			for (Dopant dopant : Dopant.values()) {
				if (dopant.getDescription().equals(stringDopant) ||  dopant.getDescription().toLowerCase().contains(stringDopant.trim().toLowerCase())) return dopant;
			}
			return Dopant.none;
		}

		public String getDescription() {
			return description;
		}
	}

	public enum NitrogenType {
		NH3(AMMONIA), N_plasma(N_PLASMA);

		private String description;

		private NitrogenType(String description) {
			this.description = description;
		}

		public static NitrogenType getNitrogenTypeByString(String stringNitrogenType) {
			for (NitrogenType nitrogenType : NitrogenType.values()) {
				if ( nitrogenType.getDescription().equals(stringNitrogenType) || nitrogenType.getDescription().toLowerCase().contains(stringNitrogenType.toLowerCase()))
					return nitrogenType;
			}
			return NitrogenType.NH3;
		}

		public String getDescription() {
			return description;
		}
	}


}
