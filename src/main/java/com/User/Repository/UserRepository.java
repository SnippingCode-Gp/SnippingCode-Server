package com.User.Repository;

/**
 * Developed by Nasser
 * Login Controller
 */
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import com.User.Domain.UserDomain;

@RestController
public interface UserRepository extends CrudRepository<UserDomain, Long> {

	public List<UserDomain> findByusername(String username);
    public List<UserDomain> findByemail(String username);

}
