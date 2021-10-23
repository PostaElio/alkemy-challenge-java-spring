package com.example.api_disney.controller;

import com.example.api_disney.controller.payload.ApiResponse;
import com.example.api_disney.controller.payload.CharacterCompactResponse;
import com.example.api_disney.controller.payload.CharacterDetailResponse;
import com.example.api_disney.controller.payload.CharacterRequest;
import com.example.api_disney.model.CharacterEntity;
import com.example.api_disney.service.CharacterService;
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
    /***********-- GET  -  Character Compact --*************/
    @GetMapping()
    public ResponseEntity<List<CharacterCompactResponse>> getCharactersCompact(){
        List<CharacterCompactResponse> characterCompactResponses = characterService.getCharacters()
                .stream().map(characterEntity -> new CharacterCompactResponse(
                        characterEntity.getImage(),
                        characterEntity.getName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterCompactResponses,HttpStatus.OK);
    }

    /***********-- GET  -  Character Details --*************/
    @GetMapping("/details")
    public ResponseEntity<List<CharacterDetailResponse>> getCharactersDetail(){
        ModelMapper modelMapper = new ModelMapper();
        List<CharacterDetailResponse> characterDetailResponses = characterService.getCharacters()
                .stream().map(characterEntity ->
                        modelMapper.map(characterEntity,CharacterDetailResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(characterDetailResponses,HttpStatus.OK);
    }
    /***********-- Character SAVE - Character UPDATE agregar ID en el json--*************/  /***********-- Update Character --*************/

   /* @PostMapping()
    public CharacterEntity save(@RequestBody CharacterEntity characterEntity){
        return characterService.save(characterEntity);
    }
    */
    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody CharacterRequest characterRequest){
        ModelMapper modelMapper = new ModelMapper();
        //UserEntity userEntity = modelMapper.map(signUpRequest,UserEntity.class);
        characterService.save(modelMapper.map(characterRequest,CharacterEntity.class));
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character added successfully"),HttpStatus.OK);
    }

    /***********-- Character Delete by id --*************/
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id){
        try {
            characterService.deleteById(id);
            //return "Character with id "+id+" is deleted.";
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Character with id "+id+" is deleted"),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Character with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);

        }
    }
    /***********-- Character Delete all --*************/
    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        characterService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Charecters are deleted"),HttpStatus.OK);
    }
    /***********-- QUERYS --*************/
    @GetMapping("/name")
    public ResponseEntity<List<CharacterEntity>> getByName(@RequestParam("name") String name){
        return new ResponseEntity<>(characterService.getByName(name), HttpStatus.OK);
    }
    @GetMapping("/age")
    public ResponseEntity<List<CharacterEntity>> getByAge(@RequestParam("age") int age){
        return new ResponseEntity<>(characterService.getByAge(age), HttpStatus.OK);
    }

    @GetMapping("/movies")
    public ResponseEntity<List<CharacterEntity>> getByMovie(@RequestParam("idMovie") Long idMovie){
        return new ResponseEntity<>(characterService.getByIdMovie(idMovie), HttpStatus.OK);
    }


    /*
    GET /characters?name=nombre
● GET /characters?age=edad
● GET /characters?movies=idMovie
    @GetMapping("/query") //localhost:8080/usuario/query?prioridad=VARIABLE
    public ArrayList<UsuarioModel> obtenerUsuariosPorPrioridad(@RequestParam("prioridad") Integer prioridad){
        return this.usuarioService.obtenerUsuariosConPrioridad(prioridad);
    }
     */
}
