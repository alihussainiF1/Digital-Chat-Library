package com.book.management.dto;

public class AuthorDTO {
    private Long id;
    private String givenName;
    private String lastName;

    // Default constructor
    public AuthorDTO() {
    }

    // Parameterized constructor
    public AuthorDTO(Long id, String givenName, String lastName) {
        this.id = id;
        this.givenName = givenName;
        this.lastName = lastName;
    }

    // Getters and setters
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}