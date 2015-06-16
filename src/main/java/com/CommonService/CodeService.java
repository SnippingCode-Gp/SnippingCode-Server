package com.CommonService;

import com.Code.Domain.CodeDomain;
import com.Code.Domain.Tags;
import com.Code.ObjectRequest.CodeObject;
import com.Code.Repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @developed sngv
 */
@Service
public class CodeService {
    @Autowired
    private CodeRepository userCodeRepository;

    @Autowired
    private UserExist userExist;


    public Set<Tags> makeTags(List<String> tags){
        Set<Tags> ret = new HashSet<Tags>();

        for(int i = 0 ; i < tags.size() ; i++){
            Tags tag = new Tags();
            tag.setName(tags.get(i));
            ret.add(tag);
        }
        return ret;
    }

    public CodeDomain CreateCodeEntity(CodeObject codeObject) {
        if(!userExist.checkUserExist(codeObject.getUsername(), codeObject.getPassword()))
            return null;

        CodeDomain codeDomain = new CodeDomain();
        codeDomain.setCode(codeObject.getCode());
        codeDomain.setType(codeObject.getType());
        codeDomain.setName(codeObject.getName());
        codeDomain.setUsername(codeObject.getUsername());
        codeDomain.setTags(makeTags(codeObject.getTags()));
        codeDomain.setDescription(codeObject.getDescription());

        return codeDomain;
    }

    public CodeDomain updateCode(CodeDomain code, CodeObject codeObject) {
        if(!userExist.checkUserExist(codeObject.getUsername(), codeObject.getPassword()))
            return null;
        code.setTags(makeTags(codeObject.getTags()));
        code.setName(codeObject.getName());
        code.setDescription(codeObject.getDescription());
        code.setCode(codeObject.getCode());
        code.setType(codeObject.getType());
        return code;
    }
}
