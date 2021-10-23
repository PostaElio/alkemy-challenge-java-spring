package com.example.api_disney.service;

import com.example.api_disney.model.GenderEntity;
import com.example.api_disney.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    @Autowired
    private GenderRepository genderRepository;

    public List<GenderEntity> getGenders(){
        return (List<GenderEntity>) genderRepository.findAll();
    }
}
