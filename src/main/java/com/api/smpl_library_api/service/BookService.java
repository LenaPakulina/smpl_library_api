package com.api.smpl_library_api.service;

import com.api.smpl_library_api.dto.BookDTO;
import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.model. Book;
import com.api.smpl_library_api.repository.AuthorRepository;
import com.api.smpl_library_api.repository. BookRepository;
import com.api.smpl_library_api.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {
    
    private final BookRepository bookRepository;

    private final SecurityConnector securityConnector;

    @Transactional
    public Optional<Book> save(Book book, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return Optional.of(bookRepository.save(book));
        }
        return Optional.empty();
    }

    @Transactional
    public boolean update(Book book, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return bookRepository.update(book) > 0L;
        }
        return false;
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Integer id, UserDTO userDTO) {
        if (securityConnector.checkUser(userDTO)) {
            return bookRepository.delete(id) > 0L;
        }
        return false;
    }

    public List< Book> findAll() {
        return bookRepository.findAll();
    }

    public List<BookDTO> findByCriterion(String criteria) {
        return bookRepository.findByTitleContainingAndAuthor(criteria);
    }
}
