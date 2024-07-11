package com.rf.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ThirdCollectionApiModel implements Serializable {

    private static final long serialVersionUID = 2394511289547399104L;

    private Integer id;

    private String url;

    private String key;

    private String name;

    private String bindId;
}
