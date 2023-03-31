package com.scalefocus.author;

public class AuthorMapper {

    Author mapStringToAuthor(String string){
        return new Author(string);
    }

    String mapAuthorToString(Author author){
        return author.getName();
    }
}
