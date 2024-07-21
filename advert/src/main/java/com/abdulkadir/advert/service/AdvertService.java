package com.abdulkadir.advert.service;

import com.abdulkadir.advert.client.OrderClient;
import com.abdulkadir.advert.client.UserClient;
import com.abdulkadir.advert.dto.request.AdvertRequestDTO;
import com.abdulkadir.advert.dto.response.AdvertResponseDTO;
import com.abdulkadir.advert.exception.EntityAlreadyExistsException;
import com.abdulkadir.advert.exception.EntityNotFoundException;
import com.abdulkadir.advert.mapper.AdvertMapper;
import com.abdulkadir.advert.model.Advert;
import com.abdulkadir.advert.model.enums.AdvertStatus;
import com.abdulkadir.advert.producer.StatusChangeProducer;
import com.abdulkadir.advert.repository.AdvertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TopicExchange exchange;

    private final AdvertMapper advertMapper;
    private final AdvertRepository advertRepository;
    private final UserClient userClient;
    private final OrderClient orderClient;
    private final StatusChangeProducer statusChangeProducer;

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

    public long getAllByUserId(Long userId) {

        return advertRepository.findByUserId(userId).stream().count();
    }


    public AdvertResponseDTO create(AdvertRequestDTO advertRequestDTO) {
        // Check if the user exists.
        if (!userClient.existsById(advertRequestDTO.getUserId())) {
            throw new EntityNotFoundException("User not found with id: " + advertRequestDTO.getUserId());
        }

        // Check if the user has available advert rights.
        if (orderClient.getAvailableAdvertRights(advertRequestDTO.getUserId()) <= 0) {
            throw new EntityNotFoundException("User does not have available advert rights");
        }

        // Check if the title already exists.
        String title = advertRequestDTO.getTitle();
        boolean titleExists = advertRepository.findAdvertByTitle(title).isPresent();
        if (titleExists) {
            throw new EntityAlreadyExistsException("Advert already exists with title: " + title);
        }

        // Save the advert entity.
        Advert savedAdvert = advertRepository.save(advertMapper.toAdvert(advertRequestDTO));

        // New code to save images

        // Send the status change message after saving the advert.
        // Assuming the status change is related to the creation of the advert,
        // and ACTIVE status is set after creation.
        // You might need to adjust the status based on your application logic.

        statusChangeProducer.sendStatusChange(savedAdvert.getId(), AdvertStatus.ACTIVE);
        // Convert the saved advert entity to AdvertResponseDTO and return it.
        return advertMapper.toAdvertResponseDTO(savedAdvert);
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
