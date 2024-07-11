package com.rf.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoModel implements Serializable {

    private static final long serialVersionUID = 4058083445872788869L;

    private Integer id;
    /**
     * 分类1
     */
    private Integer typeId1;

    private String typeId2;

    private long updateTime;

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
     * 语言
     */
    private String lang;

    /**
     * 地区
     */
    private String area;

    /**
     * 年份
     */
    private String year;


    /**
     * 演员
     */
    private String actor;

    /**
     * 导演
     */
    private String director;



}
