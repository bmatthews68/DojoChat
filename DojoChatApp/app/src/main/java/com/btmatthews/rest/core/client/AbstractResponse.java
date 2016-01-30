package com.btmatthews.rest.core.client;

public abstract class AbstractResponse implements Response {

    private String result;

    protected AbstractResponse() {
        this("OK");
    }

    protected AbstractResponse(final String result) {
        this.result = result;
    }

    public final String getResult() {
        return result;
    }

    public final void setResult(final String result) {
        this.result = result;
    }
}
