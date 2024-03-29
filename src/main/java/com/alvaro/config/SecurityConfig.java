package com.alvaro.config;

import jakarta.servlet.ServletContext;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.Redirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends HttpConfigurationProvider {

    @Override
    public org.ocpsoft.rewrite.config.Configuration getConfiguration(final ServletContext t) {
        return ConfigurationBuilder.begin()
                .addRule()
                .when(Direction.isInbound().and(Path.matches("/")))
                .perform(Redirect.temporary("/register.faces"));
    }

    @Override
    public int priority() {
        return 10;
    }

    @Bean
    public SecurityFilterChain configure(final HttpSecurity http, final MvcRequestMatcher.Builder mvc) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvc.pattern("/")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/login.faces")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register.faces")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/jakarta.faces.resource/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/fonts/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/images/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**.faces")).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login.faces").permitAll().usernameParameter("name").passwordParameter("passwd")
                        .failureUrl("/login.faces?error=true").successForwardUrl("/index.faces")
                        .defaultSuccessUrl("/index.faces"))
                .logout(logout -> logout.logoutSuccessUrl("/login.faces").deleteCookies("JSESSIONID"));
        return http.build();
    }

    @Scope("prototype")
    @Bean
    @Autowired
    public MvcRequestMatcher.Builder mvc(final HandlerMappingIntrospector hmp) {
        return new MvcRequestMatcher.Builder(hmp);
    }

}
