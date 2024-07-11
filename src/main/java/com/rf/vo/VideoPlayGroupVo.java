package com.rf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VideoPlayGroupVo implements Serializable {

    private static final long serialVersionUID = -994887132999950018L;

    private String fromName;

    private List<VideoPlayUrlVo> videoPlayUrlVoList;
}
