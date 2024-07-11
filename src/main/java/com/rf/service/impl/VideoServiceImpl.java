package com.rf.service.impl;

import com.rf.dao.VideoDao;
import com.rf.dao.VideoLinkDao;
import com.rf.elasticSearch.repository.VideoRpository;
import com.rf.model.VideoLinkModel;
import com.rf.model.VideoModel;
import com.rf.param.PageParam;
import com.rf.param.VideoQueryParam;
import com.rf.redis.RedisClient;
import com.rf.redis.VideoKey;
import com.rf.result.PageData;
import com.rf.result.ReturnResult;
import com.rf.service.VideoService;
import com.rf.utils.ConvertUtils;
import com.rf.utils.RequestUtils;
import com.rf.vo.VideoDetailVo;
import com.rf.vo.VideoListVo;
import com.rf.vo.VideoPlayGroupVo;
import com.rf.vo.VideoPlayUrlVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VideoServiceImpl implements VideoService {

    Logger logger = LoggerFactory.getLogger(VideoService.class);

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private VideoLinkDao videoLinkDao;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private VideoRpository videoRpository;


    @Override
    public ReturnResult<VideoDetailVo> getDetailByAv(String av) {

       String key = VideoKey.getVideoByAvKey(av);

       if(StringUtils.isBlank(av)){
           return ReturnResult.fail("请指定av号");
       }

       VideoDetailVo videoDetailVoCache = redisClient.get(key, VideoDetailVo.class);

       if(null!=videoDetailVoCache){
           logger.info("{}, 获取视频: {}", RequestUtils.getRemoteIp(),videoDetailVoCache.getName());
           return ReturnResult.success(videoDetailVoCache);
       }

       VideoModel videoModel =  videoDao.selectByAv(av);
        if (null == videoModel) {
            return ReturnResult.fail("视频不存在");
        }

        List<VideoLinkModel> videoLinkModelList = videoLinkDao.selectByVideoId(videoModel.getId());
        VideoDetailVo videoDetailVo = ConvertUtils.copyProperties(videoModel, VideoDetailVo.class);
        List<VideoPlayGroupVo> videoPlayGroupVoList = linkToPlayGroup(videoLinkModelList);
        videoDetailVo.setVideoPlayGroupList(videoPlayGroupVoList);
        redisClient.set(key, videoDetailVo);
        logger.info("{}, 获取视频: {}", RequestUtils.getRemoteIp(), videoModel.getName());
        return ReturnResult.success(videoDetailVo);
    }


    public ReturnResult<PageData<VideoListVo>> findByMultipleAttributes(VideoQueryParam videoQueryParam,String area, String lang, String year,
                                                                        String type){
        Integer total = videoDao.selectTotalByQueryParam(videoQueryParam);
        List<VideoModel> videoModelList = videoRpository.findByLangAndAreaAndYearAndType(lang, area, year, type);
        List<VideoListVo> videoListVoList = videoModelList.stream().map(VideoListVo::convertFromVideoModel).collect(Collectors.toList());
        PageData<VideoListVo> pageData = new PageData<>(total,videoListVoList);
        return ReturnResult.success(pageData);
    }

    @Override
    public ReturnResult<PageData<VideoListVo>> pageQuery(VideoQueryParam videoQueryParam, PageParam pageParam) {
//        String totalKey = VideoKey.getTotalByQueryParam(videoQueryParam);
//        Integer total = redisClient.get(totalKey,Integer.class);
//        if(total == null){
//            total = getTotal(videoQueryParam);
//        }
        Integer total = videoDao.selectTotalByQueryParam(videoQueryParam);
        logger.info("{} query video list, type {}, kw {}",RequestUtils.getRemoteIp(),videoQueryParam.getTypeId(),
                videoQueryParam.getKw());
        List<VideoModel> videoModelList = videoDao.selectByQuery(videoQueryParam,pageParam.getOffset(),
                pageParam.getPageSize());
        List<VideoListVo> videoListVoList = videoModelList.stream().map(VideoListVo::convertFromVideoModel).collect(Collectors.toList());
        PageData<VideoListVo> pageData = new PageData<>(total,videoListVoList);
        return ReturnResult.success(pageData);

    }

    private List<VideoPlayGroupVo> linkToPlayGroup(List<VideoLinkModel> videoLinkModelList){
        List<VideoPlayGroupVo> videoPlayGroupVoList = new ArrayList<>();

        HashMap<String,VideoPlayGroupVo> stringVideoPlayGroupVoHashMap =new HashMap<>();

        for (VideoLinkModel videoLinkModel:videoLinkModelList){
            ArrayList<VideoPlayUrlVo.Url> urls = new ArrayList<>();
            urls.add(new VideoPlayUrlVo.Url(videoLinkModel.getPlayUrl(),"原始线路",false));
            urls.get(0).setSelected(true);
            VideoPlayUrlVo videoPlayUrlVo = new VideoPlayUrlVo(videoLinkModel.getPlayName(),urls);
            
            if(stringVideoPlayGroupVoHashMap.containsKey(videoLinkModel.getFromName())){
                VideoPlayGroupVo videoPlayGroupVo = stringVideoPlayGroupVoHashMap.get(videoLinkModel.getFromName());
                videoPlayGroupVo.getVideoPlayUrlVoList().add(videoPlayUrlVo);
            }else {
                VideoPlayGroupVo videoPlayGroupVo = new VideoPlayGroupVo();
                videoPlayGroupVo.setFromName(videoLinkModel.getFromName());
                videoPlayGroupVo.setVideoPlayUrlVoList(new ArrayList<>(Collections.singletonList(videoPlayUrlVo)));
                stringVideoPlayGroupVoHashMap.put(videoLinkModel.getFromName(),videoPlayGroupVo);
                videoPlayGroupVoList.add(videoPlayGroupVo);
            }
        }
        videoPlayGroupVoList.sort(new VideoPlayGroupComparator());
        return videoPlayGroupVoList;
    }


    private static class VideoPlayGroupComparator implements Comparator<VideoPlayGroupVo>{
        @Override
        public int compare(VideoPlayGroupVo o1, VideoPlayGroupVo o2) {
            return o2.getVideoPlayUrlVoList().size() - o1.getVideoPlayUrlVoList().size();
        }
    }


    private Integer getTotal(VideoQueryParam videoQueryParam){
        String key = VideoKey.getTotalByQueryParam(videoQueryParam);
        Integer total = videoDao.selectTotalByQueryParam(videoQueryParam);
        redisClient.set(key,total);
        return total;
    }


}
