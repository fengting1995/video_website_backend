package com.rf.service;

import com.rf.model.TypeModel;
import com.rf.result.ReturnResult;

import java.util.List;

public interface TypeService {

    public ReturnResult<List<TypeModel>> selectAll();

}
