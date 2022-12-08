package edu.hu.ssbe.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account implements UserDetails {
    private String id;
    private String kind;
    private String username;
    private String credentials;
    private String password;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdon;
    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastupdate;
    private String firstname;
    private String lastname;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String country;
    private int zipcode;
    private String email;
    private String phone;
    private String status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<AuthType> authTypes = new ArrayList<>();
        AuthType authType = AuthType.builder().kind(kind).build();
        authTypes.add(authType);
        return (Collection<? extends GrantedAuthority>) authTypes;
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
}
