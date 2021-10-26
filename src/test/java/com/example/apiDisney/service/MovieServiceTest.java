package com.example.apiDisney.service;

import com.example.apiDisney.model.CharacterEntity;
import com.example.apiDisney.model.GenderEntity;
import com.example.apiDisney.model.MovieEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    }

    @AfterEach
    void tearDown() {
        characterService.deleteAll();
        genderService.deleteAll();
        movieService.deleteAll();
    }

    @Test
    void trySaveMovieWithTitleAndCreationDateExists() {
        assertThrows(Exception.class, () -> movieService.save(new MovieEntity("image.png","Toy Story 1",date1,10)));
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
    void getMovies() {
        assertEquals(3,movieService.getMovies().size());
    }

    @Test
    void deleteById() throws Exception{
       movieService.deleteById(movieEntity1.getId());
       assertEquals(2,movieService.getMovies().size());
    }
    @Test
    void tryDeleteAMovieWithACharecter() throws Exception{
        //puedo eliminar la pelicula con personajes incluidos, y estos se persisten
        Long idMovie = movieEntity1.getId();
        movieService.setCharacterInMovie(idMovie,characterEntity.getId());
        movieService.deleteById(idMovie);
        assertEquals(1,characterService.getCharacters().size());
        assertEquals(2,movieService.getMovies().size());
    }

    @Test
    void getByName() throws Exception{
        movieEntity1 = new MovieEntity("image.png","The Incredibles",date1,10);
        movieService.save(movieEntity1);
        assertEquals(2,movieService.getByName("The Incredibles").size());
    }


    @Test
    void getByIdGenre() throws Exception{
        GenderEntity genderEntity1 = new GenderEntity("Terror","imagen.png");
        GenderEntity genderEntity2 = new GenderEntity("Adventures","imagen.png");

        genderEntity1 = genderService.save(genderEntity1);
        genderEntity2 = genderService.save(genderEntity2);

        Long idMovie = movieEntity1.getId();

        movieService.setGenderInMovie(idMovie,genderEntity1.getId());
        movieService.setGenderInMovie(idMovie,genderEntity2.getId());

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
    void setCharacterInMovie() throws Exception {
        assertEquals(1,movieService.setCharacterInMovie(movieEntity1.getId(),
                characterEntity.getId()).getCharacterEntities().size());
    }

    @Test
    void tryAddACharecterNotRegisterInDataBase(){
        assertThrows(Exception.class, () ->
                movieService.setCharacterInMovie(
                        movieEntity1.getId(),
                        45645654L)
                        .getCharacterEntities().size());
    }

}