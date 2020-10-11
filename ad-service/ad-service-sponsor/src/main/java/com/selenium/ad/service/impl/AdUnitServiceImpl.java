package com.selenium.ad.service.impl;

import com.selenium.ad.constant.Constants;
import com.selenium.ad.dao.AdPlanRepository;
import com.selenium.ad.dao.AdUnitRepository;
import com.selenium.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.selenium.ad.dao.unit_condition.AdUnitItRepository;
import com.selenium.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.selenium.ad.entity.AdPlan;
import com.selenium.ad.entity.AdUnit;
import com.selenium.ad.entity.unit_condition.AdUnitDistrict;
import com.selenium.ad.entity.unit_condition.AdUnitIt;
import com.selenium.ad.entity.unit_condition.AdUnitKeyword;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.IAdUnitService;
import com.selenium.ad.vo.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private final AdPlanRepository planRepository;

    private final AdUnitRepository adUnitRepository;

    private final AdUnitKeywordRepository adUnitKeywordRepository;

    private final AdUnitItRepository adUnitItRepository;

    private final AdUnitDistrictRepository adUnitDistrictRepository;

    public AdUnitServiceImpl(AdPlanRepository planRepository, AdUnitRepository adUnitRepository, AdUnitKeywordRepository adUnitKeywordRepository, AdUnitItRepository adUnitItRepository, AdUnitDistrictRepository adUnitDistrictRepository) {
        this.planRepository = planRepository;
        this.adUnitRepository = adUnitRepository;
        this.adUnitKeywordRepository = adUnitKeywordRepository;
        this.adUnitItRepository = adUnitItRepository;
        this.adUnitDistrictRepository = adUnitDistrictRepository;
    }

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

    @Override
    public AdUnitKeywordResponse createUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        //java8 stream->map->collect
        List<Long> unitIds = request.getUnitKeywords().stream()
                .map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<Long> ids = Collections.emptyList();
        List<AdUnitKeyword> unitKeywords = new ArrayList<>();

        //java8 forEach+lambda
        if (!CollectionUtils.isEmpty(request.getUnitKeywords())) {
            request.getUnitKeywords().forEach(i -> unitKeywords.add(new AdUnitKeyword(i.getUnitId(), i.getKeyword())));
        }

        ids = adUnitKeywordRepository.saveAll(unitKeywords).stream()
                .map(AdUnitKeyword::getId)
                .collect(Collectors.toList());

        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public AdUnitItResponse createUnitIt(AdUnitItRequest request) throws AdException {
        //java8 stream->map->collect
        List<Long> unitIds = request.getUnitIts().stream()
                .map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = adUnitItRepository
                .saveAll(unitIts)
                .stream()
                .map(AdUnitIt::getId)
                .collect(Collectors.toList());

        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        List<Long> unitIds = request.getUnitDistricts()
                .stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(d -> unitDistricts.add(
                new AdUnitDistrict(d.getUnitId(), d.getProvince(), d.getCity())
        ));
        List<Long> ids = adUnitDistrictRepository
                .saveAll(unitDistricts)
                .stream()
                .map(AdUnitDistrict::getId)
                .collect(Collectors.toList());

        return new AdUnitDistrictResponse(ids);
    }

    //判断传递进来的unitIds是否已经存在
    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        //通过HashSet去除重复的unitId
        return adUnitRepository.findAllById(unitIds).size() == new HashSet<>(unitIds).size();
    }
}
