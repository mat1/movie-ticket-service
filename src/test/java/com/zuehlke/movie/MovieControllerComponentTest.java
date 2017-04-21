package com.zuehlke.movie;

import com.zuehlke.movie.movieservice.MovieServiceAdapter;
import com.zuehlke.movie.rating.RatingAdapter;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.when;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Matchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MovieControllerComponentTest {

    @MockBean
    private MovieServiceAdapter movieServiceAdapter;

    @MockBean
    private RatingAdapter ratingAdapter;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception {
        List<Movie> movies = asList(
                new Movie(1, "Batman Begins", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg"),
                new Movie(2, "Ted", "https://images-na.ssl-images-amazon.com/images/M/MV5BMTQ1OTU0ODcxMV5BMl5BanBnXkFtZTcwOTMxNTUwOA@@._V1_SX300.jpg"),
                new Movie(3, "Inception", "https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_SX300.jpg")
        );

        MovieDetail movieDetail = new MovieDetail(1,
                "Batman Begins",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg",
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.",
                2005,
                "Action",
                asList(new Rating("Internet Movie Database", "8.3/10"), new Rating("Rotten Tomatoes", "84%")));

        Mockito.when(movieServiceAdapter.getAll()).thenReturn(movies);
        Mockito.when(movieServiceAdapter.getMovieById(anyLong())).thenReturn(Optional.of(movieDetail));

        Mockito.when(ratingAdapter.getRatingsById(anyLong())).thenReturn(movieDetail.getRatings());

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