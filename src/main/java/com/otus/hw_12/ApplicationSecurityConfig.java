package com.otus.hw_12;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private static String[] mvcPatterns = new String[]{"/", "/home"};

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
