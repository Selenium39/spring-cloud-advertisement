package com.selenium.ad.service.impl;


import com.selenium.ad.dao.CreativeRepository;
import com.selenium.ad.entity.Creative;
import com.selenium.ad.exception.AdException;
import com.selenium.ad.service.ICreativeService;
import com.selenium.ad.vo.CreativeRequest;
import com.selenium.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreativeServiceImpl implements ICreativeService {

    private final CreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(CreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public CreativeResponse createCreative(CreativeRequest request) throws AdException {
        // #todo 校验完善

        Creative creative = creativeRepository.save(request.convertToEntity());
        return new CreativeResponse(creative.getId(), creative.getName());
    }
}
