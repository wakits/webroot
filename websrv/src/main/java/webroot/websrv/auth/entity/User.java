package webroot.websrv.auth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "userw")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String firstname;

    @NotNull
    @Column(nullable = false, unique = true)
    private String lastname;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String username;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private String password;
    
    @Null
    private String token;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public User(String firstName, String lastName, String username, String email, String password, Role role) {
    		this.setLastname(lastName);
        this.setFirstname(firstName);
    		this.setUsername(username);
        this.setEmail(email);
        this.setPassword(password);
        this.setRole(role);
    }

    public User(Long id, String firstname, String lastname, String username, String email, String password, Role role) {
        this(firstname, lastname, username, email, password, role);
        this.setId(id);
    }
    
    

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
