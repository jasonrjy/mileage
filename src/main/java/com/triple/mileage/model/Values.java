package com.triple.mileage.model;

public enum Values {

    SUCCESS(200, "9200", "SUCCESS"),
    FAIL(400, "9400", "FAIL"),
    EXCEPTION(500, "9500", "EXCEPTION"),

    STATUS_BAD_REQUEST(400, "400", "BAD_REQUEST"),
    STATUS_NOT_FOUND(404, "404", "NOT FOUND"),
    STATUS_INTERNAL_SERVER_ERROR(500, "500", "INTERNAL SERVER ERROR");


    private int status;
    private final String code;
    private final String message;

    Values(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }
    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
