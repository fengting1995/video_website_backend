package com.rf.controller;


import com.rf.model.TypeModel;
import com.rf.param.VideoQueryParam;
import com.rf.result.PageData;
import com.rf.result.ReturnResult;
import com.rf.service.TypeService;
import com.rf.vo.VideoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/type")
@RestController
public class TypeController{

    @Autowired
    private TypeService typeService;

    @RequestMapping(method = RequestMethod.GET,path = "/getAll")
    public ReturnResult<List<TypeModel>> getAll(){
           return typeService.selectAll();
    }






}
