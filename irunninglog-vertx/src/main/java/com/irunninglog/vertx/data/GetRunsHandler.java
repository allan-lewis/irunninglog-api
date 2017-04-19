package com.irunninglog.vertx.data;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.data.IGetDataRequest;
import com.irunninglog.api.data.IGetRunsResponse;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.vertx.AbstractProfileIdRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;

@RouteHandler(endpoint = Endpoint.RUNS_GET, request = IGetDataRequest.class, response = IGetRunsResponse.class)
public final class GetRunsHandler extends AbstractProfileIdRouteHandler<IGetDataRequest, IGetRunsResponse> {

    public GetRunsHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper);
    }

}
