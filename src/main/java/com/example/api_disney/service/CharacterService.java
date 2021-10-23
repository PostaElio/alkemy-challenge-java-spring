package com.example.api_disney.service;

import com.example.api_disney.model.CharacterEntity;
import com.example.api_disney.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    public List<CharacterEntity> getCharacters() {
        return (List<CharacterEntity>) characterRepository.findAll();
    }

    public CharacterEntity save(CharacterEntity characterEntity) {
        return characterRepository.save(characterEntity);
    }

    public void deleteById(Long id) {
        characterRepository.deleteById(id);
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
}
