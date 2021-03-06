package com.zuehlke.movie.rating;

import com.zuehlke.movie.Rating;
import com.zuehlke.movie.util.RestClientFactory;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class RatingAdapter {

    private final RatingApiClient ratingApiClient;

    public RatingAdapter(String url) {
        RatingApiClient fallback = (id) -> emptyList();
        ratingApiClient = RestClientFactory.createClientWithFallback(url, RatingApiClient.class, fallback);
    }

    public List<Rating> getRatingsById(long id) {
        List<RatingResponse> ratings = ratingApiClient.getRatingsById(id);

        return ratings.stream()
                .map(Rating::from)
                .collect(toList());
    }
}
