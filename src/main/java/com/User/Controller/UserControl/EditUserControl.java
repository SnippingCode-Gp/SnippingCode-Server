package com.User.Controller.UserControl;

import com.User.Domain.UserDomain;
import com.DataType.StringType;
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
public class EditUserControl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringType data;

    @Autowired
    private UserExist userExist;

    @RequestMapping(value="/editUser" , method = RequestMethod.POST)
    public ResponseEntity<StringType> signUp(@RequestBody UserDomain userDomain) {

        if(!userExist.checkUserExist(userDomain.getUsername(),userDomain.getPassword())){
            String error = "username and password conflict";
            return new ResponseEntity<StringType>(new StringType(error),HttpStatus.NOT_FOUND);
        }
        try{
            data.setString("Done");
            userRepository.save(userDomain);
            return new ResponseEntity<StringType>(data, HttpStatus.OK);
        }catch(Exception e){
            data.setString("unavaialble");
            return new ResponseEntity<StringType>(data, HttpStatus.CONFLICT);
        }
    }


}
