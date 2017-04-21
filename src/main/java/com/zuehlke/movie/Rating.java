package com.zuehlke.movie;

import com.zuehlke.movie.rating.RatingResponse;

@SuppressWarnings("unused")
public class Rating {
    private final String source;
    private final String value;

    public Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

    public static Rating from(RatingResponse ratingResponse) {
        return new Rating(ratingResponse.getSource(), ratingResponse.getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        return source.equals(rating.source) && value.equals(rating.value);
    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "source='" + source + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
