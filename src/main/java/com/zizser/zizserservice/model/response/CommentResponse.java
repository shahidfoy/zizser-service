package com.zizser.zizserservice.model.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {

    Long id;
    Long websiteId;
    String post;
    private LocalDateTime createdDate;
}
