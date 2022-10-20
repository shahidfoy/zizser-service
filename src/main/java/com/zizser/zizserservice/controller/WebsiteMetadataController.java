package com.zizser.zizserservice.controller;

import com.zizser.zizserservice.constant.RegexConstant;
import com.zizser.zizserservice.exception.type.BadUrlAddressException;
import com.zizser.zizserservice.model.entity.WebsiteMetaDataEntity;
import com.zizser.zizserservice.model.request.WebsiteMetaDataRequest;
import com.zizser.zizserservice.model.response.WebsiteMetaDataResponse;
import com.zizser.zizserservice.service.MetaDataService;
import com.zizser.zizserservice.util.RegexUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class WebsiteMetadataController {

    @Autowired
    MetaDataService metaDataService;

    @PostMapping("/website-metadata")
    public ResponseEntity<WebsiteMetaDataResponse> retrieveWebsiteMetadata(
            @RequestBody WebsiteMetaDataRequest websiteMetaDataRequest) throws BadUrlAddressException {
        log.info("URL: {}", websiteMetaDataRequest.getUrl());

        WebsiteMetaDataResponse websiteMetaDataResponse = new WebsiteMetaDataResponse();
        websiteMetaDataResponse.setUrl(websiteMetaDataRequest.getUrl());

        if (RegexUtil.isValidPattern(RegexConstant.HTTPS_URL_ADDRESS, websiteMetaDataRequest.getUrl())) {
            if (!websiteMetaDataRequest.getUrl().equals("")) {
                websiteMetaDataResponse = this.metaDataService.retrieveMetaData(websiteMetaDataRequest.getUrl(), websiteMetaDataResponse);
            }
        } else {
            throw new BadUrlAddressException("The URL address provided is not excepted by this website. Please provide a url that includes an https address");
        }

        WebsiteMetaDataEntity websiteMetaDataEntity = this.metaDataService.saveMetaData(websiteMetaDataResponse);
        BeanUtils.copyProperties(websiteMetaDataEntity, websiteMetaDataResponse);

        log.info("========= metadata response ============");
        log.info(websiteMetaDataResponse.toString());
        log.info("========================================");

        return new ResponseEntity<>(websiteMetaDataResponse, HttpStatus.OK);
    }
}
