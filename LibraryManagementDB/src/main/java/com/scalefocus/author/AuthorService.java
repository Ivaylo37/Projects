package com.scalefocus.author;

import com.scalefocus.book.Book;
import com.scalefocus.book.BookService;
import com.scalefocus.exception.InvalidAuthorException;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private static final BookService bookService = new BookService();
    private static final AuthorAccessor authorAccessor = new AuthorAccessor();
    private static final AuthorMapper authorMapper = new AuthorMapper();

    public List<Author> getAllAuthors() {
        return authorAccessor.getAllAuthors();
    }

    public void addAuthor(String name) {
        Author author = new Author(name);
        String authorToString = authorMapper.mapAuthorToString(author);
        authorAccessor.addAuthor(authorToString);
    }

    public Author findAuthorByName(String nameToLookFor) throws InvalidAuthorException {
        List<Author> authors = getAllAuthors();
        Author foundAuthor = null;
        for (Author author : authors) {
            if (nameToLookFor.equalsIgnoreCase(author.getName())) {
                foundAuthor = author;
                break;
            }
        }
        if (foundAuthor == null){
            throw new InvalidAuthorException("Author not found");
        }
        return foundAuthor;
    }

    public void removeAuthor(Author authorToRemove) {
        List<Author> authors = getAllAuthors();
        Author foundAuthor = null;
        for (Author author : authors) {
            if (authorToRemove.getName().equalsIgnoreCase(author.getName())) {
                foundAuthor = author;
                break;
            }
        }
        if (foundAuthor == null){
            try {
                throw new InvalidAuthorException("Author not found");
            } catch (InvalidAuthorException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
        authorAccessor.deleteAuthor(authorToRemove.getName());
    }

    public List<Book> findAllBooks(String authorName){
        return bookService.findBookByAuthor(authorName);
    }

    public void editAuthor(String oldName, String newName){
        authorAccessor.editAuthor(oldName, newName);
    }

    public int getAuthorID(Author author){
        return authorAccessor.getIDbyAuthor(author);
    }

}