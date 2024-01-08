package com.book.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.book.management.domain.Checkout;

@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
}
