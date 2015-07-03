package com.User.Controller.UserControl;

import com.User.Domain.User;
import com.DataType.StringType;
import com.User.ObjectRequest.GetUserObjectRequest;
import com.User.Repository.UserRepository;
import com.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sngv on 07/02/15.
 */
@RestController
@RequestMapping("/profile")
public class GetUserControl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringType data;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public ResponseEntity<User> getUser(@RequestBody GetUserObjectRequest user){
        User userDomain = null;

        if(!userService.checkUserExist(user.getUsername(), user.getPassword())){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try{
            userDomain = userRepository.findByusername(user.getUsername()).get(0);
            return new ResponseEntity<User>(userDomain , HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<User>(userDomain , HttpStatus.NOT_FOUND);
        }
    }


}
