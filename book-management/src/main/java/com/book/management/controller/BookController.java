package com.book.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book.management.dto.BookDTO;
import com.book.management.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Create a new book
    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        BookDTO createdBookDTO = bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBookDTO);
    }

    // Get all books with pagination
    @GetMapping
    public ResponseEntity<Page<BookDTO>> getAllBooks(Pageable pageable) {
        Page<BookDTO> bookDTOs = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(bookDTOs);
    }

    // Get a single book by ID
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO bookDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookDTO);
    }

    // Update a book
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
        BookDTO updatedBookDTO = bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(updatedBookDTO);
    }

    // Delete a book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Search books by title with pagination
    @GetMapping("/search")
    public ResponseEntity<Page<BookDTO>> searchBooksByTitle(
        @RequestParam(name = "title", required = false) String title,
        Pageable pageable
    ) {
        Page<BookDTO> bookDTOs = bookService.findBooksByTitle(title, pageable);
        return ResponseEntity.ok(bookDTOs);
    }

    // Search books by genre with pagination
    @GetMapping("/searchByGenre")
    public ResponseEntity<Page<BookDTO>> searchBooksByGenre(
        @RequestParam(name = "genre", required = false) String genre,
        Pageable pageable
    ) {
        Page<BookDTO> bookDTOs = bookService.findBooksByGenre(genre, pageable);
        return ResponseEntity.ok(bookDTOs);
    }
}
