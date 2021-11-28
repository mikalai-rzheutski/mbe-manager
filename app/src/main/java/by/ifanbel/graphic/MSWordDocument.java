package by.ifanbel.graphic;

import by.ifanbel.data.database.entities.Heterostructure;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.*;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

public class MSWordDocument {

//	private Heterostructure heterostructure;

	private List<Heterostructure> heterostructures;
	private boolean colored;

	public MSWordDocument(Heterostructure heterostructure, boolean colored) {
		this(Arrays.asList(heterostructure), colored);
	}

	public MSWordDocument(Collection<Heterostructure> heterostructures, boolean colored) {
		this.heterostructures = new ArrayList<>(heterostructures);
		this.colored = colored;
	}

	public void getDocument(OutputStream out) throws IOException, InvalidFormatException {
		XWPFDocument document = new XWPFDocument();
		CTDocument1 doc = document.getDocument();
		CTBody body = doc.getBody();
		CTSectPr section = body.addNewSectPr();
		CTPageSz pageSize;
		if (section.isSetPgSz()) {
			pageSize = section.getPgSz();
		} else {
			pageSize = section.addNewPgSz();
		}
		pageSize.setOrient(STPageOrientation.LANDSCAPE);
		pageSize.setW(BigInteger.valueOf(16840));
		pageSize.setH(BigInteger.valueOf(11900));

		for (Heterostructure heterostructure: heterostructures) {
			//create table
			XWPFTable table = document.createTable();
			table.getCTTbl().addNewTblPr().addNewTblLayout().setType(STTblLayoutType.FIXED);
			table.setCellMargins(70, 150, 70, 150);
			//create first row
			XWPFTableRow tableRowOne = table.getRow(0);
			XWPFTableCell cell00 = tableRowOne.getCell(0);
			CTTblWidth cellWidth00 = cell00.getCTTc().addNewTcPr().addNewTcW();
			cellWidth00.setType(STTblWidth.DXA);
			cellWidth00.setW(BigInteger.valueOf(20*400 + 300));
			XWPFTableCell cell01 = tableRowOne.addNewTableCell();
			CTTblWidth cellWidth01 = cell01.getCTTc().addNewTcPr().addNewTcW();
			cellWidth01.setType(STTblWidth.DXA);
			cellWidth01.setW(BigInteger.valueOf(20*150));
			XWPFTableCell cell02 = tableRowOne.addNewTableCell();
			CTTblWidth cellWidth02 = cell02.getCTTc().addNewTcPr().addNewTcW();
			cellWidth02.setType(STTblWidth.DXA);
			cellWidth02.setW(BigInteger.valueOf(20*150));
			//create second row
			XWPFTableRow tableRowTwo = table.createRow();
			tableRowTwo.setHeight(20*370);
			XWPFTableCell cell10 = tableRowTwo.getCell(0);
			cell10.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
			XWPFTableCell cell11 = tableRowTwo.getCell(1);
			XWPFTableCell cell12 = tableRowTwo.getCell(2);
			//create third row
			XWPFTableRow tableRowThree = table.createRow();
			tableRowThree.setHeight(20*15);
			XWPFTableCell cell20 = tableRowThree.getCell(0);
			XWPFTableCell cell21 = tableRowThree.getCell(1);
			XWPFTableCell cell22 = tableRowThree.getCell(2);
			//create fourth row
			XWPFTableRow tableRowFour = table.createRow();
			tableRowFour.setHeight(20*15);
			XWPFTableCell cell30 = tableRowFour.getCell(0);
			XWPFTableCell cell31 = tableRowFour.getCell(1);
			CTTblWidth cellWidth31 = cell31.getCTTc().addNewTcPr().addNewTcW();
			cellWidth31.setType(STTblWidth.DXA);
			cellWidth31.setW(BigInteger.valueOf(20*200));
			XWPFTableCell cell32 = tableRowFour.getCell(2);
			CTTblWidth cellWidth32 = cell32.getCTTc().addNewTcPr().addNewTcW();
			cellWidth32.setType(STTblWidth.DXA);
			cellWidth32.setW(BigInteger.valueOf(20*100));
			//merge the cells
			cell10.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
			cell20.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			cell30.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
			cell01.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			cell02.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			cell11.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			cell12.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			cell21.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
			cell22.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
			//fill-in the table
			XWPFParagraph p00 = cell00.getParagraphs().get(0);
			p00.setSpacingAfter(0);
			p00.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r00 = p00.insertNewRun(0);
			r00.setBold(true);
			r00.setFontSize(13);
			r00.setText(heterostructure.getSampleNumber());
			XWPFRun r00a = p00.createRun();
			r00a.setFontSize(13);
			r00a.setText(" - " + heterostructure.getDescription());
			XWPFParagraph p01 = cell01.getParagraphs().get(0);
			p01.setSpacingAfter(0);
			p01.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r01 = p01.createRun();
			r01.setBold(true);
			r01.setFontSize(13);
			r01.setText("Description");
			//insert image
			double imageCellHeight = (tableRowTwo.getHeight() + tableRowThree.getHeight() + tableRowFour.getHeight())/20;
			double imageCellWidth = 400;
			XWPFParagraph designImage = cell10.getParagraphs().get(0);
			designImage.setAlignment(ParagraphAlignment.CENTER);
			Design design = new Design(heterostructure, true, colored, true);
			OutputStream os = new ByteArrayOutputStream();
			design.toOutputStream(os, Design.PNG);
			InputStream is = new ByteArrayInputStream(((ByteArrayOutputStream) os).toByteArray());
			double width = imageCellWidth;
			double height = imageCellHeight;
			double imageWidth = design.getImageWidth();
			double imageHeight = design.getImageHeight();
			if ( imageHeight/imageWidth > imageCellHeight/imageCellWidth) width = height*imageWidth/imageHeight;
			else height = width*imageHeight/imageWidth;
			XWPFRun imageRun = designImage.createRun();
			imageRun.addPicture(is, Document.PICTURE_TYPE_PNG, "design.png", Units.toEMU(width), Units.toEMU(height));
			imageRun.getCTR().getDrawingArray(0).getInlineArray(0).addNewCNvGraphicFramePr().addNewGraphicFrameLocks().setNoChangeAspect(true);
			os.close();
			is.close();
			//fill-in the table
			String[] commentParagraphs = heterostructure.getComments().split("\n");
			IntStream.range(0, commentParagraphs.length).forEach(
					i -> {
						XWPFParagraph paragraph = cell11.addParagraph();
						paragraph.setAlignment(ParagraphAlignment.BOTH);
						paragraph.createRun().setText(commentParagraphs[i]);
					}
			);
			XWPFParagraph p21 = cell21.getParagraphs().get(0);
			p21.setAlignment(ParagraphAlignment.LEFT);
			p21.setSpacingAfter(0);
			XWPFRun r21 = p21.createRun();
			r21.setFontSize(13);
			r21.setBold(true);
			r21.setText("Originated by: ");
			XWPFRun r21a = p21.createRun();
			r21a.setFontSize(13);
			r21a.setText(heterostructure.getGrowersLastNames());
			XWPFParagraph p20 = cell20.getParagraphs().get(0);
			p20.setSpacingAfter(0);
			XWPFParagraph p31 = cell31.getParagraphs().get(0);
			p31.setSpacingAfter(0);
			p31.setAlignment(ParagraphAlignment.LEFT);
			XWPFRun r31 = p31.createRun();
			r31.setBold(true);
			r31.setFontSize(13);
			r31.setText("Date: ");
			XWPFRun r31a = p31.createRun();
			r31a.setFontSize(13);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
			r31a.setText(simpleDateFormat.format(heterostructure.getDate()));
			XWPFParagraph p32 = cell32.getParagraphs().get(0);
			p32.setSpacingAfter(0);
			p32.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun r32 = p32.createRun();
			r32.setBold(true);
			r32.setFontSize(13);
			r32.setText(heterostructure.getSampleNumber());
			// insert page break
			XWPFParagraph paragraph = document.createParagraph();
			XWPFRun run = paragraph.createRun();
			run.addBreak(BreakType.PAGE);
		}


		document.write(out);
	}
}
