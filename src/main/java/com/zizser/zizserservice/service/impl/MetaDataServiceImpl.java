package com.zizser.zizserservice.service.impl;

import com.zizser.zizserservice.exception.type.WebsiteMetaDataIdNotFoundException;
import com.zizser.zizserservice.model.entity.WebsiteMetaDataEntity;
import com.zizser.zizserservice.model.response.WebsiteMetaDataResponse;
import com.zizser.zizserservice.repository.WebsiteMetaDataRepository;
import com.zizser.zizserservice.service.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@Qualifier("metaDataService")
public class MetaDataServiceImpl implements MetaDataService {

    @Autowired
    WebsiteMetaDataRepository websiteMetaDataRepository;

    @Override
    public WebsiteMetaDataEntity findMetaDataById(Long websiteId) throws WebsiteMetaDataIdNotFoundException {
        Optional<WebsiteMetaDataEntity> websiteMetaDataEntityOptional = this.websiteMetaDataRepository.findById(websiteId);
        if (websiteMetaDataEntityOptional.isPresent()) {
            return websiteMetaDataEntityOptional.get();
        } else {
            throw new WebsiteMetaDataIdNotFoundException("website metadata id not found");
        }
    }

    @Override
    public WebsiteMetaDataResponse retrieveMetaData(String url, WebsiteMetaDataResponse websiteMetaDataResponse) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
            String resultStr = result.getBody();

            List<String> metaData = List.of(Objects.requireNonNull(
                    StringUtils.substringsBetween(resultStr, "<meta", ">")));
            metaData = metaData.stream().filter(m -> m.contains("property")).toList();

            for (String meta: metaData) {
                log.info("URL meta: {}", meta);
                if (meta.contains("property=\"og:image\"") ||
                        meta.contains("property='og:image'") ||
                        meta.contains("property=og:image ")
                ) {
                    String content;
                    content = meta.split("content=")[1];
                    try {
                        content = content.split("\"")[1].trim();
                    } catch (Exception e) {
                        log.info("catch {}", content);
                        content = content.split(" ")[0].trim();
                    }
                    log.info("image CONTENT: {}", content);
                    websiteMetaDataResponse.setImageLink(content);
                } else if (meta.contains("property=\"og:title\"") ||
                        meta.contains("property='og:title'") ||
                        meta.contains("property=og:title ")
                ) {
                    String content;
                    content = meta.split("content=")[1];
                    try {
                        content = content.split("\"")[1].trim();
                    } catch (Exception e) {
                        log.info("catch {}", content);
                        content = content.split(" ")[0].trim();
                    }
                    log.info("title CONTENT: {}", content);
                    websiteMetaDataResponse.setTitle(content);
                } else if (meta.contains("property=\"og:description\"") ||
                        meta.contains("property='og:description'") ||
                        meta.contains("property=og:description ")
                ) {
                    String content;
                    content = meta.split("content=")[1];
                    try {
                        content = content.split("\"")[1].trim();
                    } catch (Exception e) {
                        log.info("catch {}", content);
                        content = content.split(" ")[0].trim();
                    }
                    log.info("description CONTENT: {}", content);
                    websiteMetaDataResponse.setDescription(content);
                } else if (meta.contains("property=\"og:type\"") ||
                        meta.contains("property='og:type'") ||
                        meta.contains("property=og:type ")
                ) {
                    String content;
                    content = meta.split("content=")[1];
                    try {
                        content = content.split("\"")[1].trim();
                    } catch (Exception e) {
                        log.info("catch {}", content);
                        content = content.split(" ")[0].trim();
                    }
                    log.info("type CONTENT: {}", content);
                    websiteMetaDataResponse.setType(content);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=== Unable to retrieve all of the metadata");
        }

        return websiteMetaDataResponse;
    }

    @Override
    public WebsiteMetaDataEntity saveMetaData(WebsiteMetaDataResponse websiteMetaDataResponse) {
        log.info("saving website metadata");
        WebsiteMetaDataEntity websiteMetaDataEntity = this.websiteMetaDataRepository.findWebsiteMetaDataEntityByUrl(websiteMetaDataResponse.getUrl());
        if (websiteMetaDataEntity != null) {
            log.info("website metadata already exists");
            Long viewCount = websiteMetaDataEntity.getViewCount() == null ? 1L : websiteMetaDataEntity.getViewCount() + 1;
            websiteMetaDataEntity.setViewCount(viewCount);
        } else {
            log.info("website metadata does not exist saving new entity");
            websiteMetaDataEntity = new WebsiteMetaDataEntity();
            BeanUtils.copyProperties(websiteMetaDataResponse, websiteMetaDataEntity);
            websiteMetaDataEntity.setViewCount(1L);
        }
        return this.websiteMetaDataRepository.save(websiteMetaDataEntity);
    }

    @Override
    public void saveMetaData(WebsiteMetaDataEntity websiteMetaDataEntity) {
        this.websiteMetaDataRepository.save(websiteMetaDataEntity);
    }
}
