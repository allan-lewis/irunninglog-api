package com.irunninglog.api.service;

public abstract class AbstractRequest<T extends AbstractRequest> {

    @SuppressWarnings("unchecked")
    private final T myself = (T) this;

    private long id;

    public final long getId() {
        return id;
    }

    public final T setId(long id) {
        this.id = id;
        return myself;
    }

}
