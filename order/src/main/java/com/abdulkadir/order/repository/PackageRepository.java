package com.abdulkadir.order.repository;

import com.abdulkadir.order.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PackageRepository extends JpaRepository<Package, Long> {

}
