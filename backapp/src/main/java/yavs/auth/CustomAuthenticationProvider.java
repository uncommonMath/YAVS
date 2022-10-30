package yavs.auth;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;

    public CustomAuthenticationProvider(@Qualifier("userServiceImpl") UserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String customToken = (String) authentication.getPrincipal();
        // Custom logic to validate the token
        System.out.println("в провайдере в аутентификации");
        return getValidationToken(customToken);
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

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
