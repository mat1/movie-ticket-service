package com.zuehlke.movie.rating;

import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface RatingApiClient {
    @RequestLine("GET /api/v1/ratings/{id}")
    List<RatingResponse> getRatingsById(@Param("id") long id);
}
