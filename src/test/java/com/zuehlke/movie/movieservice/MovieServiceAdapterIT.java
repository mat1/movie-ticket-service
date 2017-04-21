package com.zuehlke.movie.movieservice;

import com.zuehlke.movie.Movie;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class MovieServiceAdapterIT {

    static {
        System.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "5000");
    }

    @Test
    public void getAll() throws Exception {
        MovieServiceAdapter movieServiceAdapter = new MovieServiceAdapter("https://movie-service.herokuapp.com/");

        List<Movie> movies = movieServiceAdapter.getAll();

        assertThat(movies, hasSize(7));
        assertThat(movies, hasItem(new Movie(1, "Batman Begins", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg")));
    }
}