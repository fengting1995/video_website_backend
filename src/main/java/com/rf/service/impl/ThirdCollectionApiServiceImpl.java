package com.rf.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rf.dao.ThirdCollectionApiDao;
import com.rf.dao.TypeDao;
import com.rf.model.ThirdCollectionApiModel;
import com.rf.model.TypeModel;
import com.rf.result.ReturnResult;
import com.rf.service.ThirdCollectionApiService;
import com.rf.vo.ThirdCollectionApiVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.*;


@Service
public class ThirdCollectionApiServiceImpl implements ThirdCollectionApiService {

    @Autowired
    private ThirdCollectionApiDao thirdCollectionApiDao;

    @Autowired
    private TypeDao typeDao;

    @Override
    public ReturnResult<List<ThirdCollectionApiVo>>getAll() {
        List<ThirdCollectionApiModel> thirdCollectionApis = thirdCollectionApiDao.selectAll();
        List<TypeModel> allType = typeDao.selectAll();
        HashMap<Integer, TypeModel> typeIdToType = new HashMap<>();
        for (TypeModel type : allType) {
            typeIdToType.put(type.getId(), type);
        }
        List<ThirdCollectionApiVo> thirdCollectionApiVos = new ArrayList<>();
        for (ThirdCollectionApiModel model : thirdCollectionApis) {
            thirdCollectionApiVos.add(convertToVo(model, typeIdToType));
        }
        return ReturnResult.success(thirdCollectionApiVos);
    }


    @Override
    public ReturnResult<Integer> updateOrCreate(ThirdCollectionApiVo thirdCollectionApiVo) {
        ThirdCollectionApiModel model = convertToModel(thirdCollectionApiVo);
        if (null != model.getId()) {
            thirdCollectionApiDao.update(model);
        } else {
            thirdCollectionApiDao.insert(model);
        }
        return ReturnResult.success(model.getId());
    }



    private ThirdCollectionApiVo convertToVo(ThirdCollectionApiModel model, HashMap<Integer, TypeModel> typeIdToType) {
        ThirdCollectionApiVo thirdCollectionApiVo = new ThirdCollectionApiVo();
        thirdCollectionApiVo.setId(model.getId());
        thirdCollectionApiVo.setKey(model.getKey());
        thirdCollectionApiVo.setUrl(model.getUrl());
        thirdCollectionApiVo.setName(model.getName());
        List<ThirdCollectionApiVo.BindIdVo> bindIdVos = JSON.parseArray(model.getBindId(), ThirdCollectionApiVo.BindIdVo.class);
//        JSONObject bindId = JSON.parseObject(model.getBindId());
//        List<ThirdCollectionApiVo.BindIdVo> bindIdVos = new ArrayList<>();
//        for (Map.Entry<String, Object> entry : bindId.entrySet()){
//            Integer key = Integer.parseInt(entry.getKey());
//            Integer value = Integer.parseInt(entry.getValue().toString());
//            ThirdCollectionApiVo.BindIdVo bindIdVo = new ThirdCollectionApiVo.BindIdVo();
//            bindIdVo.setSystemId(value);
//            bindIdVo.setApiId(key);
//            TypeModel typeModel = typeIdToType.get(bindIdVo.getSystemId());
//            if (null != typeModel) {
//                bindIdVo.setTypeName(typeModel.getName());
//            } else {
//                bindIdVo.setTypeName("");
//            }
//            bindIdVos.add(bindIdVo);
//        }
        thirdCollectionApiVo.setBindId(bindIdVos);
        return thirdCollectionApiVo;
    }

    private ThirdCollectionApiModel convertToModel(ThirdCollectionApiVo vo) {
        ThirdCollectionApiModel model = new ThirdCollectionApiModel();
        model.setId(vo.getId());
        model.setName(vo.getName());
        model.setKey(vo.getKey());
        model.setUrl(vo.getUrl());
        model.setBindId(JSON.toJSONString(vo.getBindId(), SerializerFeature.WriteMapNullValue));
        return model;
    }



}
