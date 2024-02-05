package com.alvaro.config;

import com.alvaro.service.UserService;
import org.joinfaces.viewscope.ViewScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http, final MvcRequestMatcher.Builder mvc) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvc.pattern("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login.faces")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register.faces")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/jakarta.faces.resource/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**.faces")).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login.faces").permitAll().usernameParameter("tabView:name").passwordParameter("tabView:passwd")
                        .failureUrl("/login.faces?error=true").successForwardUrl("/index.faces")
                        .defaultSuccessUrl("/index.faces"))
                .logout(logout -> logout.logoutSuccessUrl("/login.faces").deleteCookies("JSESSIONID"));
        return http.build();
    }

    public DaoAuthenticationProvider authenticationProvider(final UserService userService) {
        final DaoAuthenticationProvider result = new DaoAuthenticationProvider();
        result.setUserDetailsService(userService);
        result.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        return result;
    }

    @Scope("prototype")
    @Bean
    @Autowired
    public MvcRequestMatcher.Builder mvc(final HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth, final UserService userService) {
        auth.authenticationProvider(this.authenticationProvider(userService));
    }

    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        final CustomScopeConfigurer result = new CustomScopeConfigurer();
        result.addScope("view", new ViewScope());
        return result;
    }

}
