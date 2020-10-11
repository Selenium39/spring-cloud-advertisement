package com.selenium.ad.service;

import com.selenium.ad.exception.AdException;
import com.selenium.ad.vo.CreativeRequest;
import com.selenium.ad.vo.CreativeResponse;

public interface ICreativeService {
    CreativeResponse createCreative(CreativeRequest request) throws AdException;
}
