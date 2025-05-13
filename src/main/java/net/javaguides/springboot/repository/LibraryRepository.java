package net.javaguides.springboot.repository;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import net.javaguides.springboot.model.Book;
import net.javaguides.springboot.model.Library;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.*;
import java.util.Optional;

@Repository
public class LibraryRepository {

    private static final String XML_FILE = "Library.xml";
    private final Path xmlPath = Paths.get("src/main/resources/" + XML_FILE);

    /*private final LibraryRepository repository;

    public LibraryRepository(LibraryRepository repository) {
        this.repository = repository;
    }*/

    public synchronized Library loadLibrary() {
        try (InputStream is = Files.newInputStream(xmlPath)) {
            Reader rd = new InputStreamReader(is,"UTF-8");
            JAXBContext context = JAXBContext.newInstance(Library.class);
            return (Library) context.createUnmarshaller().unmarshal(rd);
        } catch (Exception e) {
            e.printStackTrace();
            return new Library();
        }
    }

    public synchronized void saveLibrary(Library library) {
        try (OutputStream os = Files.newOutputStream(xmlPath)) {
            Writer writer = new OutputStreamWriter(os, "UTF-8");
            JAXBContext context = JAXBContext.newInstance(Library.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(library, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized Optional<Book> findById(int id) {
        Library library = loadLibrary();
        return library.getBooks()
                .stream()
                .filter(book -> book.getId() == id)
                .findFirst();
    }

    public synchronized void save(Book book) {
        Library library = loadLibrary();
        library.getBooks().removeIf(b -> b.getId() == book.getId());
        // pridá knihu a uloží XML
        library.getBooks().add(book);
        saveLibrary(library);
    }
}
