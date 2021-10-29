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

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SetUpValues {

    @Autowired
    private MovieService movieService;
    @Autowired
    private CharacterService characterService;
    @Autowired
    private GenderService genderService;

    private MovieEntity theIncredibles, mickeyMouseThePicture, myMix;
    //private GenderEntity terror,action,adventures,comedy,fantasy,sciencefiction;
    private GenderEntity gender1,gender2,gender3,gender4,gender5,gender6;
    private CharacterEntity entity1,entity2,entity3,entity4,entity5,entity6,entity7,entity8,entity9,entity10;

    private SimpleDateFormat formatter;
    private Date date1,date2,date3;

    @BeforeEach
    void setUp() throws Exception{
        formatter = new SimpleDateFormat("dd-MM-yyyy");
        date1 = formatter.parse("22-01-1999 00:00:00.0");
        date2 = formatter.parse("22-01-2005 00:00:00.0");
        date3 = formatter.parse("22-01-2020 00:00:00.0");

        theIncredibles = new MovieEntity("path/to/theIncreibles-image.png","The Incredibles",date3,10);
        mickeyMouseThePicture = new MovieEntity("path/to/mickeymouseThePicture-image.png","Mickey Mouse The Picture",date2,7);
        myMix = new MovieEntity("path/to/myMix-image.png","The myMix",date1,10);

        theIncredibles = movieService.save(theIncredibles);
        mickeyMouseThePicture = movieService.save(mickeyMouseThePicture);
        myMix =movieService.save(myMix);

        gender1 = new GenderEntity("Terror","image.png");
        gender2 = new GenderEntity("Action","image.png");
        gender3 = new GenderEntity("Adventures","image.png");
        gender4 = new GenderEntity("Comedy","image.png");
        gender5 = new GenderEntity("Fantasy","image.png");
        gender6 = new GenderEntity("Scince Fiction","image.png");

        gender1 = genderService.save(gender1);
        gender2 = genderService.save(gender2);
        gender3 = genderService.save(gender3);
        gender4 = genderService.save(gender4);
        gender5 = genderService.save(gender5);
        gender6 = genderService.save(gender6);

        movieService.addGenderInMovie(theIncredibles.getId(),gender6.getId());
        movieService.addGenderInMovie(theIncredibles.getId(),gender5.getId());
        movieService.addGenderInMovie(theIncredibles.getId(),gender4.getId());
        movieService.addGenderInMovie(theIncredibles.getId(),gender3.getId());
        movieService.addGenderInMovie(theIncredibles.getId(),gender2.getId());

        movieService.addGenderInMovie(mickeyMouseThePicture.getId(),gender3.getId());
        movieService.addGenderInMovie(mickeyMouseThePicture.getId(),gender4.getId());
        movieService.addGenderInMovie(mickeyMouseThePicture.getId(),gender5.getId());

        movieService.addGenderInMovie(myMix.getId(),gender1.getId());
        movieService.addGenderInMovie(myMix.getId(),gender2.getId());
        movieService.addGenderInMovie(myMix.getId(),gender3.getId());
        movieService.addGenderInMovie(myMix.getId(),gender4.getId());


        entity1 = new CharacterEntity(
                "imagen.png",
                "Robert Bob Parr",
                40,
                1.9F,
                "Also known as Mr. Incredible, is the husband of Helen Parr and the father of Violet Parr, " +
                        "Dash Parr, and Jack-Jack Parr, also the overall main protagonist of The Incredibles franchise");
        entity2 = new CharacterEntity(
                "image.png",
                "Violet Parr",
                14,
                1.6F,
                "Is one of the two tritagonists (alongside Dash Parr) of The Incredibles franchise. She is a " +
                        "super-heroine who is the daughter (and eldest child) of Bob and Helen Parr, as well as the " +
                        "older sister of Dash and Jack-Jack");
        entity3 = new CharacterEntity(
                "image.png",
                "Helen Parr",
                38,
                1.8F,
                "Also known as Elastigirl. She is the overall deuteragonist of The Incredibles franchise. She is " +
                        "the wife of Bob Parr, and the mother of Violet, Dash, and Jack-Jack");
        entity4 = new CharacterEntity(
                "image.png",
                "Jack-Jack Parr",
                1,
                0.6F,
                "is the youngest son of Bob and Helen Parr, as well as the younger brother of Violet and Dash");
        entity5 = new CharacterEntity(
                "image.png",
                "Dash Parr",
                10,
                1.2F,
                "Also known as Dash is one of the two tritagonists (alongside Violet Parr) of The Incredibles, " +
                        "and Incredibles 2. He is the son and middle child of Bob and Helen Parr, the younger brother " +
                        "of Violet, and the older brother of Jack-Jack");


        entity6 = new CharacterEntity(
                "image.png",
                "Mickey Mouse",
                2,
                1.2F,
                "aaaaaaaaaaaaaaaaa"
        );
        entity7 = new CharacterEntity(
                "image.png",
                "Pluto",
                2,
                1.0F,
                "bbbbbbbbbbbbbbbbb"
        );
        entity8 = new CharacterEntity(
                "image.png",
                "Donald Duck",
                2,
                1.8F,
                "ccccccccccccccccc"
        );


        entity9 = new CharacterEntity(
                "image.png",
                "MyCustomCharacter1",
                30,
                1.8F,
                "qqqqqqqqqqqqqqqqqq"
        );
        entity10 = new CharacterEntity(
                "image.png",
                "MyCustomCharacter2",
                30,
                1.8F,
                "xxxxxxxxxxxxxxxxxx"
        );

        entity1 = characterService.save(entity1);
        entity2 = characterService.save(entity2);
        entity3 = characterService.save(entity3);
        entity4 = characterService.save(entity4);
        entity5 = characterService.save(entity5);
        entity6 = characterService.save(entity6);
        entity7 = characterService.save(entity7);
        entity8 = characterService.save(entity8);
        entity9 = characterService.save(entity9);
        entity10 = characterService.save(entity10);

        movieService.addCharacterInMovie(theIncredibles.getId(),entity1.getId());
        movieService.addCharacterInMovie(theIncredibles.getId(),entity2.getId());
        movieService.addCharacterInMovie(theIncredibles.getId(),entity3.getId());
        movieService.addCharacterInMovie(theIncredibles.getId(),entity4.getId());
        movieService.addCharacterInMovie(theIncredibles.getId(),entity5.getId());

        movieService.addCharacterInMovie(mickeyMouseThePicture.getId(),entity6.getId());
        movieService.addCharacterInMovie(mickeyMouseThePicture.getId(),entity7.getId());
        movieService.addCharacterInMovie(mickeyMouseThePicture.getId(),entity8.getId());

        movieService.addCharacterInMovie(myMix.getId(),entity9.getId());
        movieService.addCharacterInMovie(myMix.getId(),entity10.getId());
        movieService.addCharacterInMovie(myMix.getId(),entity6.getId());
        movieService.addCharacterInMovie(myMix.getId(),entity1.getId());
    }

    @AfterEach
    void tearDown() {
        movieService.deleteAll();
        characterService.deleteAll();
        genderService.deleteAll();
    }

    @Test
    void cuestion() throws Exception{
        //Para eliminar una pelicual primero se debe eliminar a todos sus personajes,

        Long theIncrediblesId = theIncredibles.getId();
        Long mickeyMouseThePictureId = mickeyMouseThePicture.getId();
        Long myMixId = myMix.getId();

        assertEquals(5,movieService.findById(theIncrediblesId).getGenderEntities().size());
        assertEquals(3,movieService.findById(mickeyMouseThePictureId).getGenderEntities().size());
        assertEquals(4,movieService.findById(myMixId).getGenderEntities().size());

        assertEquals(5,movieService.findById(theIncrediblesId).getCharacterEntities().size());
        assertEquals(3,movieService.findById(mickeyMouseThePictureId).getCharacterEntities().size());
        assertEquals(4,movieService.findById(myMixId).getCharacterEntities().size());

        assertEquals(3,movieService.getMovies().size());
        assertEquals(6,genderService.getGenders().size());
        assertEquals(10,characterService.getCharacters().size());
    }
}
