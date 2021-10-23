package com.example.api_disney.repository;

import com.example.api_disney.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    List<MovieEntity> findByOrderByCreationdateAsc();

    List<MovieEntity> findByOrderByCreationdateDesc();

    List<MovieEntity> getByTitle(String title);

    @Query("SELECT DISTINCT m FROM MovieEntity m WHERE (SELECT g FROM GenderEntity g WHERE g.id = :id) MEMBER OF m.genderEntities")
    List<MovieEntity> getByIdGenre(@Param("id") Long id);

}
