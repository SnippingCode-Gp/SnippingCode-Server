package com.User.Service;
/**
 * Developed by Nasser
 * Login Controller
 */
import com.User.ObjectRequest.SignUpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import com.User.Domain.UserDomain;

@RestController
@Service
public class SignUpService {

	public UserDomain createNewUser(SignUpRequest signUpReqobject) {
        UserDomain user = new UserDomain();
		user.setUsername(signUpReqobject.getUsername());
		user.setPassword(signUpReqobject.getPassword());
		user.setEmail(signUpReqobject.getEmail());
        user.setName(signUpReqobject.getName());
		return user;
	}
}
