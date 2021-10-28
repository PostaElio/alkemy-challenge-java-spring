package com.example.apiDisney.repository;

import com.example.apiDisney.model.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderEntity,Long> {

    boolean existsByName(String name);
}
