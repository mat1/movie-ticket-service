package com.zuehlke.movie;

import com.zuehlke.movie.movieservice.MovieServiceResponse;

@SuppressWarnings("unused")
public class Movie {
    private final long id;
    private final String title;
    private final String poster;

    public Movie(long id, String title, String poster) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return id == movie.id && title.equals(movie.title) && poster.equals(movie.poster);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + poster.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }

    public static Movie from(MovieServiceResponse movieServiceResponse) {
        return new Movie(movieServiceResponse.getId(), movieServiceResponse.getTitle(), movieServiceResponse.getPoster());
    }
}
