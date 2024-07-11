package com.rf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VideoDetailVo implements Serializable {

    private static final long serialVersionUID = -33735160627306932L;

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

    /**
     * 播放来源列表
     */
    private List<VideoPlayGroupVo> videoPlayGroupList;
    /**
     * 代理服务器
     */
    private List<String> proxyServicePrefixList;


}
