package com.newcine.back.global.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.newcine.back.global.common.constant.ResponseCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 널 값이 아닌 경우에만 반환
public class ErrorResponseDTO {
    private final int status;
    private final String code;
    private final String message;

    public ErrorResponseDTO(String code, String message) {
        this.status = HttpStatus.OK.value();
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ErrorResponseDTO> databaseError() {
        ErrorResponseDTO responseBody = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ResponseCode.INTERNAL_SERVER_ERROR.getCode(), ResponseCode.INTERNAL_SERVER_ERROR.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    public static ResponseEntity<ErrorResponseDTO> validationFail() {
        ErrorResponseDTO responseBody = new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                ResponseCode.VALIDATION_FAILED.getCode(), ResponseCode.VALIDATION_FAILED.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    public static ResponseEntity<ErrorResponseDTO> customError(int status, String code, String message) {
        ErrorResponseDTO responseBody = new ErrorResponseDTO(status, code, message);
        return ResponseEntity.status(status).body(responseBody);
    }

}