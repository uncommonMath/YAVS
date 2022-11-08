package yavs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (userService.validate(authentication.getPrincipal().toString())) {
            authentication.setAuthenticated(true);
            var user = userService.loadUserByUsername(authentication.getPrincipal().toString());
            var pre = new PreAuthenticatedAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
            pre.setAuthenticated(true);
            return pre;
        } else
            return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }

    private Authentication getValidationToken(String customToken) {
        // call auth service to check validity of token
        System.out.println("в провайдере в валидации");
//        if (userService.validate(customToken))
        if (true) {
            var authorizedUser = new PreAuthenticatedAuthenticationToken("AuthenticatedUser",
                    customToken,
                    new ArrayList<>(userService.loadUserByUsername("100").getAuthorities()));
            authorizedUser.setAuthenticated(true);
            SecurityContextHolder.getContext().setAuthentication(authorizedUser);

            return authorizedUser;
        }
        else
            throw new AccessDeniedException("Invalid authentication token");

    }
}
