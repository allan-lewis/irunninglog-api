package com.irunninglog.spring.ping;

import com.irunninglog.api.factory.IFactory;
import com.irunninglog.api.ping.IPing;
import com.irunninglog.api.ping.IPingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
final class PingService implements IPingService {

    private final IFactory factory;

    @Autowired
    public PingService(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public IPing ping() {
        return factory.get(IPing.class).setTimestamp(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(ZonedDateTime.now()));
    }

}
