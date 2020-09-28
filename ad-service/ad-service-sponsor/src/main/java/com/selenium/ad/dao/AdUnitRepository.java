package com.selenium.ad.dao;

import com.selenium.ad.entity.AdUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdUnitRepository extends JpaRepository<AdUnit, Long> {

    AdUnit findByPlanIdAndUnitName(Long planId, String unitName);

    List<AdUnit> findAllByUnitStatus(Integer unitStatus);
}
