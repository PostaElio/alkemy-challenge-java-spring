package com.example.api_disney.repository;

import com.example.api_disney.model.GenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<GenderEntity,Long> {
}
