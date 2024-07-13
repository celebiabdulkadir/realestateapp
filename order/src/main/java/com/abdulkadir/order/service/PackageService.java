package com.abdulkadir.order.service;

import com.abdulkadir.order.dto.request.PackageRequestDTO;
import com.abdulkadir.order.dto.response.PackageResponseDTO;
import com.abdulkadir.order.mapper.PackageMapper;
import com.abdulkadir.order.model.Package;
import com.abdulkadir.order.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PackageService {

    @Autowired
    private PackageRepository packageRepository;

    @Autowired
    private PackageMapper packageMapper;

    public PackageResponseDTO createPackage(PackageRequestDTO packageRequestDTO) {
        Package pkg = packageMapper.toPackage(packageRequestDTO);
        pkg = packageRepository.save(pkg);
        return packageMapper.toPackageResponseDTO(pkg);
    }

    public PackageResponseDTO updatePackage(Long id, PackageRequestDTO packageRequestDTO) {
        Package pkg = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        pkg.setName(packageRequestDTO.getName());
        pkg.setPrice(packageRequestDTO.getPrice());
        pkg.setDescription(packageRequestDTO.getDescription());
        pkg.setDuration(packageRequestDTO.getDuration());
        pkg.setAdvertQuantity(packageRequestDTO.getAdvertQuantity());
        pkg = packageRepository.save(pkg);
        return packageMapper.toPackageResponseDTO(pkg);
    }

    public PackageResponseDTO getPackageById(Long id) {
        Package pkg = packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found"));
        return packageMapper.toPackageResponseDTO(pkg);
    }

    public List<PackageResponseDTO> getAllPackages() {
        return packageRepository.findAll().stream()
                .map(packageMapper::toPackageResponseDTO)
                .collect(Collectors.toList());
    }

    public void deletePackage(Long id) {
        packageRepository.deleteById(id);
    }
}