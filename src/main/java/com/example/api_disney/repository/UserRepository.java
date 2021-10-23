package com.example.api_disney.repository;

import com.example.api_disney.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
