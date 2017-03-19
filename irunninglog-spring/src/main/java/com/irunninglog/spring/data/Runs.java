package com.irunninglog.spring.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.irunninglog.api.data.IRun;
import com.irunninglog.api.data.IRuns;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
final class Runs implements IRuns {

    private final List<IRun> runList = new ArrayList<>();

    @Override
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = Run.class)
    public List<IRun> getRuns() {
        return runList;
    }

}
