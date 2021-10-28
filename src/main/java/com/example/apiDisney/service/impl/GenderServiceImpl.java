package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.repository.GenderRepository;
import com.example.apiDisney.service.GenderService;
import com.example.apiDisney.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderServiceImpl implements GenderService {

    @Autowired
    private GenderRepository genderRepository;

    @Override
    public GenderEntity save(GenderEntity genderEntity) throws DataIntegrityViolationException ,CustomException{
        if(genderRepository.existsByName(genderEntity.getName())){
            throw new CustomException("Gender with name "+genderEntity.getName()+" already Exists");
        }
        try{
            return genderRepository.save(genderEntity);
        }catch(DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Enter corrects properties");
        }
    }

    @Override
    public void update(Long id, GenderEntity genderEntity) throws EmptyResultDataAccessException ,DataIntegrityViolationException{
        genderEntity.setId(findById(id).getId());
        try{
            genderRepository.save(genderEntity);
        }catch(DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Enter value not null or try with other name");
        }
    }

    @Override
    public GenderEntity findById(Long id) {
        return genderRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Entity with id "+id+" not found.",0));
    }

    @Override
    public List<GenderEntity> getGenders(){
        return genderRepository.findAll();
    }

    @Override
    public void deleteById(Long id) throws EmptyResultDataAccessException {
        try{
            genderRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Gender with id "+id+" not found in database",0);
        }
    }

    @Override
    public void deleteAll() {
        genderRepository.deleteAll();
    }

}
