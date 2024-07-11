package com.rf.controller;

import com.rf.result.ReturnResult;
import com.rf.service.IndexTreeService;
import com.rf.utils.RequestUtils;
import com.rf.vo.IndexTreeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;


@RestController
@RequestMapping("/index")
public class IndexTreeController {

    Logger logger = LoggerFactory.getLogger(IndexTreeController.class);

    @Autowired
    private IndexTreeService indexTreeService;

    @RequestMapping("/indexTree")
    public ReturnResult<IndexTreeVo> getIndexTree(){
        logger.info("ip:" + RequestUtils.getRemoteIp() + "get Index Tree");
        return indexTreeService.getIndexTree();
    }


}
