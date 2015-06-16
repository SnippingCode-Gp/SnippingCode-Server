package com.Code.Controller;

import com.Code.Domain.CodeDomain;
import com.Code.ObjectRequest.CodeObjectRequest;
import com.Code.ObjectRequest.SearchReqObj;
import com.Code.Repository.CodeRepository;
import com.Code.Repository.TagsRepository;
import com.Code.Service.CodeRetreiveService;
import com.CommonService.UserExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Code")
public class SearchCode {

    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    UserExist userExist;

    @Autowired
    private CodeRetreiveService codeRetreiveService;


    @RequestMapping(value="/getCode/{number}", method = RequestMethod.POST)
    public ResponseEntity<List<CodeObjectRequest>> getSpecificCode(@PathVariable String number , @RequestBody SearchReqObj searchReqObj) {
        if(!userExist.checkUserExist(searchReqObj)){
            return new ResponseEntity<List<CodeObjectRequest>>(HttpStatus.NOT_FOUND);
        }
        try {
            System.out.println(number);
            int num = Integer.parseInt(number.substring(1,number.length()-1));
            Pageable range = new PageRequest(num,5);
            List<CodeDomain> userCodes = codeRepository.findByusernameAndsdescription(searchReqObj.getUsername(),
                    searchReqObj.getSearch(),range);

            if(userCodes.size() == 0){
                return new ResponseEntity<List<CodeObjectRequest>>(HttpStatus.NOT_FOUND);
            }else{
                List<CodeObjectRequest> codeObjectRequests = codeRetreiveService.changeFromDomainToRequestObj(userCodes);
                return new ResponseEntity<List<CodeObjectRequest>>(codeObjectRequests, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CodeObjectRequest>>(HttpStatus.NOT_ACCEPTABLE);
        }

    }
}