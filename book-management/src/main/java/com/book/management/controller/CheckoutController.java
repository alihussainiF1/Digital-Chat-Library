package com.book.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.management.dto.CheckoutDTO;
import com.book.management.service.CheckoutService;

@RestController
@RequestMapping("/api/checkouts")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutDTO> createCheckout(@RequestBody CheckoutDTO checkoutDTO) {
        CheckoutDTO createdCheckoutDTO = checkoutService.createCheckout(checkoutDTO);
        return ResponseEntity.ok(createdCheckoutDTO);
    }

    @GetMapping
    public ResponseEntity<List<CheckoutDTO>> getAllCheckouts() {
        List<CheckoutDTO> checkoutDTOs = checkoutService.getAllCheckouts();
        return ResponseEntity.ok(checkoutDTOs);
    }


}
