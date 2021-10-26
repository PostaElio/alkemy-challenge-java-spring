package com.example.apiDisney.controller;

import com.example.apiDisney.controller.payload.ApiResponse;
import com.example.apiDisney.controller.payload.GenderDetailsResponse;
import com.example.apiDisney.controller.payload.GenderRequest;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.service.GenderService;
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
    public ResponseEntity<ApiResponse> save(@RequestBody GenderRequest genderRequest) throws DataIntegrityViolationException{
        try{
            ModelMapper modelMapper = new ModelMapper();
            genderService.save(modelMapper.map(genderRequest, GenderEntity.class));
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender added successfully"),HttpStatus.OK);
        }catch (DataIntegrityViolationException ex){
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Name for gender is already taken"),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getAGender(@PathVariable("id") Long id) throws Exception{
        try {
            return new ResponseEntity<>(genderService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Gender with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<List<GenderDetailsResponse>> getGenders(){
        ModelMapper modelMapper = new ModelMapper();
        List<GenderDetailsResponse> genderDetailsResponses = genderService.getGenders()
                .stream().map(genderEntity ->
                        modelMapper.map(genderEntity,GenderDetailsResponse.class)).collect(Collectors.toList());
        return new ResponseEntity<>(genderDetailsResponses, HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<ApiResponse> deletebyId(@PathVariable("id") Long id) throws EmptyResultDataAccessException{
        try {
            genderService.deleteById(id);
            return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"Gender with id "+id+" is deleted"),HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(new ApiResponse(Boolean.FALSE,"Gender with id "+id+" does not exist in the database"),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponse> deleteAll(){
        genderService.deleteAll();
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE,"All Genders are deleted in database"),HttpStatus.OK);
    }

}
