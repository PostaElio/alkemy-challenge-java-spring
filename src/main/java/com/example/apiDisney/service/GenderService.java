package com.example.apiDisney.service;

import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.service.exception.CustomException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Currency;
import java.util.List;

public interface GenderService {

    /*Crud*/
    GenderEntity save(GenderEntity genderEntity) throws DataIntegrityViolationException, CustomException;
    void update(Long id, GenderEntity map) throws EmptyResultDataAccessException ,DataIntegrityViolationException;
    GenderEntity findById(Long genderId) throws  Exception;
    List<GenderEntity> getGenders();
    void deleteById(Long genderId) throws EmptyResultDataAccessException;
    void deleteAll();

}

