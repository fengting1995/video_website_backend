package com.rf.service.impl;

import com.rf.constant.ResponseCode;
import com.rf.dao.TypeDao;
import com.rf.dao.VideoDao;
import com.rf.model.TypeModel;
import com.rf.model.VideoModel;
import com.rf.redis.RedisClient;
import com.rf.result.ReturnResult;
import com.rf.service.IndexTreeService;
import com.rf.vo.IndexTreeVo;
import com.rf.vo.TypeWithVideoListVo;
import com.rf.vo.VideoListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexTreeServiceImpl implements IndexTreeService {

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private VideoDao videoDao;

    @Override
    public ReturnResult<IndexTreeVo> getIndexTree() {
        String key = "IndexTreeVo";
        IndexTreeVo indexTreeVo = redisClient.get(key,IndexTreeVo.class);
        if (indexTreeVo == null){
            return ReturnResult.success(getIndexTreeVoAndRefreshRedis());
        }else {
            return ReturnResult.success(indexTreeVo);
        }
    }

    //每60分钟执行一次
    @Scheduled(cron = "0 */60 * * * ?")
    //@Scheduled(fixedRate = 5000)
    public IndexTreeVo getIndexTreeVoAndRefreshRedis(){
        String key = "IndexTreeVo";
        IndexTreeVo indexTreeVo = new IndexTreeVo();
        List<TypeModel> typeModelList = typeDao.selectAll();
        List<TypeWithVideoListVo> typeWithVideoListVoList = new ArrayList<>();
        for (TypeModel typeModel : typeModelList){
            TypeWithVideoListVo typeWithVideoListVo = new TypeWithVideoListVo();
            typeWithVideoListVo.setTypeName(typeModel.getName());
            typeWithVideoListVo.setTypeId(typeModel.getId());
            List<VideoModel> videoModelList = videoDao.selectByTypeIdWithLimit(typeModel.getId(), 18);
            List<VideoListVo> videoListVoList =
                    videoModelList.stream().map(VideoListVo::convertFromVideoModel).collect(Collectors.toList());
            typeWithVideoListVo.setVideoListVos(videoListVoList);
            typeWithVideoListVoList.add(typeWithVideoListVo);
        }
        indexTreeVo.setTypeModelList(typeModelList);
        indexTreeVo.setTypeWithVideoListVoList(typeWithVideoListVoList);
        redisClient.set(key, indexTreeVo);
        System.out.println("log:getIndexTreeVoAndRefreshRedis Success");
        return indexTreeVo;
    }

}
