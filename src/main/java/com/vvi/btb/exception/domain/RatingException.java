package com.vvi.btb.exception.domain;

public class RatingException extends Throwable {
    private String explain;
    public RatingException(String message, String explain) {
        super(message);
    }

    public String getExplain() {
        return explain;
    }
}
