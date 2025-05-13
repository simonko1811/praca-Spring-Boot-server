package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Book;
import net.javaguides.springboot.model.Library;
import net.javaguides.springboot.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Value("${library.page.size:5}")
    private int defaultPageSize;

    @Autowired
    private LibraryRepository repository;

    public List<Book> findAll() {
        List<Book> books = repository.loadLibrary().getBooks();
        return books.stream().toList();
    }

    public List<Book> findBorrowed(boolean borrowed) {
        return repository.loadLibrary().getBooks().stream()
                .filter(b -> b.isBorrowed() == borrowed)
                .toList();
    }

    public void save(Book book) {
        Library library = repository.loadLibrary();
        if (book.getId() <= 0) {
            int maxId = library.getBooks().stream()
                    .mapToInt(Book::getId)
                    .max()
                    .orElse(0);
            book.setId(maxId + 1);
        } else {
            // odstráň knihu s rovnakým ID, ak existuje (ide o update)
            library.getBooks().removeIf(b -> b.getId() == book.getId());
        }
        library.getBooks().add(book);
        repository.saveLibrary(library);
    }

    public void delete(Book book) {
        Library library = repository.loadLibrary();
        library.getBooks().removeIf(b -> b.getId() == book.getId());
        repository.saveLibrary(library);
    }

    public Book saveB(Book book) {
        repository.save(book);
        return book;
    }

    /*public void delete(int id) {
        Library library = repository.loadLibrary();
        library.getBooks().removeIf(b -> b.getId() == id);
        repository.saveLibrary(library);
    }*/

    public Optional<Book> findById(int id) {
        return repository.loadLibrary().getBooks().stream()
                .filter(b -> b.getId() == id)
                .findFirst();
    }
}
