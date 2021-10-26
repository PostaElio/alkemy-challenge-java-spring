package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.ApiResponse;
import com.example.apiDisney.controller.payload.CharacterCompactResponse;
import com.example.apiDisney.controller.payload.CharacterDetailResponse;
import com.example.apiDisney.controller.payload.CharacterRequest;
import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.service.CharacterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
            ModelMapper modelMapper = new ModelMapper();
            characterService.save(modelMapper.map(characterRequest, CharacterEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Character added successfully"), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, "Enter corrects properties"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCharacterById(@PathVariable("id") Long id){
        try {
            ModelMapper modelMapper = new ModelMapper();
            return new ResponseEntity<>(modelMapper.map(characterService.findById(id),CharacterDetailResponse.class),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Character with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);
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

        /*ModelMapper modelMapper = new ModelMapper();
        List<CharacterDetailResponse> characterDetailResponses = characterService.getCharacters()
                .stream().map(characterEntity ->
                        modelMapper.map(characterEntity,CharacterDetailResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterDetailResponses,HttpStatus.OK);*/
        return new ResponseEntity<>(toCharacterDetailsResponse(characterService.getCharacters()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id){
        try {
            characterService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character with id "+id+" is deleted"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Character with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        characterService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Charecters are deleted"),HttpStatus.OK);
    }

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

    private List<CharacterDetailResponse> toCharacterDetailsResponse(List<CharacterEntity> characterEntities) {
        ModelMapper modelMapper = new ModelMapper();
        List<CharacterDetailResponse> characterDetailResponses = characterEntities
                .stream().map(characterEntity ->
                        modelMapper.map(characterEntity,CharacterDetailResponse.class))
                .collect(Collectors.toList());
        return characterDetailResponses;
    }

}
