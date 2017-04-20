package com.zuehlke.movie;

@SuppressWarnings("unused")
public class Rating {
    private final String source;
    private final String value;

    Rating(String source, String value) {
        this.source = source;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public String getValue() {
        return value;
    }

}
