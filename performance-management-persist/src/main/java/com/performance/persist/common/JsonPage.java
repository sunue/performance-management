package com.performance.persist.common;

import java.io.Serializable;

public class JsonPage<T> implements Serializable {
    private String msg;

    private int status;

    private T data_list;

    private int total;

    private int pageNum;

    private int pageSize;
}
