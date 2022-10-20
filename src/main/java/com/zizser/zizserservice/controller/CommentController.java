package com.zizser.zizserservice.controller;

import com.zizser.zizserservice.exception.type.WebsiteMetaDataIdNotFoundException;
import com.zizser.zizserservice.model.entity.CommentEntity;
import com.zizser.zizserservice.model.entity.WebsiteMetaDataEntity;
import com.zizser.zizserservice.model.request.CommentRequest;
import com.zizser.zizserservice.model.response.CommentResponse;
import com.zizser.zizserservice.service.CommentService;
import com.zizser.zizserservice.service.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = {"/comment"})
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    MetaDataService metaDataService;

    @GetMapping("/get/website-comments/{websiteId}")
    public ResponseEntity<List<CommentResponse>> getWebsiteComments(@PathVariable("websiteId") Long websiteId) {

        log.info("retrieving website comments");

        List<CommentEntity> commentEntities = this.commentService.getWebsiteComments(websiteId);
        List<CommentResponse> commentResponses = new ArrayList<>();

        commentEntities.forEach(comment -> {
            CommentResponse commentResponse = new CommentResponse();
            BeanUtils.copyProperties(comment, commentResponse);
            commentResponses.add(commentResponse);
        });
        // TODO:: MAKE PAGEABLE
        return new ResponseEntity<>(commentResponses, HttpStatus.OK);
    }

    @PostMapping("/website/{websiteId}")
    public ResponseEntity<CommentResponse> commentOnWebsite(
            @RequestBody CommentRequest commentRequest,
            @PathVariable("websiteId") Long websiteId) throws WebsiteMetaDataIdNotFoundException {

        log.info("commenting on website");
        log.info(commentRequest.getPost());

        commentRequest.setWebsiteId(websiteId);
        CommentEntity commentEntity = this.commentService.postNewComment(commentRequest);
        CommentResponse commentResponse = new CommentResponse();
        BeanUtils.copyProperties(commentEntity, commentResponse);

        WebsiteMetaDataEntity websiteMetaDataEntity = this.metaDataService.findMetaDataById(websiteId);
        websiteMetaDataEntity.setLastUpdatedDate(LocalDateTime.now());
        this.metaDataService.saveMetaData(websiteMetaDataEntity);

        return new ResponseEntity<>(commentResponse, HttpStatus.OK);
    }
}
