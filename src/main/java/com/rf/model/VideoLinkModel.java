package com.rf.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoLinkModel implements Serializable {

    private static final long serialVersionUID = 1078107238171533393L;

    private Integer id;

    private Integer videoId;

    /**
     * 播放来源
     */
    private String fromName;

    /**
     * 选集
     */
    private String playName;

    /**
     * 播放url
     */
    private String playUrl;

}
