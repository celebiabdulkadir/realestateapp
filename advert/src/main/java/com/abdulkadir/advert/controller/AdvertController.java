package com.abdulkadir.advert.controller;

import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.service.AdvertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @GetMapping
    public List<AdvertResponseDTO> getAll() {
        return advertService.getAll();
    }

    @GetMapping("/adverts/{id}")
    public long getAllByUserId(@PathVariable("id") Long userId) {
        return advertService.getAllByUserId(userId);
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        advertService.delete(id);
        return ResponseEntity.ok().build();
    }
}
