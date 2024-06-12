package com.api.smpl_library_api.controller;

import com.api.smpl_library_api.dto.request.AuthorRequest;
import com.api.smpl_library_api.dto.UserDTO;
import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.service.AuthorService;
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
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/{authorId}")
    public ResponseEntity<Author> get(@PathVariable("authorId")
                                      @NotNull
                                      @Positive(message = "номер ресурса должен быть 1 и более")
                                      Integer authorId) {
        return authorService.findById(authorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getAll() {
        return authorService.findAll();
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody AuthorRequest authorRequest) {
        Optional<Author> author = authorService.save(authorRequest.getAuthor(), authorRequest.getUserDTO());
        if (author.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(authorRequest.getAuthor().getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(authorRequest.getAuthor());
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AuthorRequest authorRequest) {
        if (authorService.update(authorRequest.getAuthor(), authorRequest.getUserDTO())) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody AuthorRequest authorRequest) {
        authorService.update(authorRequest.getAuthor(), authorRequest.getUserDTO());
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> removeById(@PathVariable Integer authorId,
                                           @RequestBody UserDTO userDTO) {
        if (authorService.deleteById(authorId, userDTO)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
