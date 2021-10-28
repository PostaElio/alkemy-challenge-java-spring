package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.ApiResponse;
import com.example.apiDisney.controller.payload.CharacterRequest;
import com.example.apiDisney.controller.payload.GenderDetailsResponse;
import com.example.apiDisney.controller.payload.GenderRequest;
import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.service.GenderService;
import com.example.apiDisney.service.exception.CustomException;
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
@RequestMapping("/genders")
public class GenderController {

    @Autowired
    private GenderService genderService;

    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody GenderRequest genderRequest) throws Exception {
        try{
            genderService.save(new ModelMapper().map(genderRequest, GenderEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender added successfully"),HttpStatus.OK);
        }catch (CustomException ex) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }catch (DataIntegrityViolationException  ex) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable("id") Long id, @RequestBody GenderRequest genderRequest) {
        try {
            genderService.update(id,new ModelMapper().map(genderRequest, GenderEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Gender update successfully"), HttpStatus.OK);
        }catch (EmptyResultDataAccessException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,ex.getMessage()),HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException ex){
            //Try with other name or values not null
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE, ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAGender(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(new ModelMapper().map(genderService.findById(id),GenderDetailsResponse.class),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Gender with id "+id+" does not exist in the database"),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<GenderDetailsResponse>> getGenders(){
        List<GenderDetailsResponse> genderDetailsResponses = genderService.getGenders()
                .stream().map(genderEntity ->
                        new ModelMapper().map(genderEntity,GenderDetailsResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(genderDetailsResponses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id) throws EmptyResultDataAccessException{
        try {
            genderService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender with id "+id+" is deleted"),HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,e.getMessage()),HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        genderService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Genders are deleted in database"),HttpStatus.OK);
    }

}
