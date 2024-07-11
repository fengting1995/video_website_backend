package com.rf.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class VideoPlayUrlVo implements Serializable {

    private static final long serialVersionUID = -3640566189153639631L;

    @Data
    public static class Url implements Serializable{

        private static final long serialVersionUID = 75172147511416610L;
        private String src;
        private String type = "application/x-mpegURL";
        private String label;
        private Boolean selected = false;
        public Url(String src, String label, boolean selected){
            this.src = src;
            this.label = label;
            this.selected = selected;
        }
    }

    private List<Url> url;

    private String name;

    public VideoPlayUrlVo() {}

    public VideoPlayUrlVo(String name, List<Url> url) {
        this.url = url;
        this.name = name;
    }


}
