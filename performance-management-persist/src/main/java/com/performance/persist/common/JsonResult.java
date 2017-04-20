package com.performance.persist.common;

import java.io.Serializable;

public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg;

    private T data;

    private Integer status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
