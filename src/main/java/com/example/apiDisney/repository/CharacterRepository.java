package com.example.apiDisney.repository;

import com.example.apiDisney.model.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity,Long> {

    List<CharacterEntity> getByName(String name);

    List<CharacterEntity> getByAge(int age);

    @Query("SELECT DISTINCT c FROM CharacterEntity c WHERE (SELECT m FROM MovieEntity m WHERE m.id = :id) MEMBER OF c.movieEntities")
    List<CharacterEntity> getByIdMovie(@Param("id") Long id);


}
