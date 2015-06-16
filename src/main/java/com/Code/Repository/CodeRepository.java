package com.Code.Repository;

/**
 * Developed by Nasser
 */

import com.Code.Domain.CodeDomain;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface CodeRepository extends CrudRepository<CodeDomain, Long> {

    public List<CodeDomain> findByid(Long id);
    public List<CodeDomain> findBycode(String code);
    public List<CodeDomain> findByname(String name);
    public List<CodeDomain> findBytype(String type);
    public List<CodeDomain> findByusername(String username);
    public List<CodeDomain> findByusername(String username, Pageable pageable);
    //public List<CodeDomain> findByUsernameAndDescription(String username , String Description);

    @Query("SELECT p FROM CodeDomain p WHERE p.username LIKE :name and p.description LIKE %:searchTerm%")
    public List<CodeDomain> findByusernameAndsdescription(@Param("name") String username ,
                                              @Param("searchTerm") String description , Pageable pageable);

}
