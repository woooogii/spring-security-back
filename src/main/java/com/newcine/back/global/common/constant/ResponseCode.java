package com.newcine.back.global.common.constant;

import com.newcine.back.global.common.exception.BaseExceptionCode;

public enum ResponseCode implements BaseExceptionCode {
    LOGIN_SUCCESS("0000", "RESPONSE_CODE.LOGIN_SUCCESS"),
    LOGOUT_SUCCESS("0000", "RESPONSE_CODE.LOGOUT_SUCCESS"),
    JOIN_SUCCESS("0000", "RESPONSE_CODE.JOIN_SUCCESS"),
    SEARCH_SUCCESS("0000", "RESPONSE_CODE.SEARCH_SUCCESS"),
    INSERT_SUCCESS("0000", "RESPONSE_CODE.INSERT_SUCCESS"),
    UPDATE_SUCCESS("0000", "RESPONSE_CODE.UPDATE_SUCCESS"),

    VALIDATION_SUCCESS("0000", "RESPONSE_CODE.VALIDATION_SUCCESS"),

    JOIN_NOT_MATCHED("0001", "RESPONSE_CODE.JOIN_NOT_MATCHED"),
    JOIN_ALREADY_DONE("0002", "RESPONSE_CODE.JOIN_ALREADY_DONE"),

    LOGIN_TOKEN_EXPIRED("9999", "RESPONSE_CODE.LOGIN_TOKEN_EXPIRED"), // 토큰 만료
    LOGIN_NEED("9998", "RESPONSE_CODE.LOGIN_NEED"),
    LOGIN_NOT_MATCHED("0002", "RESPONSE_CODE.LOGIN_NOT_MATCHED"),

    INTERNAL_SERVER_ERROR("5000", "RESPONSE_CODE.INTERNAL_SERVER_ERROR"),
    VALIDATION_FAILED("4000", "RESPONSE_CODE.VALIDATION_FAILED"),
    ;

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
/*
 * INTERNAL_SERVER_ERROR("0001", "RESPONSE_CODE.INTERNAL_SERVER_ERROR"),
 * 
 * SEARCH_FAILED( "0003", "RESPONSE_CODE.SEARCH_FAILED"),
 * INSERT_FAILED( "0003", "RESPONSE_CODE.INSERT_FAILED"),
 * UPDATE_FAILED( "0003", "RESPONSE_CODE.UPDATE_FAILED"),
 * 
 * TRANSLATE_SUCCESS( "0000", "RESPONSE_CODE.TRANSLATE_SUCCESS"),
 * 
 * MAIL_SEND_SUCCESS("0000", "RESPONSE_CODE.MAIL_SEND_SUCCESS"),
 * MAIL_SEND_FAIL("0001","RESPONSE_CODE.MAIL_SEND_FAIL"),
 * MAIL_VERIFY_SUCCESS("0000", "RESPONSE_CODE.MAIL_VERIFY_SUCCESS"),
 * MAIL_VERIFY_TIMEOUT("0001","RESPONSE_CODE.MAIL_VERIFY_TIMEOUT"),
 * MAIL_VERIFY_FAIL("0002","RESPONSE_CODE.MAIL_VERIFY_FAIL"),
 * 
 * REISSUE_SUCCESS("0000", "RESPONSE_CODE.REISSUE_SUCCESS"),
 * REISSUE_FAILED("0001", "RESPONSE_CODE.REISSUE_FAILED"),
 * //400 BAD_REQUEST 잘못된 요청
 * INVALID_PARAMETER("4000", "RESPONSE_CODE.INVALID_PARAMETER"),
 * //404 NOT_FOUND 잘못된 리소스 접근
 * BAD_REQUEST("4030", "RESPONSE_CODE.BAD_REQUEST"),
 * NO_ROLES("4030", "RESPONSE_CODE.NO_ROLES"),
 * UNAUTHORIZED("4032", "RESPONSE_CODE.UNAUTHORIZED"),
 * DISPLAY_NOT_FOUND("4040", "RESPONSE_CODE.DISPLAY_NOT_FOUND"),
 * FTP_ERROR("5005", "RESPONSE_CODE.FTP_ERROR"),
 * ;
 * 
 * private String code;
 * private String key;
 * 
 * ResponseCode(String code, String key) {
 * this.code = code;
 * this.key = key;
 * }
 * 
 * public static String findKey(String code){
 * String find = null;
 * for(ResponseCode val : values()){
 * if(code.equals(val.getCode())){
 * find = val.getKey();
 * break;
 * }
 * }
 * return find;
 * }
 */
