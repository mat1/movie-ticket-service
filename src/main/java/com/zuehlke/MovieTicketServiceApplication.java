package com.zuehlke;

import com.zuehlke.movie.movieservice.MovieServiceAdapter;
import com.zuehlke.movie.rating.RatingAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class MovieTicketServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieTicketServiceApplication.class, args);
    }

    @Bean
    public MovieServiceAdapter movieServiceAdapter(@Value("${endpoint.movie-service}") String url) {
        return new MovieServiceAdapter(url);
    }

    @Bean
    public RatingAdapter ratingAdapter(@Value("${endpoint.movie-rating-service}") String url) {
        return new RatingAdapter(url);
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }

}
