package com.zizser.zizserservice.service.impl;

import com.zizser.zizserservice.model.entity.CommentEntity;
import com.zizser.zizserservice.model.request.CommentRequest;
import com.zizser.zizserservice.repository.CommentRepository;
import com.zizser.zizserservice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@Qualifier("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public CommentEntity postNewComment(CommentRequest commentRequest) {
        CommentEntity commentEntity = new CommentEntity();
        BeanUtils.copyProperties(commentRequest, commentEntity);
        return this.commentRepository.save(commentEntity);
    }

    @Override
    public List<CommentEntity> getWebsiteComments(Long websiteId) {
        return this.commentRepository.findCommentByWebsiteIdOrderByCreatedDateDesc(websiteId);
    }
}
