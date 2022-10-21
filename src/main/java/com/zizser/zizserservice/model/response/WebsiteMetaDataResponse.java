package com.zizser.zizserservice.model.response;

import lombok.Data;

@Data
public class WebsiteMetaDataResponse {
    private Long id;
    private String url;
    private String title;
    private String description;
    private String imageLink;
    private String type;
    private Long viewCount;
}
