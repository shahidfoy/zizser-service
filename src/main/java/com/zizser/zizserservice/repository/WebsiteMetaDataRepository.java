package com.zizser.zizserservice.repository;

import com.zizser.zizserservice.model.entity.WebsiteMetaDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "website-metadata-resource")
public interface WebsiteMetaDataRepository extends JpaRepository<WebsiteMetaDataEntity, Long> {

    WebsiteMetaDataEntity findWebsiteMetaDataEntityByUrl(String url);
}
