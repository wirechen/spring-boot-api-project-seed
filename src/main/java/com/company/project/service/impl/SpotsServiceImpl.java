package com.company.project.service.impl;

import com.company.project.dao.SpotsMapper;
import com.company.project.model.Spots;
import com.company.project.service.SpotsService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2017/11/24.
 */
@Service
@Transactional
public class SpotsServiceImpl extends AbstractService<Spots> implements SpotsService {
    @Resource
    private SpotsMapper spotsMapper;

}
