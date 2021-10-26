package com.example.apiDisney.service;

import com.example.apiDisney.model.GenderEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface GenderService {

    /*Crud*/
    GenderEntity save(GenderEntity genderEntity) throws DataIntegrityViolationException;
    GenderEntity findById(Long gender_id) throws  Exception;
    List<GenderEntity> getGenders();
    void deleteById(Long id) throws EmptyResultDataAccessException;
    void deleteAll();

}

