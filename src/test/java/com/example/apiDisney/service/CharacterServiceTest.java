package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.MovieEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        movieService.setCharacterInMovie(
                movieEntity.getId(),
                characterEntity1.getId());

        movieService.setCharacterInMovie(
                movieEntity.getId(),
                characterEntity2.getId());
        movieService.setCharacterInMovie(
                movieEntity.getId(),
                characterEntity3.getId());
        movieService.setCharacterInMovie(
                movieEntity.getId(),
                characterEntity4.getId());
        movieService.setCharacterInMovie(
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
        characterEntity5 = new CharacterEntity(
                null,
                "Dash Parr",
                10,
                1.2F,
                "Also known as Dash is one of the two tritagonists (alongside Violet Parr) of The Incredibles, " +
                        "and Incredibles 2. He is the son and middle child of Bob and Helen Parr, the younger brother " +
                        "of Violet, and the older brother of Jack-Jack");
        try{
            characterService.save(characterEntity5);
        }catch (Exception ex){

        }finally {
            assertEquals(5,characterService.getCharacters().size());
        }

    };
    @Test
    void getCharacters() {
        assertEquals(5,characterService.getCharacters().size());
    }

    /*
    DROP TABLE movie_genders;
DROP TABLE movie_characters;
DROP TABLE characters ;
DROP TABLE movies;
     */

    @Test
    void deleteById(){
        //Eliminamos un personaje, y la pelicula se persiste
        characterService.deleteById(characterEntity1.getId());
        assertEquals(4,characterService.getCharacters().size());
        assertEquals(1,movieService.getMovies().size());
    }
    @Test
    void trydeleteACharacterWithIDDontExists() throws EmptyResultDataAccessException{
        Long idFAKE = 321*500+10L;
        try {
            characterService.deleteById(idFAKE);
        }catch (EmptyResultDataAccessException ex){
            assertEquals("Character with id "+idFAKE+" not found in database",ex.getMessage());
        }
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
       // assertEquals(5,characterEntities.size());
        /*
        List<String> names =new ArrayList<>(List.of("Robert Bob Parr","Violet Parr","Helen Parr","Jack-Jack Parr","Dash Parr"));
        assertTrue(characterEntities.stream()
                .map(characterEntity -> names.contains(characterEntity.getName()))
                .reduce(Boolean::logicalAnd).get());*/
    }

}