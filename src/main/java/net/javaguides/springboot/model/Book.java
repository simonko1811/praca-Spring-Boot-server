package net.javaguides.springboot.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;

/*public class Book {
    private String title;
    private String author;

    public Book() {}

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
}*/

@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "Name")
    private String title;

    @XmlElement(name = "Author")
    private String author;

    @XmlElement(name = "Borrowed")
    private Borrowed borrowed;

    public Book() {}

    public Book(int id, String title, String author, Borrowed borrowed) {
        super();
        this.id = id;
        this.title = title;
        this.author = author;
        this.borrowed = borrowed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Borrowed getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Borrowed borrowed) {
        this.borrowed = borrowed;
    }

    public boolean isBorrowed() {
        return borrowed != null && borrowed.getFirstName() != null && !borrowed.getFirstName().isBlank();
    }
}
