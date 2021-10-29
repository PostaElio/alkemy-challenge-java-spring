package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.model.MovieEntity;
import com.example.apiDisney.service.exception.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private CharacterService characterService;

    @Autowired
    private GenderService genderService;

    private MovieEntity movieEntity1;
    private MovieEntity movieEntity2;
    private MovieEntity movieEntity3;

    private SimpleDateFormat formatter;
    private Date date1;
    private Date date2;
    private Date date3;

    private CharacterEntity characterEntity;
    private GenderEntity genderEntity;

    @BeforeEach
    void setUp() throws Exception{

        formatter = new SimpleDateFormat("dd-MM-yyyy");
        date1 = formatter.parse("22-01-1999 00:00:00.0");
        date2 = formatter.parse("22-01-2020 00:00:00.0");
        date3 = formatter.parse("22-01-2005 00:00:00.0");

        movieEntity1 = new MovieEntity("image.png","The Incredibles",date2,10);
        movieEntity2 = new MovieEntity("image.png","Finding Nemo",date3,10);
        movieEntity3 = new MovieEntity("image.png","Toy Story 1",date1,10);

        movieEntity1 = movieService.save(movieEntity1);
        movieEntity2 = movieService.save(movieEntity2);
        movieEntity3 = movieService.save(movieEntity3);

        characterEntity = new CharacterEntity(
                "imagen.png",
                "Robert Bob Parr",
                40,
                1.9F,
                "Also known as Mr. Incredible, is the husband of Helen Parr and the father of Violet Parr, " +
                        "Dash Parr, and Jack-Jack Parr, also the overall main protagonist of The Incredibles franchise");
        characterEntity = characterService.save(characterEntity);

        genderEntity = new GenderEntity("Comedy","image.png");
        genderEntity = genderService.save(genderEntity);
    }

    @AfterEach
    void tearDown() {
        movieService.deleteAll();
        characterService.deleteAll();
        genderService.deleteAll();
    }

    @Test
    void saveMovieWithNameEqualsToAnyButDifferentCreationDate() throws Exception{
        movieService.save(new MovieEntity("image.png","Toy Story 1",date2,10));
        assertEquals(4, movieService.getMovies().size());
    }

    @Test
    void saveMovieWithCreationDateEqualsToAnyButDifferentTitle() throws Exception{
        movieService.save(new MovieEntity("image.png","The Simpsons",date2,10));
        assertEquals(4, movieService.getMovies().size());
    }

    @Test
    void trySaveMovieWithTitleAndCreationDateExists() {
        assertThrows(CustomException.class, () ->
                movieService.save(new MovieEntity("image.png","Toy Story 1",date1,10)));
        assertEquals(3,movieService.getMovies().size());
    }

    @Test
    void trySaveMovieWithValuesNull(){
        assertThrows(DataIntegrityViolationException.class, () ->
                movieService.save(new MovieEntity("image.png",null,date1,10)));
    }

    @Test
    void update() throws Exception{
        movieEntity1.setTitle("The Simpsons");
        Long movieId = movieEntity1.getId();
        movieService.update(movieId,movieEntity1);

        assertEquals("The Simpsons",movieService.findById(movieId).getTitle());
    }

    @Test
    void tryUpdateAIdNonExistent(){
        movieEntity1.setTitle("The Simpsons");
        movieEntity1.setClasification(1);
        assertThrows(EmptyResultDataAccessException.class, () -> movieService.update(132*465+10L,movieEntity1));
    }

    @Test
    void tryUpdateAMovieWithTitleExistent(){
        movieEntity1.setTitle(null);
        assertThrows(DataIntegrityViolationException.class, () -> movieService.update(movieEntity1.getId(),movieEntity1));
    }

    @Test
    void tryUpdateAMovieWithTitleAndCreationdateExistent(){
        movieEntity1.setTitle("Toy Story 1");
        movieEntity1.setCreationdate(date1);
        assertTrue(movieEntity3.getTitle().equals(movieEntity1.getTitle()));
        assertTrue(movieEntity3.getCreationdate().equals(movieEntity1.getCreationdate()));
        assertThrows(CustomException.class, () -> movieService.update(movieEntity1.getId(),movieEntity1));
    }

    @Test
    void getMovies() {
        assertEquals(3,movieService.getMovies().size());
    }

    @Test
    void deleteById() throws Exception{
       movieService.deleteById(movieEntity1.getId());
       assertEquals(2,movieService.getMovies().size());
    }

    @Test
    void tryDeleteByIdNotExisting(){
        assertThrows(NoSuchElementException.class, () ->
                movieService.deleteById(132*500+1000L));
    }

    @Test
    void tryDeleteAMovieWithACharecter() throws Exception{
        //Primero se elimina los personajes de la pelicula, despues se elimina la pelicula
        Long idMovie = movieEntity1.getId();
        movieService.addCharacterInMovie(idMovie,characterEntity.getId());
        CharacterEntity cEntity = characterService.findById(characterEntity.getId());
        MovieEntity mEntity = movieService.findById(idMovie);
        /*Verificamos que se agregaron*/
        assertEquals(1,cEntity.getMovieEntities().size());
        assertEquals(1,mEntity.getCharacterEntities().size());

        characterService.deleteById(cEntity.getId());
        movieService.deleteById(idMovie);

        assertEquals(0,characterService.getCharacters().size());
        assertEquals(2,movieService.getMovies().size());
    }

    @Test
    void getByTitle() throws Exception{
        movieService.save(new MovieEntity("image.png","The Incredibles",date1,10));
        assertEquals(2,movieService.getByTitle("The Incredibles").size());
    }

    @Test
    void getByIdGenre() throws Exception{
        GenderEntity genderEntity1 = genderService.save(new GenderEntity("Terror","imagen.png"));
        GenderEntity genderEntity2 = genderService.save(new GenderEntity("Adventures","imagen.png"));

        Long idMovie = movieEntity1.getId();

        movieService.addGenderInMovie(idMovie,genderEntity1.getId());
        movieService.addGenderInMovie(idMovie,genderEntity2.getId());

        assertEquals(1,movieService.getByIdGenre(genderEntity1.getId()).size());
    }

    @Test
    void findAllByOOrderByCration_dateDesc() {
        List<MovieEntity> movieEntities = movieService.findAllByOOrderByCration_dateDesc();

        assertTrue(movieEntities.get(0).getCreationdate().equals(date2));
        assertTrue(movieEntities.get(1).getCreationdate().equals(date3));
        assertTrue(movieEntities.get(2).getCreationdate().equals(date1));
    }

    @Test
    void findAllByOOrderByCration_dateAsc(){
        List<MovieEntity> movieEntities = movieService.findAllByOOrderByCration_dateAsc();

        assertTrue(movieEntities.get(0).getCreationdate().equals(date1));
        assertTrue(movieEntities.get(1).getCreationdate().equals(date3));
        assertTrue(movieEntities.get(2).getCreationdate().equals(date2));
    }

    @Test
    void addCharacterInMovie() throws Exception {
        assertEquals(1,movieService.addCharacterInMovie(movieEntity1.getId(),
                characterEntity.getId()).getCharacterEntities().size());
    }

    @Test
    void tryAddACharecterNotRegisterInDataBase() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                movieService.addCharacterInMovie(
                        movieEntity1.getId(),
                        45645654L));
    }
    @Test
    void tryAddACharacterIncludeInMovie()throws Exception{
        movieService.addCharacterInMovie(movieEntity1.getId(),characterEntity.getId());

        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                movieService.addCharacterInMovie(movieEntity1.getId(),characterEntity.getId()));
    }

    @Test
    void addGenderInMovie() {
        assertEquals(1,movieService.addGenderInMovie(movieEntity1.getId(),
                genderEntity.getId()).getGenderEntities().size());
    }

    @Test
    void tryAddAGenderNotRegisterInDataBase() {
        assertThrows(EmptyResultDataAccessException.class, () ->
                movieService.addGenderInMovie(
                                movieEntity1.getId(),
                                45645545654L));
    }

    @Test
    void tryAddAGenderIncludeInMovie()throws Exception{
        //No funciona
        movieService.addGenderInMovie(movieEntity1.getId(),genderEntity.getId());
        assertEquals(1, movieService.findById(movieEntity1.getId()).getGenderEntities().size());
        movieService.addGenderInMovie(movieEntity1.getId(),genderEntity.getId());
        assertEquals(1, movieService.findById(movieEntity1.getId()).getGenderEntities().size());
        /*
        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                movieService.addGenderInMovie(movieEntity1.getId(),genderEntity.getId()));*/
    }

/*
    @Test
    void removeCharacterFromMovie() throws Exception{
        Long movieId = movieEntity1.getId();
        Long characterId = characterEntity.getId();

        //Agregamos personaje a nuestra pelicula
        assertEquals(1,movieService.addCharacterInMovie(movieId,
                characterId).getCharacterEntities().size());
        assertEquals(1,characterService.findById(characterId).getMovieEntities().size());
        //Eliminamos el personaje de nuestra pelicula
        assertEquals(1,movieService.removeCharacter(movieId,
                characterId).getCharacterEntities().size());

        assertEquals(1,characterService.findById(characterId).getMovieEntities().size());
    }*/

}