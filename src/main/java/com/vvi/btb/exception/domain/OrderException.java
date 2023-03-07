package com.vvi.btb.exception.domain;

public class OrderException extends Throwable {
    private String explain;
    public OrderException(String message, String explain) {
        super(message);
        this.explain=explain;
    }

    public String getExplain() {
        return explain;
    }
}
