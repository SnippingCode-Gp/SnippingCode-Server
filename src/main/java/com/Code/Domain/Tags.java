package com.Code.Domain;

import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sngv on 11/03/15.
 */
@RestController
@Entity
@Table(name = "Tags")
public class Tags implements Serializable {

    @Id
    @GeneratedValue
    @Column(name="TAGS_ID")
    private long id;

    @Column(nullable = true)
    @ManyToMany(mappedBy="tags")
    private Set<CodeDomain> codeDomain = new HashSet<CodeDomain>();

    @Column(nullable = false)
    private String name;

    public Set<CodeDomain> getCodeDomain() {
        return codeDomain;
    }

    public void setCodeDomain(Set<CodeDomain> codeDomain) {
        this.codeDomain = codeDomain;
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
}
