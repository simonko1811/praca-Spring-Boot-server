package net.javaguides.springboot.controller;

import java.util.List;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Book;
import net.javaguides.springboot.repository.LibraryRepository;
import net.javaguides.springboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookService service;
    @Autowired
    private LibraryRepository libraryRepository;
    @Autowired
    private BookService bookService;

    /*@GetMapping
    public List<Book> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        return service.findAll(page, size);
    }*/

    @GetMapping
    public List<Book> getAll() {
        return service.findAll();
    }

    @GetMapping("/available")
    public List<Book> getAvailable() {
        return service.findBorrowed(false);
    }

    @GetMapping("/borrowed")
    public List<Book> getBorrowed() {
        return service.findBorrowed(true);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        Book book = libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book "+ id +" not found"));
        return ResponseEntity.ok(book);
    }

    @PostMapping("/{id}") //TODO
    public ResponseEntity updateBook(@PathVariable int id, @RequestBody Book bookDetails) {
        Book book = libraryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book "+ id +" not found"));
        book.setId(bookDetails.getId());
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setBorrowed(bookDetails.getBorrowed());
        Book updatedBook = bookService.saveB(book);
        return ResponseEntity.ok(updatedBook);
    }

    @PostMapping("/")
    public ResponseEntity<String> saveBook(@RequestBody Book book) {
        bookService.save(book);
        return ResponseEntity.ok("Library saved successfully.");
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@RequestBody Book book) {
        bookService.delete(book);
        return ResponseEntity.ok("Deleted.");
    }
}
