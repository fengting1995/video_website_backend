package com.rf.dao;

import com.rf.model.VideoModel;
import com.rf.param.VideoQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoDao {

    public List<VideoModel> selectByPrimaryKey(Integer id);

    public List<VideoModel> selectByTypeIdWithLimit(Integer typeId,Integer limit);

    public VideoModel selectByAv(String av);

    public Integer selectTotalByQueryParam(VideoQueryParam videoQueryParam);

    public List<VideoModel> selectByQuery(VideoQueryParam videoQueryParam,Integer offset,Integer limit);


    public List<VideoModel> selectFuzzyByName(String name,Integer limit);

}
