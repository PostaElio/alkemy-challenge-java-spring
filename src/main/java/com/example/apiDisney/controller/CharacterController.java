package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.ApiResponse;
import com.example.apiDisney.controller.payload.CharacterCompactResponse;
import com.example.apiDisney.controller.payload.CharacterDetailResponse;
import com.example.apiDisney.controller.payload.CharacterRequest;
import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.service.CharacterService;
import com.example.apiDisney.service.exception.CustomException;
import org.hibernate.PropertyValueException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody CharacterRequest characterRequest) throws Exception{
        try {
            characterService.save(new ModelMapper().map(characterRequest, CharacterEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Character added successfully"), HttpStatus.OK);
        }catch (CustomException ex) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException  ex) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody CharacterRequest characterRequest) {
        try {
            characterService.update(id,new ModelMapper().map(characterRequest,CharacterEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Character update successfully"), HttpStatus.OK);
        }catch (EmptyResultDataAccessException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()),HttpStatus.NOT_FOUND);

        }catch (DataIntegrityViolationException ex){
            //Try with other name or values not null
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(characterService.findById(id),CharacterDetailResponse.class),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Character with id "+id+" does not exist in the database"),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<CharacterCompactResponse>> getCharactersCompact(){
        List<CharacterCompactResponse> characterCompactResponses = characterService.getCharacters()
                .stream().map(characterEntity -> new CharacterCompactResponse(
                        characterEntity.getImage(),
                        characterEntity.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterCompactResponses,HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<List<CharacterDetailResponse>> getCharactersDetail(){
        return new ResponseEntity<>(toCharacterDetailsResponse(characterService.getCharacters()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id){
        try {
            characterService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character with id "+id+" is deleted"),HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        characterService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Charecters are deleted"),HttpStatus.OK);
    }
    /**********-- QUERY - MOVIE --************/
    @GetMapping(params="name")
    public ResponseEntity<List<CharacterDetailResponse>> getByName(@RequestParam("name") String name){
        return new ResponseEntity<>(toCharacterDetailsResponse(characterService.getByName(name)), HttpStatus.OK);
    }

    @GetMapping(params="age")
    public ResponseEntity<List<CharacterDetailResponse>> getByAge(@RequestParam("age") int age){
        return new ResponseEntity<>(toCharacterDetailsResponse(characterService.getByAge(age)), HttpStatus.OK);
    }

    @GetMapping(params="idMovie")
    public ResponseEntity<List<CharacterDetailResponse>> getByMovie(@RequestParam("idMovie") Long idMovie){
        return new ResponseEntity<>(toCharacterDetailsResponse(characterService.getByIdMovie(idMovie)), HttpStatus.OK);
    }
    /**********-- HELP --************/
    private List<CharacterDetailResponse> toCharacterDetailsResponse(List<CharacterEntity> characterEntities) {
        ModelMapper modelMapper = new ModelMapper();
        List<CharacterDetailResponse> characterDetailResponses = characterEntities
                .stream().map(characterEntity ->
                        modelMapper.map(characterEntity,CharacterDetailResponse.class))
                .collect(Collectors.toList());
        return characterDetailResponses;
    }

}
