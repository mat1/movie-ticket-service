package com.zuehlke.movie;

import com.zuehlke.movie.movieservice.MovieServiceAdapter;
import com.zuehlke.movie.rating.RatingAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8000")
@RequestMapping("/api/v1/")
@Controller
public class MovieController {

    private final MovieServiceAdapter movieServiceAdapter;
    private final RatingAdapter ratingAdapter;

    public MovieController(MovieServiceAdapter movieServiceAdapter, RatingAdapter ratingAdapter) {
        this.movieServiceAdapter = movieServiceAdapter;
        this.ratingAdapter = ratingAdapter;
    }

    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return movieServiceAdapter.getAll();
    }

    @GetMapping("/movies/{id}")
    @ResponseBody
    public MovieDetail getMovieById(@PathVariable("id") long id) {
        Optional<MovieDetail> movieDetail = movieServiceAdapter.getMovieById(id);
        List<Rating> ratings = ratingAdapter.getRatingsById(id);

        return movieDetail.map(m -> m.setRatings(ratings))
                .orElseThrow(() -> new MovieNotFoundException("No movie found with id=" + id));
    }
}
