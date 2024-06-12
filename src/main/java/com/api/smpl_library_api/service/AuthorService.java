package com.api.smpl_library_api.service;

import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    private final SecurityConnector securityConnector;

    @Transactional
    public Optional<Author> save(Author author, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return Optional.of(authorRepository.save(author));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean update(Author author, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return authorRepository.update(author) > 0L;
        }
        return false;
    }

    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Integer id, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return authorRepository.delete(id) > 0L;
        }
        return false;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }
}
