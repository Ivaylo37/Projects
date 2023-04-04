package com.scalefocus.author;

public class AuthorMapper {
    private static final AuthorService authorService = new AuthorService();

    Author mapStringToAuthor(String string) {
        Author author = new Author(string);
        author.setWrittenBooks(authorService.findAllBooks(author.getName()));
        return author;
    }

    String mapAuthorToString(Author author) {
        return author.getName();
    }
}
