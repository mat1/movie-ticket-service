package com.zuehlke.movie.movieservice;

import com.zuehlke.movie.Movie;
import com.zuehlke.movie.util.RestClientFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MovieServiceAdapter {

    private final MovieServiceApiClient moviesApiClient;

    public MovieServiceAdapter(String url) {
        moviesApiClient = RestClientFactory.createClient(url, MovieServiceApiClient.class);
    }

    public List<Movie> getAll() {
        List<MovieServiceResponse> movies = moviesApiClient.getMovies();

        return movies.stream()
                .map(Movie::from)
                .collect(toList());
    }
}
