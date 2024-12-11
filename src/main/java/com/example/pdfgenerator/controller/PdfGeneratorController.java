package com.example.pdfgenerator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pdfgenerator.service.PdfGeneratorServiceImpl;
import com.example.pdfpdfgenerator.model.InvoiceRequest;
import com.itextpdf.io.exceptions.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfGeneratorController {
	@Autowired
	private final PdfGeneratorServiceImpl pdfService;
	PdfGeneratorController(PdfGeneratorServiceImpl pdfService) {
        this.pdfService = pdfService;
    }
	

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequest request) {
        String filePath = pdfService.generatePdf(request);
        return ResponseEntity.ok(filePath);
    }

}
