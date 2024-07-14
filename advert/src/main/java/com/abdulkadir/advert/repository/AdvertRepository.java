package com.abdulkadir.advert.repository;

import com.abdulkadir.advert.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {
    Optional<Advert> findAdvertByTitle(String title);
    List<Advert> findByUserId(Long userId);

}