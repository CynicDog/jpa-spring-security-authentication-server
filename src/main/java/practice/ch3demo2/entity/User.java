package practice.ch3demo2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private int enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Authority> authorities;

    public User(String username, String password, List<Authority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.enabled = 1;

        if (authorities != null) {
            for (Authority authority : authorities) {
                authority.setUser(this);
            }
        }
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() { }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        if (authorities == null) {
            authorities = new ArrayList<>();
        }
        authorities.add(authority);
        authority.setUser(this);
    }
}
