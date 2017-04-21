package com.zuehlke.movie;

import com.zuehlke.movie.movieservice.MovieServiceAdapter;
import com.zuehlke.movie.rating.RatingAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static java.util.Arrays.asList;

@RequestMapping("/api/v1/")
@Controller
public class MovieController {

    private final MovieServiceAdapter movieServiceAdapter = new MovieServiceAdapter("https://movie-service.herokuapp.com/");
    private final RatingAdapter ratingAdapter = new RatingAdapter("https://movie-rating-service.herokuapp.com/");

    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return movieServiceAdapter.getAll();
    }

    @GetMapping("/movies/{id}")
    @ResponseBody
    public MovieDetail getMovieById(@PathVariable("id") long id) {
        return new MovieDetail(1,
                "Batman Begins",
                "https://images-na.ssl-images-amazon.com/images/M/MV5BNTM3OTc0MzM2OV5BMl5BanBnXkFtZTYwNzUwMTI3._V1_SX300.jpg",
                "After training with his mentor, Batman begins his fight to free crime-ridden Gotham City from the corruption that Scarecrow and the League of Shadows have cast upon it.",
                2005,
                "Action",
                asList(new Rating("Internet Movie Database", "8.3/10"), new Rating("Rotten Tomatoes", "84%")));
    }
}
