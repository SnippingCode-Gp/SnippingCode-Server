/**
 * @Developed by Nasser
 * @Login FileParser
 */
package com.Code.Controller;

import com.Code.Domain.CodeDomain;
import com.Code.ObjectRequest.*;
import com.Code.Repository.CodeRepository;
import com.Code.Service.CodeRetreiveService;
import com.CommonService.CodeService;
import com.CommonService.ParseFileToString;
import com.CommonService.UserExist;
import com.DataType.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/File")
public class GetCodeController {

    @Autowired
	private ParseFileToString parsing;

	@Autowired
	private StringType date;

    @Autowired
    private CodeService fileService;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeRetreiveService codeRetreiveService;

    @Autowired
    UserExist userExist;

    @RequestMapping(value = "/getCodeByName", method = RequestMethod.POST,headers = "content-type=application/x-www-form-urlencoded")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<CodeDomainParser> getFilesByName( @ModelAttribute CodeReq codeReq) {
        if(!userExist.checkUserExist(codeReq.getUsername() , codeReq.getPassword())){
            return new ResponseEntity<CodeDomainParser>(HttpStatus.NOT_FOUND);
        }
        List<CodeDomain> codes = codeRepository.findByname(codeReq.getName());
        if(codes.size() != 0){
            CodeDomainParser code = new CodeDomainParser();
            code.setName(codes.get(0).getName());
            code.setDescription(codes.get(0).getDescription());
            code.setCode(codes.get(0).getCode());
            code.setType(codes.get(0).getType());
            code.setTags(codeRetreiveService.convertToStringTags(codes.get(0).getTags()));
            return new ResponseEntity<CodeDomainParser>(code , HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(value = "/viewPlugin", method = RequestMethod.POST,headers = "content-type=application/x-www-form-urlencoded")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<List<CodeObjectRequest>> getFilesCodesPlugin( @ModelAttribute EclipsePlugin eclipse) {
        System.out.println("wa7shnui sotak y ebn el gzma");
        System.out.println(eclipse.getUsername() + " " + eclipse.getPassword() + " " + eclipse.getNumber());
        if(!userExist.checkUserExist(eclipse.getUsername() , eclipse.getPassword())){
            return new ResponseEntity<List<CodeObjectRequest>>(HttpStatus.NOT_FOUND);
        }
        List<CodeDomain> userCodes = null;
        try {
            int num = Integer.parseInt(eclipse.getNumber());
            Pageable range = new PageRequest(num,5);
            userCodes = codeRepository.findByusername(eclipse.getUsername() , range);
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

	@RequestMapping(value = "/view/{number}", method = RequestMethod.POST)
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<List<CodeObjectRequest>> getFilesCodes(@PathVariable String number ,
                                                                 @RequestBody ValidationObject validationObject) {
        System.out.println(" number of :: " + number);
        if(!userExist.checkUserExist(validationObject)){
            return new ResponseEntity<List<CodeObjectRequest>>(HttpStatus.NOT_FOUND);
        }
        List<CodeDomain> userCodes = null;
        try {
            int num = Integer.parseInt(number);
            Pageable range = new PageRequest(num,5);
            userCodes = codeRepository.findByusername(validationObject.getUsername() , range);
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

    @RequestMapping(value = "/getFile/{name}" , method = RequestMethod.POST)
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<CodeObjectRequest> getCode(@PathVariable String name ,
                                                     @RequestBody ValidationObject validationObject){

        if(!userExist.checkUserExist(validationObject)){
            return new ResponseEntity<CodeObjectRequest>(HttpStatus.NOT_FOUND);
        }
        try{
            List<CodeDomain> code = codeRepository.findByname(name);
            System.out.println("name of code " + name + " size of codes is " + code.size());
            if(code.size() == 0)
                return new ResponseEntity<CodeObjectRequest>(HttpStatus.NOT_FOUND);

            CodeObjectRequest codeObjectRequests = codeRetreiveService.changeToCodeObject(code.get(0));
            return new ResponseEntity<CodeObjectRequest>(codeObjectRequests , HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<CodeObjectRequest>(HttpStatus.NOT_FOUND);
        }
    }

}

