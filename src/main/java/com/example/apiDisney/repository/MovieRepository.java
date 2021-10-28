package com.example.apiDisney.repository;

import com.example.apiDisney.model.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    @Query("select case when (count(m)> 0) then true else false end from MovieEntity m where m.creationdate = :creationdate AND m.title = :title")
    boolean existsByTitleAndCreationdate1(String title,Date creationdate);

    List<MovieEntity> findByOrderByCreationdateAsc();

    List<MovieEntity> findByOrderByCreationdateDesc();

    List<MovieEntity> getByTitle(String title);

    @Query("SELECT DISTINCT m FROM MovieEntity m WHERE (SELECT g FROM GenderEntity g WHERE g.id = :id) MEMBER OF m.genderEntities")
    List<MovieEntity> getByIdGenre(@Param("id") Long id);
}
