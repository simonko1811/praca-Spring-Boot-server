package net.javaguides.springboot.model;

import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "Library")
@XmlAccessorType(XmlAccessType.FIELD)
public class Library {

    @XmlElement(name = "Book")
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
