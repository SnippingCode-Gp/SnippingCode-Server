package com.Code.Service;

import com.Code.Domain.Code;
import com.Code.Domain.Tags;
import com.Code.ObjectRequest.CodeObject;
import com.Code.ObjectRequest.CodeObjectRequest;
import com.Code.Repository.CodeRepository;
import com.User.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @developed sngv
 */
@Service
public class CodeService {
    @Autowired
    private CodeRepository codeRepo;

    @Autowired
    private UserService userService;


    public Set<Tags> makeTags(List<String> tags){
        Set<Tags> ret = new HashSet<Tags>();

        for(int i = 0 ; i < tags.size() ; i++){
            Tags tag = new Tags();
            tag.setName(tags.get(i));
            ret.add(tag);
        }
        return ret;
    }

    public Code CreateCodeEntity(CodeObject codeObject) {
        if(!userService.checkUserExist(codeObject.getUsername(), codeObject.getPassword()))
            return null;

        Code code = new Code();
        code.setCode(codeObject.getCode());
        code.setType(codeObject.getType());
        code.setName(codeObject.getName());
        code.setUsername(codeObject.getUsername());
        code.setTags(makeTags(codeObject.getTags()));
        code.setDescription(codeObject.getDescription());

        return code;
    }

    public Code updateCode(Code code, CodeObject codeObject) {
        if(!userService.checkUserExist(codeObject.getUsername(), codeObject.getPassword()))
            return null;
        code.setTags(makeTags(codeObject.getTags()));
        code.setName(codeObject.getName());
        code.setDescription(codeObject.getDescription());
        code.setCode(codeObject.getCode());
        code.setType(codeObject.getType());
        return code;
    }

    @Transactional
    public List<CodeObjectRequest> changeFromDomainToRequestObj(List<Code> codes){
        try{
            List<CodeObjectRequest> ret = new ArrayList<CodeObjectRequest>();
            for(Code code : codes){
                CodeObjectRequest codeObjectRequest= new CodeObjectRequest();
                codeObjectRequest.setName(code.getName());
                codeObjectRequest.setDescription(code.getDescription());
                codeObjectRequest.setCode(code.getCode());
                codeObjectRequest.setId(code.getId());
                codeObjectRequest.setType(code.getType());
                codeObjectRequest.setUsername(code.getUsername());
                codeObjectRequest.setTags(convertToStringTags(codeRepo.findByid(code.getId()).get(0).getTags()));
                ret.add(codeObjectRequest);
            }
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public CodeObjectRequest changeToCodeObject(Code code){
        CodeObjectRequest ret = new CodeObjectRequest();
        ret.setUsername(code.getUsername());
        ret.setType(code.getType());
        ret.setId(code.getId());
        ret.setCode(code.getCode());
        ret.setDescription(code.getDescription());
        ret.setName(code.getName());
        ret.setTags(convertToStringTags(code.getTags()));
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
