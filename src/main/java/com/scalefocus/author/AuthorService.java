package com.scalefocus.author;

import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private static final BookService bookService = new BookService();
    private static final AuthorAccessor authorAccessor = new AuthorAccessor();
    private static final AuthorMapper authorMapper = new AuthorMapper();

    public List<Author> getAllAuthors() {
        List<String> authorStrings = authorAccessor.readAllAuthors();
        List<Author> authors = new ArrayList<>();
        for (String authorString : authorStrings) {
            Author author = authorMapper.mapStringToAuthor(authorString);
            authors.add(author);
        }
        return authors;
    }

    public void addAuthor(String name) {
        Author author = new Author(name);
        String authorToString = authorMapper.mapAuthorToString(author);
        authorAccessor.addAuthor(authorToString);
    }

    public Author findAuthorByName(String nameToLookFor) {
        List<Author> authors = getAllAuthors();
        for (Author author : authors) {
            if (nameToLookFor.equalsIgnoreCase(author.getName())) {
                return author;
            }
        }
        return null;
    }

    public void removeAuthor(Author authorToRemove) {
        String toDelete = authorMapper.mapAuthorToString(authorToRemove).trim();
        List<String> authorStrings = authorAccessor.readAllAuthors();
        if (authorStrings.contains(toDelete)) {
            authorStrings.remove(toDelete);
        }
        String newString = new String();
        for (String string : authorStrings) {
            newString = newString.concat(string);
            newString = newString.concat("\n");
        }
        authorAccessor.deleteAuthor(newString);
    }

    public List<Book> findAllBooks(String authorName){
        return bookService.findBookByAuthor(authorName);
    }
}