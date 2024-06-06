package com.fastcampus.fintechservice.dto.response;


import lombok.Getter;

@Getter
public class MessageResponse {

    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }
}
