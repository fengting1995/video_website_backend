package com.rf.service.impl;

import com.rf.dao.TypeDao;
import com.rf.model.TypeModel;
import com.rf.result.ReturnResult;
import com.rf.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Override
    public ReturnResult<List<TypeModel>> selectAll() {
        return ReturnResult.success(typeDao.selectAll());
    }
}
