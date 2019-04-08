package com.otus.hw_12.services;

import com.otus.hw_12.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public long getCount() {
        return authorRepository.count();
    }

}
