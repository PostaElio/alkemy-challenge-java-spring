package com.example.apiDisney.service;

import com.example.apiDisney.model.GenderEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
class GenderServiceTest {

    @Autowired
    private GenderService genderService;

    private GenderEntity genderEntity1;
    private GenderEntity genderEntity2;
    private GenderEntity genderEntity3;

    @BeforeEach
    void setUp() throws DataIntegrityViolationException {

        genderEntity1 = new GenderEntity("Action","image.png");
        genderEntity2 = new GenderEntity("Adventure","image.png");
        genderEntity3 = new GenderEntity("Comedy","image.png");

        genderEntity1 = genderService.save(genderEntity1);
        genderEntity2 = genderService.save(genderEntity2);
        genderEntity3 = genderService.save(genderEntity3);

    }

    @AfterEach
    void tearDown() {
        genderService.deleteAll();
    }

    @Test
    void getGenders() {
        assertEquals(3,genderService.getGenders().size());
    }

    @Test
    void trySaveAGenderWithNameExisting() throws DataIntegrityViolationException {
        genderEntity3 = new GenderEntity("Comedy","image.png");
        try{
            genderService.save(genderEntity3);
        }catch (DataIntegrityViolationException ex){
            assertEquals(3,genderService.getGenders().size());
        }
    }

    @Test
    void deleteById() {
        genderService.deleteById(genderEntity1.getId());
        assertEquals(2,genderService.getGenders().size());
    }

    @Test
    void tryDeleteByIdFake() throws EmptyResultDataAccessException{
        Long idFAKE = 321*500+10L;
        try {
            genderService.deleteById(idFAKE);
        }catch (EmptyResultDataAccessException ex){
            assertEquals("Gender with id "+idFAKE+" not found in database",ex.getMessage());
        }
    }

}