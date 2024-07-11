package com.rf.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TypeModel implements Serializable {

    private static final long serialVersionUID = -2180128278982216643L;

    private Integer id;

    private String name;

    private Integer parentType;

    private Integer sort;

}
