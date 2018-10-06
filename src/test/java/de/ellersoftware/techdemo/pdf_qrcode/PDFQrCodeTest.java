package de.ellersoftware.techdemo.pdf_qrcode;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;

import org.junit.Test;


public class PDFQrCodeTest { 

	@Test
	public void testDocMit1QRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Doc_Mit_1QRCode.pdf")),
				   equalTo("analysiere Seite 1\t1 QR-Codes enthalten:\t\thttps://www.eller-software.de")); 
    }

	@Test
	public void testDocMit2QRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Doc_Mit_2QRCode.pdf")),
				   equalTo("analysiere Seite 1\t2 QR-Codes enthalten:\t\thttps://www.eller-software.de\t\teine Technologiedemo von https://www.eller-software.de")); 
    }

	@Test
	public void testDocOhneQRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Doc_Ohne_QRCode.pdf")),
				   equalTo("analysiere Seite 1\tkein QR-Code enthalten")); 
    }

	@Test
	public void testScanMit1QRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Scan_Mit_1QRCode.pdf")),
				   equalTo("analysiere Seite 1\t1 QR-Codes enthalten:\t\thttps://www.eller-software.de")); 
    }

	@Test
	public void testScanMit2QRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Scan_Mit_2QRCode.pdf")),
				equalTo("analysiere Seite 1\t2 QR-Codes enthalten:\t\thttps://www.eller-software.de\t\teine Technologiedemo von https://www.eller-software.de")); 
    }

	@Test
	public void testScanOhneQRCode() {
		assertThat(PDFQrCode.analyzeFile(new File("./src/test/resources/Scan_Ohne_QRCode.pdf")),
				equalTo("analysiere Seite 1\tkein QR-Code enthalten")); 
    }

}

     
