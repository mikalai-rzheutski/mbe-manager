package by.ifanbel.graphic;

import by.ifanbel.data.database.entities.Material;

import java.awt.*;

/**
 * The class produces a color depending on the material. In future it is planned to add a black-and-white option and a possibility of user-defined colors.
 */
public class LayerColor {


	public static Color get(float xAl, float yIn, Material.NitrogenType nitrogenType) {
		Color ammoniaAlNColor = new Color(255, 255, 150);
		Color ammoniaGaNColor = new Color(50, 150, 255);
		Color ammoniaInNColor = new Color(255, 100, 100);
		Color plasmaAlNColor = new Color(200, 255, 100);
		Color plasmaGaNColor = new Color(100, 255, 255);
		Color plasmaInNColor = new Color(255, 0, 255);
		int red, green, blue;
		if (nitrogenType == Material.NitrogenType.NH3) {
			red = (int) (ammoniaAlNColor.getRed() * xAl + ammoniaGaNColor.getRed() * (1 - xAl - yIn) + ammoniaInNColor.getRed() * yIn);
			green = (int) (ammoniaAlNColor.getGreen() * xAl + ammoniaGaNColor.getGreen() * (1 - xAl - yIn) + ammoniaInNColor.getGreen() * yIn);
			blue = (int) (ammoniaAlNColor.getBlue() * xAl + ammoniaGaNColor.getBlue() * (1 - xAl - yIn) + ammoniaInNColor.getBlue() * yIn);
		} else {
			red = (int) (plasmaAlNColor.getRed() * xAl + plasmaGaNColor.getRed() * (1 - xAl - yIn) + plasmaInNColor.getRed() * yIn);
			green = (int) (plasmaAlNColor.getGreen() * xAl + plasmaGaNColor.getGreen() * (1 - xAl - yIn) + plasmaInNColor.getGreen() * yIn);
			blue = (int) (plasmaAlNColor.getBlue() * xAl + plasmaGaNColor.getBlue() * (1 - xAl - yIn) + plasmaInNColor.getBlue() * yIn);
		}
		return new Color(red, green, blue);
	}

	public static Color get(Material material) {
		return get(material.getxAl(), material.getyIn(), material.getNitrogenType());
	}
}
