package com.efub.cafetimes.domain;

import com.efub.cafetimes.dto.OAuthAttributesDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@NoArgsConstructor
@Getter
public class UserPrincipal implements UserDetails, OAuth2User {
    private User user;
    private List<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(User user, List<GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.user = user;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    /**
     * OAuth2 로그인시 사용
     */
    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        return new UserPrincipal(user, List.of(() -> user.getRole()), attributes);
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user, List.of(() -> user.getRole()), new HashMap<>());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return String.valueOf(user.getEmail());
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

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public <A> A getAttribute(String name) {
        return (A) attributes.get(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableList(authorities);
    }

    @Override
    public String getName() {
        return String.valueOf(user.getUserId());
    }
}
