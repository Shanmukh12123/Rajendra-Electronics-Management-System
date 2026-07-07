package com.rajendraelectronics.service;

import java.util.List;

import com.rajendraelectronics.dto.SupplierRequestDto;
import com.rajendraelectronics.dto.SupplierResponseDto;

public interface SupplierService {

    SupplierResponseDto addSupplier(SupplierRequestDto supplierRequestDto);

    SupplierResponseDto getSupplierById(Long supplierId);

    SupplierResponseDto updateSupplier(Long supplierId, SupplierRequestDto dto);

    String deleteSupplier(Long supplierId);

    List<SupplierResponseDto> getAllSuppliers();
}
