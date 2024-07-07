package com.abdulkadir.user.repository;

import com.abdulkadir.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}