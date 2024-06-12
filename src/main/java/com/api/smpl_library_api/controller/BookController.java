package com.api.smpl_library_api.controller;

import com.api.smpl_library_api.dto.BookDTO;
import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.dto.request.BookRequest;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.model.Book;
import com.api.smpl_library_api.service.BookService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Validated
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> get(@PathVariable("bookId")
                                    @NotNull
                                    @Positive(message = "номер ресурса должен быть 1 и более")
                                    Integer bookId) {
        return bookService.findById(bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll() {
        return bookService.findAll();
    }

    @PostMapping
    public ResponseEntity<Book> save(@RequestBody BookRequest bookRequest) {
        Optional<Book> book = bookService.save(bookRequest.getBook(), bookRequest.getUserDTO());
        if (book.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookRequest.getBook().getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(bookRequest.getBook());
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody BookRequest bookRequest) {
        if (bookService.update(bookRequest.getBook(), bookRequest.getUserDTO())){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody BookRequest bookRequest) {
        bookService.update(bookRequest.getBook(), bookRequest.getUserDTO());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> removeById(@PathVariable Integer bookId,
                                           @RequestBody UserDTO userDTO) {
        if (bookService.deleteById(bookId, userDTO)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/filter")
    public List<BookDTO> getBooksByNameAndAuthor(@RequestParam String name) {
        return bookService.findByCriterion(name);
    }
}


