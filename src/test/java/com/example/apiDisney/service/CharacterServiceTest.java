package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.MovieEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CharacterServiceTest {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MovieService movieService;

    private MovieEntity movieEntity;

    private CharacterEntity characterEntity1;
    private CharacterEntity characterEntity2;
    private CharacterEntity characterEntity3;
    private CharacterEntity characterEntity4;
    private CharacterEntity characterEntity5;

    @BeforeEach
    void setUp() throws Exception {
        characterService.deleteAll();
        movieService.deleteAll();

        characterEntity1 = new CharacterEntity(
                "imagen.png",
                "Robert Bob Parr",
                40,
                1.9F,
                "Also known as Mr. Incredible, is the husband of Helen Parr and the father of Violet Parr, " +
                        "Dash Parr, and Jack-Jack Parr, also the overall main protagonist of The Incredibles franchise");
        characterEntity2 = new CharacterEntity(
                "image.png",
                "Violet Parr",
                14,
                1.6F,
                "Is one of the two tritagonists (alongside Dash Parr) of The Incredibles franchise. She is a " +
                        "super-heroine who is the daughter (and eldest child) of Bob and Helen Parr, as well as the " +
                        "older sister of Dash and Jack-Jack");
        characterEntity3 = new CharacterEntity(
                "image.png",
                "Helen Parr",
                38,
                1.8F,
                "Also known as Elastigirl. She is the overall deuteragonist of The Incredibles franchise. She is " +
                        "the wife of Bob Parr, and the mother of Violet, Dash, and Jack-Jack");
        characterEntity4 = new CharacterEntity(
                "image.png",
                "Jack-Jack Parr",
                1,
                0.6F,
                "is the youngest son of Bob and Helen Parr, as well as the younger brother of Violet and Dash");
        characterEntity5 = new CharacterEntity(
                "null",
                "Dash Parr",
                10,
                1.2F,
                "Also known as Dash is one of the two tritagonists (alongside Violet Parr) of The Incredibles, " +
                        "and Incredibles 2. He is the son and middle child of Bob and Helen Parr, the younger brother " +
                        "of Violet, and the older brother of Jack-Jack");

        characterEntity1 = characterService.save(characterEntity1);
        characterEntity2 = characterService.save(characterEntity2);
        characterEntity3 = characterService.save(characterEntity3);
        characterEntity4 = characterService.save(characterEntity4);
        characterEntity5 = characterService.save(characterEntity5);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String date = "10-01-2004 00:00:00.0";
        movieEntity = new MovieEntity(
                "imagen.png",
                "The Incredibles",formatter.parse(date),
                10);
        movieEntity = movieService.save(movieEntity);

        movieService.addCharacterInMovie(
                movieEntity.getId(),
                characterEntity1.getId());
        movieService.addCharacterInMovie(
                movieEntity.getId(),
                characterEntity2.getId());
        movieService.addCharacterInMovie(
                movieEntity.getId(),
                characterEntity3.getId());
        movieService.addCharacterInMovie(
                movieEntity.getId(),
                characterEntity4.getId());
        movieService.addCharacterInMovie(
                movieEntity.getId(),
                characterEntity5.getId());
    }

    @AfterEach
    void tearDown() {

        movieService.deleteAll();
        characterService.deleteAll();


    }

    @Test
    void trySaveCharacterWithAtributtesNull(){
        assertThrows(Exception.class, () ->
                characterService.save(new CharacterEntity(
                null,
                "Mickey Mouse",
                1,
                1.5F,
                "dsfgdfgdfgdfgdfgk")));
    };

    @Test
    void update() throws Exception{
        characterEntity1.setName("Monsters Inc");
        characterEntity1.setImage("nuevaimagen.png");
        Long characterId =characterEntity1.getId();
        characterService.update(characterId,characterEntity1);

        assertEquals("Monsters Inc",characterService.findById(characterId).getName());
        assertEquals("nuevaimagen.png",characterService.findById(characterId).getImage());
    }

    @Test
    void tryUpdateAIdNonExistent(){
        characterEntity1.setName("Monsters Inc");
        assertThrows(EmptyResultDataAccessException.class, () -> characterService.update(132*465+10L,characterEntity1));
    }

    @Test
    void tryUpdateAGenderWithNameExistent(){
        characterEntity1.setName("Jack-Jack Parr");
        assertThrows(DataIntegrityViolationException.class, () -> characterService.update(characterEntity1.getId(),characterEntity1));
    }

    @Test
    void getCharacters() {
        assertEquals(5,characterService.getCharacters().size());
    }

    /*
    DROP TABLE movie_genders;
DROP TABLE movie_characters;
DROP TABLE characters ;
DROP TABLE movies;


DROP TABLE movie_genders;
DROP TABLE movie_characters;
DROP TABLE characters ;
DROP TABLE movies;
DROP TABLE genders;
DROP TABLE users
     */

    @Test
    void deleteFirstHisCharactersForDeleteAMovie() throws Exception {
        //Para eliminar una pelicula tenemos que eliminar todos sus personajes
        characterService.deleteAll();
        //Podemos eliminarlo por que le eliminamos todos sus personajes asociados
        movieService.deleteById(movieEntity.getId());

        assertEquals(0,characterService.getCharacters().size());
        assertEquals(0,movieService.getMovies().size());
    }

    @Test
    void deleteAnyCharactersForAMovieThisCanBeDeleteBecuseHasMovie() {
        //Eliminamos algunos personajes, no podemos eliminar la pelicula por que todavia tiene personajes
        characterService.deleteById(characterEntity1.getId());
        characterService.deleteById(characterEntity2.getId());
        //No podemos eliminarlo por que tiene personajes asociados
        assertThrows(Exception.class,() -> movieService.deleteById(movieEntity.getId()));

        assertEquals(3,characterService.getCharacters().size());
        assertEquals(1,movieService.getMovies().size());
    }

    @Test
    void trydeleteACharacterWithIDDontExists() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                characterService.deleteById(321*500+10L));
    }

    @Test
    void getByName() {
        assertEquals(1,characterService.getByName("Robert Bob Parr").size());
        assertEquals(1,characterService.getByName("Violet Parr").size());
        assertEquals(1,characterService.getByName("Helen Parr").size());
        assertEquals(1,characterService.getByName("Jack-Jack Parr").size());
        assertEquals(1,characterService.getByName("Dash Parr").size());
    }

    @Test
    void getByAge() {
        List<CharacterEntity> characterEntities = characterService.getByAge(10);
        assertEquals(1,characterEntities.size());
        assertTrue(characterEntities.stream()
                .map(characterEntity -> characterEntity.getAge()==10)
                        .reduce(Boolean::logicalAnd).get());
    }

    @Test
    void getByIdMovie(){
        List<CharacterEntity> characterEntities = characterService.getByIdMovie(movieEntity.getId());
        assertEquals(5,characterEntities.size());

        List<String> names =new ArrayList<>(List.of("Robert Bob Parr","Violet Parr","Helen Parr","Jack-Jack Parr","Dash Parr"));
        assertTrue(characterEntities.stream()
                .map(characterEntity -> names.contains(characterEntity.getName()))
                .reduce(Boolean::logicalAnd).get());
    }

}