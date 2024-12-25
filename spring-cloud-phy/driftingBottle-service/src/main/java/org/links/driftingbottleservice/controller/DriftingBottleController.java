package org.links.driftingbottleservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/driftingBottle")
@RefreshScope
public class DriftingBottleController {
    @Value("${config.info}")
    private String myInfo;

    @GetMapping(value = "/test")
    public String test() {
        return "this is my driftingBottle-service" ;
    }

    @GetMapping(value = "/test/getConfigInfo")
    public String getConfigInfo() {
        return myInfo ;
    }

}
