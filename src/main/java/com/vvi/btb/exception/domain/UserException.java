package com.vvi.btb.exception.domain;

public class UserException extends Throwable {
    private String explain;
    public UserException(String message, String explain) {
        super(message);
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }
}
