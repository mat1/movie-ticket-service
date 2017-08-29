package com.zuehlke.movie.movieservice;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.zuehlke.movie.Movie;
import com.zuehlke.movie.MovieDetail;
import com.zuehlke.movie.util.RestClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class MovieServiceAdapter implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String url;
    private final MovieServiceApiClient moviesApiClient;

    public MovieServiceAdapter(String url) {
        this.url = url;
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

    /**
     * Set management.security.enabled=false to see more details
     */
    @Override
    public Health health() {
        try {
            moviesApiClient.getHealthStatus();

            return Health.up()
                    .withDetail("Endpoint", url)
                    .build();
        } catch (Exception ex) {
            return Health.down()
                    .withDetail("Endpoint", url)
                    .build();
        }
    }
}
