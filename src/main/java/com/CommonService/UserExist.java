package com.CommonService;

import com.Code.ObjectRequest.SearchReqObj;
import com.Code.ObjectRequest.ValidationObject;
import com.User.Domain.UserDomain;
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
public class UserExist {

    @Autowired
    UserRepository userRepository;

    public Boolean checkUserExist(String username , String passwrd){
        List<UserDomain> users = userRepository.findByusername(username);
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(passwrd)) return true;
        return false;
    }

    public boolean checkUserExist(ValidationObject validationObject) {
        List<UserDomain> users = userRepository.findByusername(validationObject.getUsername());
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(validationObject.getPassword())) return true;
        return false;
    }

    public boolean checkUserExist(SearchReqObj searchReqObj) {
        List<UserDomain> users = userRepository.findByusername(searchReqObj.getUsername());
        if(users.size() == 0) return false;
        if(users.get(0).getPassword().equals(searchReqObj.getPassword())) return true;
        return false;
    }
}
