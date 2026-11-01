package com.example.my_school.commons.data.response;

import lombok.Data;

@Data
public class MessageResponse {
    private String content;

    public MessageResponse(String content){
        this.content=content;
    }
}
