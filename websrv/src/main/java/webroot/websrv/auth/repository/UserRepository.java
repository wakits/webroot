package webroot.websrv.auth.repository;
       
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import webroot.websrv.auth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	/**
     * Finds user by email
     * @param email to look for
     * @return user by given email
     */
    User findByEmail(String email);

    /**
     * Finds user by username
     * @param username to look for
     * @return user by given username
     */
    User findByUsername(String username);
    
    @Query("SELECT u FROM userw u where u.firstname like %:name% or u.lastname like %:name% or u.username like %:name%") 
    List<User> findByAnyname(@Param("name") String name);
    
    @Query("SELECT u FROM userw u where u.token = :token") 
    User getOneByToken(@Param("token") String token);
    
    @Transactional
    @Modifying
    @Query("UPDATE userw u SET u.token = :token WHERE u.username = :username")
    void saveToken(String username, String token);
    
}