package yavs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Qualifier("userServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/user/**")
//                .hasAuthority(Permission.WRITE.getPermissionAsText())
//                .antMatchers("/")
//                .hasAnyRole("USER")
//                .permitAll()
//                .antMatchers(HttpMethod.POST,"/user/**")
//                .hasAuthority(Permission.WRITE.getPermissionAsText())
//                .antMatchers(HttpMethod.POST)
//                .hasRole(Role.ADMIN.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin();
//                .loginPage("/auth/login");
//                .httpBasic();
        return http.build();
    }



//    @Bean
//    @Autowired
//    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        UserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                .password(bCryptPasswordEncoder.encode("user"))
//                .authorities(Permission.READ.getPermissionAsText())
//                .roles(Role.USER.name())
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(bCryptPasswordEncoder.encode("admin"))
//                .roles(Role.ADMIN.name())
//                .authorities(Role.ADMIN.getAuthorities())
//                .build());
//
//        UserDetailsManager manager1 = new JdbcUserDetailsManager();
//        manager1.createUser(User.withUsername("user")
//                .password(bCryptPasswordEncoder.encode("user"))
//                .authorities(Permission.READ.getPermissionAsText())
//                .roles(Role.USER.name())
//                .build());
//        return manager;
//    }

//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }

    @Bean
    protected AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder(12);
    }
}
