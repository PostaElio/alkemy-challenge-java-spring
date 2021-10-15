package com.example.challenge.repositories;

import com.example.challenge.models.PersonajeModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

@Repository
public interface PersonajeRepository extends CrudRepository<PersonajeModel,Long>{
}
