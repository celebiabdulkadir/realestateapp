package com.abdulkadir.order.mapper;

import com.abdulkadir.order.dto.request.PackageRequestDTO;
import com.abdulkadir.order.dto.response.PackageResponseDTO;
import com.abdulkadir.order.model.Package;
import org.springframework.stereotype.Service;

@Service
public class PackageMapper {

    public Package toPackage(PackageRequestDTO dto) {
        return Package.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .description(dto.getDescription())
                .duration(dto.getDuration())
                .advertQuantity(dto.getAdvertQuantity())
                .build();
    }

    public PackageResponseDTO toPackageResponseDTO(Package pkg) {
        return PackageResponseDTO.builder()
                .id(pkg.getId())
                .name(pkg.getName())
                .price(pkg.getPrice())
                .description(pkg.getDescription())
                .duration(pkg.getDuration())
                .advertQuantity(pkg.getAdvertQuantity())
                .build();
    }
}