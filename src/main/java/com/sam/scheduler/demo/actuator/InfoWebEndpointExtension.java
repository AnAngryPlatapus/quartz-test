package com.sam.scheduler.demo.actuator;

import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.boot.actuate.endpoint.web.annotation.EndpointWebExtension;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EndpointWebExtension(endpoint = InfoEndpoint.class)
public class InfoWebEndpointExtension  {

    private final InfoEndpoint delegate;

    public InfoWebEndpointExtension() {
        InfoContributor i = (b) -> b.withDetail("Info", "Details Object");
        InfoContributor i2 = (b) -> b.withDetail("Info2", "Details Object2");

        delegate = new InfoEndpoint(List.of(i, i2));
    }
    // standard constructor

    @ReadOperation
    public WebEndpointResponse<Map> info() {
        log.info("Sup dawg");

        Map<String, Object> info = this.delegate.info();
        Integer status = getStatus(info);
        return new WebEndpointResponse<>(info, status);
    }

    private Integer getStatus(Map<String, Object> info) {
        // return 5xx if this is a snapshot
        return 200;
    }
}