package practice.ch3demo2.entity;

import javax.persistence.*;

@Entity
@Table(name = "otps")
public class Otp {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String otpCode;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Otp() { }

    public Otp(String username, String otp) {
        this.username = username;
        this.otpCode = otp;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otp) {
        this.otpCode = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
