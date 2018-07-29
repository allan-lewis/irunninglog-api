package com.irunninglog.vertx.statistics;

import com.irunninglog.api.Endpoint;
import com.irunninglog.api.IRequest;
import com.irunninglog.api.IResponse;
import com.irunninglog.api.ResponseStatus;
import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.mapping.IMapper;
import com.irunninglog.api.security.AuthnException;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.date.ApiDate;
import com.irunninglog.vertx.AbstractRequestResponseVerticle;
import com.irunninglog.vertx.EndpointVerticle;

import java.time.LocalDate;

@EndpointVerticle(endpoint = Endpoint.GET_STATISTICS)
public class GetStatisticsVerticle extends AbstractRequestResponseVerticle {

    private final IStatisticsService service;
    private final ApiDate apiDate;

    public GetStatisticsVerticle(IFactory factory, IMapper mapper, IStatisticsService service, ApiDate apiDate) {
        super(factory, mapper);

        this.service = service;
        this.apiDate = apiDate;
    }

    @Override
    protected void handle(IRequest request, IResponse response) throws AuthnException {
        String start = request.getMap().get("startDate");
        String end = request.getMap().get("endDate");

        LocalDate startDate = start == null || start.trim().isEmpty() ? null : apiDate.parseLocalDate(start);
        LocalDate endDate = end == null || end.trim().isEmpty() ? null : apiDate.parseLocalDate(end);

        response.setStatus(ResponseStatus.OK).setBody(service.get(request.getUser(), request.getOffset(), startDate, endDate));
    }

}
