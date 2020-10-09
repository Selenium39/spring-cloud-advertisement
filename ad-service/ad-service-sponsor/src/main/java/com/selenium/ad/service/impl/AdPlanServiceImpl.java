package com.selenium.ad.service.impl;

import com.selenium.ad.constant.CommonStatus;
import com.selenium.ad.constant.Constants;
import com.selenium.ad.dao.AdPlanRepository;
import com.selenium.ad.dao.AdUserRepository;
import com.selenium.ad.entity.AdPlan;
import com.selenium.ad.entity.AdUser;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.IAdPlanService;
import com.selenium.ad.utils.CommonUtils;
import com.selenium.ad.vo.AdPlanGetRequest;
import com.selenium.ad.vo.AdPlanRequest;
import com.selenium.ad.vo.AdPlanResponse;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private final AdUserRepository userRepository;

    private final AdPlanRepository planRepository;

    public AdPlanServiceImpl(AdUserRepository userRepository, AdPlanRepository planRepository) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
    }


    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {
        if (!request.createValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        //确保关联的User是存在的
        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_ERROR);
        }
        AdPlan oldPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_PLAN_ERROR);
        }
        AdPlan newAdPlan = planRepository.save(new AdPlan(request.getUserId(), request.getPlanName(), CommonUtils.parseStringToDate(request.getStartDate()), CommonUtils.parseStringToDate(request.getEndDate())));

        return new AdPlanResponse(newAdPlan.getId(), newAdPlan.getPlanName());
    }

    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());
    }

    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {
        if (!request.updateValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_ERROR);
        }
        if (request.getPlanName() != null) {
            plan.setPlanName(request.getPlanName());
        }
        if (request.getStartDate() != null) {
            plan.setStartDate(CommonUtils.parseStringToDate(request.getStartDate()));
        }
        if (request.getEndDate() != null) {
            plan.setEndDate(CommonUtils.parseStringToDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());
        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteValidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        plan.setUpdateTime(new Date());
        //不是真的删除
        planRepository.save(plan);
    }
}
