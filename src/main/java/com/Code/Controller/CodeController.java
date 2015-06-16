package com.Code.Controller;

import com.Code.Domain.CodeDomain;
import com.Code.ObjectRequest.CodeObject;
import com.Code.Repository.CodeRepository;
import com.CommonService.CodeService;
import com.DataType.StringType;
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
public class CodeController {

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private StringType date;

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public ResponseEntity<Long> UploadFile(@RequestBody CodeObject uploadReqObj) {
        CodeDomain codeDomain = codeService.CreateCodeEntity(uploadReqObj);
        if(codeDomain == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        CodeDomain tmp = null;
        try {
            List<CodeDomain> all = codeRepository.findByusername(uploadReqObj.getUsername());
            for(int i = 0 ; i < all.size(); i++){
                if(all.get(i).equals(uploadReqObj.getName())){
                    return new ResponseEntity(HttpStatus.CONFLICT);
                }
            }
            try {
                System.out.println("**********************************");
                System.out.println(codeDomain.getName());
                System.out.println("**********************************");
                codeRepository.save(codeDomain);
            }catch (Exception e){
                System.out.println("Exception is \n " + e.toString());
                return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
            }
            return new ResponseEntity(codeDomain.getId(),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @RequestMapping(value = "/update/{oldName}" , method = RequestMethod.POST)
    public ResponseEntity<StringType> updateCode(@PathVariable String oldName,
                                                 @RequestBody CodeObject uploadReqObj){
        List<CodeDomain> codes = codeRepository.findByname(oldName);
        if(codes.size() == 0)
            return new ResponseEntity<StringType>(HttpStatus.NOT_FOUND);
        CodeDomain code = codes.get(0);
        CodeDomain codeDomain = codeService.updateCode(code , uploadReqObj);

        if(codeDomain == null)
            return new ResponseEntity<StringType>(new StringType("Not Found"),HttpStatus.NOT_FOUND);

        try {
            codeRepository.save(codeDomain);
            date.setString("Done");
            return new ResponseEntity<StringType>(date , HttpStatus.OK);
        }catch (Exception e){
            date.setString("make sure of user name");
            return new ResponseEntity<StringType>(date , HttpStatus.NOT_FOUND);
        }
    }
}
