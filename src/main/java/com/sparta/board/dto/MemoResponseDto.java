package com.sparta.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MemoResponseDto {

    private String msg;
    private String status;

    /*
    requiredAgsConstructor
    final 필드 생성자
    @notnull 필드 생성자
    */
}
