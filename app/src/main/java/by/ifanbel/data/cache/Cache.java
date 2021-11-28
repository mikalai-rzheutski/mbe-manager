package by.ifanbel.data.cache;

import by.ifanbel.data.database.dto.JspBeanHeterostructure;
import by.ifanbel.data.database.entities.Heterostructure;
import by.ifanbel.data.database.services.HeterostructureService;
import by.ifanbel.graphic.Design;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;


/**
 * The class includes static methods for putting/getting image files to/from a cache.
 * The cache should be renewed after every updating of the database.
 */
public class Cache {

	private static String resourcefolder;

	private static List<Object[]> tableOfHeterostructures;

	public static void setResourcefolder(String resourcefolder) {
		Cache.resourcefolder = resourcefolder
				.concat("/graphs/");
	}

	/**
	 * returns a table of heterostrures which are stored in the database.
	 *
	 * @param heterostructureService is a service to access the database.
	 * @return a list of objects containing heterostructures' date, name and short description.
	 */
	public static List<Object[]> getTableOfHeterostructures(HeterostructureService heterostructureService) {
		if (tableOfHeterostructures == null) tableOfHeterostructures = heterostructureService.getTable();
		return tableOfHeterostructures;
	}


	/**
	 * returns a File for the given heterostructure. The new file is created and stored in the cache if not present.
	 *
	 * @param h        is a Heterostrucuture object.
	 * @param fileType is a string representing an image file extension ("png" of "svg")
	 * @return a File object.
	 */
	public static File getFile(Heterostructure h, String fileType, boolean drawConditions) {
	//	File folder = new File(resourcefolder);
		String drawConditionsChar = drawConditions? "" : "-";
		//if (!folder.exists()) folder.mkdirs();
		File file = new File(resourcefolder
				.concat(h.getSampleNumber())
				.concat(drawConditionsChar)
				.concat(".")
				.concat(fileType));
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		if (!file.exists())
			try {
				file.createNewFile();
				new Design(h, true, true, drawConditions).toFile(file);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			} finally {
			}
		return file;
	}

	/**
	 * renews the cache by deleting of the existing files corresponding to the given heterostructure.
	 *
	 * @param fileName is the heterostructure's name.
	 */
	public static void clear(String fileName) {
		tableOfHeterostructures = null;
		File folder = new File(resourcefolder);
		if (folder.exists()) {
			File[] files = folder.listFiles();
			for (File file : files) {
				if ((file.isFile()) & (file.getName()
						.toLowerCase()
						.contains(fileName.toLowerCase()
								.trim()))) {
					file.delete();
				}
			}
		}
	}

	/**
	 * returns an image file for the given heterostructure as a byte array. The new file is created and stored in the cache if not present.
	 *
	 * @param h        is a Heterostrucuture object.
	 * @param fileType is a string representing an image file extension ("png" of "svg")
	 * @return an image file as a byte array.
	 */
	public static byte[] getBytes(Heterostructure h, String fileType, boolean drawConditions) throws IOException {
		File file = getFile(h, fileType, drawConditions);
		InputStream in = new FileInputStream(file);
		//byte[] byteArray = IOUtils.readFully(in);
		byte[] byteArray = new byte[(int)file.length()];
		IOUtils.readFully(in, byteArray);
		in.close();
		if (!fileType.contains(Design.SVG)) return byteArray;
		else return new String(byteArray).getBytes("UTF-8");
	}

	/**
	 * renews the cache by deleting of the existing files corresponding to the given heterostructure.
	 *
	 * @param h is a Heterostrucuture object.
	 */
	public static void clear(Heterostructure h) {
		clear(h.getSampleNumber());
	}

	/**
	 * renews the cache by deleting of the existing files corresponding to the given heterostructure.
	 *
	 * @param h is a JspBeanHeterostructure object.
	 */
	public static void clear(JspBeanHeterostructure h) {
		clear(h.getSampleNumber());
	}
}
