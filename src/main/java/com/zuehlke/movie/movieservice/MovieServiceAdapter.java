package com.zuehlke.movie.movieservice;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.zuehlke.movie.Movie;
import com.zuehlke.movie.MovieDetail;
import com.zuehlke.movie.util.RestClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class MovieServiceAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
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

    public Optional<MovieDetail> getMovieById(long id) {
        try {
            MovieServiceResponse movieServiceResponse = moviesApiClient.getMovieById(id);
            MovieDetail movieDetail = MovieDetail.from(movieServiceResponse);
            return Optional.of(movieDetail);
        } catch (HystrixRuntimeException ex) {
            logger.warn("Error in get movie with id={}.", id, ex);
        }
        return Optional.empty();
    }
}
