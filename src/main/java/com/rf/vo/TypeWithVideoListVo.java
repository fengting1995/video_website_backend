package com.rf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TypeWithVideoListVo implements Serializable {

    private static final long serialVersionUID = -8861051662274171957L;
    private String typeName;
    private Integer typeId;
    private List<VideoListVo> videoListVos;

}
