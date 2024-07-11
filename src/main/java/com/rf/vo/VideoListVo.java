package com.rf.vo;

import com.rf.model.VideoModel;
import com.rf.utils.ConvertUtils;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

@Data
public class VideoListVo implements Serializable {


    private static final long serialVersionUID = -6070574222953121873L;
    /**
     * 分类1
     */
    private Integer typeId1;

    private Integer typeId2;

    /**
     * 名称
     */
    private String name;

    /**
     * 图片
     */
    private String picture;

    /**
     * 简介内容
     */
    private String content;
    /**
     * av
     */
    private String av;

    private String updateTime;

    public static VideoListVo convertFromVideoModel(VideoModel videoModel){
        if(videoModel ==null){
            return null;
        }
        VideoListVo videoListVo = ConvertUtils.copyProperties(videoModel,VideoListVo.class);
        String dataString = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(videoModel.getUpdateTime()*1000);
        videoListVo.setUpdateTime(dataString);
        return videoListVo;
    }


}
