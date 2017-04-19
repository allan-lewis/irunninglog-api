package com.irunninglog.vertx.identity;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.identity.IIdentityRequest;
import com.irunninglog.api.identity.IIdentityResponse;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.IUser;
import com.irunninglog.vertx.AbstractRouteHandler;
import com.irunninglog.vertx.RouteHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.RoutingContext;

@RouteHandler(endpoint = Endpoint.IDENTITY)
public final class IdentityHandler extends AbstractRouteHandler<IIdentityRequest, IIdentityResponse> {

    public IdentityHandler(Vertx vertx, IFactory factory, IMapper mapper) {
        super(vertx, factory, mapper, IIdentityRequest.class, IIdentityResponse.class);
    }

    @Override
    protected void request(IIdentityRequest request, RoutingContext routingContext) {
        super.request(request, routingContext);

        request.setUsername(((IUser) routingContext.get("user")).getUsername());
    }

}