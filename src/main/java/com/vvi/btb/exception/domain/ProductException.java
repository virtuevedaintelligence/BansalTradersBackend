package com.vvi.btb.exception.domain;

public class ProductException extends Throwable {
    private String explain;
    public ProductException(String message, String explain) {
        super(message);
        this.explain=explain;
    }

    public String getExplain() {
        return explain;
    }
}
