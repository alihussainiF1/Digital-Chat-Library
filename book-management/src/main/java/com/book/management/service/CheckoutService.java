package com.book.management.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.book.management.domain.Book;
import com.book.management.domain.Checkout;
import com.book.management.dto.CheckoutDTO;
import com.book.management.repository.BookRepository;
import com.book.management.repository.CheckoutRepository;

import jakarta.transaction.Transactional;

@Service
public class CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final BookRepository bookRepository;
    private final EmailService emailService;  // Inject EmailService

    public CheckoutService(CheckoutRepository checkoutRepository, 
                           BookRepository bookRepository,
                           EmailService emailService) {  
        this.checkoutRepository = checkoutRepository;
        this.bookRepository = bookRepository;
        this.emailService = emailService;
    }

    @Transactional
    public CheckoutDTO createCheckout(CheckoutDTO checkoutDTO) {
        Book book = bookRepository.findById(checkoutDTO.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found for id :: " + checkoutDTO.getBookId()));
        
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for checkout");
        }

        Checkout checkout = new Checkout();
        checkout.setBook(book);
        checkout.setCheckedOutDate(checkoutDTO.getCheckedOutDate());
        checkout.setDueDate(checkoutDTO.getDueDate());
        checkout = checkoutRepository.save(checkout);

        // Update book availability
        book.setAvailable(false);
        bookRepository.save(book);

        // Send an email notification
        emailService.sendEmail("Book Checked Out", "The book '" + book.getTitle() + "' has been checked out.");
//      emailService.sendEmail();
        
        return mapToDTO(checkout);
    }

    public List<CheckoutDTO> getAllCheckouts() {
        return checkoutRepository.findAll().stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    

    private CheckoutDTO mapToDTO(Checkout checkout) {
        return new CheckoutDTO(
            checkout.getId(),
            checkout.getBook().getId(),
            checkout.getCheckedOutDate(),
            checkout.getDueDate(),
            checkout.getReturnedDate()
        );
    }
}
