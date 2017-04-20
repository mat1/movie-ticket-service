package com.zuehlke.movie;

import java.util.List;

@SuppressWarnings("unused")
public class MovieDetail {
    private final long id;
    private final String title;
    private final String poster;
    private final String plot;
    private final int year;
    private final String genre;
    private final List<Rating> ratings;

    MovieDetail(long id, String title, String poster, String plot, int year, String genre, List<Rating> ratings) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.plot = plot;
        this.year = year;
        this.genre = genre;
        this.ratings = ratings;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster() {
        return poster;
    }

    public String getPlot() {
        return plot;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public List<Rating> getRatings() {
        return ratings;
    }
}
