package com.scalefocus.author;

import com.scalefocus.requests.AuthorRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorAccessor authorAccessor;

    public AuthorController(AuthorService authorService, AuthorAccessor authorAccessor) {
        this.authorService = authorService;
        this.authorAccessor = authorAccessor;
    }
    @GetMapping("/index")
    public ResponseEntity<String> getIndex(){
        return ResponseEntity.ok("Welcome to");
    }
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> printAllAuthors() {
        List<Author> authorList = authorService.getAllAuthors();
        return ResponseEntity.ok(authorList);
    }
    @PostMapping("/authors")
    public ResponseEntity<Void> addAuthor(@RequestBody AuthorRequest authorRequest) {
        authorService.addAuthor(authorRequest.getName());
        return ResponseEntity.status(201).build();
    }
    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthorWithResponse(
            @RequestBody AuthorRequest authorRequest,@PathVariable int id){
        Author oldAuthor = authorAccessor.getAuthorByID(id);
        Author author = authorService.editAuthor(oldAuthor.getName(), authorRequest.getName());
        return ResponseEntity.ok(author);
    }
}