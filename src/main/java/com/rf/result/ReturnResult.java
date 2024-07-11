package com.rf.result;

import com.rf.constant.ResponseCode;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReturnResult<T> implements Serializable {

    private static final long serialVersionUID = 8838222998265382213L;
    private Integer code;

    private String msg;

    private T data;

    public ReturnResult(ResponseCode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
    }


    public ReturnResult(ResponseCode code, T data) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = data;
    }

    public ReturnResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ReturnResult<T> success(T data){
        return  new ReturnResult<T>(ResponseCode.SUCCESS,data);
    }

    public static <T> ReturnResult<T> fail(String msg){
        return  new ReturnResult<>(ResponseCode.FAIL.getCode(),msg);
    }


}
