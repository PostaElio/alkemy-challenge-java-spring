package com.example.apiDisney.service.impl;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.repository.CharacterRepository;
import com.example.apiDisney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<CharacterEntity> getCharacters() {
        return characterRepository.findAll();
    }

    public CharacterEntity save(CharacterEntity characterEntity) throws Exception{
        try{
            return characterRepository.save(characterEntity);
        }catch (Exception ex){
            throw new Exception("Enter Correct properties");
        }
    }

    public void deleteById(Long id) throws EmptyResultDataAccessException{
        try{
            CharacterEntity characterEntity= findById(id);
            characterEntity.removeAllMovies();
            characterRepository.save(characterEntity);
            characterRepository.deleteById(id);
        }catch (EmptyResultDataAccessException ex){
            throw new EmptyResultDataAccessException("Character with id "+id+" not found in database",0);
        }
        /*
        try{
            CharacterEntity characterEntity  = characterRepository.findById(id).get();
            characterEntity.getMovieEntities()
                            .stream()
                                    .forEach(movieEntity -> {
                                       movieEntity.removeCharacter(characterEntity);
                                    });
            characterRepository.save(characterEntity);
        }catch (Exception ex){
            throw  new Exception("Not found");
        }*/
    }


    public void deleteAll() {
        characterRepository.deleteAll();
    }

    public List<CharacterEntity> getByName(String name) {
        return characterRepository.getByName(name);
    }

    public List<CharacterEntity> getByAge(int age) {
        return characterRepository.getByAge(age);
    }

    public List<CharacterEntity> getByIdMovie(Long idMovie) {
        return characterRepository.getByIdMovie(idMovie);
    }

    public CharacterEntity findById(Long character_id){
        return characterRepository.findById(character_id).orElseThrow(() -> new EmptyResultDataAccessException("Not Found",0));
    }
}
