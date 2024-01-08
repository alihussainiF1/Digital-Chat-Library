package com.book.management.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String genre;
    private Integer publicationYear;
    private String summary;
    private AuthorDTO author;
    private boolean isAvailable; // New field to indicate availability

    // Default constructor
    public BookDTO() {
    }

    // Parameterized constructor
    public BookDTO(Long id, String title, String genre, Integer publicationYear, String summary, AuthorDTO author, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.summary = summary;
        this.author = author;
        this.isAvailable = isAvailable;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}