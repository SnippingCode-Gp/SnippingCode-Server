package com.Code.Service;

import com.Code.Domain.CodeDomain;
import com.Code.Domain.Tags;
import com.Code.ObjectRequest.CodeObjectRequest;
import com.Code.Repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sngv on 12/03/15.
 */
@RestController
@Service
public class CodeRetreiveService {

    @Autowired
    CodeRepository codeRepository;

    @Transactional
    public List<CodeObjectRequest> changeFromDomainToRequestObj(List<CodeDomain> codeDomains ){
        try{
            List<CodeObjectRequest> ret = new ArrayList<CodeObjectRequest>();
            for(CodeDomain code : codeDomains){
                CodeObjectRequest codeObjectRequest= new CodeObjectRequest();
                codeObjectRequest.setName(code.getName());
                codeObjectRequest.setDescription(code.getDescription());
                codeObjectRequest.setCode(code.getCode());
                codeObjectRequest.setId(code.getId());
                codeObjectRequest.setType(code.getType());
                codeObjectRequest.setUsername(code.getUsername());
                codeObjectRequest.setTags(convertToStringTags(codeRepository.findByid(code.getId()).get(0).getTags()));
                ret.add(codeObjectRequest);
            }
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CodeObjectRequest changeToCodeObject(CodeDomain codeDomain){
        CodeObjectRequest ret = new CodeObjectRequest();
        ret.setUsername(codeDomain.getUsername());
        ret.setType(codeDomain.getType());
        ret.setId(codeDomain.getId());
        ret.setCode(codeDomain.getCode());
        ret.setDescription(codeDomain.getDescription());
        ret.setName(codeDomain.getName());
        ret.setTags(convertToStringTags(codeDomain.getTags()));
        return ret;
    }


    public String convertToStringTags(Set<Tags> tags) {
        if(tags == null)
            return "";
        StringBuilder string = new StringBuilder("");
        for(Tags tag : tags){
            string.append(tag.getName() + ",");
        }
        return string.toString();
    }
}
