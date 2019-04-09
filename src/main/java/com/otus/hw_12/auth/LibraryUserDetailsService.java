package com.otus.hw_12.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final User user = this.userRepository.findByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("Cannot find username: " + username);
        }
        return new LibraryUserPrincipal(user);
    }

}
