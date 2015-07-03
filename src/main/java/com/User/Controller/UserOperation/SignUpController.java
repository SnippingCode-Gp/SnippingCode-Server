package com.User.Controller.UserOperation;

/**
 * Developed by Nasser
 * SignUpController
 */
import com.User.ObjectRequest.SignUpRequest;
import com.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.User.Domain.User;
import com.DataType.StringType;
import com.User.Repository.UserRepository;

@RestController
@RequestMapping("/SignUp")
@Secured({ "ROLE_ANONYMOUS" })
public class SignUpController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository user;

	@Autowired
	private StringType data;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<StringType> signUp(@RequestBody SignUpRequest signUpReqobject) {

		if (user.findByusername(signUpReqobject.getUsername()).size() == 1) {
			data.setString("user name already exist");
			return new ResponseEntity<StringType>(data, HttpStatus.CONFLICT);
		}

        if (user.findByemail(signUpReqobject.getEmail()).size() == 1) {
            data.setString("email already exist");
            return new ResponseEntity<StringType>(data, HttpStatus.CONFLICT);
        }

		User newUsr = userService.createNewUser(signUpReqobject);
		data.setString("Done");
		user.save(newUsr);
		return new ResponseEntity<StringType>(data, HttpStatus.OK);

	}
}
