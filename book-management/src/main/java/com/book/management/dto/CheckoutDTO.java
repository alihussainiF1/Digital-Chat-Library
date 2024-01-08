package com.book.management.dto;

import java.time.LocalDate;

public class CheckoutDTO {
    private Long id;
    private Long bookId;
    private LocalDate checkedOutDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    // Default Constructor
    public CheckoutDTO() {
    }

    // Parameterized Constructor
    public CheckoutDTO(Long id, Long bookId, LocalDate checkedOutDate, LocalDate dueDate, LocalDate returnedDate) {
        this.id = id;
        this.bookId = bookId;
        this.checkedOutDate = checkedOutDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(LocalDate checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }
}
