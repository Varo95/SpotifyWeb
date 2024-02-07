package com.alvaro.service;

import com.alvaro.model.orm.SPAuthority;
import com.alvaro.model.orm.User;
import com.alvaro.repositories.SPAuthorityRepository;
import com.alvaro.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service("userService")
@Slf4j
public class UserService implements UserDetailsService, UserDetailsPasswordService, CommandLineRunner {

    private final UserRepository userRepository;
    private final SPAuthorityRepository spAuthorityRepository;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Autowired
    public UserService(final UserRepository userRepository, final AuthenticationManagerBuilder auth, SPAuthorityRepository spAuthorityRepository) {
        this.userRepository = userRepository;
        this.spAuthorityRepository = spAuthorityRepository;
        auth.authenticationProvider(this.authenticationProvider(this));
    }

    private DaoAuthenticationProvider authenticationProvider(final UserService userService) {
        final DaoAuthenticationProvider result = new DaoAuthenticationProvider();
        result.setUserDetailsService(userService);
        result.setPasswordEncoder(this.passwordEncoder);
        result.setUserDetailsPasswordService(userService);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return this.userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User register(final User user, SPAuthority authority) {
        user.setPasswd(this.passwordEncoder.encode(user.getPasswd()));
        if (authority == null) {
            authority = this.userRepository.findAll().isEmpty() ? this.spAuthorityRepository.findByAuthority("ROLE_ADMIN").orElseThrow() : this.spAuthorityRepository.findByAuthority("ROLE_USER").orElseThrow();
        } else {
            authority = this.spAuthorityRepository.findById(authority.getId()).orElseThrow();
        }
        user.setAuthorities(List.of(authority));
        return this.userRepository.save(user);
    }

    @Override
    public UserDetails updatePassword(UserDetails user, final String newPassword) {
        if (user instanceof User u) {
            u.setPasswd(this.passwordEncoder.encode(newPassword));
        } else {
            final User u = new User();
            u.setName(user.getUsername());
            u.setPasswd(this.passwordEncoder.encode(newPassword));
            final List<SPAuthority> authorities = new CopyOnWriteArrayList<>();
            for (final GrantedAuthority authority : user.getAuthorities()) {
                final SPAuthority a = this.spAuthorityRepository.findByAuthority(authority.getAuthority()).orElseThrow();
                authorities.add(a);
            }
            u.setAuthorities(authorities);
            u.setDisabled(!user.isEnabled());
            user = u;
        }
        return this.userRepository.save((User) user);
    }

    public List<SPAuthority> findAllAuthorities() {
        return this.spAuthorityRepository.findAll();
    }

    @Override
    public void run(final String... args) {
        final List<SPAuthority> authorities = this.findAllAuthorities();
        if (authorities.isEmpty()) {
            authorities.add(this.spAuthorityRepository.save(new SPAuthority("ROLE_USER")));
            authorities.add(this.spAuthorityRepository.save(new SPAuthority("ROLE_ADMIN")));
            log.info("Authorities created: {}", authorities);
        }
    }
}
