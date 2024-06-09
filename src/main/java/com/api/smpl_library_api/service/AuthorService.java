package com.api.smpl_library_api.service;

import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public boolean update(Author author) {
        return authorRepository.update(author) > 0L;
    }

    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Integer id) {
        return authorRepository.delete(id) > 0L;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
