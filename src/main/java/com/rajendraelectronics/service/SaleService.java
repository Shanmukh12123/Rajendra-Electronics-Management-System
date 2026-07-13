package com.rajendraelectronics.service;

import java.util.List;

import com.rajendraelectronics.dto.RecentSaleDto;
import com.rajendraelectronics.dto.SalePaymentDto;
import com.rajendraelectronics.dto.SaleRequestDto;
import com.rajendraelectronics.dto.SaleResponseDto;

public interface SaleService {
	
	SaleResponseDto createSale(SaleRequestDto dto);
	
	SaleResponseDto updateSalePayment(
	        Long saleId,
	        SalePaymentDto dto);
	
	List<SaleResponseDto> getPendingSales();
	
	List<RecentSaleDto> getRecentSales();

}
