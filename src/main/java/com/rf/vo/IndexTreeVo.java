package com.rf.vo;

import com.rf.model.TypeModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexTreeVo implements Serializable {


    private static final long serialVersionUID = -36215781558738957L;

    private List<TypeModel> typeModelList;

    private List<TypeWithVideoListVo> typeWithVideoListVoList;



}
