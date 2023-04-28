package com.micro.reviewservice.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Error {
    public Error(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }
    private String msg;
    private String code;

}
