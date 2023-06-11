package practice.authorization.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import practice.authorization.entity.Authority;
import practice.authorization.entity.User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return toGrantedAuthorities(user.getAuthorities());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static Collection<? extends GrantedAuthority> toGrantedAuthorities(List<Authority> authorities) {

        if (authorities != null) {
            return authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }

    public static List<Authority> toAuthorities(String username, Collection<? extends GrantedAuthority> grantedAuthorities) {

        if (grantedAuthorities != null) {
            return grantedAuthorities.stream()
                    .map(grantedAuthority ->  new Authority(username, grantedAuthority.getAuthority()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}
