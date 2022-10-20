package com.zizser.zizserservice.repository;

import com.zizser.zizserservice.model.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "comment-resource")
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findCommentByWebsiteIdOrderByCreatedDateAsc(Long websiteId);

    List<CommentEntity> findCommentByWebsiteIdOrderByCreatedDateDesc(Long websiteId);
}
