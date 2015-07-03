package com.Code.Domain;

/**
 * Developed by Nasser
 */

import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@RestController
@Entity

public class Code implements Serializable {

    public Code() {}

    @Id
    @GeneratedValue
    @Column(name="CodeDomain_ID")
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(unique=true , nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name="CODEDOMAIN_TAGS" , joinColumns={@JoinColumn(name="CodeDomain_ID")},
               inverseJoinColumns={@JoinColumn(name="TAGS_ID")})
    private Set<Tags> tags = new HashSet<Tags>();



    public String getDescription() {
        return description;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
