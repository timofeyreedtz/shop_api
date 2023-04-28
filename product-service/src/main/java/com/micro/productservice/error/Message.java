package com.micro.productservice.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Message {


    private boolean status;

    public Message(boolean status, java.lang.Error error) {
        this.status = status;
        this.error = error;
    }

    public Message(boolean status) {
        this.status = status;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private java.lang.Error error;


}
