package de.ellersoftware.techdemo.pdf_qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.decoder.Decoder;

public class PDFQrCode 
{
    public static void main(String[] args) throws Exception {
    	System.out.println("\n-----+++++##### PDFQrCode - (c)2018 Alexander Eller Softwareloesungen #####+++++-----\n");
    	if (args.length == 1) {
	    	File pdfFile = new File(args[0]);
	    	if (pdfFile.exists() && pdfFile.isFile()) {
	    		System.out.println("die Datei [" + args[0] + "] wird analysiert...\n");
		    	System.out.println(analyzeFile(pdfFile));
	    	} else {
	    		System.out.println("die Dateiangabe [" + args[0] + "] ist keine gueltige Datei");
	    	}
    	} else {
    		System.out.println("Aufruf: java -jar PDFQrCode das-ist-ein-file.pdf\n\n");
    	}
    	System.out.println("\n... fertig\n");
    }

    public static String analyzeFile(File pdfFile) {
    	StringBuffer result = new StringBuffer();
		try (PDDocument document = PDDocument.load(pdfFile)) {
	    	PDFRenderer pdfRenderer = new PDFRenderer(document);
	    	for (int page=0; page < document.getNumberOfPages(); page++) {
	    		BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
	    		result.append(analyzePage(page, bufferedImage));
	    	}
		} catch (IOException ioex) {
			result.append("... ist offensichtlich keine gueltige PDF-Datei");
		}
    	return result.toString();
    }
    
	public static String analyzePage(int pageIndex, BufferedImage bufferedImage) 
			 throws IOException {
		
		StringBuffer resultBuffer = new StringBuffer();
		
		resultBuffer.append("analysiere Seite " + (pageIndex + 1));
        try {
		
        	LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);  
        	BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        	BitMatrix bitMatrix = bitmap.getBlackMatrix();
    	
        	Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        	hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        
        	DetectorResult[] detectorResults = new MultiDetector(bitMatrix).detectMulti(hints);
        	resultBuffer.append("\t" + detectorResults.length + " QR-Codes enthalten:");
	    	for (DetectorResult detectorResult : detectorResults) {
	    		Decoder decoder = new Decoder();
	    		DecoderResult result = decoder.decode(detectorResult.getBits());
	    		resultBuffer.append("\t\t" + result.getText());
	    	}
        } catch (ChecksumException | FormatException ex) {
        	resultBuffer.append("\tchecksum/format exception");
        	
        } catch (NotFoundException nfex) {
        	resultBuffer.append("\tkein QR-Code enthalten");
        }
        
        return resultBuffer.toString();
	}
}