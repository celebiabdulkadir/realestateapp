package com.abdulkadir.order.controller;

import com.abdulkadir.order.dto.request.PackageRequestDTO;
import com.abdulkadir.order.dto.response.PackageResponseDTO;
import com.abdulkadir.order.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    @Autowired
    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @PostMapping
    public ResponseEntity<PackageResponseDTO> createPackage(@RequestBody @Valid PackageRequestDTO packageRequestDTO) {
        PackageResponseDTO createdPackage = packageService.createPackage(packageRequestDTO);
        return ResponseEntity.ok(createdPackage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> getPackageById(@PathVariable Long id) {
        PackageResponseDTO packageResponseDTO = packageService.getPackageById(id);
        return ResponseEntity.ok(packageResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PackageResponseDTO>> getAllPackages() {
        List<PackageResponseDTO> packages = packageService.getAllPackages();
        return ResponseEntity.ok(packages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> updatePackage(@PathVariable Long id, @RequestBody @Valid PackageRequestDTO packageRequestDTO) {
        PackageResponseDTO updatedPackage = packageService.updatePackage(id, packageRequestDTO);
        return ResponseEntity.ok(updatedPackage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.ok().build();
    }
}