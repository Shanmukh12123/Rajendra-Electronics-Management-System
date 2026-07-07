package com.rajendraelectronics.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajendraelectronics.dto.SupplierRequestDto;
import com.rajendraelectronics.dto.SupplierResponseDto;
import com.rajendraelectronics.service.SupplierService;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping("")
    public SupplierResponseDto addSupplier(@RequestBody SupplierRequestDto supplierRequestDto) {
        return supplierService.addSupplier(supplierRequestDto);
    }

    @GetMapping("")
    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{supplierId}")
    public SupplierResponseDto getSupplierById(@PathVariable Long supplierId) {
        return supplierService.getSupplierById(supplierId);
    }

    @PutMapping("/{supplierId}")
    public SupplierResponseDto updateSupplier(@PathVariable Long supplierId, @RequestBody SupplierRequestDto dto) {
        return supplierService.updateSupplier(supplierId, dto);
    }

    @DeleteMapping("/{supplierId}")
    public String deleteSupplier(@PathVariable Long supplierId) {
        return supplierService.deleteSupplier(supplierId);
    }

}
