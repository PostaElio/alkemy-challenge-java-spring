package com.example.apiDisney.service;

import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.repository.GenderRepository;
import com.example.apiDisney.service.exception.CustomException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class GenderServiceTest {

    @Autowired
    private GenderService genderService;

    private GenderEntity genderEntity1;
    private GenderEntity genderEntity2;
    private GenderEntity genderEntity3;

    @BeforeEach
    void setUp() throws DataIntegrityViolationException, CustomException {

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
    void update() throws Exception{
        genderEntity1.setName("Terror");
        Long genderID =genderEntity1.getId();
        genderService.update(genderID,genderEntity1);

        assertEquals("Terror",genderService.findById(genderID).getName());
    }

    @Test
    void tryUpdateAIdNonExistent(){
        genderEntity1.setName("Terror");
        assertThrows(EmptyResultDataAccessException.class, () -> genderService.update(132*465+10L,genderEntity1));
    }
    @Test
    void tryUpdateAGenderWithNameExistent(){
        genderEntity1.setName("Comedy");
        assertThrows(DataIntegrityViolationException.class, () -> genderService.update(genderEntity1.getId(),genderEntity1));
    }

    @Test
    void getGenders() {
        assertEquals(3,genderService.getGenders().size());
    }

    @Test
    void trySaveAGenderWithNameExisting() throws DataIntegrityViolationException {
        assertThrows(CustomException.class, () ->
                genderService.save(new GenderEntity("Comedy","image123.png")));
    }

    @Test
    void deleteById() {
        genderService.deleteById(genderEntity1.getId());
        assertEquals(2,genderService.getGenders().size());
    }

    @Test
    void tryDeleteByIdFake() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                genderService.deleteById(321*500+10L));
    }

}