package com.Code.ObjectRequest;

import java.io.Serializable;

public class CodeDomainParser implements Serializable {

	public CodeDomainParser() {

	}

	private String name;
	private String type;
	private String code;
    private String description;
    private String tags;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


    public void printAll() {
        System.out.println("*************** code ***************");
        System.out.println("name{" + name + "} , type{" + type + "}");
        System.out.println("description{" + description + "}");
        System.out.println(code);
        System.out.println("*************** code ***************");
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
