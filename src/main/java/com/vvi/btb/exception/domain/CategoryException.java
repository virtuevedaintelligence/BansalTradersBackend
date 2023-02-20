package com.vvi.btb.exception.domain;

public class CategoryException extends Throwable {
    private String explain;
    public CategoryException(String message, String explain) {
        super(message);
        this.explain=explain;
    }

    public String getExplain() {
        return explain;
    }
}
