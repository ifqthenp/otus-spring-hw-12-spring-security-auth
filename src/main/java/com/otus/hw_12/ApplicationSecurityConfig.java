package com.otus.hw_12;

import com.otus.hw_12.auth.LibraryUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private static String[] mvcPatterns = new String[]{"/", "/home"};

    private final LibraryUserDetailsService userDetailsService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .mvcMatchers(HttpMethod.GET, mvcPatterns).permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic();
    }

}
