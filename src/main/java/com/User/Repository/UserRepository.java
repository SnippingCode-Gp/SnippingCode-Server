package com.User.Repository;

/**
 * Developed by Nasser
 * Login Controller
 */
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

import com.User.Domain.User;

@RestController
public interface UserRepository extends CrudRepository<User, Long> {

	public List<User> findByusername(String username);
    public List<User> findByemail(String mail);

}
