package com.User.Service;

import com.Code.ObjectRequest.SearchReqObj;
import com.Code.ObjectRequest.ValidationObject;
import com.User.Domain.User;
import com.User.ObjectRequest.SignUpRequest;
import com.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by sngv on 25/02/15.
 */

@RestController
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Boolean checkUserExist(String username , String passwrd){
        List<User> users = userRepository.findByusername(username);
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(passwrd)) return true;
        return false;
    }

    public boolean checkUserExist(ValidationObject validationObject) {
        List<User> users = userRepository.findByusername(validationObject.getUsername());
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(validationObject.getPassword())) return true;
        return false;
    }

    public boolean checkUserExist(SearchReqObj searchReqObj) {
        List<User> users = userRepository.findByusername(searchReqObj.getUsername());
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(searchReqObj.getPassword())) return true;
        return false;
    }

    public User createNewUser(SignUpRequest signUpReqobject) {
        User user = new User();
        user.setUsername(signUpReqobject.getUsername());
        user.setPassword(signUpReqobject.getPassword());
        user.setEmail(signUpReqobject.getEmail());
        user.setName(signUpReqobject.getName());
        return user;
    }
}
