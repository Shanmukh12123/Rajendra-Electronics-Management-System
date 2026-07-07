package com.rajendraelectronics.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rajendraelectronics.dto.SupplierRequestDto;
import com.rajendraelectronics.dto.SupplierResponseDto;
import com.rajendraelectronics.entity.Supplier;
import com.rajendraelectronics.repository.SupplierRepository;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierResponseDto addSupplier(SupplierRequestDto supplierRequestDto) {
        Supplier supplier = toEntity(supplierRequestDto);
        Supplier saved = supplierRepository.save(supplier);
        return toResponseDto(saved);
    }

    @Override
    public SupplierResponseDto getSupplierById(Long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId));
        return toResponseDto(supplier);
    }

    @Override
    public SupplierResponseDto updateSupplier(Long supplierId, SupplierRequestDto dto) {
        Supplier existing = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId));

        existing.setSupplierName(dto.supplierName());
        existing.setMobileNumber(dto.mobileNumber());
        existing.setEmail(dto.email());
        existing.setAddress(dto.address());

        Supplier updated = supplierRepository.save(existing);
        return toResponseDto(updated);
    }

    @Override
    public String deleteSupplier(Long supplierId) {
        if (!supplierRepository.existsById(supplierId)) {
            throw new RuntimeException("Supplier not found with id: " + supplierId);
        }
        supplierRepository.deleteById(supplierId);
        return "Supplier deleted successfully";
    }

    @Override
    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    private Supplier toEntity(SupplierRequestDto dto) {
        Supplier s = new Supplier();
        s.setSupplierName(dto.supplierName());
        s.setMobileNumber(dto.mobileNumber());
        s.setEmail(dto.email());
        s.setAddress(dto.address());
        return s;
    }

    private SupplierResponseDto toResponseDto(Supplier s) {
        return new SupplierResponseDto(s.getId(), s.getSupplierName(), s.getMobileNumber(), s.getEmail(), s.getAddress());
    }
}
