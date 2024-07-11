package com.rf.service;



import com.rf.model.ThirdCollectionApiModel;
import com.rf.result.ReturnResult;
import com.rf.vo.ThirdCollectionApiVo;

import java.util.List;

public interface ThirdCollectionApiService {

    public ReturnResult<List<ThirdCollectionApiVo>> getAll();

    public ReturnResult<Integer> updateOrCreate(ThirdCollectionApiVo thirdCollectionApiVo);


}
