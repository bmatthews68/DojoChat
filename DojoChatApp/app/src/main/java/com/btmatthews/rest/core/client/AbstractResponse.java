package com.btmatthews.rest.core.client;

public abstract class AbstractResponse implements Response {

    private String result;

    protected AbstractResponse() {
        this("OK");
    }

    protected AbstractResponse(final String result) {
        this.result = result;
    }

    public final boolean isOk() { return result.equals(OK); }

    public final boolean isError() { return result.equals(ERROR); }

    public final String getResult() {
        return result;
    }

    public final void setResult(final String result) {
        this.result = result;
    }
}
