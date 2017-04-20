package com.zuehlke.movie;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerComponentTest {

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;
    }

    @Test
    public void getMovies() throws Exception {
        when().
            get("/api/v1/movies").
        then().
            statusCode(200).
            body("[0].id", equalTo(1)).
            body("[0].title", equalTo("Batman Begins")).
            body("[0].poster", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg")).
            body("[1].id", equalTo(2)).
            body("[2].id", equalTo(3));
    }

    @Test
    public void getMovieById() throws Exception {
        when().
            get("/api/v1/movies/1").
        then().
            statusCode(200).
            body("id", equalTo(1)).
            body("title", equalTo("Batman Begins")).
            body("poster", equalTo("https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg")).
            body("plot", equalTo("After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.")).
            body("year", equalTo(2005)).
            body("genre", equalTo("Action")).
            body("ratings.source", hasItems("Internet Movie Database", "Rotten Tomatoes")).
            body("ratings.value", hasItems("8.3/10", "84%"));
    }

}