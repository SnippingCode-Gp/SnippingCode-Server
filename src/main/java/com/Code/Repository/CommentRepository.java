package com.Code.Repository;

import com.Code.Domain.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by sngv on 23/03/15.
 */
public interface CommentRepository extends CrudRepository<Comment, Long> {
    public List<Comment> findByCodeNameAndUsername(String codename , String username);
    public List<Comment> findByCodeName(String codeName);
}