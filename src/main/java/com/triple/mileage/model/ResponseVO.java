package com.triple.mileage.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResponseVO {

    private String code;
    private String message;
    private Object data;

    public ResponseVO() {
        this.code = Values.STATUS_BAD_REQUEST.getCode();
        this.message = null;
        this.data = null;
    }
}
