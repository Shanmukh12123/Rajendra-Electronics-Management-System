package com.rajendraelectronics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.dto.RecentSaleDto;
import com.rajendraelectronics.dto.SalePaymentDto;
import com.rajendraelectronics.dto.SaleRequestDto;
import com.rajendraelectronics.dto.SaleResponseDto;
import com.rajendraelectronics.service.InvoicePdfService;
import com.rajendraelectronics.service.SaleService;

import java.io.ByteArrayInputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/sales")
public class SaleController {
	
	private final SaleService saleService;
	
	private final InvoicePdfService invoicePdfService;
	

	public SaleController(SaleService saleService, InvoicePdfService invoicePdfService) {
		super();
		this.saleService = saleService;
		this.invoicePdfService = invoicePdfService;
	}
	
	@PostMapping
	public SaleResponseDto createSale(@RequestBody SaleRequestDto dto) {
		return saleService.createSale(dto);
	}
	
	@PutMapping("/{saleId}/payment")
	public SaleResponseDto updateSalePayment(
	        @PathVariable Long saleId,
	        @RequestBody SalePaymentDto dto) {

	    return saleService.updateSalePayment(saleId, dto);
	}
	
	@GetMapping("/pending")
	public List<SaleResponseDto> getPendingSales() {
	    return saleService.getPendingSales();
	}
	
	@GetMapping("/recent")
	public List<RecentSaleDto> getRecentSales() {
	    return saleService.getRecentSales();
	}
	
	@GetMapping("/{saleId}/invoice")
	public ResponseEntity<InputStreamResource> downloadInvoice(
	        @PathVariable Long saleId) {

	    ByteArrayInputStream pdf =
	            invoicePdfService.generateInvoice(saleId);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition",
	            "inline; filename=invoice.pdf");

	    return ResponseEntity
	            .ok()
	            .headers(headers)
	            .contentType(MediaType.APPLICATION_PDF)
	            .body(new InputStreamResource(pdf));
	}
}
