package com.abdulkadir.advert.service;

import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.exception.EntityAlreadyExistsException;
import com.abdulkadir.advert.exception.EntityNotFoundException;
import com.abdulkadir.advert.mapper.AdvertMapper;
import com.abdulkadir.advert.model.Advert;
import com.abdulkadir.advert.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;

    public List<AdvertResponseDTO> getAll() {
        return advertRepository
                .findAll()
                .stream()
                .map(advertMapper::toAdvertResponseDTO)
                .collect(Collectors.toList());
    }

    public AdvertResponseDTO getById(Long id) {
        return advertRepository.findById(id).map(advertMapper::toAdvertResponseDTO).orElseThrow(
                () -> new EntityNotFoundException("Advert not found with id: " + id)
        );
    }



    public AdvertResponseDTO create(AdvertRequestDTO advertRequestDTO) {
        // Assuming email is the unique identifier. Adjust according to your user model.
        String title = advertRequestDTO.getTitle();
        boolean userExists = advertRepository.findAdvertByTitle(title).isPresent();

        if (userExists) {
            throw new EntityAlreadyExistsException("Advert already exists with title: " + title);
        }

        return advertMapper.toAdvertResponseDTO(advertRepository.save(advertMapper.toAdvert(advertRequestDTO)));
    }

    public AdvertResponseDTO update(Long id, AdvertRequestDTO advertRequestDTO) {
        return advertRepository.findById(id).map(advert -> {
            Advert updatedAdvert = advertMapper.updateAdvertFields(advert, advertRequestDTO);
            Advert savedAdvert = advertRepository.save(updatedAdvert);
            return advertMapper.toAdvertResponseDTO(savedAdvert);
        }).orElseThrow(
                () -> new EntityNotFoundException("Advert not found with id: " + id)
        );
    }
}
