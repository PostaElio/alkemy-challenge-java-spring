package com.example.api_disney.repository;

import com.example.api_disney.model.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity,Long> {

    List<CharacterEntity> getByName(String name);

    List<CharacterEntity> getByAge(int age);

    @Query("SELECT DISTINCT a FROM CharacterEntity a WHERE (SELECT f FROM MovieEntity f WHERE f.id = :id) MEMBER OF a.movieEntities")
    List<CharacterEntity> getByIdMovie(@Param("id") Long id);
}
