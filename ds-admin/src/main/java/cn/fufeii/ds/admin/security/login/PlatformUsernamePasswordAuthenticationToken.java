package cn.fufeii.ds.admin.security.login;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author FuFei
 */
public class PlatformUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String platformUsername;

    public PlatformUsernamePasswordAuthenticationToken(String platformUsername, Object principal, Object credentials) {
        super(principal, credentials);
        this.platformUsername = platformUsername;
    }

    public PlatformUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public String getPlatformUsername() {
        return platformUsername;
    }

}
