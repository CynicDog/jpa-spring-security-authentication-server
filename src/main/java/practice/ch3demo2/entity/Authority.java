package practice.ch3demo2.entity;

import javax.persistence.*;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String authority;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public Authority() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
