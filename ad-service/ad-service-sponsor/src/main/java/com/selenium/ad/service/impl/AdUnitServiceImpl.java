package com.selenium.ad.service.impl;

import com.selenium.ad.constant.Constants;
import com.selenium.ad.dao.AdPlanRepository;
import com.selenium.ad.dao.AdUnitRepository;
import com.selenium.ad.entity.AdPlan;
import com.selenium.ad.entity.AdUnit;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.IAdUnitService;
import com.selenium.ad.vo.AdUnitRequest;
import com.selenium.ad.vo.AdUnitResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class AdUnitServiceImpl implements IAdUnitService {

    @Autowired
    private AdPlanRepository planRepository;

    @Autowired
    private AdUnitRepository adUnitRepository;

    @Override
    public AdUnitResponse createUnit(AdUnitRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> adPlan = planRepository.findById(request.getPlanId());

        if (!adPlan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_ERROR);
        }

        AdUnit oldAdUnit = adUnitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldAdUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_UNIT_ERROR);
        }

        AdUnit newAdUnit = new AdUnit(request.getPlanId(), request.getUnitName(), request.getPositionType(), request.getBudget());


        return new AdUnitResponse(newAdUnit.getId(), newAdUnit.getUnitName());
    }
}
