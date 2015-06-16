package com.Code.Repository;

/**
 * Developed by Nasser
 * Login Controller
 */

import com.Code.Domain.Tags;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface TagsRepository extends CrudRepository<Tags, Long> {
	public List<Tags> findByname(String name);
}
