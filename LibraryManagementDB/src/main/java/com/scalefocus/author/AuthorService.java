package com.scalefocus.author;

import com.scalefocus.exception.InvalidAuthorException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorAccessor authorAccessor ;

    public AuthorService(AuthorAccessor authorAccessor) {
        this.authorAccessor = authorAccessor;
    }

    public List<Author> getAllAuthors() {
        return authorAccessor.getAllAuthors();
    }

    public void addAuthor(String name) {
        authorAccessor.addAuthor(name);
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
    public Author editAuthor(String oldName, String newName){
        return authorAccessor.editAuthor(oldName, newName);
    }
}