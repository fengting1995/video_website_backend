package com.rf.controller;



import com.rf.param.PageParam;
import com.rf.param.VideoQueryParam;
import com.rf.result.PageData;
import com.rf.result.ReturnResult;
import com.rf.service.VideoService;
import com.rf.vo.VideoDetailVo;
import com.rf.vo.VideoListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/video")
public class VideoController{

    @Autowired
    private VideoService videoService;

    @RequestMapping("/detail")
    public ReturnResult<VideoDetailVo> getVideoDetail(@RequestParam("av") String av){
        return videoService.getDetailByAv(av);
    }

    @RequestMapping("/pageByQuery")
    public ReturnResult<PageData<VideoListVo>> getVideoPage(VideoQueryParam videoQueryParam, PageParam pageParam){

        if(StringUtils.isBlank(videoQueryParam.getKw())){
            videoQueryParam.setKw(null);
        }else {
            videoQueryParam.setKw(videoQueryParam.getKw().trim());
        }
        return videoService.pageQuery(videoQueryParam,pageParam);
    }

//    @RequestMapping(method = RequestMethod.GET,path = "/getVideoByMultipleAttributes")
//    public ReturnResult<PageData<VideoListVo>> getVideoByMultipleAttributes(VideoQueryParam videoQueryParam, String area,
//                                                                        String lang, String year,
//                                                                       String type){
//
//    }


}
