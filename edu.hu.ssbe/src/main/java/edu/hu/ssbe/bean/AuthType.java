package edu.hu.ssbe.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthType implements GrantedAuthority {

    private String kind;

    @Override
    public String getAuthority() {
        return kind;
    }
}
