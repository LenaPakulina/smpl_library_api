package com.api.smpl_library_api.controller;

import com.api.smpl_library_api.model.Author;
import com.api.smpl_library_api.service.AuthorService;
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
    public ResponseEntity<Author> save(@RequestBody Author author) {
        authorService.save(author);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(uri)
                .body(author);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Author author) {
        if (authorService.update(author)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void change(@RequestBody Author author) {
        authorService.update(author);
    }

    @DeleteMapping("/{authorId}")
    public ResponseEntity<Void> removeById(@PathVariable Integer authorId) {
        if (authorService.deleteById(authorId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
