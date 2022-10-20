package com.zizser.zizserservice.service;

import com.zizser.zizserservice.model.entity.CommentEntity;
import com.zizser.zizserservice.model.request.CommentRequest;

import java.util.List;

public interface CommentService {

    CommentEntity postNewComment(CommentRequest commentRequest);

    List<CommentEntity> getWebsiteComments(Long websiteId);
}
