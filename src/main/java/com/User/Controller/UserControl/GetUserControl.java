package com.User.Controller.UserControl;

import com.User.Domain.UserDomain;
import com.DataType.StringType;
import com.User.ObjectRequest.GetUserObjectRequest;
import com.User.Repository.UserRepository;
import com.CommonService.UserExist;
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
    private UserExist userExist;

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    public ResponseEntity<UserDomain> getUser(@RequestBody GetUserObjectRequest user){
        UserDomain userDomain = null;

        if(!userExist.checkUserExist(user.getUsername(), user.getPassword())){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        try{
            userDomain = userRepository.findByusername(user.getUsername()).get(0);
            return new ResponseEntity<UserDomain>(userDomain , HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<UserDomain>(userDomain , HttpStatus.NOT_FOUND);
        }
    }


}
