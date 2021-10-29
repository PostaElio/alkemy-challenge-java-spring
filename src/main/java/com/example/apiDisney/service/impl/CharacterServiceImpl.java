package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.repository.CharacterRepository;
import com.example.apiDisney.service.CharacterService;
import com.example.apiDisney.service.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl
        implements CharacterService{

    @Autowired
    private CharacterRepository characterRepository;

    public CharacterEntity save(CharacterEntity characterEntity) throws CustomException, DataIntegrityViolationException{
        if(characterRepository.existsByName(characterEntity.getName())){
            throw new CustomException("Character with name "+characterEntity.getName()+" already Exists");
        }
        try{
            return characterRepository.save(characterEntity);
        }catch(DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Enter corrects properties");
        }
    }

    public void update(Long id, CharacterEntity characterEntity) throws DataIntegrityViolationException, EmptyResultDataAccessException{
        characterEntity.setId(findById(id).getId());
        try{
            characterRepository.save(characterEntity);
        }catch(DataIntegrityViolationException ex){
            throw new DataIntegrityViolationException("Enter value not null or try with other name");
        }
    }

    public CharacterEntity findById(Long id){
        return characterRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Entity with id "+id+" not found.",0));
    }

    public List<CharacterEntity> getCharacters() {
        return characterRepository.findAll();
    }

    public void deleteById(Long id) throws EmptyResultDataAccessException{
        try{
            characterRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Character with id "+id+" not found in database",0);
        }
    }

    public void deleteAll() {
        characterRepository.deleteAll();
    }
    /*las queries se testean,nose por que aparece en rojo con converge*/
    public List<CharacterEntity> getByName(String name) {
        return characterRepository.getByName(name);
    }

    public List<CharacterEntity> getByAge(int age) {
        return characterRepository.getByAge(age);
    }

    public List<CharacterEntity> getByIdMovie(Long idMovie) {
        return characterRepository.getByIdMovie(idMovie);
    }

}
