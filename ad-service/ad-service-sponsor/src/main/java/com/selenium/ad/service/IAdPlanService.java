package com.selenium.ad.service;

import com.selenium.ad.entity.AdPlan;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.vo.AdPlanGetRequest;
import com.selenium.ad.vo.AdPlanRequest;
import com.selenium.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {

    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;

    void deleteAdPlan(AdPlanRequest request) throws AdException;
}
