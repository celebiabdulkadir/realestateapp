package com.abdulkadir.advert.controller;

import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.service.AdvertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @GetMapping
    public List<AdvertResponseDTO> getAll() {
        return advertService.getAll();
    }

    @GetMapping("/{id}")
    public AdvertResponseDTO getById(@PathVariable Long id) {
        return advertService.getById(id);
    }

    @PostMapping
    public AdvertResponseDTO create(@RequestBody @Valid AdvertRequestDTO advertRequestDTO) {
        return advertService.create(advertRequestDTO);
    }
    @PutMapping("/{id}")
    public AdvertResponseDTO update(@PathVariable Long id, @RequestBody @Valid AdvertRequestDTO advertRequestDTO) {
        return advertService.update(id, advertRequestDTO);
    }
}
