package com.rf.dao;

import com.rf.model.VideoLinkModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VideoLinkDao {

    public List<VideoLinkModel> selectByVideoId(Integer videoId);

}
