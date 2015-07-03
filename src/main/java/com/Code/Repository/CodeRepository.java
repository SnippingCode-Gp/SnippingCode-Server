package com.Code.Repository;

/**
 * Developed by Nasser
 */

import com.Code.Domain.Code;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface CodeRepository extends CrudRepository<Code, Long> {

    public List<Code> findByid(Long id);
    public List<Code> findBycode(String code);
    public List<Code> findByname(String name);
    public List<Code> findBytype(String type);
    public List<Code> findByusername(String username);
    public List<Code> findByusername(String username, Pageable pageable);
    //public List<CodeDomain> findByUsernameAndDescription(String username , String Description);

    @Query("SELECT p FROM Code p WHERE p.username LIKE :name and p.description LIKE %:searchTerm%")
    public List<Code> findByusernameAndsdescription(@Param("name") String username ,
                                              @Param("searchTerm") String description , Pageable pageable);

}
