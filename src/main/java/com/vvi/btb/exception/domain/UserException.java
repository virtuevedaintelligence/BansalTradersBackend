package com.vvi.btb.exception.domain;

public class UserException extends Throwable {
    private String explain;
    public UserException(String message, String explain) {
        super(message);
    }

    public String getExplain() {
        return explain;
    }
}
