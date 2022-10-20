package com.zizser.zizserservice.service;


import com.zizser.zizserservice.exception.type.WebsiteMetaDataIdNotFoundException;
import com.zizser.zizserservice.model.entity.WebsiteMetaDataEntity;
import com.zizser.zizserservice.model.response.WebsiteMetaDataResponse;

public interface MetaDataService {

    WebsiteMetaDataEntity findMetaDataById(Long websiteId) throws WebsiteMetaDataIdNotFoundException;

    WebsiteMetaDataResponse retrieveMetaData(String url, WebsiteMetaDataResponse websiteMetaDataResponse);

    WebsiteMetaDataEntity saveMetaData(WebsiteMetaDataResponse websiteMetaDataResponse);

    void saveMetaData(WebsiteMetaDataEntity websiteMetaDataEntity);
}
