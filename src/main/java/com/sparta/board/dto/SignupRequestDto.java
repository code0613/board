package com.sparta.board.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter
@Getter
public class SignupRequestDto {

    @Size(min=4, max=10, message = "최소4 최대 10")
    @NotBlank(message = "이름은 비워둘 수 없습니다")
    @Pattern(regexp = "^[a-z0-9]*$",message = "a-z0-9")
    private String username;

    
    @Size(min=8, max=15,message = "최소8 최대 15")
    @NotBlank(message = "비밀번호는 비워둘 수 없습니다")
    @Pattern(regexp = "[a-zA-Z0-9]*$",message = "a-zA-Z0-9")
    private String password;


}
