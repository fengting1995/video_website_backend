package com.rf.service;



import com.rf.model.VideoModel;
import com.rf.param.PageParam;
import com.rf.param.VideoQueryParam;
import com.rf.result.PageData;
import com.rf.result.ReturnResult;
import com.rf.vo.VideoDetailVo;
import com.rf.vo.VideoListVo;

import java.util.List;

public interface VideoService {

    public ReturnResult<VideoDetailVo> getDetailByAv(String av);

    public ReturnResult<PageData<VideoListVo>>pageQuery(VideoQueryParam videoQueryParam, PageParam pageParam);

    public ReturnResult<PageData<VideoListVo>> findByMultipleAttributes(VideoQueryParam videoQueryParam,String area, String lang, String year,
                                                                        String type);

}
