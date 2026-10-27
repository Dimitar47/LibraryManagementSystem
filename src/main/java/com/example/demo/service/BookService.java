
package com.example.demo.service;

import com.example.demo.Author;
import com.example.demo.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository,
                       AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Book not found with id: " + id));
    }

    public Book saveBook(Book book) {
        if (book.getAuthor() == null ||
                book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author ID is required");
        }

        Author author = authorRepository.findById(
                book.getAuthor().getId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Author not found with id: "
                                + book.getAuthor().getId()
                )
        );

        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
