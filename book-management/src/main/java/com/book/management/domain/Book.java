package com.book.management.domain;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    private boolean isAvailable;
    @Version
    private Long version;
	@Column(length = 100, unique = true)
	@NotNull
	@Size(min = 1, max = 100)
	private String title;

	@Column(length = 50)
	@NotNull
	@Size(min  = 1, max = 50)
	private String genre;

	@NotNull
	private Integer publicationYear;

	@Column(length = 1000)
	@NotNull
	@Size(min  = 1, max = 1000)
	private String summary;

	@ManyToOne(fetch = FetchType.LAZY)
	private Author author;

	public Book() {

	}

	public Author getAuthor() {
		return author;
	}
    // Getters and Setters
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

	public void setAuthor(Author author2) {
		this.author = author2;
	}

	public Book(String title, String genre, Integer publicationYear) {

		this.title = title;
		this.genre = genre;
		this.publicationYear = publicationYear;
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

	@Override
	public int hashCode() {
		return Objects.hash(genre, publicationYear, summary, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Book other = (Book) obj;
		return Objects.equals(genre, other.genre) && Objects.equals(publicationYear, other.publicationYear)
				&& Objects.equals(summary, other.summary) && Objects.equals(title, other.title);
	}

	@Override
	public String toString() {
		return title;
	}

    public Long getId() {
        return id;
    }

}
