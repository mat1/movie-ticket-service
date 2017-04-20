package com.zuehlke.movie;

@SuppressWarnings("unused")
public class Movie {
    private final long id;
    private final String title;
    private final String poster;

    Movie(long id, String title, String poster) {
        this.id = id;
        this.title = title;
        this.poster = poster;
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
}
