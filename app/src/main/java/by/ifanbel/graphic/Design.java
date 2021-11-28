package by.ifanbel.graphic;

import by.ifanbel.data.database.entities.Heterostructure;
import by.ifanbel.data.database.entities.Layer;
import by.ifanbel.data.database.entities.Material;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.transcoder.wmf.tosvg.WMFTranscoder;
import org.freehep.graphics2d.AbstractVectorGraphics;
import org.freehep.graphics2d.TagString;
import org.freehep.graphicsbase.util.UserProperties;
import org.freehep.graphicsio.AbstractVectorGraphicsIO;
import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.graphicsio.gif.GIFGraphics2D;
import org.freehep.graphicsio.pdf.PDFGraphics2D;
import org.freehep.graphicsio.svg.SVGGraphics2D;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;


/**
 * The class includes methods for generating of images of heterostructures in different raster and vector formats.
 */
public class Design {

	public static final String EMF = "emf";

	public static final String WMF = "wmf";

	public static final String SVF = "svf";

	public static final String PS = "ps";

	public static final String SVG = "svg";

	public static final String JPG = "jpg";

	public static final String PNG = "png";

	public static final String PDF = "pdf";

	public static final String GIF = "gif";

	private static final String SUBSCRIPT_START_TAG = "<sub>";

	private static final String SUBSCRIPT_END_TAG = "</sub>";

	private static final String SUPERSCRIPT_START_TAG = "<sup>";

	private static final String SUPERSCRIPT_END_TAG = "</sup>";

	private static final int HETEROSTRUCTURE_WIDTH = 700;

	private static final int CONDITION_SECTION_WIDTH = 600;

	private static final int IMAGE_WIDTH = 2100;

	private static final int IMAGE_HEIGHT = 2970;

	private static final int LEADER_TEXT_GAP = 70;

	private static final int MIN_FONT_SIZE = 20;

	private static final int MAX_FONT_SIZE = 50;

	private static final int LEADER_TEXT_FONT_SIZE = 40;

	private static final int MAX_LAYER_HEIGHT = 500;

	private static final int SUBSTRATE_HEIGHT = 200;

	private Heterostructure heterostructure;

	private ByteArrayOutputStream outputStream;

	private AbstractVectorGraphics graphicsIO;

	private Dimension dimension;

	private int imageHeight = IMAGE_HEIGHT;

	private int realHeight = 0; // Integer.MAX_VALUE;

	private int realWidth = HETEROSTRUCTURE_WIDTH + CONDITION_SECTION_WIDTH;

	private int leftLeaderLineLevel = 0;

	private int rightLeaderLineLevel = 0;

	private int absTopY = IMAGE_HEIGHT;

	private int absBottomY = 0;

	private int absLeftX = IMAGE_WIDTH;

	private int absRightX = 0;

	private double scaleY = 1;

	private double scaleX = 1;

	private boolean colored = true;

	boolean tightVertical = false;

	boolean drawConditions = true;

	private boolean dummyRendering = true;



	/**
	 * constructs a Design object and sets the heterostructure object which is to be drawn.
	 *
	 * @param heterostructure is the heterostructure object which is to be drawn
	 */
	public Design(Heterostructure heterostructure) {
		this(heterostructure, false, true, true);
	}

	public Design(Heterostructure heterostructure, boolean tightVertical, boolean colored, boolean drawConditions) {
		this.tightVertical = tightVertical;
		this.colored = colored;
		this.heterostructure = heterostructure;
		this.drawConditions = drawConditions;
		preliminarRendering();
	}

	public int getImageWidth() {
		return (int)dimension.getWidth();
	}

	public int getImageHeight() {
		return (int)dimension.getHeight();
	}

	private static List<String> getUnformattedText(List<String> text) {
		return text.stream()
				.map(string -> string.replace(SUBSCRIPT_START_TAG, "")
						.replace(SUBSCRIPT_END_TAG, "")
						.replace(SUPERSCRIPT_START_TAG, "")
						.replace(SUPERSCRIPT_END_TAG, ""))
				.collect(Collectors.toList());
	}

	/**
	 * produces an svg-file for the given heterostructure.
	 *
	 * @param isBase64 is a boolean which defines whether the svg-file should be encoded according to Base64 scheme.
	 * @return svg-file as a String
	 */
	public String getSvgString(boolean isBase64) {
		outputStream = new ByteArrayOutputStream();
		graphicsIO = new SVGGraphics2D(outputStream, dimension);
		exportImage();
		String svgString = outputStream.toString();
		if (isBase64) {
			try {
				return Base64.getEncoder()
						.encodeToString(svgString.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return svgString;
	}

	/**
	 * draws a heterostructure's scheme to a grafic file.
	 *
	 * @param file is a File object. The File's name should include an extension which is used to determine the file format. The following formats are supported now: "emf", "svg", "pdf", "gif", "png".
	 * @throws IOException
	 */
	public void toFile(File file) throws IOException {
		String extension = file.getName()
				.substring(file.getName()
						.lastIndexOf('.') + 1);
		OutputStream fileOutputStream = new FileOutputStream(file);
		toOutputStream(fileOutputStream, extension);
	}

	/**
	 * draws a heterostructure's scheme to an OutputStream
	 * @param outputStream
	 * @param extension determines a type of the image. The "emf", "svg", "pdf", "gif" and "png" are acceptable.
	 * @throws IOException
	 */
	public void toOutputStream(OutputStream outputStream, String extension) throws IOException {

		switch (extension) {
			case EMF:
				graphicsIO = new EMFGraphics2D(outputStream, dimension);
				exportImage();
				break;
			case SVG:
				graphicsIO = new SVGGraphics2D(outputStream, dimension);
				exportImage();
				break;
			case PDF:
				graphicsIO = new PDFGraphics2D(outputStream, dimension);
				exportImage();
				break;
			case GIF:
				graphicsIO = new GIFGraphics2D(outputStream, dimension);
				exportImage();
				break;
			case PNG:
				ByteArrayOutputStream svgOutputStream = new ByteArrayOutputStream();
			//	OutputStream pngFileOutputStream = new FileOutputStream(outputStream);
				ByteArrayOutputStream pngArrayOutputStream = new ByteArrayOutputStream();
				try {
					graphicsIO = new SVGGraphics2D(svgOutputStream, dimension);
					exportImage();
					String svgString = svgOutputStream.toString();
					TranscoderInput transcoderInput = new TranscoderInput(new ByteArrayInputStream(svgString.getBytes("UTF-8")));
					TranscoderOutput transcoderOutput = new TranscoderOutput(pngArrayOutputStream);
					PNGTranscoder pngTranscoder = new PNGTranscoder();
					try {
						pngTranscoder.transcode(transcoderInput, transcoderOutput);
						outputStream.write(pngArrayOutputStream.toByteArray());
						outputStream.flush();
					} catch (TranscoderException e) {
						e.printStackTrace();
					}
				} finally {
					svgOutputStream.close();
					outputStream.close();
					pngArrayOutputStream.close();
				}
				break;
			default:
		}
	}

	private void preliminarRendering() {
		dimension = new Dimension(IMAGE_WIDTH, imageHeight);
		OutputStream outputStream = new ByteArrayOutputStream();
		graphicsIO = new SVGGraphics2D(outputStream, dimension);
		dummyRendering = true;
		renderImage();
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (tightVertical) {
			imageHeight = (int)(realHeight*1.1*scaleX);
			dimension = new Dimension(IMAGE_WIDTH, imageHeight);
		}
	}

	private void exportImage() {
		dummyRendering = false;
		renderImage();
	}

	private void renderImage() {
		int leftX = 0;
		int layerTopY = 0;
		int conditionTopY = layerTopY;
		leftLeaderLineLevel = 0;
		rightLeaderLineLevel = 0;
		if (!dummyRendering) {
			UserProperties prop = new UserProperties();
			prop.setProperty(AbstractVectorGraphicsIO.TEXT_AS_SHAPES, false);
			graphicsIO.startExport();
			graphicsIO.setProperties(prop);
			graphicsIO.scale(scaleX, scaleX);
			double shiftX = (IMAGE_WIDTH - realWidth*scaleX)*0.5 - absLeftX;
			double shiftY = (imageHeight - realHeight*scaleX)*0.5 - absTopY;
			graphicsIO.translate(shiftX, shiftY);
		}
		for (int i = heterostructure.layersNumber() - 1; i >= 0; i--) {
			int layerHeight = drawLayer(heterostructure.getLayer(i), leftX, layerTopY);
			layerTopY += layerHeight;
			if ((i == 0) || (!heterostructure.getLayer(i)
					.isSameFlowAndTemperature(heterostructure.getLayer(i - 1)))) {
				int conditionHeight = layerTopY - conditionTopY;
				if (drawConditions) {
					drawConditionSection(heterostructure.getLayer(i), leftX + HETEROSTRUCTURE_WIDTH, conditionTopY, conditionHeight);
					conditionTopY = layerTopY;
				}
			}
		}
		if (!dummyRendering) {
			drawSubstrate(leftX, layerTopY);
			graphicsIO.endExport();
		}
		absBottomY = Math.max(absBottomY, layerTopY + SUBSTRATE_HEIGHT);
		realWidth = absRightX - absLeftX;
		scaleX = Math.min( (double)IMAGE_WIDTH / realWidth, 1);
		scaleY = Math.min((double)IMAGE_HEIGHT / (absBottomY - absTopY), 1);
		realHeight = getPxSize(absBottomY - absTopY);
	}

	private void drawSubstrate(int leftX, int topY) {
		Rectangle substrate = new Rectangle(leftX, topY, HETEROSTRUCTURE_WIDTH, SUBSTRATE_HEIGHT);
		graphicsIO.setPaint(new Color(230, 230, 230));
		graphicsIO.fill(substrate);
		graphicsIO.setPaint(Color.black);
		graphicsIO.draw(substrate);
		List<String> text = new ArrayList<>();
		if ((heterostructure.getSubstrateMaterial() != null) && (!heterostructure.getSubstrateMaterial()
				.trim()
				.isEmpty())) text.add(heterostructure.getSubstrateMaterial());
		if ((heterostructure.getWaferSize() != null) && (!heterostructure.getWaferSize()
				.trim()
				.isEmpty())) text.add(heterostructure.getWaferSize());
		drawText(substrate, text, true);
		absBottomY = Math.max(absBottomY, topY + SUBSTRATE_HEIGHT);
	}

	private int drawLayer(Layer layer, int leftX, int topY) {
		int layerHeight = getPxSize(layer.getThickness());
		boolean brokenHeight = false;
		if (layerHeight > MAX_LAYER_HEIGHT) {
			layerHeight = MAX_LAYER_HEIGHT + (getPxSize(layer.getThickness()) - MAX_LAYER_HEIGHT) / 5;
			brokenHeight = true;
		}
		int bottomY = topY + layerHeight; // bottom level of the layer
		int rightX = leftX + HETEROSTRUCTURE_WIDTH; // right side of the layer
		Rectangle rectangle = new Rectangle(leftX, topY, HETEROSTRUCTURE_WIDTH, layerHeight);
		absTopY = Math.min(absTopY, topY);
		absBottomY = Math.max(absBottomY, topY + layerHeight);
		absLeftX = Math.min(absLeftX, leftX);
		absRightX = Math.max(absRightX, rightX);
		if (!dummyRendering) {
			Paint lgp;
			if (!colored) lgp = Color.white;
			else {
				Color[] colors = {LayerColor.get(layer.getStartMaterial()), LayerColor.get(layer.getStopMaterial())};
				if (layer.isGradientContent())
					lgp = new GradientPaint(leftX, bottomY, colors[0], leftX, topY - 1, colors[1]);
				else lgp = LayerColor.get(layer.getStartMaterial());
			}
			graphicsIO.setPaint(lgp);
			graphicsIO.fill(rectangle);
			graphicsIO.setPaint(Color.BLACK);
			if (brokenHeight) {
				graphicsIO.drawLine(leftX, topY, rightX, topY);
				graphicsIO.drawLine(leftX, bottomY, rightX, bottomY);
				graphicsIO.drawLine(leftX, topY, leftX, (topY + bottomY) / 2 - MAX_LAYER_HEIGHT / 40);
				graphicsIO.drawLine(leftX, (topY + bottomY) / 2 + MAX_LAYER_HEIGHT / 40, leftX, bottomY);
				graphicsIO.drawLine(leftX - MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 + MAX_LAYER_HEIGHT / 40, leftX + MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 - 2 * MAX_LAYER_HEIGHT / 40);
				graphicsIO.drawLine(leftX - MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 + 2 * MAX_LAYER_HEIGHT / 40, leftX + MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 - MAX_LAYER_HEIGHT / 40);
				graphicsIO.drawLine(rightX, topY, rightX, (topY + bottomY) / 2 - MAX_LAYER_HEIGHT / 40);
				graphicsIO.drawLine(rightX, (topY + bottomY) / 2 + MAX_LAYER_HEIGHT / 40, rightX, bottomY);
				graphicsIO.drawLine(rightX - MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 + MAX_LAYER_HEIGHT / 40, rightX + MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 - 2 * MAX_LAYER_HEIGHT / 40);
				graphicsIO.drawLine(rightX - MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 + 2 * MAX_LAYER_HEIGHT / 40, rightX + MAX_LAYER_HEIGHT / 20, (topY + bottomY) / 2 - MAX_LAYER_HEIGHT / 40);
			} else {
				graphicsIO.draw(rectangle);
			}
		}
		drawText(rectangle, getLayerText(layer), true);
		return layerHeight;
	}

	private void drawConditionSection(Layer layer, int leftX, int topY, int height) {
		Rectangle rectangle = new Rectangle(leftX, topY, CONDITION_SECTION_WIDTH, height);
		absRightX = Math.max(absRightX, leftX + CONDITION_SECTION_WIDTH);
		drawText(rectangle, getConditionText(layer), false);
		if (!dummyRendering) {
			graphicsIO.setPaint(Color.BLACK);
			graphicsIO.draw(rectangle);
		}
	}

	private void drawText(Rectangle rectangle, List<String> text, boolean leadToLeft) {
		if ((text == null) || (text.size() < 1)) return;
		int fontSize = MAX_FONT_SIZE;
		while (fontSize > MIN_FONT_SIZE) {
			boolean labeled;
			Font font = new Font("Arial", java.awt.Font.PLAIN, fontSize);
			for (int numberOfLines = 0; numberOfLines < 4; numberOfLines++) {
				labeled = arrangeTextIntoRectangle(rectangle, ListToLines(numberOfLines, text), font);
				if (labeled) return;
			}
			fontSize--;
		}
		Font font = new Font("Arial", java.awt.Font.PLAIN, LEADER_TEXT_FONT_SIZE);
		drawLeaderText(rectangle, ListToLines(1, text), font, leadToLeft);
	}

	private boolean arrangeTextIntoRectangle(Rectangle rectangle, List<String> text, Font font) {
		int leftX = rectangle.x;
		int topY = rectangle.y;
		int width = rectangle.width;
		int height = rectangle.height;
		java.util.List<String> unformattedText = getUnformattedText(text);
		int lineQuantity = text.size();
		int textHeight = getTextHeight(text, font);
		int maxWidth = getTextWidth(text, font);
		int lineHeight = textHeight / lineQuantity;
		List<Integer> textWidth = unformattedText.stream()
				.map(string -> graphicsIO.getFontMetrics(font)
						.stringWidth(string))
				.collect(Collectors.toList());
		if ((maxWidth <= width) && (textHeight <= height)) {
			int yFirstLine = topY + (int) ((height - textHeight) * 0.5) + graphicsIO.getFontMetrics(font)
					.getAscent();
			for (int i = 0; i < text.size(); i++) {
				int x = leftX + (int) ((width - textWidth.get(i)) * 0.5);
				int y = yFirstLine + i * lineHeight;
				if (!dummyRendering) {
					graphicsIO.setFont(font);
					graphicsIO.setPaint(Color.BLACK);
					TagString tagString = new TagString(text.get(i));
					graphicsIO.drawString(tagString, x, y);
				}
			}
			return true;
		}
		return false;
	}

	private void drawLeaderText(Rectangle rectangle, List<String> text, Font font, boolean leadToLeft) {
		int y = rectangle.y + rectangle.height / 2;
		int textHeight = getTextHeight(text, font);
		int maxWidth = getTextWidth(text, font);
		int topY = y - textHeight;
		int leftX = rectangle.x - maxWidth - LEADER_TEXT_GAP;
		int xLayerEdge = rectangle.x;
		int xTextEdge = leftX + maxWidth;
		if (leadToLeft) {
			topY = Math.max(topY, leftLeaderLineLevel);
			leftLeaderLineLevel = topY + (int) (textHeight * 1.2);
			absLeftX = Math.min(absLeftX, leftX);
		} else {
			leftX = rectangle.x + rectangle.width + LEADER_TEXT_GAP;
			xLayerEdge = rectangle.x + rectangle.width;
			xTextEdge = leftX;
			topY = Math.max(topY, rightLeaderLineLevel);
			rightLeaderLineLevel = topY + (int) (textHeight * 1.2);
			absRightX = Math.max(absRightX, leftX + maxWidth);
		}
		Rectangle textRectangle = new Rectangle(leftX, topY, maxWidth, textHeight);
		absTopY = Math.min(absTopY, topY);
		absBottomY = Math.max(absBottomY, topY + textHeight);
		arrangeTextIntoRectangle(textRectangle, text, font);
		if (!dummyRendering) {
			graphicsIO.setPaint(Color.BLACK);
			graphicsIO.drawLine(leftX, topY + textHeight, leftX + maxWidth, topY + textHeight);
			graphicsIO.drawLine(xTextEdge, topY + textHeight, xLayerEdge, y);
		}
	}

	private int getTextHeight(List<String> text, Font font) {
		java.util.List<String> unformattedText = getUnformattedText(text);
		int lineQuantity = unformattedText.size();
		return (int) (graphicsIO.getFontMetrics(font)
				.getHeight() * lineQuantity * 1.3);
	}

	private int getTextWidth(List<String> text, Font font) {
		java.util.List<String> unformattedText = getUnformattedText(text);
		List<Integer> textWidth = unformattedText.stream()
				.map(string -> graphicsIO.getFontMetrics(font)
						.stringWidth(string))
				.collect(Collectors.toList());
		return (int) (Collections.max(textWidth) * 1.1);
	}

	private String NumberToString(Number number, int decimalDigits) {
		StringBuilder pattern = new StringBuilder();
		pattern.append("#.");
		for (int i = 0; i < decimalDigits; i++) pattern.append("#");
		return new DecimalFormat(pattern.toString(), new DecimalFormatSymbols(Locale.US)).format(number);
	}

	private List<String> ListToLines(int numberOfLines, List<String> text) {
		if (numberOfLines == 0) return text;
		List result = new ArrayList<>();
		int textSize = text.size();
		int lineSize = textSize / numberOfLines;
		int rest = textSize % numberOfLines;
		int cursor = 0;
		for (int i = 0; i < numberOfLines; i++) {
			StringBuilder s = new StringBuilder();
			int thisLineSize = lineSize;
			if (rest > 0) {
				thisLineSize++;
				rest--;
			}
			for (int c = cursor; c < cursor + thisLineSize; c++) {
				if (c != cursor) s.append(", ");
				s.append(text.get(c));
			}
			cursor += thisLineSize;
			result.add(s.toString());
			if (cursor >= textSize) break;
		}
		return result;
	}

	private int getPxSize(double size) {
		return (int) (size * scaleY);
	}

	private List<String> getLayerText(Layer layer) {
		Material startMaterial = layer.getStartMaterial();
		Material stopMaterial = layer.getStopMaterial();
		double thickness = layer.getThickness();
		boolean nonAlInGaNlayer = false;
		String comment = layer.getComment();
		if (comment.startsWith("!")) nonAlInGaNlayer = true;
		comment = comment.replace("!", "").trim();
		List<String> res = new ArrayList<>();
		if (!comment.trim()
					.isEmpty()) res.add(comment);
		if (!nonAlInGaNlayer) {
			StringBuilder chemFormula = new StringBuilder();
			List<String> gradient = new ArrayList<>();
			if ((startMaterial.getxAl() > 0) || (stopMaterial.getxAl() > 0)) {
				chemFormula.append("Al");
				if (startMaterial.getxAl() != stopMaterial.getxAl()) {
					chemFormula.append(subscript("x"));
					gradient.add("x=" + NumberToString(startMaterial.getxAl(), 2) + "-" + NumberToString(stopMaterial.getxAl(), 2));
				} else if (startMaterial.getxAl() < 1)
					chemFormula.append(subscript(NumberToString(startMaterial.getxAl(), 2)));
			}
			if ((startMaterial.getyIn() > 0) || (stopMaterial.getyIn() > 0)) {
				chemFormula.append("In");
				if (startMaterial.getyIn() != stopMaterial.getyIn()) {
					chemFormula.append(subscript("y"));
					gradient.add("y=" + NumberToString(startMaterial.getyIn(), 2) + "-" + NumberToString(stopMaterial.getyIn(), 2));
				} else if (startMaterial.getyIn() < 1)
					chemFormula.append(subscript(NumberToString(startMaterial.getyIn(), 2)));
			}
			if ((1 - startMaterial.getxAl() - startMaterial.getyIn() > 0) || (1 - stopMaterial.getxAl() - stopMaterial.getyIn() > 0)) {
				chemFormula.append("Ga");
				if ((startMaterial.getxAl() != stopMaterial.getxAl()) && (startMaterial.getyIn() != stopMaterial.getyIn()))
					chemFormula.append(subscript("1-x-y"));
				if ((startMaterial.getxAl() == stopMaterial.getxAl()) && (startMaterial.getyIn() == stopMaterial.getyIn()))
					if ((1 - startMaterial.getxAl() - startMaterial.getyIn()) < 1)
						chemFormula.append(subscript(NumberToString(1 - startMaterial.getxAl() - startMaterial.getyIn(), 2)));
				if ((startMaterial.getxAl() != stopMaterial.getxAl()) && (startMaterial.getyIn() == stopMaterial.getyIn()))
					chemFormula.append(subscript(NumberToString(1 - startMaterial.getyIn(), 2)))
							   .append(subscript("-x"));
				if ((startMaterial.getxAl() == stopMaterial.getxAl()) && (startMaterial.getyIn() != stopMaterial.getyIn()))
					chemFormula.append(subscript(NumberToString(1 - startMaterial.getxAl(), 2)))
							   .append(subscript("-y"));
			}
			chemFormula.append("N");
			chemFormula.append(startMaterial.getDopant()
											.getDescription());
			if (startMaterial.getDopant() != stopMaterial.getDopant()) chemFormula.append(stopMaterial.getDopant()
																									  .getDescription());
			res.add(chemFormula.toString());
			res.addAll(gradient);
		}
		StringBuilder thickStr = new StringBuilder();
		thickStr.append(NumberToString(thickness, 2))
				.append(" nm");
		res.add(thickStr.toString());
		return res;
	}

	private List<String> getConditionText(Layer layer) {
		List<String> res = new ArrayList<>();
		Material startMaterial = layer.getStartMaterial();
		Material stopMaterial = layer.getStopMaterial();
		StringBuilder temperature = new StringBuilder();
		StringBuilder substrateHeat = new StringBuilder();
		StringBuilder nitrogenFlow = new StringBuilder();
		temperature.append("T = ")
				.append(NumberToString(startMaterial.getTemperature(), 1));
		if (startMaterial.getTemperature() != stopMaterial.getTemperature()) temperature.append(" - ")
				.append(NumberToString(stopMaterial.getTemperature(), 1));
		temperature.append(superscript("o"))
				.append("C");
		substrateHeat.append(NumberToString(startMaterial.getSubstrateHeat(), 1));
		if (startMaterial.getSubstrateHeat() != stopMaterial.getSubstrateHeat()) substrateHeat.append(" - ")
				.append(NumberToString(stopMaterial.getSubstrateHeat(), 1));
		substrateHeat.append("%");
		nitrogenFlow.append(startMaterial.getNitrogenType()
				.getDescription())
				.append("=")
				.append(NumberToString(startMaterial.getNitrogenFlow(), 1));
		if (startMaterial.getNitrogenFlow() != stopMaterial.getNitrogenFlow()) temperature.append(" - ")
				.append(NumberToString(stopMaterial.getNitrogenFlow(), 1));
		nitrogenFlow.append(" sccm");
		res.add(temperature.toString());
		res.add(substrateHeat.toString());
		res.add(nitrogenFlow.toString());
		return res;
	}

	private String subscript(String s) {
		return SUBSCRIPT_START_TAG.concat(s)
				.concat(SUBSCRIPT_END_TAG);
	}

	private String superscript(String s) {
		return SUPERSCRIPT_START_TAG.concat(s)
				.concat(SUPERSCRIPT_END_TAG);
	}
}

