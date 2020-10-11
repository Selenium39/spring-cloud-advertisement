package com.selenium.ad.controller;

import com.alibaba.fastjson.JSON;
import com.selenium.ad.entity.AdPlan;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.IAdPlanService;
import com.selenium.ad.vo.AdPlanGetRequest;
import com.selenium.ad.vo.AdPlanRequest;
import com.selenium.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AdPlanController {
    private final IAdPlanService adPlanService;


    public AdPlanController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: createAdPlan->{}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(@RequestBody AdPlanGetRequest request) throws AdException {
        log.info("ad-sponsor: getAdPlanByIds->{}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PutMapping("/update/adPlan")
    public AdPlanResponse updateAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: updateAdPlan->{}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor: deleteAdPlan->{}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }

}
