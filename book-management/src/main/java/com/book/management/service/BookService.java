package com.book.management.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.book.management.domain.Author;
import com.book.management.domain.Book;
import com.book.management.dto.AuthorDTO;
import com.book.management.dto.BookDTO;
import com.book.management.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setSummary(bookDTO.getSummary());
        if (bookDTO.getAuthor() != null) {
            book.setAuthor(authorService.getAuthorEntityById(bookDTO.getAuthor().getId()));
        }
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    // Method to check out a book
    public BookDTO checkOutBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book not found for id :: " + id));
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for checkout");
        }
        book.setAvailable(false);
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    // Method to return a book
    public BookDTO returnBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book not found for id :: " + id));
        book.setAvailable(true);
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    public Page<BookDTO> findBooksByTitle(String title, Pageable pageable) {
        return bookRepository.findByTitleContainingIgnoreCase(title, pageable)
                .map(this::mapToDTO);
    }

    public Page<BookDTO> findBooksByGenre(String genre, Pageable pageable) {
        return bookRepository.findByGenreContainingIgnoreCase(genre, pageable)
                .map(this::mapToDTO);
    }

    public Page<BookDTO> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::mapToDTO);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book not found for id :: " + id));
        return mapToDTO(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Book not found for id :: " + id));
        book.setTitle(bookDTO.getTitle());
        book.setGenre(bookDTO.getGenre());
        book.setPublicationYear(bookDTO.getPublicationYear());
        book.setSummary(bookDTO.getSummary());
        if (bookDTO.getAuthor() != null) {
            book.setAuthor(authorService.getAuthorEntityById(bookDTO.getAuthor().getId()));
        }
        book = bookRepository.save(book);
        return mapToDTO(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }



    private BookDTO mapToDTO(Book book) {
        AuthorDTO authorDTO = book.getAuthor() != null ?
                authorService.getAuthorById(book.getAuthor().getId()) : null;
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getGenre(),
                book.getPublicationYear(),
                book.getSummary(),
                authorDTO,
                book.isAvailable() // Include the isAvailable field
        );
    }

    @SuppressWarnings("unused")
	private Author getAuthorEntityById(Long id) {
        Author author = authorService.getAuthorEntityById(id);
        return author;
    }
}
