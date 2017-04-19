package com.irunninglog.spring.identity;

import com.irunninglog.api.identity.IIdentity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class Identity implements IIdentity {

    private String username;
    private long id;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public IIdentity setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public IIdentity setId(long id) {
        this.id = id;
        return this;
    }

}