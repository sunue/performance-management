package com.performance.persist.common;

import java.io.Serializable;

public class JsonPage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg;

    private int status;

    private T data_list;

    private int total;

    private int pageNum;

    private int pageSize;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData_list() {
        return data_list;
    }

    public void setData_list(T data_list) {
        this.data_list = data_list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
