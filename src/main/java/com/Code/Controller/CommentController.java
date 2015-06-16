package com.Code.Controller;

import com.Code.Domain.Comment;
import com.Code.ObjectRequest.AllCommentRequestObject;
import com.Code.ObjectRequest.CommentRequestObject;
import com.Code.Repository.CommentRepository;
import com.CommonService.UserExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sngv on 23/03/15.
 */

@RestController
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserExist userExist;

    @RequestMapping(value = "/newComment" , method = RequestMethod.POST)
    public ResponseEntity newComment(@RequestBody CommentRequestObject commentRequestObject){
        if(!userExist.checkUserExist(commentRequestObject.getUsername(),commentRequestObject.getPassword()))
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        Comment comment = new Comment();
        comment.setDescription(commentRequestObject.getDescription());
        comment.setCodeName(commentRequestObject.getCodeName());
        comment.setUsername(commentRequestObject.getUsername());
        commentRepository.save(comment);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getComments/{number}" , method = RequestMethod.POST)
    public ResponseEntity<List<Comment>> getComments(@RequestBody AllCommentRequestObject commentRequestObject){
        if(!userExist.checkUserExist(commentRequestObject.getUsername(),commentRequestObject.getPassword()))
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        List<Comment> comment = commentRepository.findByCodeName(commentRequestObject.getCodeName());
        System.out.println("size " + comment.size());
        return new ResponseEntity<List<Comment>>(comment , HttpStatus.OK);
    }

}
