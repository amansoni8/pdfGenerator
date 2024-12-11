package com.example.pdfgenerator.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.pdfpdfgenerator.model.InvoiceRequest;
import com.itextpdf.html2pdf.HtmlConverter;

@Service
public class PdfGeneratorServiceImpl{

	
//	private final TemplateEngine templateEngine;
//	@Autowired
//	public PdfGeneratorServiceImpl(TemplateEngine templateEngine){
//		this.templateEngine = templateEngine;
//	}
//	
//
//    public String generatePdf(InvoiceRequest request) throws  IOException {
//        // Check if PDF already exists
//        String pdfName = "invoice_" + request.hashCode() + ".pdf";
//        File pdfFile = new File("invoices/" + pdfName);
//        if (pdfFile.exists()) {
//            return pdfFile.getAbsolutePath();
//        }
//
//        Context context = new Context();
//        context.setVariable("seller", request.getSeller());
//        context.setVariable("sellerGstin", request.getSellerGstin());
//        context.setVariable("sellerAddress", request.getSellerAddress());
//        context.setVariable("buyer", request.getBuyer());
//        context.setVariable("buyerGstin", request.getBuyerGstin());
//        context.setVariable("buyerAddress", request.getBuyerAddress());
//        context.setVariable("items", request.getItems());
//
//        String htmlContent = templateEngine.process("invoice-template", context);
//
//        // Generate PDF
//        pdfFile.getParentFile().mkdirs();
//        try (OutputStream os = new FileOutputStream(pdfFile)) {
//            HtmlConverter.convertToPdf(htmlContent, os);
//        }
//        return pdfFile.getAbsolutePath();
//    }
//	
//	
	
	 @Value("${pdf.storage.path:./pdfs/}")
	    private String pdfStoragePath;

	    private final TemplateEngine templateEngine;

	    public PdfGeneratorServiceImpl(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }

//	    public String generatePdf(InvoiceRequest request) {
//	        try {
//	            // Create storage directory if not exists
//	            File storageDir = new File(pdfStoragePath);
//	            if (!storageDir.exists()) {
//	                 storageDir.mkdirs();
//	            }
//
//	            // Use Thymeleaf template
//	            Context context = new Context();
//	            context.setVariable("seller", request.getSeller());
//	            context.setVariable("sellerGstin", request.getSellerGstin());
//	            context.setVariable("sellerAddress", request.getSellerAddress());
//	            context.setVariable("buyer", request.getBuyer());
//	            context.setVariable("buyerGstin", request.getBuyerGstin());
//	            context.setVariable("buyerAddress", request.getBuyerAddress());
//	            context.setVariable("items", request.getItems());
//
//	            String html = templateEngine.process("invoice", context);
//
//	            // Generate unique file name
//	            String fileName = "invoice_" + request.hashCode() + ".pdf";
//	            String filePath = pdfStoragePath + fileName;
//
//	            try (OutputStream os = new FileOutputStream(filePath)) {
//	                ITextRenderer renderer = new ITextRenderer();
//	                renderer.setDocumentFromString(html);
//	                renderer.layout();
//	                renderer.createPDF(os);
//	            }
//
//	            return fileName;
//	        } catch (Exception e) {
//	            throw new RuntimeException("Error generating PDF", e);
//	        }
//
//	    }
	    
	    public String generatePdf(InvoiceRequest request) {
	        try {
	            // Create storage directory if it doesn't exist
	            File storageDir = new File(pdfStoragePath);
	            if (!storageDir.exists()) {
	                storageDir.mkdirs();
	            }

	            // Generate a unique hash for the request
	            String uniqueHash = generateHash(request);

	            // File name based on the hash
	            String fileName = "invoice_" + uniqueHash + ".pdf";
	            String filePath = pdfStoragePath + fileName;

	            // Check if the file already exists
	            File pdfFile = new File(filePath);
	            if (pdfFile.exists()) {
	                return filePath; // Return the existing file path
	            }

	            // Use Thymeleaf template to generate the content
	            Context context = new Context();
	            context.setVariable("seller", request.getSeller());
	            context.setVariable("sellerGstin", request.getSellerGstin());
	            context.setVariable("sellerAddress", request.getSellerAddress());
	            context.setVariable("buyer", request.getBuyer());
	            context.setVariable("buyerGstin", request.getBuyerGstin());
	            context.setVariable("buyerAddress", request.getBuyerAddress());
	            context.setVariable("items", request.getItems());

	            String html = templateEngine.process("invoice", context);

	            // Generate the PDF and save it
	            try (OutputStream os = new FileOutputStream(filePath)) {
	                ITextRenderer renderer = new ITextRenderer();
	                renderer.setDocumentFromString(html);
	                renderer.layout();
	                renderer.createPDF(os);
	            }

	            return filePath; // Return the newly created file path
	        } catch (Exception e) {
	            throw new RuntimeException("Error generating PDF", e);
	        }
	    }

	    /**
	     * Generates a unique hash for the given InvoiceRequest.
	     */
	    private String generateHash(InvoiceRequest request) throws NoSuchAlgorithmException {
	        // Combine fields to create a unique string representation
	        String dataToHash = request.getSeller() + request.getSellerGstin() +
	                            request.getSellerAddress() + request.getBuyer() +
	                            request.getBuyerGstin() + request.getBuyerAddress() +
	                            request.getItems().toString();

	        // Use SHA-256 to generate a hash
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] encodedHash = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

	        // Convert the byte array to a hex string
	        StringBuilder hexString = new StringBuilder();
	        for (byte b : encodedHash) {
	            String hex = Integer.toHexString(0xff & b);
	            if (hex.length() == 1) {
	                hexString.append('0');
	            }
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    }

}