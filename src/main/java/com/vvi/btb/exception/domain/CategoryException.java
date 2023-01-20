package com.vvi.btb.exception.domain;

public class CategoryException extends Throwable {
    private String explain;
    public CategoryException(String message, String explain) {
        super(message);
    }

    public String getExplain() {
        return explain;
    }
}
