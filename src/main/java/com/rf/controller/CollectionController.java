package com.rf.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.rf.constant.ResponseCode;
import com.rf.model.ThirdCollectionApiModel;
import com.rf.model.TypeModel;
import com.rf.result.ReturnResult;
import com.rf.service.ThirdCollectionApiService;
import com.rf.vo.CollectTaskInfoVo;
import com.rf.vo.ThirdCollectionApiVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@RestController
@RequestMapping("/admin/collection")
public class CollectionController {

    private Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @Value("${collection.url}")
    private String collectionUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ThirdCollectionApiService thirdCollectionApiService;


    @RequestMapping("/getAll")
    public ReturnResult<List<ThirdCollectionApiVo>> selectAll(){
        return thirdCollectionApiService.getAll();
    }

    @RequestMapping("/updateOrCreate")
    public ReturnResult<Integer> updateOrCreate(@RequestBody ThirdCollectionApiVo thirdCollectionApiVo){
        return thirdCollectionApiService.updateOrCreate(thirdCollectionApiVo);
    }


    @RequestMapping("/startCollect")
    public ReturnResult<String> startTask(@RequestParam("key") String key,@RequestParam("hour") String hour){

        if(StringUtils.isEmpty(key)){
            return null;
        }
        String url = collectionUrl + "/api/collectionVideo/start_task?key=" + key + "&hour=" + hour;
        String result = restTemplate.getForObject(url,String.class);
        logger.info("url>"+url);
        return ReturnResult.success(result);

    }



    @RequestMapping("/getTaskByKey")
    public ReturnResult<CollectTaskInfoVo>getTaskByKey(String key){

        String url = collectionUrl + "/api/collectionVideo/get_task_by_key?key=" +key;
        CollectTaskInfoVo collectTaskInfoVo = restTemplate.getForObject(url,CollectTaskInfoVo.class);
        logger.info("url>"+url);
        return ReturnResult.success(collectTaskInfoVo);

    }

    @RequestMapping("/getTypesByKey")
    public ReturnResult<List<TypeModel>> getTypeByKey(String key){
        String url = collectionUrl + "/api/collectionVideo/get_types_by_key?key=" +key;
        String str = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = JSON.parseObject(str).getJSONArray("data");
        List<TypeModel>apiTypeModels = jsonArray.toJavaList(TypeModel.class);
        return ReturnResult.success(apiTypeModels);
    }




}
