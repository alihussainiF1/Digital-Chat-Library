package com.book.management.domain;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "checkout")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    private LocalDate checkedOutDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    // Default Constructor
    public Checkout() {
    }

    // Parameterized Constructor
    public Checkout(Book book, LocalDate checkedOutDate, LocalDate dueDate) {
        this.book = book;
        this.checkedOutDate = checkedOutDate;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkout)) return false;
        Checkout checkout = (Checkout) o;
        return Objects.equals(getId(), checkout.getId()) &&
                Objects.equals(getBook(), checkout.getBook()) &&
                Objects.equals(getCheckedOutDate(), checkout.getCheckedOutDate()) &&
                Objects.equals(getDueDate(), checkout.getDueDate()) &&
                Objects.equals(getReturnedDate(), checkout.getReturnedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBook(), getCheckedOutDate(), getDueDate(), getReturnedDate());
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "id=" + id +
                ", book=" + book +
                ", checkedOutDate=" + checkedOutDate +
                ", dueDate=" + dueDate +
                ", returnedDate=" + returnedDate +
                '}';
    }
}
