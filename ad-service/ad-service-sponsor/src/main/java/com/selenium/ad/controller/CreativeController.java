package com.selenium.ad.controller;

import com.alibaba.fastjson.JSON;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.ICreativeService;
import com.selenium.ad.vo.CreativeRequest;
import com.selenium.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeController {

    private final ICreativeService creativeService;

    public CreativeController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/creative")
    public CreativeResponse createCreative(@RequestBody CreativeRequest request) throws AdException {
        log.info("ad-sponsor: createCreative -> {}", JSON.toJSONString(request));
        return creativeService.createCreative(request);
    }
}
