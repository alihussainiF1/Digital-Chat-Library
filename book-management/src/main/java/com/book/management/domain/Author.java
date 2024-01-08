package com.book.management.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.Size;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "author", uniqueConstraints = {@UniqueConstraint(columnNames = {"givenName", "lastName"})})
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length=50)
	@javax.validation.constraints.NotNull
	@Size(min = 1, max = 50)
	private String givenName;

	@Column(length=50)
	@javax.validation.constraints.NotNull
	@Size(min = 1, max = 50)
	private String lastName;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Book> books = new ArrayList<>();

	public Author() {
	}

	public void addBook(Book book) {
		books.add(book);
		book.setAuthor(this);
	}

	public Author(String givenName, String lastName) {
		this.givenName = givenName;
		this.lastName = lastName;
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

	@Override
	public int hashCode() {
		return Objects.hash(givenName, lastName);
	}

	@Override
	public String toString() {
		return givenName + " " + lastName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		Author other = (Author) obj;
		return Objects.equals(givenName, other.givenName) && Objects.equals(lastName, other.lastName);
	}


    public Long getId() {
        return id;
    }
}
