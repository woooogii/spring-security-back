package com.newcine.back.global.common.domain;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDomain<T> {
    // @ApiModelProperty
    private String code; // 응답코드
    private String message; // 응답메세지
    private List<T> list;
    private int totalCnt;
    private T data;
    private HttpStatus status;
}