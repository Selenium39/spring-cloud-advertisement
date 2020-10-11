package com.selenium.ad.service;

import com.selenium.ad.exception.AdException;
import com.selenium.ad.vo.AdUnitRequest;
import com.selenium.ad.vo.AdUnitResponse;

public interface IAdUnitService {
    AdUnitResponse createUnit(AdUnitRequest request) throws AdException;
}
