package webroot.websrv.auth.repository;
       
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
     * Finds user by name
     * @param name to look for
     * @return user by given name
     */
    User findByName(String name);
}