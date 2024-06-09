package com.api.smpl_library_api.service;

import com.api.smpl_library_api.dto.BookDTO;
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
    
    private final AuthorRepository authorRepository;

    @Transactional
    public Book save(Book book, List<Author> authors) {
        Book result = bookRepository.save(book);
        for (Author author : authors) {
            authorRepository.createConnectionAuthorToBook(author, book);
        }
        return result;
    }

    @Transactional
    public boolean update(Book book) {
        return bookRepository.update(book) > 0L;
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(Integer id) {
        return bookRepository.delete(id) > 0L;
    }

    public List< Book> findAll() {
        return bookRepository.findAll();
    }

    public List<BookDTO> findByCriterion(String criteria) {
        return bookRepository.findByTitleContainingAndAuthor(criteria);
    }
}
