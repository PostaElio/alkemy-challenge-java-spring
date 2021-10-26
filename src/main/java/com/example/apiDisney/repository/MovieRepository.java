package com.example.apiDisney.repository;

import com.example.apiDisney.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    Boolean existsByTitleAndCreationdate(String title,Date creationDate);

    Boolean existsByTitle(String title);

    Boolean existsByCreationdate(Date creationDate);

    List<MovieEntity> findByOrderByCreationdateAsc();

    List<MovieEntity> findByOrderByCreationdateDesc();

    List<MovieEntity> getByTitle(String title);

    @Query("SELECT DISTINCT m FROM MovieEntity m WHERE (SELECT g FROM GenderEntity g WHERE g.id = :id) MEMBER OF m.genderEntities")
    List<MovieEntity> getByIdGenre(@Param("id") Long id);

    MovieEntity getByTitleAndCreationdate(String title,Date crationDate);
}
