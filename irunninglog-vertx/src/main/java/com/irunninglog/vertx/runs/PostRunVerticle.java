package com.irunninglog.vertx.runs;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.runs.IRun;
import com.irunninglog.api.runs.IRunsService;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

@EndpointVerticle(endpoint = Endpoint.POST_RUN)
public class PostRunVerticle extends AbstractRequestResponseVerticle {

    private final IRunsService runsService;
    private final IMapper mapper;

    public PostRunVerticle(IFactory factory, IMapper mapper, IRunsService runsService) {
        super(factory, mapper);
        this.runsService = runsService;
        this.mapper = mapper;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        IRun run = mapper.decode(request.getBody(), IRun.class);

        System.out.println();
    }

}
