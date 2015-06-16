package com.Code.Controller;

import com.CommonService.UserExist;
import com.Code.Domain.CodeDomain;
import com.DataType.StringType;
import com.Code.ObjectRequest.DeleteRequestObject;
import com.Code.Repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sngv on 06/02/15.
 */

@RestController
@RequestMapping("/File")
public class DeleteCodeController {
    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    UserExist userExist;

    @Autowired
    private StringType responseString;

    @RequestMapping(value = "/delete/{name}" , method =  RequestMethod.POST)
    public ResponseEntity<StringType> deleteObject(@RequestBody DeleteRequestObject deleteObjReq ,
                                                   @PathVariable("name") String name){

        if(!userExist.checkUserExist(deleteObjReq.getUsername() , deleteObjReq.getPassword())){
            responseString.setString("user invalid");
            return new ResponseEntity<StringType>(responseString,HttpStatus.NOT_FOUND);
        }

        if(name.length() <= 2){
            responseString.setString("naming of file is invalid");
            return new ResponseEntity<StringType>(responseString,HttpStatus.NOT_FOUND);
        }

        name = name.substring(1 , name.length() - 1);
        List<CodeDomain> codeDomain = codeRepository.findByname(name);
        if(codeDomain.size() == 0){
            responseString.setString("code not found");
            return new ResponseEntity<StringType>(responseString,HttpStatus.NOT_FOUND);
        }
        System.out.println("creid :: " + userExist.checkUserExist(deleteObjReq.getUsername() , deleteObjReq.getPassword()));
        try {
            codeRepository.delete(codeDomain.get(0));
            responseString.setString("Success");
            return new ResponseEntity<StringType>(responseString , HttpStatus.OK);
        }catch (Exception e){
            responseString.setString("server error");
            return new ResponseEntity<StringType>(responseString , HttpStatus.NOT_FOUND);
        }
    }
}
