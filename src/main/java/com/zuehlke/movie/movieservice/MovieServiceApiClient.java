package com.zuehlke.movie.movieservice;

import feign.RequestLine;

import java.util.List;

public interface MovieServiceApiClient {
    @RequestLine("GET /api/v1/movies")
    List<MovieServiceResponse> getMovies();
}
