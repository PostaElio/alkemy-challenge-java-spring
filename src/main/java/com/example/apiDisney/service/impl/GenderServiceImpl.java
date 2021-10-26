package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.repository.GenderRepository;
import com.example.apiDisney.service.GenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<GenderEntity> getGenders(){
        return genderRepository.findAll();
    }

    public GenderEntity save(GenderEntity genderEntity) throws DataIntegrityViolationException{
        try{
            return genderRepository.save(genderEntity);
        }catch(DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Gender with name "+genderEntity.getName()+" exists in database");
        }
    }

    public void deleteById(Long id) throws EmptyResultDataAccessException {
        try{
            genderRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Gender with id "+id+" not found in database",0);
        }
    }

    public void deleteAll() {
        genderRepository.deleteAll();
    }

    public GenderEntity findById(Long gender_id) throws  Exception{
        return genderRepository.findById(gender_id).orElseThrow(() -> new Exception("Not Found"));
    }
}
