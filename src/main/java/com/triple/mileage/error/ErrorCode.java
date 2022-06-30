package com.triple.mileage.error;

public enum ErrorCode {
    DUPLICATED_REVIEW(400, "001", "리뷰는 한 개만 작성 가능합니다."),
    NOT_EXISTS_REVIEW(400, "002", "수정하려면 리뷰가 있어야 합니다."),
    NOT_EXISTS_REVIEW_CONTENTS(400, "003", "리뷰에는 내용이 있어야 합니다."),
    ALREADY_DELETED(400, "004", "이미 삭제된 리뷰입니다."),
    NOT_EXISTS_POINT(400, "005", "포인트가 없습니다."),
    NOT_EXISTS_USER(400, "006", "유저가 없습니다.");

    private int status;
    private final String code;
    private final String message;

    ErrorCode(final int status, final String code, final String message) {
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
